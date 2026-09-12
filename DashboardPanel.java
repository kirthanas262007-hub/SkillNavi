package com.skillnavi.ui;

import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.enums.GapStatus;
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
import java.awt.GridLayout;
import java.util.List;

public class DashboardPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JLabel studentLabel = new JLabel();
    private final JLabel roleLabel = new JLabel();
    private final JLabel readinessLabel = new JLabel();
    private final JLabel matchedLabel = new JLabel();
    private final JLabel improvementLabel = new JLabel();
    private final JLabel missingLabel = new JLabel();
    private final JLabel noteLabel = new JLabel();
    private final GapTableModel gapTableModel = new GapTableModel();

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JPanel header = new JPanel(new BorderLayout(4, 4));
        JLabel heading = new JLabel("Career Readiness Dashboard");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        header.add(heading, BorderLayout.NORTH);
        JPanel details = new JPanel(new GridLayout(0, 1, 0, 3));
        details.add(studentLabel);
        details.add(roleLabel);
        details.add(readinessLabel);
        header.add(details, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        add(new JScrollPane(new JTable(gapTableModel)), BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout(6, 6));
        JPanel counts = new JPanel(new FlowLayout(FlowLayout.LEFT));
        counts.add(matchedLabel);
        counts.add(improvementLabel);
        counts.add(missingLabel);
        footer.add(counts, BorderLayout.NORTH);
        footer.add(noteLabel, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton roadmapButton = new JButton("View Learning Roadmap");
        roadmapButton.addActionListener(event -> mainFrame.showScreen(MainFrame.ROADMAP));
        buttons.add(roadmapButton);
        footer.add(buttons, BorderLayout.SOUTH);
        add(footer, BorderLayout.SOUTH);
    }

    public void refreshData() {
        AnalysisResult analysisResult = mainFrame.getSession().getLatestAnalysisResult();
        if (analysisResult == null) {
            studentLabel.setText("Student: " + mainFrame.getSession().getCurrentStudent().getName());
            roleLabel.setText("Target role: select a role and run analysis");
            readinessLabel.setText("Career Readiness: not calculated");
            matchedLabel.setText("Matched Skills: -");
            improvementLabel.setText("Needs Improvement: -");
            missingLabel.setText("Missing Skills: -");
            noteLabel.setText("Run Skill Gap Analysis to generate this dashboard.");
            gapTableModel.setSkillGaps(List.of());
            return;
        }

        studentLabel.setText("Student: " + analysisResult.getStudent().getName());
        roleLabel.setText("Target role: " + analysisResult.getTargetJobRole().getJobTitle());
        readinessLabel.setText("Career Readiness: " + analysisResult.getReadinessScore() + "%");
        matchedLabel.setText("Matched Skills: " + analysisResult.getCountByStatus(GapStatus.MATCHED));
        improvementLabel.setText("Needs Improvement: " + analysisResult.getCountByStatus(GapStatus.NEEDS_IMPROVEMENT));
        missingLabel.setText("Missing Skills: " + analysisResult.getCountByStatus(GapStatus.MISSING));
        noteLabel.setText("This score is skill requirement coverage from the predefined database, not a job probability.");
        gapTableModel.setSkillGaps(analysisResult.getSkillGaps());
    }
}
