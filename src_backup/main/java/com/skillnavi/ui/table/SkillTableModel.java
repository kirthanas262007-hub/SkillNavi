package com.skillnavi.ui.table;

import com.skillnavi.model.StudentSkill;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SkillTableModel extends AbstractTableModel {
    private final String[] columns = {"Skill", "Category", "Proficiency"};
    private List<StudentSkill> studentSkills = new ArrayList<>();

    public void setStudentSkills(List<StudentSkill> studentSkills) {
        this.studentSkills = new ArrayList<>(studentSkills);
        fireTableDataChanged();
    }

    public StudentSkill getStudentSkillAt(int rowIndex) {
        return studentSkills.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return studentSkills.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StudentSkill studentSkill = studentSkills.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> studentSkill.getSkill().getSkillName();
            case 1 -> studentSkill.getSkill().getCategory();
            case 2 -> studentSkill.getProficiencyLevel();
            default -> "";
        };
    }
}
