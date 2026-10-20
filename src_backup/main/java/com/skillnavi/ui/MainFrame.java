
package com.skillnavi.ui;

import com.skillnavi.dao.JobRoleDAO;
import com.skillnavi.dao.JobSkillDAO;
import com.skillnavi.dao.LearningResourceDAO;
import com.skillnavi.dao.ProgressDAO;
import com.skillnavi.dao.SkillDAO;
import com.skillnavi.dao.StudentDAO;
import com.skillnavi.dao.StudentSkillDAO;
import com.skillnavi.dao.StudentTargetRoleDAO;
import com.skillnavi.model.AnalysisResult;
import com.skillnavi.model.JobRole;
import com.skillnavi.model.Student;
import com.skillnavi.service.CareerAnalysisService;
import com.skillnavi.service.ReadinessCalculator;
import com.skillnavi.service.RoadmapGenerator;
import com.skillnavi.service.SkillGapAnalyzer;
import com.skillnavi.util.AppSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainFrame extends JFrame {

    public static final String WELCOME = "WELCOME";
    public static final String PROFILE = "PROFILE";
    public static final String SKILLS = "SKILLS";
    public static final String JOB_ROLE = "JOB_ROLE";
    public static final String ANALYSIS = "ANALYSIS";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String ROADMAP = "ROADMAP";
    public static final String RESOURCES = "RESOURCES";
    public static final String PROGRESS = "PROGRESS";

    // Theme colors
    private static final Color NAVY = new Color(22, 38, 70);
    private static final Color NAVY_LIGHT = new Color(35, 54, 91);
    private static final Color BLUE = new Color(65, 125, 235);
    private static final Color PAGE_BACKGROUND = new Color(246, 248, 252);
    private static final Color TEXT = new Color(40, 49, 67);
    private static final Color MUTED = new Color(137, 146, 163);
    private static final Color WHITE = Color.WHITE;

    private final AppSession session = new AppSession();

    private final StudentDAO studentDAO = new StudentDAO();
    private final SkillDAO skillDAO = new SkillDAO();
    private final StudentSkillDAO studentSkillDAO = new StudentSkillDAO();
    private final JobRoleDAO jobRoleDAO = new JobRoleDAO();
    private final ProgressDAO progressDAO = new ProgressDAO();
    private final StudentTargetRoleDAO studentTargetRoleDAO =
            new StudentTargetRoleDAO();

    private final CareerAnalysisService careerAnalysisService =
            new CareerAnalysisService(
                    studentSkillDAO,
                    new JobSkillDAO(),
                    new LearningResourceDAO(),
                    new SkillGapAnalyzer(),
                    new ReadinessCalculator(),
                    new RoadmapGenerator()
            );

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    private final JLabel pageTitle = new JLabel("Welcome");
    private final JLabel pageSubtitle =
            new JLabel("Your personal career readiness workspace");

    private final Map<String, JButton> navigationButtons =
            new LinkedHashMap<>();

    private final WelcomePanel welcomePanel = new WelcomePanel(this);
    private final ProfilePanel profilePanel = new ProfilePanel(this);
    private final SkillPanel skillPanel = new SkillPanel(this);
    private final JobRolePanel jobRolePanel = new JobRolePanel(this);
    private final AnalysisPanel analysisPanel = new AnalysisPanel(this);
    private final DashboardPanel dashboardPanel = new DashboardPanel(this);
    private final RoadmapPanel roadmapPanel = new RoadmapPanel(this);
    private final ResourcePanel resourcePanel = new ResourcePanel(this);
    private final ProgressPanel progressPanel = new ProgressPanel(this);

    public MainFrame() {
        setTitle("SkillNavi | Student Skill Gap Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 680));
        setSize(1240, 780);
        setLocationRelativeTo(null);

        applyGlobalStyle();

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(PAGE_BACKGROUND);

        root.add(createNavigationPanel(), BorderLayout.WEST);

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(PAGE_BACKGROUND);
        mainArea.add(createHeader(), BorderLayout.NORTH);

        contentPanel.setBackground(PAGE_BACKGROUND);
        mainArea.add(contentPanel, BorderLayout.CENTER);

        root.add(mainArea, BorderLayout.CENTER);
        setContentPane(root);

        addScreens();
        showScreen(WELCOME);
    }

    private void applyGlobalStyle() {
        Font bodyFont = new Font("SansSerif", Font.PLAIN, 14);

        UIManager.put("Label.font", bodyFont);
        UIManager.put("Button.font", bodyFont);
        UIManager.put("TextField.font", bodyFont);
        UIManager.put("ComboBox.font", bodyFont);
        UIManager.put("OptionPane.messageFont", bodyFont);
        UIManager.put("OptionPane.buttonFont", bodyFont);
    }

    private JPanel createNavigationPanel() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(235, 0));
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));

        JPanel branding = new JPanel();
        branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));
        branding.setBackground(NAVY);
        branding.setBorder(new EmptyBorder(0, 8, 28, 0));

        JLabel logo = new JLabel("SkillNavi");
        logo.setFont(new Font("SansSerif", Font.BOLD, 27));
        logo.setForeground(WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("CAREER COMPANION");
        tagline.setFont(new Font("SansSerif", Font.BOLD, 10));
        tagline.setForeground(new Color(166, 187, 225));
        tagline.setBorder(new EmptyBorder(7, 1, 0, 0));
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        branding.add(logo);
        branding.add(tagline);

        sidebar.add(branding, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(NAVY);

        addNavigationButton(buttonsPanel, "Welcome", WELCOME);
        addNavigationButton(buttonsPanel, "Dashboard", DASHBOARD);

        addSectionLabel(buttonsPanel, "MY CAREER");

        addNavigationButton(buttonsPanel, "Profile", PROFILE);
        addNavigationButton(buttonsPanel, "Manage Skills", SKILLS);
        addNavigationButton(buttonsPanel, "Target Role", JOB_ROLE);
        addNavigationButton(buttonsPanel, "Skill Analysis", ANALYSIS);

        addSectionLabel(buttonsPanel, "LEARNING");

        addNavigationButton(buttonsPanel, "Roadmap", ROADMAP);
        addNavigationButton(buttonsPanel, "Resources", RESOURCES);
        addNavigationButton(buttonsPanel, "Progress", PROGRESS);

        sidebar.add(buttonsPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "<html><font color='#A6B7D7'>STUDENT SKILL GAP ANALYZER</font></html>"
        );
        footer.setFont(new Font("SansSerif", Font.PLAIN, 9));
        footer.setBorder(new EmptyBorder(20, 8, 0, 0));
        sidebar.add(footer, BorderLayout.SOUTH);

        return sidebar;
    }

    private void addSectionLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 10));
        label.setForeground(new Color(151, 169, 204));
        label.setBorder(new EmptyBorder(22, 12, 8, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.add(label);
    }

    private void addNavigationButton(
            JPanel panel, String label, String screenName) {

        JButton button = new JButton(label);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(new Color(221, 229, 243));
        button.setBackground(NAVY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 14, 12, 10));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(200, 45));

        button.addActionListener(event -> showScreen(screenName));

        navigationButtons.put(screenName, button);
        panel.add(button);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, new Color(229, 233, 241)),
                new EmptyBorder(20, 30, 20, 30)
        ));

        JPanel titles = new JPanel();
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.setBackground(WHITE);

        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        pageTitle.setForeground(NAVY);

        pageSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pageSubtitle.setForeground(MUTED);
        pageSubtitle.setBorder(new EmptyBorder(5, 1, 0, 0));

        titles.add(pageTitle);
        titles.add(pageSubtitle);

        JLabel brandLabel = new JLabel("SKILLNAVI");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        brandLabel.setForeground(BLUE);

        header.add(titles, BorderLayout.WEST);
        header.add(brandLabel, BorderLayout.EAST);

        return header;
    }

    private void addScreens() {
        contentPanel.add(welcomePanel, WELCOME);
        contentPanel.add(profilePanel, PROFILE);
        contentPanel.add(skillPanel, SKILLS);
        contentPanel.add(jobRolePanel, JOB_ROLE);
        contentPanel.add(analysisPanel, ANALYSIS);
        contentPanel.add(dashboardPanel, DASHBOARD);
        contentPanel.add(roadmapPanel, ROADMAP);
        contentPanel.add(resourcePanel, RESOURCES);
        contentPanel.add(progressPanel, PROGRESS);
    }

    public void showScreen(String screenName) {
        if (requiresStudent(screenName) && !session.hasActiveStudent()) {
            showError("Create or load a student profile first.");
            updateActiveNavigation(WELCOME);
            updateHeader(WELCOME);
            cardLayout.show(contentPanel, WELCOME);
            return;
        }

        refreshScreen(screenName);
        cardLayout.show(contentPanel, screenName);
        updateHeader(screenName);
        updateActiveNavigation(screenName);
    }

    private void updateHeader(String screenName) {
        switch (screenName) {
            case WELCOME -> {
                pageTitle.setText("Welcome to SkillNavi");
                pageSubtitle.setText(
                        "Your personal career readiness workspace");
            }
            case PROFILE -> {
                pageTitle.setText("Student Profile");
                pageSubtitle.setText(
                        "Create or manage your student information");
            }
            case SKILLS -> {
                pageTitle.setText("Manage Skills");
                pageSubtitle.setText(
                        "Record and update your technical skill proficiency");
            }
            case JOB_ROLE -> {
                pageTitle.setText("Target Job Role");
                pageSubtitle.setText(
                        "Choose the career role you want to work towards");
            }
            case ANALYSIS -> {
                pageTitle.setText("Skill Gap Analysis");
                pageSubtitle.setText(
                        "Compare your current skills with role requirements");
            }
            case DASHBOARD -> {
                pageTitle.setText("Dashboard");
                pageSubtitle.setText(
                        "Your career preparation at a glance");
            }
            case ROADMAP -> {
                pageTitle.setText("Learning Roadmap");
                pageSubtitle.setText(
                        "Explore the steps for improving your skills");
            }
            case RESOURCES -> {
                pageTitle.setText("Learning Resources");
                pageSubtitle.setText(
                        "Find resources to support your learning journey");
            }
            case PROGRESS -> {
                pageTitle.setText("Learning Progress");
                pageSubtitle.setText(
                        "Review and track your learning activities");
            }
            default -> {
                pageTitle.setText("SkillNavi");
                pageSubtitle.setText(
                        "Student Skill Gap Analyzer and Career Readiness System");
            }
        }
    }

    private void updateActiveNavigation(String activeScreen) {
        for (Map.Entry<String, JButton> entry :
                navigationButtons.entrySet()) {

            JButton button = entry.getValue();
            boolean active = entry.getKey().equals(activeScreen);

            button.setBackground(active ? BLUE : NAVY);
            button.setForeground(WHITE);

            if (active) {
                button.setFont(new Font("SansSerif", Font.BOLD, 14));
            } else {
                button.setFont(new Font("SansSerif", Font.PLAIN, 14));
            }
        }
    }

    private boolean requiresStudent(String screenName) {
        return !WELCOME.equals(screenName) && !PROFILE.equals(screenName);
    }

    private void refreshScreen(String screenName) {
        switch (screenName) {
            case PROFILE -> profilePanel.refreshData();
            case SKILLS -> skillPanel.refreshData();
            case JOB_ROLE -> jobRolePanel.refreshData();
            case ANALYSIS -> analysisPanel.refreshData();
            case DASHBOARD -> dashboardPanel.refreshData();
            case ROADMAP -> roadmapPanel.refreshData();
            case RESOURCES -> resourcePanel.refreshData();
            case PROGRESS -> progressPanel.refreshData();
            default -> {
                // No database refresh is required for the welcome screen.
            }
        }
    }

    public void setCurrentStudent(Student student) {
        session.setCurrentStudent(student);

        JobRole selectedRole =
                studentTargetRoleDAO
                        .findSelectedRoleByStudentId(student.getStudentId())
                        .orElse(null);

        session.setSelectedJobRole(selectedRole);
    }

    public void startNewProfile() {
        session.clear();
        showScreen(PROFILE);
    }

    public boolean runAnalysis() {
        try {
            AnalysisResult analysisResult = careerAnalysisService.analyze(
                    session.getCurrentStudent(),
                    session.getSelectedJobRole()
            );

            session.setLatestAnalysisResult(analysisResult);
            return true;

        } catch (RuntimeException exception) {
            showError(exception.getMessage());
            return false;
        }
    }

    public void clearAnalysis() {
        session.clearAnalysis();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "SkillNavi",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "SkillNavi",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public AppSession getSession() {
        return session;
    }

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }

    public SkillDAO getSkillDAO() {
        return skillDAO;
    }

    public StudentSkillDAO getStudentSkillDAO() {
        return studentSkillDAO;
    }

    public JobRoleDAO getJobRoleDAO() {
        return jobRoleDAO;
    }

    public ProgressDAO getProgressDAO() {
        return progressDAO;
    }

    public StudentTargetRoleDAO getStudentTargetRoleDAO() {
        return studentTargetRoleDAO;
    }
}
