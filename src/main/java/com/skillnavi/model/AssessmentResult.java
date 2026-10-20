package com.skillnavi.model;

import com.skillnavi.model.enums.ProficiencyLevel;

public class AssessmentResult {

    private int studentId;
    private int skillId;
    private int correctAnswers;
    private int totalQuestions;
    private double score;
    private ProficiencyLevel proficiencyLevel;

    public AssessmentResult(int studentId, int skillId,
                            int correctAnswers, int totalQuestions,
                            double score,
                            ProficiencyLevel proficiencyLevel) {

        this.studentId = studentId;
        this.skillId = skillId;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.score = score;
        this.proficiencyLevel = proficiencyLevel;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSkillId() {
        return skillId;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public double getScore() {
        return score;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }
}