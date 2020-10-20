package Main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI_Login {

	private JFrame frame;
	
	private JLabel lblLegajo, lblPassword, lblTitulo, lblAdmin;
	private JButton btnConectarInspector, btnConectarAdmin, btnSalir;
	private JTextField txtLegajo;
	private JPasswordField txtContraseña;
	
	//Parte logica del login
	private Login login;
	private Connection cnx;
	
	
	/*
	public static void mostrarMensaje(String s) {
		JOptionPane.showMessageDialog(null, "asd","asd", JOptionPane.INFORMATION_MESSAGE);
	}
	*/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Login ventana = new GUI_Login();
					ventana.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		

	public GUI_Login() {
		Login login = new Login();
		inicializarGUI();
	}

	private void inicializarGUI() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 589, 354);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setBackground(new Color(51, 204, 204));
		frame.setTitle("Proyecto BD - Parquimetros");
		frame.getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.getContentPane().setLayout(null);		
		
				
		// Labels
		lblTitulo = new JLabel("Panel de logueo");
		lblTitulo.setBounds(160, 11, 272, 69);
		lblTitulo.setFont(new Font("Dubai", Font.PLAIN, 40));
		frame.getContentPane().add(lblTitulo);
		
		lblAdmin = new JLabel("Conectar como admin");
		lblAdmin.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblAdmin.setBounds(448, 216, 111, 20);
		frame.getContentPane().add(lblAdmin);		
		
		lblLegajo = new JLabel("Legajo");
		lblLegajo.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblLegajo.setBounds(137, 128, 59, 35);
		frame.getContentPane().add(lblLegajo);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblPassword.setBounds(137, 159, 59, 35);
		frame.getContentPane().add(lblPassword);
		
		// Txt & password
		txtLegajo = new JTextField();
		txtLegajo.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtLegajo.setToolTipText("Ingrese legajo");
		txtLegajo.setBounds(210, 135, 136, 20);
		frame.getContentPane().add(txtLegajo);
		txtLegajo.setColumns(10);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtContraseña.setEchoChar('*');
		txtContraseña.setToolTipText("Ingrese contrase\u00F1a");
		txtContraseña.setBounds(210, 166, 136, 20);
		frame.getContentPane().add(txtContraseña);
		
		// Botones
		btnConectarInspector = new JButton("Conectar");
		btnConectarInspector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cnx = login.conectarBD(txtLegajo.getText(), txtContraseña.getText());
				
				if(cnx == null) { //Exito al conectarse a la base de datos
					JOptionPane.showMessageDialog(null, "Conexión exitosa","Éxito", JOptionPane.INFORMATION_MESSAGE);
					txtContraseña.setText("");
					txtLegajo.setText("");
					frame.setVisible(false);
					
					GUI_Inspector gi = new GUI_Inspector(cnx);					
				}
				else { //Error al conectarse
					JOptionPane.showMessageDialog(null, "Intentelo de nuevo","Error", JOptionPane.ERROR_MESSAGE);
					txtContraseña.setText("");
					txtLegajo.setText("");
				}				
			}
		});
		btnConectarInspector.setToolTipText("Conectarse como inspector");
		btnConectarInspector.setFont(new Font("Dubai", Font.BOLD, 12));
		btnConectarInspector.setBounds(366, 135, 89, 51);
		frame.getContentPane().add(btnConectarInspector);
		
		btnConectarAdmin = new JButton("Conectar");
		btnConectarAdmin.setToolTipText("Conectarse como administrador");
		btnConectarAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnx = login.conectarBD("admin");
				if(cnx == null) { //Exito al entrar
					JOptionPane.showMessageDialog(null, "Conexión exitosa","Éxito", JOptionPane.INFORMATION_MESSAGE);
					
					frame.setVisible(false);
					GUI_Admin ga = new GUI_Admin(cnx);
					
				}
				else { //Intentar de nuevo
					JOptionPane.showMessageDialog(null, "Intentelo de nuevo","Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		btnConectarAdmin.setFont(new Font("Dubai", Font.BOLD, 12));
		btnConectarAdmin.setBounds(458, 237, 89, 51);
		frame.getContentPane().add(btnConectarAdmin);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.desconectarBD();
				System.exit(0);
			}
		});
		btnSalir.setToolTipText("Salir de la app");
		btnSalir.setFont(new Font("Dubai", Font.BOLD, 12));
		btnSalir.setBounds(23, 237, 89, 51);
		frame.getContentPane().add(btnSalir);		
		
		
		
	}
}
