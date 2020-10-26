package Main;

import java.util.ArrayList;
import java.util.Vector;

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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class GUI_Inspector {

	private JFrame frame;
	
	private Inspector inspector;
	private String nombreInspector;
	private GUI_Login guiLogin;
	
	private JPanel panelPatentes, panelUbicacionParquimetro, panelMulta; 
	private JScrollPane scrollPaneTablaMultas;
	private JTextField txtPatente;
	private JLabel lblPatente, lblListaPatente, lblSeleccionarUbicacion, lblMultasLabradas; 
	private JButton btnAddPatente, btnEliminarPatente, btnConfirmarPatentes, btnConfirmarUbicacionParquimetro, btnVolver;
	private JList listaPatentes;
	private JComboBox cbUbicaciones;
	private JTable tablaMultas;
	
	private DefaultTableModel modeloTabla;
	private DefaultListModel modeloLista,modeloListaErronea;
	private DefaultComboBoxModel bm;
	private JButton btnVolverMenu;
	private JLabel lblMultasErroneasPorError;
	private JScrollPane scrollPaneListaPatentes;


	public GUI_Inspector(GUI_Login guiLogin, int legajo, String nombreInspector) {
		this.guiLogin = guiLogin;
		this.nombreInspector = nombreInspector;
		inspector = new Inspector(this, legajo);
		inicializarGUI();
		this.frame.setVisible(true);
	}


	private void inicializarGUI() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		frame.getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setTitle("Inspector: " + nombreInspector);
		frame.setBounds(362, 11, 291, 452);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		btnVolverMenu = new JButton("Volver al login");
		btnVolverMenu.setBounds(10, 380, 114, 23);
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
		panelPatentes.setBounds(0, 0, 275, 350);
		panelPatentes.setBackground(new Color(51, 204, 255));
		panelPatentes.setLayout(null);
		panelPatentes.setVisible(true);
		frame.getContentPane().add(panelPatentes);
		
		
		panelUbicacionParquimetro = new JPanel();
		panelUbicacionParquimetro.setBounds(0, 0, 275, 350);
		panelUbicacionParquimetro.setBackground(new Color(51, 204, 255));
		panelUbicacionParquimetro.setLayout(null);
		panelUbicacionParquimetro.setVisible(false);
		frame.getContentPane().add(panelUbicacionParquimetro);
		
		panelMulta = new JPanel();
		panelMulta.setBounds(0, 0, 980, 370);
		panelMulta.setBackground(new Color(51, 204, 255));
		panelMulta.setLayout(null);
		panelMulta.setVisible(false);
		frame.getContentPane().add(panelMulta);
		

		
		
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
		lblListaPatente.setBounds(20, 99, 103, 14);
		lblListaPatente.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelPatentes.add(lblListaPatente);
		
		btnEliminarPatente = new JButton("Eliminar patente");
		btnEliminarPatente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listaPatentes.getSelectedValue() != null) {
		           String patente = listaPatentes.getSelectedValue().toString();
		           inspector.removePatente(patente);
		           modeloLista.removeElement(patente);
				}				
			}
		});
		btnEliminarPatente.setBounds(134, 124, 122, 20);
		btnEliminarPatente.setToolTipText("Eliminar patente");
		btnEliminarPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelPatentes.add(btnEliminarPatente);
		
		scrollPaneListaPatentes = new JScrollPane();
		scrollPaneListaPatentes.setBounds(20, 124, 103, 205);
		panelPatentes.add(scrollPaneListaPatentes);
		
		listaPatentes = new JList();
		scrollPaneListaPatentes.setViewportView(listaPatentes);
		listaPatentes.setToolTipText("Lista patentes");
		listaPatentes.setFont(new Font("Dubai", Font.PLAIN, 12));
		modeloLista = new DefaultListModel();
		listaPatentes.setModel(modeloLista);
		
		
		
		
		

		
		
		//Componentes del panelUbicacionParquimetro	
		
		lblSeleccionarUbicacion = new JLabel("Seleccionar ubicacion");
		lblSeleccionarUbicacion.setBounds(82, 11, 122, 14);
		lblSeleccionarUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccionarUbicacion.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelUbicacionParquimetro.add(lblSeleccionarUbicacion);
		
		cbUbicaciones = new JComboBox();
		cbUbicaciones.setBounds(25, 31, 229, 21);
		cbUbicaciones.setToolTipText("Ubicaciones");
		cbUbicaciones.setFont(new Font("Dubai", Font.PLAIN, 12));
		bm = new DefaultComboBoxModel();
		cbUbicaciones.setModel(bm);
		panelUbicacionParquimetro.add(cbUbicaciones);
		
		btnConfirmarUbicacionParquimetro = new JButton("Confirmar");
		btnConfirmarUbicacionParquimetro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int opcion = JOptionPane.showConfirmDialog(null, "¿Confirma la calle y ubicación?", "Confirmación", JOptionPane.YES_NO_OPTION);
				
				if (opcion == JOptionPane.OK_OPTION) {
					String direccion = cbUbicaciones.getSelectedItem().toString();
		        	
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
	                
		        	ArrayList<ArrayList<String>> datos = inspector.conectarParquimetro(calle, altura);
		        	
		        	if (datos == null) {
		        		JOptionPane.showMessageDialog(null, "Ubicación no asignada", "Error", JOptionPane.CANCEL_OPTION);
		        	}
		        	else {
		        		inspector.limpiarListaPatentes();
		        		cargarTablaMulta(datos);
		        		panelUbicacionParquimetro.setVisible(false);
		        		panelMulta.setVisible(true);
		        		frame.setBounds(100, 100, 983, 489); //Agrando el frame a tamaño de la tabla
		        	}
		        }
			}
		});
		btnConfirmarUbicacionParquimetro.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnConfirmarUbicacionParquimetro.setBounds(160, 319, 89, 23);
		panelUbicacionParquimetro.add(btnConfirmarUbicacionParquimetro);
			btnVolver = new JButton("Volver");
			btnVolver.setFont(new Font("Dubai", Font.PLAIN, 12));
			btnVolver.setBounds(30, 319, 89, 23);
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panelUbicacionParquimetro.setVisible(false);
					panelPatentes.setVisible(true);
					bm.removeAllElements();
				}
			});
		panelUbicacionParquimetro.add(btnVolver);
		

		


		//Componentes del panelMulta
		
		
		lblMultasLabradas = new JLabel("Multas labradas");
		lblMultasLabradas.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultasLabradas.setBounds(193, 15, 265, 35);
		lblMultasLabradas.setFont(new Font("Dubai", Font.PLAIN, 30));
		panelMulta.add(lblMultasLabradas);
		
		scrollPaneTablaMultas = new JScrollPane();
		scrollPaneTablaMultas.setToolTipText("Multas que fueron labradas");
		scrollPaneTablaMultas.setBounds(10, 56, 706, 297);
		panelMulta.add(scrollPaneTablaMultas);
		
		tablaMultas = new JTable();
		tablaMultas.setFont(new Font("Dubai", Font.PLAIN, 12));
		modeloTabla = new DefaultTableModel();
		tablaMultas.setModel(modeloTabla);
		scrollPaneTablaMultas.setViewportView(tablaMultas);
		
		JLabel lblMultasErroneas = new JLabel("Multas no creadas");
		lblMultasErroneas.setBounds(712, 15, 265, 35);
		panelMulta.add(lblMultasErroneas);
		lblMultasErroneas.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultasErroneas.setFont(new Font("Dubai", Font.PLAIN, 30));
		
		JScrollPane scrollPaneListaErronea = new JScrollPane();
		scrollPaneListaErronea.setBounds(772, 56, 130, 297);
		panelMulta.add(scrollPaneListaErronea);
		
		JList listaErroneas = new JList();
		listaErroneas.setFont(new Font("Dubai", Font.PLAIN, 12));
		scrollPaneListaErronea.setViewportView(listaErroneas);
		
		lblMultasErroneasPorError = new JLabel("(Por errores)");
		lblMultasErroneasPorError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultasErroneasPorError.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblMultasErroneasPorError.setBounds(888, 35, 75, 27);
		modeloListaErronea = new DefaultListModel();
		listaErroneas.setModel(modeloListaErronea);
		panelMulta.add(lblMultasErroneasPorError);
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
	
	public void cargarListaErrores(ArrayList<String> multasErroneas) {
		modeloListaErronea.removeAllElements();
		
		for(int i = 0; i < multasErroneas.size(); i++) {
			modeloListaErronea.add(i, multasErroneas.get(i));
		}		
	}
}
