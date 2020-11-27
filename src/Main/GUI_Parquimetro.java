package Main;


import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GUI_Parquimetro {

	private JFrame frame;
	private GUI_Login guiLogin;
	
	//Componentes graficos
	private JLabel lblUbicaciones, lblParquimetros, lblTarjetas;
	private JComboBox cbUbicaciones, cbParquimetros, cbTarjetas;
	private JButton btnConectar, btnVolver;
	
	//Modelos para los combobox
	private DefaultComboBoxModel modeloUbicaciones, modeloParquimetros, modeloTarjetas;

	public GUI_Parquimetro(GUI_Login gl) {
		guiLogin = gl;
		initialize();
		cargarUbicaciones();
		cargarTarjetas();
		this.frame.setVisible(true);
	}

	/*
	 * Inicializo todos los componentes del frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Parquimetro");
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		frame.getContentPane().setFont(new Font("Dubai", Font.PLAIN, 16));
		frame.getContentPane().setLayout(null);
		
		lblUbicaciones = new JLabel("Seleccionar una ubicaci\u00F3n");
		lblUbicaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblUbicaciones.setFont(new Font("Dubai", Font.PLAIN, 16));
		lblUbicaciones.setBounds(69, 11, 200, 26);
		frame.getContentPane().add(lblUbicaciones);
		
		cbUbicaciones = new JComboBox();
		cbUbicaciones.setBounds(10, 41, 300, 22);
		modeloUbicaciones = new DefaultComboBoxModel();
		cbUbicaciones.setModel(modeloUbicaciones);
		cbUbicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbUbicaciones.getSelectedItem() != null) {
					String direccion = cbUbicaciones.getSelectedItem().toString();
					String[] calleAltura = tratarDireccion(direccion);
					cargarParquimetros(calleAltura[0], calleAltura[1]);
					
					//Habilito el parquimetro
					lblParquimetros.setVisible(true);
					cbParquimetros.setVisible(true);					
				}
			}
		});
		frame.getContentPane().add(cbUbicaciones);
		
		lblParquimetros = new JLabel("Seleccionar un parquimetro");
		lblParquimetros.setHorizontalAlignment(SwingConstants.CENTER);
		lblParquimetros.setFont(new Font("Dubai", Font.PLAIN, 16));
		lblParquimetros.setBounds(69, 89, 200, 26);
		lblParquimetros.setVisible(false);
		frame.getContentPane().add(lblParquimetros);
		
		cbParquimetros = new JComboBox();
		cbParquimetros.setFont(new Font("Dubai", Font.PLAIN, 16));
		cbParquimetros.setBounds(10, 119, 300, 22);
		cbParquimetros.setVisible(false);
		modeloParquimetros = new DefaultComboBoxModel();
		cbParquimetros.setModel(modeloParquimetros);
		frame.getContentPane().add(cbParquimetros);
		
		lblTarjetas = new JLabel("Seleccionar una tarjeta");
		lblTarjetas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTarjetas.setFont(new Font("Dubai", Font.PLAIN, 16));
		lblTarjetas.setBounds(69, 172, 200, 26);
		frame.getContentPane().add(lblTarjetas);
		
		cbTarjetas = new JComboBox();
		cbTarjetas.setFont(new Font("Dubai", Font.PLAIN, 16));
		cbTarjetas.setBounds(10, 202, 300, 22);
		modeloTarjetas = new DefaultComboBoxModel();
		cbTarjetas.setModel(modeloTarjetas);
		frame.getContentPane().add(cbTarjetas);
		
		btnConectar = new JButton("Conectar");
		btnConectar.setFont(new Font("Dubai", Font.PLAIN, 16));
		btnConectar.setBounds(113, 264, 108, 23);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Chequeo que haya seleccionado algo en los tres comboBox
				if(cbUbicaciones.getSelectedItem() != null && cbParquimetros.getSelectedItem() != null && cbTarjetas.getSelectedItem() != null) {
					String id_tarjeta = cbTarjetas.getSelectedItem().toString();
					String id_parq = cbParquimetros.getSelectedItem().toString();
					
					ArrayList<String> resultado = Parquimetro.conectarParquimetro(id_tarjeta, id_parq);
					
					String mensaje = "";					
					for (int i = 0; i < resultado.size(); i++) {
						mensaje += resultado.get(i)  + "\n";
					}
					
					JOptionPane.showMessageDialog(null, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
					volverMenu();				
				}
				else {
					JOptionPane.showMessageDialog(null, "Seleccione todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
				}			
			}
		});
		frame.getContentPane().add(btnConectar);
		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Dubai", Font.PLAIN, 16));
		btnVolver.setBounds(10, 323, 89, 23);	
		frame.getContentPane().add(btnVolver);
		frame.setBounds(100, 100, 348, 397);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverMenu();
			}
		});	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*
	 * Carga las ubicaciones en el comboBoxUbicaciones
	 */
	private void cargarUbicaciones() {
		ArrayList<String> ubicaciones = Parquimetro.getUbicaciones();

		for(int i = 0; i < ubicaciones.size(); i++) {
			modeloUbicaciones.addElement(ubicaciones.get(i));
		}
	}
	
	/*
	 * Carga los parquimetros al comboBoxParquimetros
	 * Dependiendo de la calle y altura pasadas por parametros.
	 */
	private void cargarParquimetros(String calle, String altura) {
		modeloParquimetros.removeAllElements();
		ArrayList<String> parquimetros = Parquimetro.getParquimetros(calle, altura);

		for(int i = 0; i < parquimetros.size(); i++) {
			modeloParquimetros.addElement(parquimetros.get(i));
		}
	}
	
	/*
	 * Cargar las tarjetas en el comboBoxTarjetas
	 */
	private void cargarTarjetas() {
		ArrayList<String> tarjetas = Parquimetro.getTarjetas();

		for(int i = 0; i < tarjetas.size(); i++) {
			modeloTarjetas.addElement(tarjetas.get(i));
		}
		
	}
	
	/*
	 * Separo la direccion, devolviendo un arreglo de 2 componentes
	 * la primera componente: la direccion
	 * la segunda componente: es la altura
	 */
	private String[] tratarDireccion(String direccion) {

		String toReturn[] = new String[2];

		// Separar el string de la ubicacion en calle y altura
		String[]separado = direccion.split(" ");
        String calle = "";

        for(int i=0;i < separado.length-1; i++) {
            calle+= separado[i];
            if(i != separado.length-2) {
            	calle+=" ";
            }
        }

        String altura = separado[separado.length-1];


        toReturn[0] = calle;
        toReturn[1] = altura;

        return toReturn;
	}
	
	
	/*
	 * Metodo para volver al menú
	 */
	private void volverMenu() {
		guiLogin.getFrame().setVisible(true);
		Login.desconectarBD();
		frame.dispose();
	}
	
	
}


