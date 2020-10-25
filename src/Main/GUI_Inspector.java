package Main;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUI_Inspector {

	private JFrame frame;
	
	private Inspector inspector;
	private GUI_Login guiLogin;
	
	private JPanel panelPatentes, panelUbicacionParquimetro, panelMulta; 
	private JScrollPane scrollPane;
	private JTextField txtPatente;
	private JLabel lblPatente, lblListaPatente, lblSeleccionarUbicacion, lbSeleccionarParquimetro, lblMultasLabradas; 
	private JButton btnAddPatente, btnEliminarPatente, btnConfirmarPatentes, btnConfirmarUbicacionParquimetro, btnVolver;
	private JList listaPatentes;
	private JComboBox cbUbicaciones, cbParquimetros;
	private JTable tablaMultas;
	
	private DefaultTableModel modeloTabla;
	private DefaultListModel modeloLista;
	private DefaultComboBoxModel bm;
	private JButton btnVolverMenu;


	public GUI_Inspector(GUI_Login guiLogin, int legajo) {
		this.guiLogin = guiLogin;
		inspector = new Inspector(legajo); // MANDAR LEGAJO
		inicializarGUI();
		this.frame.setVisible(true);
	}


	private void inicializarGUI() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		frame.getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setTitle("Tablero inspector");
		frame.setBounds(100, 100, 640, 448);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		btnVolverMenu = new JButton("Volver al login");
		btnVolverMenu.setBounds(10, 372, 114, 23);
		btnVolverMenu.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnVolverMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMulta.setVisible(false);
				panelUbicacionParquimetro.setVisible(false);
				panelPatentes.setVisible(false);
				
				guiLogin.getFrame().setVisible(true);
				Login.desconectarBD();
				frame.dispose();				
			}
		});
		frame.getContentPane().add(btnVolverMenu);
		
		
	
		
		panelPatentes = new JPanel();
		panelPatentes.setBounds(150, 0, 291, 353);
		panelPatentes.setBackground(new Color(51, 204, 255));
		frame.getContentPane().add(panelPatentes);
		panelPatentes.setLayout(null);
		
		panelUbicacionParquimetro = new JPanel();
		panelUbicacionParquimetro.setBounds(150, 0, 291, 216);
		panelUbicacionParquimetro.setBackground(new Color(51, 204, 255));
		frame.getContentPane().add(panelUbicacionParquimetro);
		panelUbicacionParquimetro.setLayout(null);
		panelUbicacionParquimetro.setVisible(false);
		
		panelMulta = new JPanel();
		panelMulta.setBounds(0, 0, 634, 353);
		panelMulta.setBackground(new Color(51, 204, 255));
		frame.getContentPane().add(panelMulta);
		panelMulta.setLayout(null);
		panelMulta.setVisible(false);
		
		
		
		
		//Componentes del panelPatente
		
		lblPatente = new JLabel("Patente");
		lblPatente.setBounds(50, 11, 46, 14);
		panelPatentes.add(lblPatente);
		lblPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		
		txtPatente = new JTextField();
		txtPatente.setBounds(20, 25, 103, 20);
		panelPatentes.add(txtPatente);
		txtPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtPatente.setColumns(10);
		
		btnAddPatente = new JButton("Agregar patente");
		btnAddPatente.setBounds(134, 25, 122, 20);
		btnAddPatente.setToolTipText("Agregar patente");
		btnAddPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnAddPatente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//En construcción
				if (txtPatente.getText().length() <= 6) {
					modeloLista.add(modeloLista.size(), txtPatente.getText());
					inspector.addPatente(txtPatente.getText());
					txtPatente.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "Patente incorrecta","Error", JOptionPane.ERROR_MESSAGE);
					txtPatente.setText("");
				}
			}
		});
		panelPatentes.add(btnAddPatente);
		
		btnConfirmarPatentes = new JButton("Confirmar");
		btnConfirmarPatentes.setBounds(134, 56, 122, 20);
		btnConfirmarPatentes.setToolTipText("Agregar patente");
		btnConfirmarPatentes.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnConfirmarPatentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarUbicaciones();
				panelUbicacionParquimetro.setVisible(true);
				panelPatentes.setVisible(false);
				frame.repaint();
			}
		});
		panelPatentes.add(btnConfirmarPatentes);
		
		lblListaPatente = new JLabel("Listas patentes");
		lblListaPatente.setBounds(20, 98, 103, 14);
		lblListaPatente.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelPatentes.add(lblListaPatente);
		
		listaPatentes = new JList();
		listaPatentes.setBounds(20, 123, 103, 212);		
		listaPatentes.setToolTipText("Lista patentes");
		listaPatentes.setFont(new Font("Dubai", Font.PLAIN, 12));	
		modeloLista = new DefaultListModel();
		listaPatentes.setModel(modeloLista);
		panelPatentes.add(listaPatentes);
		
		btnEliminarPatente = new JButton("Eliminar patente");
		btnEliminarPatente.setBounds(134, 124, 122, 20);
		btnEliminarPatente.setToolTipText("Eliminar patente");
		btnEliminarPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelPatentes.add(btnEliminarPatente);
		


		//Componentes del panelMulta
		
		
		lblMultasLabradas = new JLabel("Multas labradas");
		lblMultasLabradas.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultasLabradas.setBounds(193, 10, 265, 35);
		lblMultasLabradas.setFont(new Font("Dubai", Font.PLAIN, 30));
		panelMulta.add(lblMultasLabradas);
		
		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("Multas que fueron labradas");
		scrollPane.setBounds(10, 56, 614, 357);
		panelMulta.add(scrollPane);
		
		tablaMultas = new JTable();
		tablaMultas.setFont(new Font("Dubai", Font.PLAIN, 12));
		//modeloTabla = new DefaultTableModel( new Object[][] {}, new String[] { "Nro multa", "Fecha", "Hora", "Calle", "Altura", "Patente", "Legajo inspector"});
		modeloTabla = new DefaultTableModel();
		tablaMultas.setModel(modeloTabla);
		scrollPane.setViewportView(tablaMultas);
		
		

		
		
		//Componentes del panelUbicacionParquimetro	
		
		lblSeleccionarUbicacion = new JLabel("Seleccionar ubicacion");
		lblSeleccionarUbicacion.setBounds(85, 11, 122, 14);
		lblSeleccionarUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccionarUbicacion.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelUbicacionParquimetro.add(lblSeleccionarUbicacion);
		
		cbUbicaciones = new JComboBox();
		cbUbicaciones.setBounds(38, 31, 229, 21);
		cbUbicaciones.setToolTipText("Ubicaciones");
		cbUbicaciones.setFont(new Font("Dubai", Font.PLAIN, 12));
		bm = new DefaultComboBoxModel();
		cbUbicaciones.setModel(bm);
		panelUbicacionParquimetro.add(cbUbicaciones);
		
		lbSeleccionarParquimetro = new JLabel("Seleccionar parquimetro");
		lbSeleccionarParquimetro.setBounds(85, 88, 122, 14);
		panelUbicacionParquimetro.add(lbSeleccionarParquimetro);
		lbSeleccionarParquimetro.setHorizontalAlignment(SwingConstants.CENTER);
		lbSeleccionarParquimetro.setFont(new Font("Dubai", Font.PLAIN, 12));
		
		cbParquimetros = new JComboBox();
		cbParquimetros.setBounds(38, 113, 229, 21);
		panelUbicacionParquimetro.add(cbParquimetros);
		cbParquimetros.setToolTipText("Parquimetros");
		cbParquimetros.setFont(new Font("Dubai", Font.PLAIN, 12));
		
		btnConfirmarUbicacionParquimetro = new JButton("Confirmar");
		btnConfirmarUbicacionParquimetro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int opcion = JOptionPane.showConfirmDialog(null, "¿Confirma la calle y ubicación?", "Confirmación", JOptionPane.YES_NO_OPTION);
				
				if (opcion == JOptionPane.OK_OPTION) {
					String direccion = cbUbicaciones.getSelectedItem().toString();
		        	String[]separado = direccion.split("\\s+"); //Para tener dir y numero
		        	ArrayList<ArrayList<String>> datos = inspector.conectarParquimetro(separado[0], separado[1]);
		        	
		        	if (datos == null)
		        		JOptionPane.showMessageDialog(null, "Ubicación no asignada", "Error", JOptionPane.CANCEL_OPTION);
		        	else {
		        		cargarTablaMulta(datos);
		        		panelUbicacionParquimetro.setVisible(false);
		        		panelMulta.setVisible(true);
		        	}
		        }
			}
		});
		btnConfirmarUbicacionParquimetro.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnConfirmarUbicacionParquimetro.setBounds(178, 155, 89, 23);
		panelUbicacionParquimetro.add(btnConfirmarUbicacionParquimetro);

		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnVolver.setBounds(38, 155, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelUbicacionParquimetro.setVisible(false);
				panelPatentes.setVisible(true);
				bm.removeAllElements();
			}
		});

		panelUbicacionParquimetro.add(btnVolver);
	}
	
	private void cargarUbicaciones() {
		ArrayList<String> ubicaciones = inspector.getParquimetros();
		
		for(int i = 0; i < ubicaciones.size(); i++) {
			bm.addElement(ubicaciones.get(i));
		}
	}
	
	private void cargarTablaMulta(ArrayList<ArrayList<String>> datos) {
		
		//Reseteo la tabla
		modeloTabla.setRowCount(0);
		modeloTabla.fireTableDataChanged();
		
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = 7; //Por las cantidad de columnas
	    columnNames.add("Nro multa");
	    columnNames.add("Fecha");
	    columnNames.add("Hora");
	    columnNames.add("Calle");
	    columnNames.add("Altura");
	    columnNames.add("Patente");
	    columnNames.add("Legajo inspector");

	    
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    for(ArrayList<String> al : datos) {
	    	Vector<Object> vector = new Vector<Object>();
	    	for (String s : al) {
	    		vector.add(s);
	    	}
	    	data.add(vector);
	    }

	    modeloTabla.setDataVector(data, columnNames);
	    
	    //resizeColumnWidth(TablaDatos);
	}
}
