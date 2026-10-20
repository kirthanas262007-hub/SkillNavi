

package com.skillnavi.ui;

import com.skillnavi.model.Student;
import com.skillnavi.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class LoginFrame extends JFrame {

    private static final Color NAVY = new Color(30, 45, 72);
    private static final Color TEAL = new Color(55, 137, 125);
    private static final Color BG = new Color(244, 247, 250);
    private static final Color TEXT = new Color(52, 63, 79);
    private static final Color MUTED = new Color(119, 131, 146);
    private static final Color WHITE = Color.WHITE;

    private final AuthService authService = new AuthService();

    private JTextField loginIdentifier;
    private JPasswordField loginPassword;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField departmentField;
    private JTextField yearField;
    private JTextField usernameField;
    private JPasswordField registerPassword;

    public LoginFrame() {
        setTitle("SkillNavi | Student Career Readiness");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(940, 650);
        setMinimumSize(new Dimension(760, 560));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridLayout(1, 2, 0, 0));
        root.setBackground(BG);

        root.add(createWelcomePanel());
        root.add(createFormPanel());

        setContentPane(root);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(NAVY);
        panel.setBorder(new EmptyBorder(42, 38, 35, 38));

        JLabel logo = new JLabel("✦  SkillNavi");
        logo.setFont(new Font("SansSerif", Font.BOLD, 27));
        logo.setForeground(WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel eyebrow = new JLabel("YOUR CAREER JOURNEY");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 11));
        eyebrow.setForeground(new Color(127, 207, 190));
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(
                "<html>Turn your skills<br>into your future.</html>");
        heading.setFont(new Font("SansSerif", Font.BOLD, 31));
        heading.setForeground(WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "<html>Understand your strengths, discover skill gaps,<br>"
                        + "and build a roadmap towards your career goals.</html>");
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(new Color(207, 217, 229));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(logo);
        panel.add(Box.createVerticalStrut(75));
        panel.add(eyebrow);
        panel.add(Box.createVerticalStrut(14));
        panel.add(heading);
        panel.add(Box.createVerticalStrut(18));
        panel.add(description);
        panel.add(Box.createVerticalStrut(35));

        panel.add(createFeature("01", "Discover your strengths",
                "Understand your current skill levels."));
        panel.add(Box.createVerticalStrut(18));

        panel.add(createFeature("02", "Identify skill gaps",
                "Compare your skills with career requirements."));
        panel.add(Box.createVerticalStrut(18));

        panel.add(createFeature("03", "Build your roadmap",
                "Find the next steps towards your goals."));

        panel.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("LEARN  •  GROW  •  GET CAREER READY");
        footer.setFont(new Font("SansSerif", Font.BOLD, 10));
        footer.setForeground(new Color(171, 188, 207));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(footer);

        return panel;
    }

    private JPanel createFeature(String number, String title, String subtitle) {
        JPanel feature = new JPanel(new BorderLayout(13, 0));
        feature.setOpaque(false);
        feature.setAlignmentX(Component.LEFT_ALIGNMENT);
        feature.setMaximumSize(new Dimension(430, 65));

        JLabel badge = new JLabel(number, SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setBackground(new Color(48, 67, 96));
        badge.setForeground(new Color(137, 218, 198));
        badge.setFont(new Font("SansSerif", Font.BOLD, 13));
        badge.setPreferredSize(new Dimension(42, 42));

        JPanel labels = new JPanel();
        labels.setOpaque(false);
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(title);
        name.setForeground(WHITE);
        name.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel detail = new JLabel(subtitle);
        detail.setForeground(new Color(190, 203, 219));
        detail.setFont(new Font("SansSerif", Font.PLAIN, 11));

        labels.add(name);
        labels.add(Box.createVerticalStrut(5));
        labels.add(detail);

        feature.add(badge, BorderLayout.WEST);
        feature.add(labels, BorderLayout.CENTER);

        return feature;
    }

    private JPanel createFormPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(390, 500));

        JLabel title = new JLabel("Welcome back");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel("Sign in or create your student account");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(Box.createVerticalStrut(8));
        heading.add(subtitle);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 12));
        tabs.setForeground(NAVY);
        tabs.addTab("Sign in", createLoginPanel());
        tabs.addTab("Create account", createRegisterPanel());

        card.add(heading, BorderLayout.NORTH);
        card.add(tabs, BorderLayout.CENTER);

        outer.add(card);
        return outer;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        panel.setBorder(new EmptyBorder(18, 3, 3, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);

        JLabel intro = new JLabel("Continue your progress");
        intro.setFont(new Font("SansSerif", Font.BOLD, 15));
        intro.setForeground(TEXT);

        loginIdentifier = new JTextField();
        styleField(loginIdentifier);

        loginPassword = new JPasswordField();
        styleField(loginPassword);

        JCheckBox showPassword = new JCheckBox("Show password");
        showPassword.setBackground(WHITE);
        showPassword.setForeground(MUTED);
        showPassword.setFont(new Font("SansSerif", Font.PLAIN, 11));
        showPassword.addActionListener(e -> {
            loginPassword.setEchoChar(
                    showPassword.isSelected() ? '\0' : '\u2022');
        });

        JButton loginButton = createButton("Sign in");
        loginButton.addActionListener(e -> login());

        int row = 0;
        addRow(panel, gbc, row++, intro);
        addRow(panel, gbc, row++, makeLabel("Username or email"));
        addRow(panel, gbc, row++, loginIdentifier);
        addRow(panel, gbc, row++, makeLabel("Password"));
        addRow(panel, gbc, row++, loginPassword);
        addRow(panel, gbc, row++, showPassword);
        addRow(panel, gbc, row++, loginButton);

        JLabel hint = new JLabel(
                "<html><center>Use the username or email and password<br>"
                        + "you registered with.</center></html>",
                SwingConstants.CENTER);
        hint.setForeground(MUTED);
        hint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        addRow(panel, gbc, row, hint);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        panel.setBorder(new EmptyBorder(8, 3, 3, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 0, 3, 0);

        nameField = new JTextField();
        emailField = new JTextField();
        departmentField = new JTextField();
        yearField = new JTextField();
        usernameField = new JTextField();
        registerPassword = new JPasswordField();

        styleField(nameField);
        styleField(emailField);
        styleField(departmentField);
        styleField(yearField);
        styleField(usernameField);
        styleField(registerPassword);

        int row = 0;
        addRow(panel, gbc, row++, makeLabel("Full name"));
        addRow(panel, gbc, row++, nameField);
        addRow(panel, gbc, row++, makeLabel("College email"));
        addRow(panel, gbc, row++, emailField);
        addRow(panel, gbc, row++, makeLabel("Department"));
        addRow(panel, gbc, row++, departmentField);
        addRow(panel, gbc, row++, makeLabel("Study year (1–10)"));
        addRow(panel, gbc, row++, yearField);
        addRow(panel, gbc, row++, makeLabel("Username"));
        addRow(panel, gbc, row++, usernameField);
        addRow(panel, gbc, row++, makeLabel("Password (minimum 8 characters)"));
        addRow(panel, gbc, row++, registerPassword);

        JButton registerButton = createButton("Create account");
        registerButton.addActionListener(e -> register());
        addRow(panel, gbc, row, registerButton);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        return wrapRegisterPanel(scroll);
    }

    private JPanel wrapRegisterPanel(JScrollPane scroll) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(WHITE);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        return label;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(new Color(250, 251, 253));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 233)),
                new EmptyBorder(8, 10, 8, 10)));
        field.setPreferredSize(new Dimension(240, 38));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(WHITE);
        button.setBackground(TEAL);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 42));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc,
                        int row, Component component) {
        gbc.gridy = row;
        panel.add(component, gbc);
    }

    private void login() {
        char[] password = loginPassword.getPassword();

        try {
            Student student = authService.login(
                    loginIdentifier.getText().trim(), password);

            openDashboard(student);

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (RuntimeException ex) {
            showError("Login failed. Check your username, password, and database connection.");
        } finally {
            Arrays.fill(password, '\0');
            loginPassword.setText("");
        }
    }

    private void register() {
        char[] password = registerPassword.getPassword();

        try {
            int year = Integer.parseInt(yearField.getText().trim());

            Student student = authService.register(
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    departmentField.getText().trim(),
                    year,
                    usernameField.getText().trim(),
                    password
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Your SkillNavi account has been created!",
                    "Registration successful",
                    JOptionPane.INFORMATION_MESSAGE);

            openDashboard(student);

        } catch (NumberFormatException ex) {
            showError("Enter a valid study year.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (RuntimeException ex) {
            showError("Registration failed. Check your database connection and account details.");
        } finally {
            Arrays.fill(password, '\0');
            registerPassword.setText("");
        }
    }

    
private void openDashboard(Student student) {
    MainFrame mainFrame = new MainFrame();

    // Set the logged-in student first.
    mainFrame.setCurrentStudent(student);

    // Then open the dashboard.
    mainFrame.showScreen(MainFrame.DASHBOARD);

    mainFrame.setVisible(true);
    dispose();
}
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "SkillNavi",
                JOptionPane.ERROR_MESSAGE);
    }
}
