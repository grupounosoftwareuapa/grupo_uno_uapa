package com.uapa.software;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.uapa.software.views.Login;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginTest {

	private Login login;

	@BeforeEach
	public void setUp() {
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println("Running in headless mode");
		} else {
			System.out.println("Running with graphical environment");
		}

		login = new Login();
		login.setVisible(!GraphicsEnvironment.isHeadless()); // Only set visible if not headless
	}

	@Test
	public void testLoginWithValidCredentials() {
		// Mock JOptionPane
		try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
			// Locate components
			JTextField txtUsername = findComponent(login.getContentPane(), JTextField.class);
			JPasswordField txtPassword = findComponent(login.getContentPane(), JPasswordField.class);
			JButton btnLogin = findButton(login.getContentPane(), "Login");

			assertNotNull(txtUsername, "Username field not found");
			assertNotNull(txtPassword, "Password field not found");
			assertNotNull(btnLogin, "Login button not found");

			// Simulate valid user input
			txtUsername.setText("admin");
			txtPassword.setText("password");

			// Trigger login button click
			btnLogin.doClick();

			// Verify no error dialog is shown
			mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(any(), eq("Credenciales incorrectas"),
					eq("Error"), eq(JOptionPane.ERROR_MESSAGE)), never());
		}
	}

	@Test
	public void testLoginWithInvalidCredentials() {
		// Mock JOptionPane
		try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
			// Locate components
			JTextField txtUsername = findComponent(login.getContentPane(), JTextField.class);
			JPasswordField txtPassword = findComponent(login.getContentPane(), JPasswordField.class);
			JButton btnLogin = findButton(login.getContentPane(), "Login");

			assertNotNull(txtUsername, "Username field not found");
			assertNotNull(txtPassword, "Password field not found");
			assertNotNull(btnLogin, "Login button not found");

			// Simulate invalid user input
			txtUsername.setText("user");
			txtPassword.setText("wrong");

			// Trigger login button click
			btnLogin.doClick();

			// Verify error dialog is shown
			mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(any(), eq("Credenciales incorrectas"),
					eq("Error"), eq(JOptionPane.ERROR_MESSAGE)));
		}
	}

	/**
	 * Utility method to find a component of a specific type in a container
	 * hierarchy.
	 */
	private <T> T findComponent(Container container, Class<T> componentClass) {
		for (Component component : container.getComponents()) {
			if (componentClass.isInstance(component)) {
				return componentClass.cast(component);
			}
			if (component instanceof Container) {
				T found = findComponent((Container) component, componentClass);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	/**
	 * Utility method to find a button by its text in a container hierarchy.
	 */
	private JButton findButton(Container container, String buttonText) {
		for (Component component : container.getComponents()) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				if (button.getText().equals(buttonText)) {
					return button;
				}
			}
			if (component instanceof Container) {
				JButton found = findButton((Container) component, buttonText);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}
}