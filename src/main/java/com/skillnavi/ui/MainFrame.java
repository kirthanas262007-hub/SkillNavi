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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

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

    private final AppSession session = new AppSession();
    private final StudentDAO studentDAO = new StudentDAO();
    private final SkillDAO skillDAO = new SkillDAO();
    private final StudentSkillDAO studentSkillDAO = new StudentSkillDAO();
    private final JobRoleDAO jobRoleDAO = new JobRoleDAO();
    private final ProgressDAO progressDAO = new ProgressDAO();
    private final StudentTargetRoleDAO studentTargetRoleDAO = new StudentTargetRoleDAO();
    private final CareerAnalysisService careerAnalysisService = new CareerAnalysisService(
            studentSkillDAO,
            new JobSkillDAO(),
            new LearningResourceDAO(),
            new SkillGapAnalyzer(),
            new ReadinessCalculator(),
            new RoadmapGenerator()
    );

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
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
        setTitle("SkillNavi - Student Skill Gap Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1020, 650));
        setSize(1120, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createNavigationPanel(), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        addScreens();
        showScreen(WELCOME);
    }

    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel(new BorderLayout(8, 12));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));
        navigationPanel.setPreferredSize(new Dimension(180, 0));

        JLabel title = new JLabel("SkillNavi");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        navigationPanel.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(0, 1, 0, 7));
        addNavigationButton(buttons, "Welcome", WELCOME);
        addNavigationButton(buttons, "Profile", PROFILE);
        addNavigationButton(buttons, "Manage Skills", SKILLS);
        addNavigationButton(buttons, "Target Role", JOB_ROLE);
        addNavigationButton(buttons, "Skill Analysis", ANALYSIS);
        addNavigationButton(buttons, "Dashboard", DASHBOARD);
        addNavigationButton(buttons, "Roadmap", ROADMAP);
        addNavigationButton(buttons, "Resources", RESOURCES);
        addNavigationButton(buttons, "Progress", PROGRESS);
        navigationPanel.add(buttons, BorderLayout.CENTER);
        return navigationPanel;
    }

    private void addNavigationButton(JPanel panel, String label, String screenName) {
        JButton button = new JButton(label);
        button.addActionListener(event -> showScreen(screenName));
        panel.add(button);
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
            cardLayout.show(contentPanel, WELCOME);
            return;
        }

        refreshScreen(screenName);
        cardLayout.show(contentPanel, screenName);
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
                // The welcome panel does not need database data before it is shown.
            }
        }
    }

    public void setCurrentStudent(Student student) {
        session.setCurrentStudent(student);
        JobRole selectedRole = studentTargetRoleDAO.findSelectedRoleByStudentId(student.getStudentId()).orElse(null);
        session.setSelectedJobRole(selectedRole);
    }

    public void startNewProfile() {
        session.clear();
        showScreen(PROFILE);
    }

    public boolean runAnalysis() {
        try {
            AnalysisResult analysisResult = careerAnalysisService.analyze(
                    session.getCurrentStudent(), session.getSelectedJobRole());
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
        JOptionPane.showMessageDialog(this, message, "SkillNavi", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "SkillNavi", JOptionPane.INFORMATION_MESSAGE);
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
