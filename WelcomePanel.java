package com.skillnavi.ui;

import com.skillnavi.model.Student;
import com.skillnavi.util.ValidationUtil;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class WelcomePanel extends JPanel {
    private final MainFrame mainFrame;
    private final JTextField emailField = new JTextField(24);

    public WelcomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(36, 48, 36, 48));

        JLabel heading = new JLabel("Welcome to SkillNavi");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(new JLabel("Enter your email to continue:"), constraints);
        constraints.gridx = 1;
        formPanel.add(emailField, constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton continueButton = new JButton("Continue with Existing Profile");
        continueButton.addActionListener(event -> continueWithProfile());
        JButton createButton = new JButton("Create New Profile");
        createButton.addActionListener(event -> mainFrame.startNewProfile());
        buttonPanel.add(continueButton);
        buttonPanel.add(createButton);
        constraints.gridx = 1;
        constraints.gridy = 1;
        formPanel.add(buttonPanel, constraints);

        add(formPanel, BorderLayout.CENTER);
        add(new JLabel("This screen identifies a student profile by email; it is not password-based authentication."),
                BorderLayout.SOUTH);
    }

    private void continueWithProfile() {
        try {
            String email = ValidationUtil.requireValidEmail(emailField.getText());
            Student student = mainFrame.getStudentDAO().findByEmail(email).orElse(null);
            if (student == null) {
                mainFrame.showError("No profile was found for this email. Create a new profile first.");
                return;
            }
            mainFrame.setCurrentStudent(student);
            mainFrame.showScreen(MainFrame.PROFILE);
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }
}
