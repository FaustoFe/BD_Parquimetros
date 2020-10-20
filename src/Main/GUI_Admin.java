package Main;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;

public class GUI_Admin {

	private JFrame frame;
	
	private Admin admin;

	
	public GUI_Admin(Connection cnx) {
		admin = new Admin(cnx);
		inicializarGUI();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void inicializarGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
