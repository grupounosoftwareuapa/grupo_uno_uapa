package com.uapa.software;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.uapa.software.views.Login;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginTest {

	private Login login;

	@BeforeEach
	public void setUp() {
		login = new Login();
		login.setVisible(true); // Ensure the JFrame is visible for testing
	}

	@Test
	public void testLoginWithValidCredentials() throws Exception {
		// Mock JOptionPane
		try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
			// Use reflection to access private fields
			JTextField txtUsername = getField(login, "txtUsername", JTextField.class);
			JPasswordField txtPassword = getField(login, "txtPassword", JPasswordField.class);

			// Simulate user input
			txtUsername.setText("admin");
			txtPassword.setText("password");

			// Trigger login button click
			JButton btnLogin = findButton(login, "Login");
			assertNotNull(btnLogin, "Login button not found");
			btnLogin.doClick();

			// Verify that no error dialog is shown
			mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(any(), eq("Credenciales incorrectas"),
					eq("Error"), eq(JOptionPane.ERROR_MESSAGE)), never());
		}
	}

	@Test
	public void testLoginWithInvalidCredentials() throws Exception {
		// Mock JOptionPane
		try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
			// Use reflection to access private fields
			JTextField txtUsername = getField(login, "txtUsername", JTextField.class);
			JPasswordField txtPassword = getField(login, "txtPassword", JPasswordField.class);

			// Simulate user input
			txtUsername.setText("user");
			txtPassword.setText("wrong");

			// Trigger login button click
			JButton btnLogin = findButton(login, "Login");
			assertNotNull(btnLogin, "Login button not found");
			btnLogin.doClick();

			// Verify that the error dialog is shown
			mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(any(), eq("Credenciales incorrectas"),
					eq("Error"), eq(JOptionPane.ERROR_MESSAGE)));
		}
	}

	/**
	 * Utility method to use reflection to get a private field.
	 */
	private <T> T getField(Object object, String fieldName, Class<T> fieldType) throws Exception {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true); // Allow access to private fields
		return fieldType.cast(field.get(object));
	}

	/**
	 * Utility method to find a button by its text.
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
				JButton button = findButton((Container) component, buttonText);
				if (button != null) {
					return button;
				}
			}
		}
		return null;
	}
}
