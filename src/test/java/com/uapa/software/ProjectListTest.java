package com.uapa.software;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uapa.software.views.ProjectList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Container;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ProjectListTest {

    private ProjectList projectList;

    @BeforeEach
    void setUp() {
        projectList = new ProjectList();
    }

    @Test
    void testAddProjectUsingReflection() throws Exception {
        // Get the "addProject" method using reflection
        Method addProjectMethod = ProjectList.class.getDeclaredMethod("addProject", String.class, String.class, String.class);
        addProjectMethod.setAccessible(true);

        // Invoke the method with test data
        addProjectMethod.invoke(projectList, "Test Project", "Test Description", "2025-01-01");

        // Access the JTable inside the ProjectList
        JScrollPane scrollPane = (JScrollPane) ((Container) projectList.getContentPane().getComponent(1)).getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Check if the project was added
        boolean projectFound = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if ("Test Project".equals(model.getValueAt(i, 0))) {
                projectFound = true;
                break;
            }
        }

        assertTrue(projectFound, "El proyecto no fue encontrado en la tabla");
    }

    @Test
    void testLoadExampleProjects() {
        // Access the JTable inside the ProjectList
        JScrollPane scrollPane = (JScrollPane) ((Container) projectList.getContentPane().getComponent(1)).getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Assert the number of example projects and their data
        assertEquals(3, model.getRowCount(), "El número de proyectos cargados no es el esperado");
        assertEquals("Project A", model.getValueAt(0, 0), "El primer proyecto no coincide con 'Project A'");
        assertEquals("Project B", model.getValueAt(1, 0), "El segundo proyecto no coincide con 'Project B'");
        assertEquals("Project C", model.getValueAt(2, 0), "El tercer proyecto no coincide con 'Project C'");
    }

}
