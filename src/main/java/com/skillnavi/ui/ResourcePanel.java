package com.skillnavi.ui;

import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.LearningResource;
import com.skillnavi.model.RoadmapItem;
import com.skillnavi.ui.table.ResourceTableModel;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class ResourcePanel extends JPanel {
    private final MainFrame mainFrame;
    private final JLabel summaryLabel = new JLabel();
    private final ResourceTableModel resourceTableModel = new ResourceTableModel();

    public ResourcePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JPanel header = new JPanel(new BorderLayout(4, 4));
        JLabel heading = new JLabel("Recommended Learning Resources");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        header.add(heading, BorderLayout.NORTH);
        header.add(summaryLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(resourceTableModel)), BorderLayout.CENTER);
    }

    public void refreshData() {
        AnalysisResult analysisResult = mainFrame.getSession().getLatestAnalysisResult();
        if (analysisResult == null) {
            summaryLabel.setText("Run Skill Gap Analysis to view resources for your skill gaps.");
            resourceTableModel.setResources(List.of());
            return;
        }

        List<LearningResource> resources = new ArrayList<>();
        for (RoadmapItem roadmapItem : analysisResult.getRoadmapItems()) {
            resources.addAll(roadmapItem.getResources());
        }
        summaryLabel.setText("Resources for skills in your generated learning roadmap.");
        resourceTableModel.setResources(resources);
    }
}
