
package com.skillnavi.ui;

import com.skillnavi.model.Student;
import com.skillnavi.util.ValidationUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomePanel extends JPanel {

    private final MainFrame mainFrame;
    private final JTextField emailField = new JTextField(25);

    private static final Color BACKGROUND = new Color(246, 248, 252);
    private static final Color NAVY = new Color(22, 38, 70);
    private static final Color BLUE = new Color(65, 125, 235);
    private static final Color TEXT = new Color(40, 49, 67);
    private static final Color MUTED = new Color(120, 131, 151);
    private static final Color BORDER = new Color(224, 230, 240);
    private static final Color WHITE = Color.WHITE;

    public WelcomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel content = new JPanel(new BorderLayout(0, 25));
        content.setBackground(BACKGROUND);

        content.add(createIntroPanel(), BorderLayout.NORTH);
        content.add(createFormCard(), BorderLayout.CENTER);
        content.add(createFooter(), BorderLayout.SOUTH);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        add(content, constraints);
    }

    private JPanel createIntroPanel() {
        JPanel intro = new JPanel();
        intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));
        intro.setBackground(BACKGROUND);

        JLabel eyebrow = new JLabel("YOUR CAREER JOURNEY STARTS HERE");
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 11));
        eyebrow.setForeground(BLUE);
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(
                "<html>Build skills.<br>Move towards your career goals.</html>"
        );
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(NAVY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        heading.setBorder(new EmptyBorder(12, 0, 10, 0));

        JLabel description = new JLabel(
                "<html>Understand your strengths, discover skill gaps, and plan<br>"
                        + "your next steps with SkillNavi.</html>"
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        description.setForeground(MUTED);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        intro.add(eyebrow);
        intro.add(heading);
        intro.add(description);

        return intro;
    }

    private JPanel createFormCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(28, 30, 28, 30)
        ));

        JLabel cardTitle = new JLabel("Get started");
        cardTitle.setFont(new Font("SansSerif", Font.BOLD, 21));
        cardTitle.setForeground(NAVY);
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cardDescription = new JLabel(
                "Enter your student email to continue."
        );
        cardDescription.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cardDescription.setForeground(MUTED);
        cardDescription.setBorder(new EmptyBorder(7, 0, 22, 0));
        cardDescription.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = new JLabel("Student email");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        emailLabel.setForeground(TEXT);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField.setForeground(TEXT);
        emailField.setCaretColor(BLUE);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        emailField.setPreferredSize(new Dimension(500, 44));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 12, 10, 12)
        ));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton continueButton = createButton(
                "Continue with Existing Profile", BLUE, WHITE
        );
        continueButton.setBackground(BLUE);
        continueButton.setForeground(WHITE);
        continueButton.setOpaque(true);
        continueButton.setContentAreaFilled(true);
        continueButton.setBorderPainted(false);
        continueButton.addActionListener(
                event -> continueWithProfile()
        );

        JButton createButton = createButton(
                "Create New Profile", WHITE, NAVY
        );
        createButton.setBackground(WHITE);
        createButton.setForeground(NAVY);
        createButton.setOpaque(true);
        createButton.setContentAreaFilled(true);
        createButton.setBorderPainted(true);
        createButton.addActionListener(
                event -> mainFrame.startNewProfile()
        );

        JPanel buttons = new JPanel(new GridLayout(1, 2, 12, 0));
        buttons.setBackground(WHITE);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        buttons.add(continueButton);
        buttons.add(createButton);

        JLabel note = new JLabel(
                "<html>Your email identifies your profile. "
                        + "This is not password-based authentication.</html>"
        );
        note.setFont(new Font("SansSerif", Font.PLAIN, 11));
        note.setForeground(MUTED);
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        note.setBorder(new EmptyBorder(18, 0, 0, 0));

        card.add(cardTitle);
        card.add(cardDescription);
        card.add(emailLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(emailField);
        card.add(Box.createVerticalStrut(20));
        card.add(buttons);
        card.add(note);

        return card;
    }

    private JButton createButton(
            String text, Color background, Color foreground) {

        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(12, 12, 12, 12)
        ));

        return button;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BACKGROUND);

        JLabel footerLabel = new JLabel(
                "SKILLNAVI  |  STUDENT SKILL GAP ANALYZER"
        );
        footerLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        footerLabel.setForeground(MUTED);

        footer.add(footerLabel, BorderLayout.WEST);

        return footer;
    }

    private void continueWithProfile() {
        try {
            String email = ValidationUtil.requireValidEmail(
                    emailField.getText()
            );

            Student student = mainFrame.getStudentDAO()
                    .findByEmail(email)
                    .orElse(null);

            if (student == null) {
                mainFrame.showError(
                        "No profile was found for this email. "
                                + "Create a new profile first."
                );
                return;
            }

            mainFrame.setCurrentStudent(student);
            mainFrame.showScreen(MainFrame.PROFILE);

        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }
}

