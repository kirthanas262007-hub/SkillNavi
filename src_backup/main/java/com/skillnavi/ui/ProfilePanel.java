package com.skillnavi.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.skillnavi.model.Student;

public class ProfilePanel extends JPanel {

    private final MainFrame mainFrame;

    private final JTextField nameField = new JTextField(28);
    private final JTextField emailField = new JTextField(28);
    private final JTextField departmentField = new JTextField(28);
    private final JTextField yearField = new JTextField(28);

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
        addFormRow(form, constraints, 2, "Department:", departmentField);
        addFormRow(form, constraints, 3, "Year:", yearField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton saveButton = new JButton("Save Profile");
        saveButton.addActionListener(event -> saveProfile());

        JButton nextButton = new JButton("Manage Skills");
        nextButton.addActionListener(
                event -> mainFrame.showScreen(MainFrame.SKILLS)
        );

        buttons.add(saveButton);
        buttons.add(nextButton);

        constraints.gridx = 1;
        constraints.gridy = 4;
        form.add(buttons, constraints);

        add(form, BorderLayout.CENTER);
    }

    private void addFormRow(
            JPanel form,
            GridBagConstraints constraints,
            int row,
            String label,
            JTextField field) {

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
            departmentField.setText("");
            yearField.setText("");
            return;
        }

        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        departmentField.setText(student.getDepartment());
        yearField.setText(String.valueOf(student.getYear()));
    }

    private void saveProfile() {

        try {

            Student currentStudent =
                    mainFrame.getSession().getCurrentStudent();

            String name = nameField.getText();
            String email = emailField.getText();
            String department = departmentField.getText();

            Integer year = Integer.parseInt(yearField.getText());

            if (currentStudent == null) {

                currentStudent = new Student(
                        name,
                        email,
                        department,
                        year
                );

                mainFrame.getStudentDAO().create(currentStudent);

            } else {

                currentStudent.setName(name);
                currentStudent.setEmail(email);
                currentStudent.setDepartment(department);
                currentStudent.setYear(year);

                mainFrame.getStudentDAO().update(currentStudent);
            }

            mainFrame.setCurrentStudent(currentStudent);

            mainFrame.showInfo(
                    "Student profile saved successfully."
            );

        } catch (NumberFormatException exception) {

            mainFrame.showError(
                    "Year must be a number."
            );

        } catch (RuntimeException exception) {

            exception.printStackTrace();

    mainFrame.showError(
            exception.getMessage()
            );
        }
    }
}