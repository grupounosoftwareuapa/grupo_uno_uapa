package com.uapa.software;

import javax.swing.SwingUtilities;

import com.uapa.software.views.Login;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
	}

}
