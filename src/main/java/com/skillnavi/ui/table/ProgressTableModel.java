package com.skillnavi.ui.table;

import com.skillnavi.model.Progress;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProgressTableModel extends AbstractTableModel {
    private final String[] columns = {"Skill", "Progress", "Status"};
    private List<Progress> progressEntries = new ArrayList<>();

    public void setProgressEntries(List<Progress> progressEntries) {
        this.progressEntries = new ArrayList<>(progressEntries);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return progressEntries.size();
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
        Progress progress = progressEntries.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> progress.getSkill().getSkillName();
            case 1 -> progress.getProgressPercentage() + "%";
            case 2 -> progress.getStatus();
            default -> "";
        };
    }
}
