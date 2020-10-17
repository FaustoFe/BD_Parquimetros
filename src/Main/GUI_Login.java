package Main;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

@SuppressWarnings("serial")
public class GUI_Login extends javax.swing.JInternalFrame{
	
	private JLabel lblLegajo, lblPassword, lblTitulo, lblAdmin;
	private JButton btnConectarInspector, btnConectarAdmin,btnSalir;
	private JTextField txtLegajo;
	private JPasswordField txtContraseña;
	//private Login login;
	
	public GUI_Login() {
		//login = new Login();
		
		getContentPane().setBackground(new Color(51, 204, 204));
		setTitle("Proyecto BD - Parquimetros");
		getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		getContentPane().setLayout(null);
		
		// Labels
		lblTitulo = new JLabel("Panel de logueo");
		lblTitulo.setBounds(173, 11, 272, 69);
		lblTitulo.setFont(new Font("Dubai", Font.PLAIN, 40));
		getContentPane().add(lblTitulo);
		
		lblAdmin = new JLabel("Conectar como admin");
		lblAdmin.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblAdmin.setBounds(453, 358, 111, 20);
		getContentPane().add(lblAdmin);		
		
		lblLegajo = new JLabel("Legajo");
		lblLegajo.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblLegajo.setBounds(137, 128, 59, 35);
		getContentPane().add(lblLegajo);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblPassword.setBounds(137, 159, 59, 35);
		getContentPane().add(lblPassword);
		
		// Txt & password
		txtLegajo = new JTextField();
		txtLegajo.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtLegajo.setToolTipText("Ingrese legajo");
		txtLegajo.setBounds(210, 135, 136, 20);
		getContentPane().add(txtLegajo);
		txtLegajo.setColumns(10);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtContraseña.setEchoChar('*');
		txtContraseña.setToolTipText("Ingrese contrase\u00F1a");
		txtContraseña.setBounds(210, 166, 136, 20);
		getContentPane().add(txtContraseña);
		
		// Botones
		btnConectarInspector = new JButton("Conectar");
		btnConectarInspector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean resultado;
				resultado = true; //login.conectar(txtLegajo.getText(), txtContraseña.getText());
				
				if(resultado) {
					//Exito al conectarse a la base de datos
					JOptionPane.showMessageDialog(null, "Conexión exitosa","Éxito", JOptionPane.INFORMATION_MESSAGE);
					//txtContraseña.setText("");
					//txtLegajo.setText("");
				}
				else {
					//Error al conectarse
					JOptionPane.showMessageDialog(null, "Intentelo de nuevo","Error", JOptionPane.ERROR_MESSAGE);
					txtContraseña.setText("");
					txtLegajo.setText("");
				}				
			}
		});
		btnConectarInspector.setToolTipText("Conectarse como inspector");
		btnConectarInspector.setFont(new Font("Dubai", Font.BOLD, 12));
		btnConectarInspector.setBounds(366, 135, 89, 51);
		getContentPane().add(btnConectarInspector);
		
		btnConectarAdmin = new JButton("Conectar");
		btnConectarAdmin.setToolTipText("Conectarse como administrador");
		btnConectarAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean resultado = true;
				//resultado = login.conectar("admin");
				if(resultado) {
					//Exito al entrar
					JOptionPane.showMessageDialog(null, "Conexión exitosa","Éxito", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					//Intentar de nuevo
					JOptionPane.showMessageDialog(null, "Intentelo de nuevo","Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		btnConectarAdmin.setFont(new Font("Dubai", Font.BOLD, 12));
		btnConectarAdmin.setBounds(463, 379, 89, 51);
		getContentPane().add(btnConectarAdmin);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//login.salir();
				System.exit(0);
			}
		});
		btnSalir.setToolTipText("Salir de la app");
		btnSalir.setFont(new Font("Dubai", Font.BOLD, 12));
		btnSalir.setBounds(28, 379, 89, 51);
		getContentPane().add(btnSalir);
		
		
		//inicializarGUI();
	}
		
		
	
	private void inicializarGUI() {
	
	}
}
