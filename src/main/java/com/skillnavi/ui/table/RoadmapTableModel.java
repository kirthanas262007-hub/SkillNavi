package com.skillnavi.ui.table;

import com.skillnavi.model.RoadmapItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RoadmapTableModel extends AbstractTableModel {
    private final String[] columns = {"Step", "Skill", "Action", "Priority", "Resources"};
    private List<RoadmapItem> roadmapItems = new ArrayList<>();

    public void setRoadmapItems(List<RoadmapItem> roadmapItems) {
        this.roadmapItems = new ArrayList<>(roadmapItems);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return roadmapItems.size();
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
        RoadmapItem item = roadmapItems.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> item.getSequenceNumber();
            case 1 -> item.getSkill().getSkillName();
            case 2 -> item.getAction();
            case 3 -> item.getPriority();
            case 4 -> item.getResources().size();
            default -> "";
        };
    }
}
