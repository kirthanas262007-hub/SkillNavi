package com.skillnavi.ui.table;

import com.skillnavi.model.SkillGap;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class GapTableModel extends AbstractTableModel {
    private final String[] columns = {"Skill", "Current Level", "Required Level", "Status", "Priority"};
    private List<SkillGap> skillGaps = new ArrayList<>();

    public void setSkillGaps(List<SkillGap> skillGaps) {
        this.skillGaps = new ArrayList<>(skillGaps);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return skillGaps.size();
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
        SkillGap skillGap = skillGaps.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> skillGap.getSkill().getSkillName();
            case 1 -> skillGap.getCurrentLevel() == null ? "Not added" : skillGap.getCurrentLevel();
            case 2 -> skillGap.getRequiredLevel();
            case 3 -> skillGap.getStatus();
            case 4 -> skillGap.getPriority();
            default -> "";
        };
    }
}
