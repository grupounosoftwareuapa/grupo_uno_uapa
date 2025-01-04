package com.uapa.software.views;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProjectList extends JFrame {

    private JTable projectTable;
    private DefaultTableModel tableModel;

    public ProjectList() {
        // Configure JFrame
        setTitle("Project List");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window on the screen

        // Set Look and Feel for modern design
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main container with BorderLayout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        contentPane.add(tablePanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        contentPane.add(footerPanel, BorderLayout.SOUTH);

        // Load example projects
        loadExampleProjects();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue background

        JLabel titleLabel = new JLabel("Project List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Table Model and JTable
        tableModel = new DefaultTableModel(new Object[]{"Project Name", "Description", "Start Date"}, 0);
        projectTable = new JTable(tableModel);
        projectTable.setFont(new Font("Arial", Font.PLAIN, 14));
        projectTable.setRowHeight(20);

        // Add JScrollPane for table
        JScrollPane scrollPane = new JScrollPane(projectTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAdd = new JButton("Add Project");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.addActionListener(e -> addProject("New Project", "Example Description", "2025-01-01"));

        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Arial", Font.BOLD, 14));
        btnClose.addActionListener(e -> dispose());

        footerPanel.add(btnAdd);
        footerPanel.add(btnClose);

        return footerPanel;
    }

    private void addProject(String name, String description, String startDate) {
        tableModel.addRow(new Object[]{name, description, startDate});
    }

    private void loadExampleProjects() {
        addProject("Project A", "Description of Project A", "2025-01-10");
        addProject("Project B", "Description of Project B", "2025-02-15");
        addProject("Project C", "Description of Project C", "2025-03-20");
    }

}
