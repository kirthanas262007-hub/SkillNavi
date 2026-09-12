package com.skillnavi.ui;

import com.skillnavi.model.Student;

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

public class ProfilePanel extends JPanel {
    private final MainFrame mainFrame;
    private final JTextField nameField = new JTextField(28);
    private final JTextField emailField = new JTextField(28);
    private final JTextField educationField = new JTextField(28);

    public ProfilePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(28, 40, 28, 40));

        JLabel heading = new JLabel("Student Profile");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.anchor = GridBagConstraints.WEST;
        addFormRow(form, constraints, 0, "Name:", nameField);
        addFormRow(form, constraints, 1, "Email:", emailField);
        addFormRow(form, constraints, 2, "Education:", educationField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton saveButton = new JButton("Save Profile");
        saveButton.addActionListener(event -> saveProfile());
        JButton nextButton = new JButton("Manage Skills");
        nextButton.addActionListener(event -> mainFrame.showScreen(MainFrame.SKILLS));
        buttons.add(saveButton);
        buttons.add(nextButton);
        constraints.gridx = 1;
        constraints.gridy = 3;
        form.add(buttons, constraints);
        add(form, BorderLayout.CENTER);
    }

    private void addFormRow(JPanel form, GridBagConstraints constraints, int row,
                            String label, JTextField field) {
        constraints.gridx = 0;
        constraints.gridy = row;
        form.add(new JLabel(label), constraints);
        constraints.gridx = 1;
        form.add(field, constraints);
    }

    public void refreshData() {
        Student student = mainFrame.getSession().getCurrentStudent();
        if (student == null) {
            nameField.setText("");
            emailField.setText("");
            educationField.setText("");
            return;
        }
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        educationField.setText(student.getEducation());
    }

    private void saveProfile() {
        try {
            Student currentStudent = mainFrame.getSession().getCurrentStudent();
            if (currentStudent == null) {
                currentStudent = new Student(nameField.getText(), emailField.getText(), educationField.getText());
                mainFrame.getStudentDAO().create(currentStudent);
            } else {
                currentStudent.setName(nameField.getText());
                currentStudent.setEmail(emailField.getText());
                currentStudent.setEducation(educationField.getText());
                mainFrame.getStudentDAO().update(currentStudent);
            }
            mainFrame.setCurrentStudent(currentStudent);
            mainFrame.showInfo("Student profile saved successfully.");
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }
}
