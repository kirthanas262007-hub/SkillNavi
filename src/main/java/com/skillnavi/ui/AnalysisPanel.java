package com.skillnavi.ui;

import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.JobRole;
import com.skillnavi.ui.table.GapTableModel;

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

public class AnalysisPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JLabel roleLabel = new JLabel();
    private final JLabel explanationLabel = new JLabel("Run analysis to compare your skills with the selected role.");
    private final GapTableModel gapTableModel = new GapTableModel();

    public AnalysisPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JPanel header = new JPanel(new BorderLayout(4, 4));
        JLabel heading = new JLabel("Skill Gap Analysis");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        header.add(heading, BorderLayout.NORTH);
        header.add(roleLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        add(new JScrollPane(new JTable(gapTableModel)), BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.add(explanationLabel, BorderLayout.NORTH);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton analyzeButton = new JButton("Run Analysis");
        analyzeButton.addActionListener(event -> runAnalysis());
        JButton dashboardButton = new JButton("View Dashboard");
        dashboardButton.addActionListener(event -> mainFrame.showScreen(MainFrame.DASHBOARD));
        buttons.add(analyzeButton);
        buttons.add(dashboardButton);
        footer.add(buttons, BorderLayout.SOUTH);
        add(footer, BorderLayout.SOUTH);
    }

    public void refreshData() {
        JobRole role = mainFrame.getSession().getSelectedJobRole();
        roleLabel.setText(role == null ? "Target role: not selected" : "Target role: " + role.getJobTitle());
        AnalysisResult analysisResult = mainFrame.getSession().getLatestAnalysisResult();
        if (analysisResult == null) {
            gapTableModel.setSkillGaps(List.of());
            explanationLabel.setText("Run analysis to compare your skills with the selected role.");
            return;
        }
        gapTableModel.setSkillGaps(analysisResult.getSkillGaps());
        explanationLabel.setText("Skill Requirement Coverage: " + analysisResult.getReadinessScore()
                + "% (based on predefined database requirements, not a job probability).");
    }

    private void runAnalysis() {
        if (mainFrame.runAnalysis()) {
            refreshData();
            mainFrame.showInfo("Skill gap analysis completed.");
        }
    }
}
