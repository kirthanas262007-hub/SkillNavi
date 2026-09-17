package com.skillnavi.ui;

import com.skillnavi.model.JobRole;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class JobRolePanel extends JPanel {
    private final MainFrame mainFrame;
    private final DefaultListModel<JobRole> jobRoleListModel = new DefaultListModel<>();
    private final JList<JobRole> jobRoleList = new JList<>(jobRoleListModel);
    private final JTextArea descriptionArea = new JTextArea(6, 30);

    public JobRolePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel heading = new JLabel("Select Target Job Role");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        add(heading, BorderLayout.NORTH);

        jobRoleList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jobRoleList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                updateDescription();
            }
        });
        add(new JScrollPane(jobRoleList), BorderLayout.CENTER);

        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
        rightPanel.add(new JLabel("Role description"), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton saveButton = new JButton("Save Target Role");
        saveButton.addActionListener(event -> saveSelectedRole());
        JButton nextButton = new JButton("Run Skill Analysis");
        nextButton.addActionListener(event -> mainFrame.showScreen(MainFrame.ANALYSIS));
        buttons.add(saveButton);
        buttons.add(nextButton);
        add(buttons, BorderLayout.SOUTH);
    }

    public void refreshData() {
        try {
            JobRole previouslySelected = mainFrame.getSession().getSelectedJobRole();
            jobRoleListModel.clear();
            for (JobRole jobRole : mainFrame.getJobRoleDAO().findAll()) {
                jobRoleListModel.addElement(jobRole);
                if (previouslySelected != null && jobRole.getJobId().equals(previouslySelected.getJobId())) {
                    jobRoleList.setSelectedValue(jobRole, true);
                }
            }
            updateDescription();
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }

    private void updateDescription() {
        JobRole selectedRole = jobRoleList.getSelectedValue();
        descriptionArea.setText(selectedRole == null ? "" : selectedRole.getDescription());
    }

    private void saveSelectedRole() {
        try {
            JobRole selectedRole = jobRoleList.getSelectedValue();
            if (selectedRole == null) {
                throw new IllegalArgumentException("Select a target job role.");
            }
            int studentId = mainFrame.getSession().getCurrentStudent().getStudentId();
            mainFrame.getStudentTargetRoleDAO().saveSelectedRole(studentId, selectedRole.getJobId());
            mainFrame.getSession().setSelectedJobRole(selectedRole);
            mainFrame.showInfo("Target role saved successfully.");
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }
}
