package com.skillnavi.ui;

import com.skillnavi.model.Progress;
import com.skillnavi.model.Skill;
import com.skillnavi.model.Student;
import com.skillnavi.model.StudentSkill;
import com.skillnavi.model.enums.ProgressStatus;
import com.skillnavi.ui.table.ProgressTableModel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class ProgressPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JComboBox<Skill> skillComboBox = new JComboBox<>();
    private final JSpinner percentageSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    private final JComboBox<ProgressStatus> statusComboBox = new JComboBox<>(ProgressStatus.values());
    private final ProgressTableModel progressTableModel = new ProgressTableModel();

    public ProgressPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel heading = new JLabel("Progress Tracking");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        add(heading, BorderLayout.NORTH);

        JPanel centrePanel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Skill:"));
        form.add(skillComboBox);
        form.add(new JLabel("Progress percentage:"));
        form.add(percentageSpinner);
        form.add(new JLabel("Status:"));
        form.add(statusComboBox);
        JButton saveButton = new JButton("Save Progress");
        saveButton.addActionListener(event -> saveProgress());
        form.add(saveButton);
        centrePanel.add(form, BorderLayout.NORTH);
        centrePanel.add(new JScrollPane(new JTable(progressTableModel)), BorderLayout.CENTER);
        add(centrePanel, BorderLayout.CENTER);
    }

    public void refreshData() {
        try {
            Student student = mainFrame.getSession().getCurrentStudent();
            skillComboBox.removeAllItems();
            for (StudentSkill studentSkill : mainFrame.getStudentSkillDAO().findByStudentId(student.getStudentId())) {
                skillComboBox.addItem(studentSkill.getSkill());
            }
            progressTableModel.setProgressEntries(mainFrame.getProgressDAO().findByStudentId(student.getStudentId()));
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }

    private void saveProgress() {
        try {
            Skill selectedSkill = (Skill) skillComboBox.getSelectedItem();
            ProgressStatus selectedStatus = (ProgressStatus) statusComboBox.getSelectedItem();
            if (selectedSkill == null || selectedStatus == null) {
                throw new IllegalArgumentException("Add a current skill before tracking its progress.");
            }
            int percentage = (Integer) percentageSpinner.getValue();
            int studentId = mainFrame.getSession().getCurrentStudent().getStudentId();
            mainFrame.getProgressDAO().saveOrUpdate(new Progress(studentId, selectedSkill, percentage, selectedStatus));
            refreshData();
            mainFrame.showInfo("Progress saved successfully.");
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }
}
