package Main;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Color;

public class GUI_Inspector {

	private JFrame frame;
	
	private Inspector inspector;
	private JTextField txtPatente;
	
	private JLabel lblPatente, lblListaPatente, lblSeleccionarUbicacion, lbSeleccionarParquimetro; 
	private JButton btnAddPatente, btnEliminarPatente, btnConfirmar;
	private JList listaPatentes;
	private JComboBox cbUbicaciones, cbParquimetros;
	
	private DefaultListModel modeloLista;
	private DefaultComboBoxModel bm;


	public GUI_Inspector(Connection cnx) {
		inspector = new Inspector(cnx);
		inicializarGUI();
		this.frame.setVisible(true);
	}


	private void inicializarGUI() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 204, 255));
		frame.getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setTitle("Tablero inspector");
		frame.setBounds(100, 100, 694, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtPatente = new JTextField();
		txtPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtPatente.setBounds(21, 32, 103, 20);
		frame.getContentPane().add(txtPatente);
		txtPatente.setColumns(10);
		
		lblPatente = new JLabel("Patente");
		lblPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblPatente.setBounds(50, 11, 46, 14);
		frame.getContentPane().add(lblPatente);
		
		listaPatentes = new JList();
		listaPatentes.setToolTipText("Lista patentes");
		listaPatentes.setFont(new Font("Dubai", Font.PLAIN, 12));
		listaPatentes.setBounds(21, 130, 103, 277);
		modeloLista = new DefaultListModel();
		listaPatentes.setModel(modeloLista);
		frame.getContentPane().add(listaPatentes);		
		
		btnAddPatente = new JButton("Agregar patente");
		btnAddPatente.setToolTipText("Agregar patente");
		btnAddPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnAddPatente.setBounds(134, 32, 122, 20);
		btnAddPatente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//En construcción, mandar a lista;
				if (txtPatente.getText().length() <= 6) {
					modeloLista.add(modeloLista.size(), txtPatente.getText());
					//inspector.addPatente(txtPatente.getText());
				}
				else {
					JOptionPane.showMessageDialog(null, "Patente incorrecta","Error", JOptionPane.ERROR_MESSAGE);
					txtPatente.setText("");
				}
			}
		});
		frame.getContentPane().add(btnAddPatente);
		
		lblListaPatente = new JLabel("Listas patentes");
		lblListaPatente.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblListaPatente.setBounds(21, 105, 103, 14);
		frame.getContentPane().add(lblListaPatente);
		
		lblSeleccionarUbicacion = new JLabel("Seleccionar ubicacion");
		lblSeleccionarUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccionarUbicacion.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblSeleccionarUbicacion.setBounds(476, 11, 122, 14);
		frame.getContentPane().add(lblSeleccionarUbicacion);
		
		cbUbicaciones = new JComboBox();
		cbUbicaciones.setToolTipText("Ubicaciones");
		cbUbicaciones.setFont(new Font("Dubai", Font.PLAIN, 12));
		cbUbicaciones.setBounds(426, 32, 229, 21);
		bm = new DefaultComboBoxModel();
		cbUbicaciones.setModel(bm);
		frame.getContentPane().add(cbUbicaciones);
		
		lbSeleccionarParquimetro = new JLabel("Seleccionar parquimetro");
		lbSeleccionarParquimetro.setHorizontalAlignment(SwingConstants.CENTER);
		lbSeleccionarParquimetro.setFont(new Font("Dubai", Font.PLAIN, 12));
		lbSeleccionarParquimetro.setBounds(395, 325, 122, 14);
		frame.getContentPane().add(lbSeleccionarParquimetro);
		
		cbParquimetros = new JComboBox();
		cbParquimetros.setToolTipText("Parquimetros");
		cbParquimetros.setFont(new Font("Dubai", Font.PLAIN, 12));
		cbParquimetros.setBounds(337, 350, 229, 21);
		frame.getContentPane().add(cbParquimetros);
		
		btnEliminarPatente = new JButton("Eliminar patente");
		btnEliminarPatente.setToolTipText("Eliminar patente");
		btnEliminarPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnEliminarPatente.setBounds(134, 131, 122, 20);
		frame.getContentPane().add(btnEliminarPatente);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddPatente.setEnabled(false);
				btnConfirmar.setEnabled(false);
				listaPatentes.setEnabled(false);
				txtPatente.setEnabled(false);
				
				cargarUbicaciones();
				
				
				
			}
		});
		btnConfirmar.setToolTipText("Agregar patente");
		btnConfirmar.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnConfirmar.setBounds(296, 32, 122, 20);
		frame.getContentPane().add(btnConfirmar);
	}
	
	
	private void cargarUbicaciones() {
		ArrayList<String> ubicaciones = inspector.getUbicaciones();
		
		for(int i = 0; i < ubicaciones.size(); i++) {
			bm.addElement(ubicaciones.get(i));
		}
	}
	
}
