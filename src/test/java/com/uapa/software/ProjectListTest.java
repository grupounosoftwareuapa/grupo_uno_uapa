package com.uapa.software;

import com.uapa.software.views.ProjectList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field; // Import Field for reflection
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectListTest {

	private ProjectList projectList;

	@BeforeEach
	public void setUp() {
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println("Running in headless mode");
		} else {
			System.out.println("Running with graphical environment");
		}

		projectList = new ProjectList();
		projectList.setVisible(!GraphicsEnvironment.isHeadless()); // Only set visible if not headless
	}

	@Test
	public void testAddProjectUsingReflection() {
		try {
			// Access private method using reflection
			Method addProjectMethod = ProjectList.class.getDeclaredMethod("addProject", String.class, String.class,
					String.class);
			addProjectMethod.setAccessible(true);

			// Invoke the private method
			addProjectMethod.invoke(projectList, "Test Project", "Test Description", "2025-01-01");

			// Verify the project was added
			Field tableModelField = ProjectList.class.getDeclaredField("tableModel");
			tableModelField.setAccessible(true);
			DefaultTableModel tableModel = (DefaultTableModel) tableModelField.get(projectList);

			assertNotNull(tableModel, "Table model should not be null");
			assertEquals(4, tableModel.getRowCount(), "There should now be 4 rows in the table");
			assertArrayEquals(new Object[] { "Test Project", "Test Description", "2025-01-01" }, new Object[] {
					tableModel.getValueAt(3, 0), tableModel.getValueAt(3, 1), tableModel.getValueAt(3, 2) });
		} catch (Exception e) {
			fail("Failed to invoke addProject method: " + e.getMessage());
		}
	}

	@Test
	public void testLoadExampleProjects() {
		// Access the DefaultTableModel
		try {
			Field tableModelField = ProjectList.class.getDeclaredField("tableModel");
			tableModelField.setAccessible(true);
			DefaultTableModel tableModel = (DefaultTableModel) tableModelField.get(projectList);

			assertNotNull(tableModel, "Table model should not be null");

			// Verify the example projects are loaded
			assertEquals(3, tableModel.getRowCount(), "Three example projects should be loaded");

			assertArrayEquals(new Object[] { "Project A", "Description of Project A", "2025-01-10" }, new Object[] {
					tableModel.getValueAt(0, 0), tableModel.getValueAt(0, 1), tableModel.getValueAt(0, 2) });
		} catch (NoSuchFieldException | IllegalAccessException e) {
			fail("Failed to access tableModel field: " + e.getMessage());
		}
	}

	@Test
	public void testCloseButton() {
		try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
			JButton closeButton = findButtonByText("Close");

			assertNotNull(closeButton, "Close button should not be null");

			// Simulate clicking the Close button
			closeButton.doClick();

			// Verify no unexpected dialogs were shown
			mockedJOptionPane.verifyNoInteractions();
		}
	}

	/**
	 * Utility method to find a button by its displayed text.
	 */
	private JButton findButtonByText(String text) {
		Container container = projectList.getContentPane();
		for (Component component : container.getComponents()) {
			if (component instanceof JPanel) {
				JPanel panel = (JPanel) component;
				for (Component innerComponent : panel.getComponents()) {
					if (innerComponent instanceof JButton) {
						JButton button = (JButton) innerComponent;
						if (button.getText().equals(text)) {
							return button;
						}
					}
				}
			}
		}
		return null;
	}
}
