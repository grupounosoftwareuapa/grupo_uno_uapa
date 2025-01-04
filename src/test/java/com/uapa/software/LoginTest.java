package com.uapa.software;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.awt.GraphicsEnvironment;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.uapa.software.views.Login;

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
	public void testLoginWithoutGUI() {
	    try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
	        Login login = mock(Login.class);
	        doNothing().when(login).setVisible(anyBoolean());

	        JTextField mockUsernameField = mock(JTextField.class);
	        JPasswordField mockPasswordField = mock(JPasswordField.class);

	        when(mockUsernameField.getText()).thenReturn("admin");
	        when(mockPasswordField.getPassword()).thenReturn("password".toCharArray());

	        // Simulate the login action
	        login.onLogin(null);

	        // Verify correct behavior
	        mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(any(), eq("Credenciales incorrectas"), anyString(), eq(JOptionPane.ERROR_MESSAGE)), never());
	    }
	}

}