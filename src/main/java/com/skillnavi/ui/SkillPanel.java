package com.skillnavi.ui;

import com.skillnavi.model.Skill;
import com.skillnavi.model.Student;
import com.skillnavi.model.StudentSkill;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.ui.table.SkillTableModel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class SkillPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JComboBox<Skill> skillComboBox = new JComboBox<>();
    private final JComboBox<ProficiencyLevel> proficiencyComboBox = new JComboBox<>(ProficiencyLevel.values());
    private final SkillTableModel skillTableModel = new SkillTableModel();
    private final JTable skillsTable = new JTable(skillTableModel);

    public SkillPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel heading = new JLabel("Manage Current Skills");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        add(heading, BorderLayout.NORTH);

        JPanel centrePanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Skill:"));
        inputPanel.add(skillComboBox);
        inputPanel.add(new JLabel("Proficiency:"));
        inputPanel.add(proficiencyComboBox);
        JButton saveButton = new JButton("Add / Update Skill");
        saveButton.addActionListener(event -> saveSkill());
        inputPanel.add(saveButton);
        centrePanel.add(inputPanel, BorderLayout.NORTH);

        skillsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        centrePanel.add(new JScrollPane(skillsTable), BorderLayout.CENTER);
        add(centrePanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton removeButton = new JButton("Remove Selected Skill");
        removeButton.addActionListener(event -> removeSelectedSkill());
        JButton nextButton = new JButton("Select Target Role");
        nextButton.addActionListener(event -> mainFrame.showScreen(MainFrame.JOB_ROLE));
        buttons.add(removeButton);
        buttons.add(nextButton);
        add(buttons, BorderLayout.SOUTH);
    }

    public void refreshData() {
        try {
            skillComboBox.removeAllItems();
            for (Skill skill : mainFrame.getSkillDAO().findAll()) {
                skillComboBox.addItem(skill);
            }

            Student student = mainFrame.getSession().getCurrentStudent();
            skillTableModel.setStudentSkills(mainFrame.getStudentSkillDAO().findByStudentId(student.getStudentId()));
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }

    private void saveSkill() {
        try {
            Skill selectedSkill = (Skill) skillComboBox.getSelectedItem();
            ProficiencyLevel selectedLevel = (ProficiencyLevel) proficiencyComboBox.getSelectedItem();
            if (selectedSkill == null || selectedLevel == null) {
                throw new IllegalArgumentException("Select both a skill and proficiency level.");
            }

            Student student = mainFrame.getSession().getCurrentStudent();
            mainFrame.getStudentSkillDAO().saveOrUpdate(
                    new StudentSkill(student.getStudentId(), selectedSkill, selectedLevel)
            );
            mainFrame.clearAnalysis();
            refreshData();
            mainFrame.showInfo("Skill saved successfully.");
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }

    private void removeSelectedSkill() {
        int selectedRow = skillsTable.getSelectedRow();
        if (selectedRow < 0) {
            mainFrame.showError("Select a skill to remove.");
            return;
        }

        int decision = JOptionPane.showConfirmDialog(this, "Remove the selected skill?", "SkillNavi",
                JOptionPane.YES_NO_OPTION);
        if (decision != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            StudentSkill selectedSkill = skillTableModel.getStudentSkillAt(selectedRow);
            mainFrame.getStudentSkillDAO().delete(selectedSkill.getStudentId(), selectedSkill.getSkill().getSkillId());
            mainFrame.clearAnalysis();
            refreshData();
        } catch (RuntimeException exception) {
            mainFrame.showError(exception.getMessage());
        }
    }
}
