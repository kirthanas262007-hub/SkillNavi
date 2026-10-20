
package com.skillnavi.ui;

import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.ui.table.GapTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;

    private final JLabel studentLabel = new JLabel("Student");
    private final JLabel roleLabel = new JLabel("Target role not selected");
    private final JLabel readinessValue = new JLabel("--");
    private final JLabel matchedValue = new JLabel("--");
    private final JLabel improvementValue = new JLabel("--");
    private final JLabel missingValue = new JLabel("--");
    private final JLabel statusMessage = new JLabel();

    private final GapTableModel gapTableModel = new GapTableModel();
    private final JTable gapTable = new JTable(gapTableModel);

    private static final Color NAVY = new Color(22, 38, 70);
    private static final Color BLUE = new Color(65, 125, 235);
    private static final Color BACKGROUND = new Color(246, 248, 252);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(40, 49, 67);
    private static final Color MUTED = new Color(120, 131, 151);
    private static final Color BORDER = new Color(224, 230, 240);
    private static final Color GREEN = new Color(32, 145, 100);
    private static final Color ORANGE = new Color(220, 145, 45);
    private static final Color RED = new Color(210, 80, 85);

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(0, 20));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(26, 30, 26, 30));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        styleTable();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setBackground(BACKGROUND);

        JPanel titles = new JPanel();
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.setBackground(BACKGROUND);

        JLabel heading = new JLabel("Career Readiness Dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(NAVY);

        studentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        studentLabel.setForeground(TEXT);

        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        roleLabel.setForeground(MUTED);

        titles.add(heading);
        titles.add(Box.createVerticalStrut(10));
        titles.add(studentLabel);
        titles.add(Box.createVerticalStrut(4));
        titles.add(roleLabel);

        JButton analysisButton = new JButton("Run Skill Analysis");
        styleButton(analysisButton, BLUE, WHITE);
        analysisButton.addActionListener(event ->
                mainFrame.showScreen(MainFrame.ANALYSIS)
        );

        header.add(titles, BorderLayout.CENTER);
        header.add(analysisButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BACKGROUND);

        JPanel cards = new JPanel(new GridLayout(1, 4, 14, 0));
        cards.setBackground(BACKGROUND);

        cards.add(createSummaryCard(
                "CAREER READINESS", readinessValue, "Based on skill coverage", BLUE
        ));
        cards.add(createSummaryCard(
                "MATCHED SKILLS", matchedValue, "Requirements met", GREEN
        ));
        cards.add(createSummaryCard(
                "NEEDS IMPROVEMENT", improvementValue, "Skills to strengthen", ORANGE
        ));
        cards.add(createSummaryCard(
                "MISSING SKILLS", missingValue, "Skills to learn", RED
        ));

        JPanel tableCard = new JPanel(new BorderLayout(0, 12));
        tableCard.setBackground(WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JPanel tableHeader = new JPanel(new BorderLayout(5, 5));
        tableHeader.setBackground(WHITE);

        JLabel tableTitle = new JLabel("Skill Gap Overview");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        tableTitle.setForeground(NAVY);

        JLabel tableSubtitle = new JLabel(
                "Compare your current skills with your target role requirements."
        );
        tableSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tableSubtitle.setForeground(MUTED);

        tableHeader.add(tableTitle, BorderLayout.NORTH);
        tableHeader.add(tableSubtitle, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(gapTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(WHITE);

        statusMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusMessage.setForeground(MUTED);

        tableCard.add(tableHeader, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);
        tableCard.add(statusMessage, BorderLayout.SOUTH);

        content.add(cards, BorderLayout.NORTH);
        content.add(tableCard, BorderLayout.CENTER);

        return content;
    }

    private JPanel createSummaryCard(
            String title, JLabel valueLabel, String description, Color accent) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(17, 16, 17, 16)
        ));

        JPanel accentLine = new JPanel();
        accentLine.setBackground(accent);
        accentLine.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
        accentLine.setPreferredSize(new Dimension(100, 4));
        accentLine.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        titleLabel.setForeground(MUTED);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(12, 0, 0, 0));

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 29));
        valueLabel.setForeground(accent);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        valueLabel.setBorder(new EmptyBorder(8, 0, 6, 0));

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(accentLine);
        card.add(titleLabel);
        card.add(valueLabel);
        card.add(descriptionLabel);

        return card;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout(10, 0));
        footer.setBackground(BACKGROUND);

        JLabel note = new JLabel(
                "Readiness represents skill requirement coverage, not job probability."
        );
        note.setFont(new Font("SansSerif", Font.PLAIN, 11));
        note.setForeground(MUTED);

        JButton roadmapButton = new JButton("View Learning Roadmap");
        styleButton(roadmapButton, NAVY, WHITE);
        roadmapButton.addActionListener(event ->
                mainFrame.showScreen(MainFrame.ROADMAP)
        );

        footer.add(note, BorderLayout.CENTER);
        footer.add(roadmapButton, BorderLayout.EAST);

        return footer;
    }

    private void styleButton(
            JButton button, Color background, Color foreground) {

        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setBorder(new EmptyBorder(12, 16, 12, 16));
    }

    private void styleTable() {
        gapTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gapTable.setForeground(TEXT);
        gapTable.setBackground(WHITE);
        gapTable.setRowHeight(34);
        gapTable.setShowVerticalLines(false);
        gapTable.setGridColor(BORDER);
        gapTable.setSelectionBackground(new Color(225, 237, 255));
        gapTable.setSelectionForeground(NAVY);
        gapTable.setFillsViewportHeight(true);

        gapTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );
        gapTable.getTableHeader().setBackground(BACKGROUND);
        gapTable.getTableHeader().setForeground(TEXT);
        gapTable.getTableHeader().setReorderingAllowed(false);
    }

    public void refreshData() {
        AnalysisResult result =
                mainFrame.getSession().getLatestAnalysisResult();

        if (result == null) {
            var student = mainFrame.getSession().getCurrentStudent();

            studentLabel.setText(
                    "Student: " + (student != null ? student.getName() : "Not selected")
            );
            roleLabel.setText("Target role: select a role and run analysis");

            readinessValue.setText("--");
            matchedValue.setText("--");
            improvementValue.setText("--");
            missingValue.setText("--");

            statusMessage.setText(
                    "No analysis available yet. Select a target role and run Skill Analysis."
            );
            gapTableModel.setSkillGaps(List.of());
            return;
        }

        studentLabel.setText("Student: " + result.getStudent().getName());
        roleLabel.setText(
                "Target role: " + result.getTargetJobRole().getJobTitle()
        );

        readinessValue.setText(result.getReadinessScore() + "%");
        matchedValue.setText(
                String.valueOf(result.getCountByStatus(GapStatus.MATCHED))
        );
        improvementValue.setText(
                String.valueOf(
                        result.getCountByStatus(GapStatus.NEEDS_IMPROVEMENT)
                )
        );
        missingValue.setText(
                String.valueOf(result.getCountByStatus(GapStatus.MISSING))
        );

        statusMessage.setText(
                "Analysis updated. Use the roadmap to work on identified skill gaps."
        );

        gapTableModel.setSkillGaps(result.getSkillGaps());
    }
}
