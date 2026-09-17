package com.skillnavi.ui;

import com.skillnavi.model.AnalysisResult;
import com.skillnavi.ui.table.RoadmapTableModel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

public class RoadmapPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JLabel summaryLabel = new JLabel();
    private final RoadmapTableModel roadmapTableModel = new RoadmapTableModel();

    public RoadmapPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JPanel header = new JPanel(new BorderLayout(4, 4));
        JLabel heading = new JLabel("Learning Roadmap");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        header.add(heading, BorderLayout.NORTH);
        header.add(summaryLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        add(new JScrollPane(new JTable(roadmapTableModel)), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton resourcesButton = new JButton("View Learning Resources");
        resourcesButton.addActionListener(event -> mainFrame.showScreen(MainFrame.RESOURCES));
        buttons.add(resourcesButton);
        add(buttons, BorderLayout.SOUTH);
    }

    public void refreshData() {
        AnalysisResult analysisResult = mainFrame.getSession().getLatestAnalysisResult();
        if (analysisResult == null) {
            summaryLabel.setText("Run Skill Gap Analysis before viewing the roadmap.");
            roadmapTableModel.setRoadmapItems(List.of());
            return;
        }
        summaryLabel.setText("Target role: " + analysisResult.getTargetJobRole().getJobTitle()
                + " — high-priority gaps appear first.");
        roadmapTableModel.setRoadmapItems(analysisResult.getRoadmapItems());
    }
}
