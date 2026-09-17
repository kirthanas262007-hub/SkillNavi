package com.skillnavi.service;

import com.skillnavi.dao.JobSkillDAO;
import com.skillnavi.dao.LearningResourceDAO;
import com.skillnavi.dao.StudentSkillDAO;
import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.JobRole;
import com.skillnavi.model.JobSkill;
import com.skillnavi.model.LearningResource;
import com.skillnavi.model.RoadmapItem;
import com.skillnavi.model.SkillGap;
import com.skillnavi.model.Student;
import com.skillnavi.model.StudentSkill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CareerAnalysisService {
    private final StudentSkillDAO studentSkillDAO;
    private final JobSkillDAO jobSkillDAO;
    private final LearningResourceDAO learningResourceDAO;
    private final SkillGapAnalyzer skillGapAnalyzer;
    private final ReadinessCalculator readinessCalculator;
    private final RoadmapGenerator roadmapGenerator;

    public CareerAnalysisService(StudentSkillDAO studentSkillDAO, JobSkillDAO jobSkillDAO,
                                 LearningResourceDAO learningResourceDAO,
                                 SkillGapAnalyzer skillGapAnalyzer,
                                 ReadinessCalculator readinessCalculator,
                                 RoadmapGenerator roadmapGenerator) {
        this.studentSkillDAO = studentSkillDAO;
        this.jobSkillDAO = jobSkillDAO;
        this.learningResourceDAO = learningResourceDAO;
        this.skillGapAnalyzer = skillGapAnalyzer;
        this.readinessCalculator = readinessCalculator;
        this.roadmapGenerator = roadmapGenerator;
    }

    public AnalysisResult analyze(Student student, JobRole targetJobRole) {
        validateAnalysisRequest(student, targetJobRole);

        List<StudentSkill> studentSkills = studentSkillDAO.findByStudentId(student.getStudentId());
        List<JobSkill> requiredSkills = jobSkillDAO.findByJobId(targetJobRole.getJobId());
        List<SkillGap> skillGaps = skillGapAnalyzer.analyze(requiredSkills, studentSkills);
        int readinessScore = readinessCalculator.calculate(skillGaps);
        Map<Integer, List<LearningResource>> resourcesBySkillId = loadResources(skillGaps);
        List<RoadmapItem> roadmapItems = roadmapGenerator.generate(skillGaps, resourcesBySkillId);

        return new AnalysisResult(student, targetJobRole, skillGaps, roadmapItems, readinessScore);
    }

    private Map<Integer, List<LearningResource>> loadResources(List<SkillGap> skillGaps) {
        Map<Integer, List<LearningResource>> resourcesBySkillId = new HashMap<>();
        for (SkillGap gap : skillGaps) {
            int skillId = gap.getSkill().getSkillId();
            resourcesBySkillId.put(skillId, learningResourceDAO.findBySkillId(skillId));
        }
        return resourcesBySkillId;
    }

    private void validateAnalysisRequest(Student student, JobRole targetJobRole) {
        if (student == null || student.getStudentId() == null) {
            throw new IllegalArgumentException("Select a saved student profile before running analysis.");
        }
        if (targetJobRole == null || targetJobRole.getJobId() == null) {
            throw new IllegalArgumentException("Select a target job role before running analysis.");
        }
    }
}
