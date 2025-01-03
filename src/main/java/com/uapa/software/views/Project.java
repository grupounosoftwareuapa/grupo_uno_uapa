package com.uapa.software.views;

import javax.swing.*;
import java.awt.*;

public class Project extends JFrame {

    public Project() {
        // Configure JFrame
        setTitle("Project View");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(400, 300)); // Set minimum size
        setLocationRelativeTo(null); // Center the window on the screen

        // Set Look and Feel for modern design
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main Container with BorderLayout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Center Panel (Form)
        JPanel centerPanel = createFormPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        contentPane.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue background

        JLabel titleLabel = new JLabel("Project Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Project Name
        JLabel lblProjectName = new JLabel("Project Name:");
        lblProjectName.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblProjectName, gbc);

        JTextField txtProjectName = new JTextField();
        txtProjectName.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(txtProjectName, gbc);

        // Project Description
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(lblDescription, gbc);

        JTextArea txtDescription = new JTextArea(4, 20);
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtDescription);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);

        // Start Date
        JLabel lblStartDate = new JLabel("Start Date:");
        lblStartDate.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(lblStartDate, gbc);

        JTextField txtStartDate = new JTextField();
        txtStartDate.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        formPanel.add(txtStartDate, gbc);

        return formPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(240, 240, 240)); // Light gray background

        JButton btnSave = new JButton("Save");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(btnSave);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(btnCancel);

        return footerPanel;
    }
}
