package com.uapa.software.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Login extends JFrame {

    // Declaración de los campos como variables de instancia
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public Login() {
        // Configuración de la ventana
        setTitle("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false); // Deshabilitar maximizar
        setLocationRelativeTo(null); // Centrar la ventana

        // Establecer Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Etiqueta Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblUsername, gbc);

        // Campo de texto para Username
        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtUsername, gbc);

        // Etiqueta Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        // Campo de texto para Password
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtPassword, gbc);

        // Botón Login
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.addActionListener(this::onLogin);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        // Panel inferior con botones de "Olvidé mi contraseña" y "Registrarse"
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JButton btnForgotPassword = new JButton("Olvidé mi contraseña");
        JButton btnRegister = new JButton("Registrarse");

        btnForgotPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        btnRegister.setFont(new Font("Arial", Font.PLAIN, 12));

        bottomPanel.add(btnForgotPassword);
        bottomPanel.add(btnRegister);

        // Añadir los paneles a la ventana
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    public void onLogin(ActionEvent event) {
        // Obtener el texto ingresado
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Lógica de inicio de sesión (ejemplo simple)
        if (username.equals("admin") && password.equals("password")) {
        	
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
