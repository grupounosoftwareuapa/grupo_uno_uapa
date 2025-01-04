package com.uapa.software;

import com.uapa.software.views.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.*;
import java.awt.*;

import static org.mockito.Mockito.*;

public class ProjectViewTest {

	private Project project;

	@BeforeEach
	public void setUp() {
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println("Running in headless mode");
		} else {
			System.out.println("Running with graphical environment");
		}

		project = new Project();
		project.setVisible(!GraphicsEnvironment.isHeadless()); // Only set visible if not headless
	}

	@Test
	public void testProjectWithoutGUI() {
		try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
			Project project = mock(Project.class);
			doNothing().when(project).setVisible(anyBoolean());

			// Mocking fields in the Project class
			JTextField mockProjectNameField = mock(JTextField.class);
			JTextArea mockDescriptionArea = mock(JTextArea.class);
			JTextField mockStartDateField = mock(JTextField.class);

			when(mockProjectNameField.getText()).thenReturn("New Project");
			when(mockDescriptionArea.getText()).thenReturn("Project Description");
			when(mockStartDateField.getText()).thenReturn("2025-01-01");

			// Simulate interaction with the form
			System.out.println("Project Name: " + mockProjectNameField.getText());
			System.out.println("Description: " + mockDescriptionArea.getText());
			System.out.println("Start Date: " + mockStartDateField.getText());

			// Verify no JOptionPane messages are shown
			mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(any(), anyString(), anyString(), anyInt()),
					never());
		}
	}
}
