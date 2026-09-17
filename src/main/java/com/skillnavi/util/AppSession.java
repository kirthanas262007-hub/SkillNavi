package com.skillnavi.util;

import com.skillnavi.model.JobRole;
import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.Student;

public class AppSession {
    private Student currentStudent;
    private JobRole selectedJobRole;
    private AnalysisResult latestAnalysisResult;

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public JobRole getSelectedJobRole() {
        return selectedJobRole;
    }

    public void setSelectedJobRole(JobRole selectedJobRole) {
        this.selectedJobRole = selectedJobRole;
        latestAnalysisResult = null;
    }

    public AnalysisResult getLatestAnalysisResult() {
        return latestAnalysisResult;
    }

    public void setLatestAnalysisResult(AnalysisResult latestAnalysisResult) {
        this.latestAnalysisResult = latestAnalysisResult;
    }

    public void clearAnalysis() {
        latestAnalysisResult = null;
    }

    public boolean hasActiveStudent() {
        return currentStudent != null && currentStudent.getStudentId() != null;
    }

    public void clear() {
        currentStudent = null;
        selectedJobRole = null;
        latestAnalysisResult = null;
    }
}
