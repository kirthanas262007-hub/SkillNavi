package com.skillnavi.ui.table;

import com.skillnavi.model.LearningResource;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ResourceTableModel extends AbstractTableModel {
    private final String[] columns = {"Skill", "Resource", "Type", "Link"};
    private List<LearningResource> resources = new ArrayList<>();

    public void setResources(List<LearningResource> resources) {
        this.resources = new ArrayList<>(resources);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return resources.size();
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
        LearningResource resource = resources.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> resource.getSkill().getSkillName();
            case 1 -> resource.getResourceName();
            case 2 -> resource.getResourceType();
            case 3 -> resource.getResourceLink();
            default -> "";
        };
    }

    public LearningResource getResourceAt(int rowIndex) {
        return resources.get(rowIndex);
    }
}
