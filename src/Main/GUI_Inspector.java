package Main;

import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JButton;

public class GUI_Inspector {

	private JFrame frame;
	
	private Inspector inspector;


	public GUI_Inspector(Connection cnx) {
		inspector = new Inspector(cnx);
		inicializarGUI();
		this.frame.setVisible(true);
	}


	private void inicializarGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(147, 118, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
