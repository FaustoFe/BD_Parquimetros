package Main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI_Login {

	private JFrame frame;
	
	//Componentes graficos
	private JLabel lblLegajo, lblPassword, lblTitulo, lblAdmin;
	private JButton btnConectarInspector, btnConectarAdmin, btnSalir;
	private JTextField txtLegajo;
	private JPasswordField txtContraseña;
	
	
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
		frame.setLocationRelativeTo(null);
		
				
		// Labels
		lblTitulo = new JLabel("Panel de logueo");
		lblTitulo.setBounds(125, 11, 415, 69);
		lblTitulo.setFont(new Font("Dubai", Font.PLAIN, 40));
		frame.getContentPane().add(lblTitulo);
		
		lblAdmin = new JLabel("Conectar como admin");
		lblAdmin.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblAdmin.setBounds(447, 216, 159, 20);
		frame.getContentPane().add(lblAdmin);		
		
		lblLegajo = new JLabel("Legajo");
		lblLegajo.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblLegajo.setBounds(113, 128, 83, 35);
		frame.getContentPane().add(lblLegajo);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dubai", Font.PLAIN, 15));
		lblPassword.setBounds(113, 159, 83, 35);
		frame.getContentPane().add(lblPassword);
		
		// Txt & password
		txtLegajo = new JTextField();
		txtLegajo.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtLegajo.setToolTipText("Ingrese legajo");
		txtLegajo.setBounds(181, 135, 165, 20);
		frame.getContentPane().add(txtLegajo);
		txtLegajo.setColumns(10);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtContraseña.setEchoChar('*');
		txtContraseña.setToolTipText("Ingrese contrase\u00F1a");
		txtContraseña.setBounds(181, 166, 165, 20);
		frame.getContentPane().add(txtContraseña);
		
		// Botones
		btnConectarInspector = new JButton("Conectar");
		btnConectarInspector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombreInspector = Login.conectarBD(txtLegajo.getText(), txtContraseña.getText());
				
				
				if(Login.getConexion() != null) { //Exito al conectarse a la base de datos
					JOptionPane.showMessageDialog(null, "Conexión exitosa","Éxito", JOptionPane.INFORMATION_MESSAGE);

					inicializarInspector(Integer.parseInt(txtLegajo.getText()), nombreInspector);
				}
				else { //Error al conectarse
					JOptionPane.showMessageDialog(null, "Intentelo de nuevo","Error", JOptionPane.ERROR_MESSAGE);
				}
				
				//Limpio los campos
				txtContraseña.setText("");
				txtLegajo.setText("");
				
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
				String password = null;
				JPasswordField pf = new JPasswordField();
				int opcion = JOptionPane.showConfirmDialog(null, pf, "Ingrese contraseña de administrador",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				if (opcion == JOptionPane.OK_OPTION) {
					
					password = new String(pf.getPassword());
					Login.conectarBD(password);
					
					if(Login.getConexion() != null) { //Exito al entrar
						JOptionPane.showMessageDialog(null, "Conexión exitosa","Éxito", JOptionPane.PLAIN_MESSAGE);
						inicializarAdmin();
					}
					else { //Intentar de nuevo
						JOptionPane.showMessageDialog(null, "Intentelo de nuevo","Error", JOptionPane.ERROR_MESSAGE);	
					}
				}
			}
		});
		btnConectarAdmin.setFont(new Font("Dubai", Font.BOLD, 12));
		btnConectarAdmin.setBounds(458, 237, 89, 51);
		frame.getContentPane().add(btnConectarAdmin);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.desconectarBD();
				System.exit(0);
			}
		});
		btnSalir.setToolTipText("Salir de la app");
		btnSalir.setFont(new Font("Dubai", Font.BOLD, 12));
		btnSalir.setBounds(23, 237, 89, 51);
		frame.getContentPane().add(btnSalir);		
	}
	
	
	/*
	 * Inicializamos el Inspector
	 */
	private void inicializarInspector(int legajo, String nombreInspector) {
		GUI_Inspector gi = new GUI_Inspector(this, legajo, nombreInspector);
		frame.setVisible(false);
	}
	
	/*
	 * Inicializamos el Administrador
	 */
	private void inicializarAdmin() {
		GUI_Admin ga = new GUI_Admin(this);
		frame.setVisible(false);
	}
	
	/*
	 * Metodo para obtener el frame de la GUI_Login
	 */
	public JFrame getFrame() {
		return this.frame;
	}
}
