

package com.skillnavi.ui;

import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.SkillGap;
import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.ui.table.GapTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;

    private final JLabel studentLabel = new JLabel("Student");
    private final JLabel roleLabel = new JLabel("Choose a target role to begin");

    private final JLabel readinessValue = new JLabel("--%");
    private final JLabel matchedValue = new JLabel("--");
    private final JLabel improvementValue = new JLabel("--");
    private final JLabel missingValue = new JLabel("--");
    private final JLabel statusMessage = new JLabel("Run an analysis to see your results.");

    private final GapTableModel gapTableModel = new GapTableModel();
    private final JTable gapTable = new JTable(gapTableModel);

    private final ReadinessGauge readinessGauge = new ReadinessGauge();

    private final JPanel chartRows = new JPanel();
    private final JPanel chartEmpty = new JPanel(new BorderLayout());

    private static final Color NAVY = new Color(30, 45, 72);
    private static final Color TEAL = new Color(55, 137, 125);
    private static final Color BLUE = new Color(76, 119, 190);
    private static final Color BG = new Color(244, 247, 250);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(52, 63, 79);
    private static final Color MUTED = new Color(119, 131, 146);
    private static final Color BORDER = new Color(225, 230, 237);
    private static final Color GREEN = new Color(44, 143, 103);
    private static final Color ORANGE = new Color(205, 137, 52);
    private static final Color RED = new Color(195, 81, 89);

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel page = new JPanel(new BorderLayout(0, 22));
        page.setBackground(BG);
        page.setBorder(new EmptyBorder(24, 28, 24, 28));

        page.add(createHeader(), BorderLayout.NORTH);
        page.add(createContent(), BorderLayout.CENTER);
        page.add(createFooter(), BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
        styleTable();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 12));
        header.setBackground(BG);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("YOUR PROGRESS, AT A GLANCE");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 10));
        eyebrow.setForeground(TEAL);

        JLabel heading = new JLabel("Career dashboard");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(NAVY);

        studentLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        studentLabel.setForeground(TEXT);

        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        roleLabel.setForeground(MUTED);

        titles.add(eyebrow);
        titles.add(Box.createVerticalStrut(7));
        titles.add(heading);
        titles.add(Box.createVerticalStrut(8));
        titles.add(studentLabel);
        titles.add(Box.createVerticalStrut(3));
        titles.add(roleLabel);

        JButton analysisButton = createButton("Run skill analysis", TEAL);
        analysisButton.addActionListener(e ->
                mainFrame.showScreen(MainFrame.ANALYSIS));

        header.add(titles, BorderLayout.CENTER);
        header.add(analysisButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel topRow = new JPanel(new GridLayout(1, 2, 16, 0));
        topRow.setBackground(BG);
        topRow.setPreferredSize(new Dimension(700, 205));
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 205));
        topRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        topRow.add(createReadinessCard());
        topRow.add(createSummaryCards());

        content.add(topRow);
        content.add(Box.createVerticalStrut(18));
        content.add(createChartCard());
        content.add(Box.createVerticalStrut(18));
        content.add(createGapTableCard());

        return content;
    }

    private JPanel createReadinessCard() {
        JPanel card = cardPanel();
        card.setLayout(new BorderLayout(15, 8));
        card.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel label = sectionLabel("CAREER READINESS");
        JLabel title = new JLabel("Your readiness score");
        title.setFont(new Font("SansSerif", Font.BOLD, 19));
        title.setForeground(NAVY);

        JLabel description = new JLabel(
                "<html>Measure your current skills<br>against your target role.</html>");
        description.setFont(new Font("SansSerif", Font.PLAIN, 12));
        description.setForeground(MUTED);

        readinessValue.setFont(new Font("SansSerif", Font.BOLD, 25));
        readinessValue.setForeground(TEAL);

        info.add(label);
        info.add(Box.createVerticalStrut(12));
        info.add(title);
        info.add(Box.createVerticalStrut(8));
        info.add(readinessValue);
        info.add(Box.createVerticalStrut(8));
        info.add(description);

        card.add(info, BorderLayout.CENTER);
        card.add(readinessGauge, BorderLayout.EAST);

        return card;
    }

    private JPanel createSummaryCards() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setOpaque(false);

        panel.add(summaryCard("MATCHED", matchedValue,
                "Requirements met", GREEN));
        panel.add(summaryCard("IMPROVE", improvementValue,
                "Skills to strengthen", ORANGE));
        panel.add(summaryCard("MISSING", missingValue,
                "Skills to learn", RED));

        JLabel targetValue = new JLabel("--");
        targetValue.setFont(new Font("SansSerif", Font.BOLD, 23));
        targetValue.setForeground(BLUE);
        panel.add(summaryCard("TARGET ROLE", targetValue,
                "Selected career", BLUE));

        return panel;
    }

    private JPanel summaryCard(String title, JLabel value,
                               String description, Color accent) {
        JPanel card = cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(13, 15, 12, 12));

        JLabel heading = sectionLabel(title);
        value.setFont(new Font("SansSerif", Font.BOLD, 23));
        value.setForeground(accent);

        JLabel detail = new JLabel(description);
        detail.setFont(new Font("SansSerif", Font.PLAIN, 10));
        detail.setForeground(MUTED);

        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        value.setAlignmentX(Component.LEFT_ALIGNMENT);
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(7));
        card.add(value);
        card.add(Box.createVerticalStrut(3));
        card.add(detail);

        return card;
    }

    private JPanel createChartCard() {
        JPanel card = cardPanel();
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(new EmptyBorder(20, 22, 20, 22));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 420));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Your skill profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Current proficiency compared with the required level");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(subtitle);
        heading.add(Box.createVerticalStrut(12));
        heading.add(createLegend());

        chartRows.setLayout(new BoxLayout(chartRows, BoxLayout.Y_AXIS));
        chartRows.setBackground(WHITE);

        chartEmpty.setBackground(WHITE);
        JLabel emptyLabel = new JLabel(
                "<html><center>No skill chart yet.<br>"
                        + "Select a target role and run skill analysis.</center></html>",
                SwingConstants.CENTER);
        emptyLabel.setForeground(MUTED);
        chartEmpty.add(emptyLabel, BorderLayout.CENTER);

        card.add(heading, BorderLayout.NORTH);
        card.add(chartRows, BorderLayout.CENTER);

        return card;
    }

    private JPanel createLegend() {
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        legend.setOpaque(false);

        legend.add(legendItem(TEAL, "Current level"));
        legend.add(legendItem(BLUE, "Required level"));

        return legend;
    }

    private JPanel legendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        item.setOpaque(false);

        JPanel swatch = new JPanel();
        swatch.setBackground(color);
        swatch.setPreferredSize(new Dimension(11, 11));

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 11));
        label.setForeground(MUTED);

        item.add(swatch);
        item.add(label);
        return item;
    }

    private JPanel createGapTableCard() {
        JPanel card = cardPanel();
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(20, 22, 20, 20));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setPreferredSize(new Dimension(700, 330));
        card.setMinimumSize(new Dimension(350, 240));

        JLabel title = new JLabel("Detailed skill-gap overview");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Review the skills that match your target career requirements.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(Box.createVerticalStrut(5));
        heading.add(subtitle);

        JScrollPane scroll = new JScrollPane(gapTable);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.getViewport().setBackground(WHITE);

        statusMessage.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusMessage.setForeground(MUTED);

        card.add(heading, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(statusMessage, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout(10, 0));
        footer.setOpaque(false);

        JLabel note = new JLabel(
                "Readiness measures skill coverage, not job probability.");
        note.setFont(new Font("SansSerif", Font.PLAIN, 11));
        note.setForeground(MUTED);

        JButton roadmapButton = createButton("Open learning roadmap", NAVY);
        roadmapButton.addActionListener(e ->
                mainFrame.showScreen(MainFrame.ROADMAP));

        footer.add(note, BorderLayout.CENTER);
        footer.add(roadmapButton, BorderLayout.EAST);

        return footer;
    }

    private JPanel cardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(1, 1, 1, 1)));
        return panel;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 10));
        label.setForeground(MUTED);
        return label;
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(11, 15, 11, 15));
        return button;
    }

    private void styleTable() {
        gapTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        gapTable.setForeground(TEXT);
        gapTable.setBackground(WHITE);
        gapTable.setRowHeight(35);
        gapTable.setShowVerticalLines(false);
        gapTable.setGridColor(BORDER);
        gapTable.setSelectionBackground(new Color(225, 239, 235));
        gapTable.setSelectionForeground(NAVY);
        gapTable.setFillsViewportHeight(true);
        gapTable.setAutoCreateRowSorter(true);

        gapTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12));
        gapTable.getTableHeader().setBackground(new Color(241, 245, 248));
        gapTable.getTableHeader().setForeground(TEXT);
        gapTable.getTableHeader().setReorderingAllowed(false);

        gapTable.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean selected,
                            boolean focused, int row, int column) {

                        Component c = super.getTableCellRendererComponent(
                                table, value, selected, focused, row, column);

                        if (!selected) {
                            c.setBackground(row % 2 == 0
                                    ? WHITE : new Color(248, 250, 252));
                        }
                        setBorder(new EmptyBorder(0, 9, 0, 9));
                        return c;
                    }
                });
    }

    public void refreshData() {
        AnalysisResult result =
                mainFrame.getSession().getLatestAnalysisResult();

        var student = mainFrame.getSession().getCurrentStudent();
        studentLabel.setText("Student: "
                + (student != null ? student.getName() : "Not selected"));

        if (result == null) {
            roleLabel.setText("Target role: select a role and run analysis");

            readinessValue.setText("--%");
            matchedValue.setText("--");
            improvementValue.setText("--");
            missingValue.setText("--");

            readinessGauge.setScore(0);
            readinessGauge.setNoData(true);

            chartRows.removeAll();
            chartRows.add(chartEmpty);
            chartRows.revalidate();
            chartRows.repaint();

            statusMessage.setText(
                    "No analysis available. Choose a target role and run Skill Analysis.");
            gapTableModel.setSkillGaps(List.of());
            return;
        }

        roleLabel.setText(
                "Target role: " + result.getTargetJobRole().getJobTitle());

        int score = Math.max(0, Math.min(100, result.getReadinessScore()));
        readinessValue.setText(score + "%");
        readinessGauge.setScore(score);
        readinessGauge.setNoData(false);

        matchedValue.setText(String.valueOf(
                result.getCountByStatus(GapStatus.MATCHED)));
        improvementValue.setText(String.valueOf(
                result.getCountByStatus(GapStatus.NEEDS_IMPROVEMENT)));
        missingValue.setText(String.valueOf(
                result.getCountByStatus(GapStatus.MISSING)));

        statusMessage.setText(
                "Analysis updated. Use your roadmap to work on identified skill gaps.");

        gapTableModel.setSkillGaps(result.getSkillGaps());
        updateChart(result.getSkillGaps());

        // Show a compact summary of the selected role in the final card.
        // The real role name is shown in the header above.
        // The count cards remain based on the analysis result.
    }

    private void updateChart(List<SkillGap> gaps) {
        chartRows.removeAll();

        if (gaps.isEmpty()) {
            chartRows.add(chartEmpty);
        } else {
            int count = Math.min(gaps.size(), 8);

            for (int i = 0; i < count; i++) {
                SkillGap gap = gaps.get(i);

                JPanel row = new JPanel(new BorderLayout(12, 4));
                row.setBackground(WHITE);
                row.setBorder(new EmptyBorder(6, 0, 6, 0));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

                String name = gap.getSkill() != null
                        ? gap.getSkill().getSkillName() : "Skill";

                JLabel skillName = new JLabel(name);
                skillName.setFont(new Font("SansSerif", Font.BOLD, 12));
                skillName.setForeground(TEXT);
                skillName.setPreferredSize(new Dimension(125, 25));

                JPanel bars = new JPanel();
                bars.setOpaque(false);
                bars.setLayout(new BoxLayout(bars, BoxLayout.Y_AXIS));

                int current = gap.getCurrentLevel() == null ? 0
                        : gap.getCurrentLevel().getNumericValue();
                int required = gap.getRequiredLevel() == null ? 0
                        : gap.getRequiredLevel().getNumericValue();

                bars.add(createLevelBar(current, TEAL, "Current"));
                bars.add(Box.createVerticalStrut(5));
                bars.add(createLevelBar(required, BLUE, "Required"));

                row.add(skillName, BorderLayout.WEST);
                row.add(bars, BorderLayout.CENTER);

                chartRows.add(row);
            }

            if (gaps.size() > count) {
                JLabel more = new JLabel(
                        "+" + (gaps.size() - count)
                                + " more skills shown in the detailed table");
                more.setForeground(MUTED);
                more.setFont(new Font("SansSerif", Font.ITALIC, 11));
                more.setBorder(new EmptyBorder(6, 0, 0, 0));
                chartRows.add(more);
            }
        }

        chartRows.revalidate();
        chartRows.repaint();
    }

    private JPanel createLevelBar(int level, Color color, String label) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 13));

        JProgressBar bar = new JProgressBar(0, 3);
        bar.setValue(Math.max(0, Math.min(3, level)));
        bar.setStringPainted(false);
        bar.setBorderPainted(false);
        bar.setBackground(new Color(233, 238, 242));
        bar.setForeground(color);
        bar.setPreferredSize(new Dimension(100, 9));

        String levelText = level == 0 ? "Not set"
                : ProficiencyLevel.fromNumericValue(level).name()
                        .toLowerCase().replace('_', ' ');

        JLabel value = new JLabel(levelText);
        value.setFont(new Font("SansSerif", Font.PLAIN, 10));
        value.setForeground(MUTED);
        value.setPreferredSize(new Dimension(100, 14));

        row.add(bar, BorderLayout.CENTER);
        row.add(value, BorderLayout.EAST);

        return row;
    }

    private static class ReadinessGauge extends JPanel {
        private int score;
        private boolean noData = true;

        ReadinessGauge() {
            setOpaque(false);
            setPreferredSize(new Dimension(125, 125));
            setMinimumSize(new Dimension(100, 100));
        }

        void setScore(int score) {
            this.score = Math.max(0, Math.min(100, score));
            repaint();
        }

        void setNoData(boolean noData) {
            this.noData = noData;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight()) - 22;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;
            int stroke = 10;

            g.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));

            g.setColor(new Color(231, 237, 241));
            g.draw(new Arc2D.Double(x, y, size, size,
                    90, -360, Arc2D.OPEN));

            if (!noData) {
                g.setColor(TEAL);
                g.draw(new Arc2D.Double(x, y, size, size,
                        90, -3.6 * score, Arc2D.OPEN));
            }

            g.dispose();
        }
    }
}
