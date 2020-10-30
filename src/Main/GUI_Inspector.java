package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI_Inspector {

	private JFrame frame;
	
	private Inspector inspector;
	private String nombreInspector;
	private GUI_Login guiLogin;
	private HashMap<String,String> mapaErrores; //Key: patente, Value: msg con el error
	private Set<String> patentes; //Conjunto de patentes que fue agregando el inspector
	
	//Componentes graficos
	private JPanel panelPatentes, panelUbicacionParquimetro, panelMulta; 
	private JScrollPane scrollPaneTablaMultas, scrollPaneListaPatentes, scrollPaneListaErronea;
	private JTextField txtPatente;
	private JLabel lblPatente, lblListaPatente, lblSeleccionarUbicacion, lblMultasLabradas, lblMultasErroneasPorError, lblSeleccionarParquimetros, lblMultasErroneas; 
	private JButton btnAddPatente, btnEliminarPatente, btnConfirmarPatentes, btnConfirmarUbicacionParquimetro, btnVolver, btnVolverMenu;
	private JList listaPatentes, listaErroneas;
	private JComboBox cbParquimetros, cbUbicaciones;
	private JTable tablaMultas;
	
	//Modelos para la tabla, lista y los combobox
	private DefaultTableModel modeloTabla;
	private DefaultListModel modeloLista,modeloListaErronea;
	private DefaultComboBoxModel bm, modeloParquimetros;


	public GUI_Inspector(GUI_Login guiLogin, int legajo, String nombreInspector) {
		this.guiLogin = guiLogin;
		this.nombreInspector = nombreInspector;
		patentes = new HashSet<String>();
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
		
		
		
		//Creacion de los 3 paneles
		
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
		lblPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelPatentes.add(lblPatente);
		
		txtPatente = new JTextField();
		txtPatente.setBounds(20, 25, 103, 20);
		txtPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtPatente.setColumns(10);
		panelPatentes.add(txtPatente);
		
		btnAddPatente = new JButton("Agregar patente");
		btnAddPatente.setBounds(134, 25, 122, 20);
		btnAddPatente.setToolTipText("Agregar patente");
		btnAddPatente.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnAddPatente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean pudoInsertar = false;
				
				if (txtPatente.getText().length() == 6) {
					pudoInsertar = patentes.add(txtPatente.getText());
					if(pudoInsertar) {
						modeloLista.add(modeloLista.size(), txtPatente.getText());
					}
					else { //Patente duplicada;
						JOptionPane.showMessageDialog(null, "Patente duplicada","Error", JOptionPane.ERROR_MESSAGE);	
					}
				}
				else { //Patente invalida;
					JOptionPane.showMessageDialog(null, "Patente incorrecta","Error", JOptionPane.ERROR_MESSAGE);
				}
				
				txtPatente.setText("");
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
		           patentes.remove(patente);
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
		lblSeleccionarUbicacion.setBounds(82, 10, 122, 14);
		lblSeleccionarUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccionarUbicacion.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelUbicacionParquimetro.add(lblSeleccionarUbicacion);
		
		cbUbicaciones = new JComboBox();
		cbUbicaciones.setBounds(25, 30, 230, 20);
		cbUbicaciones.setToolTipText("Ubicaciones");
		cbUbicaciones.setFont(new Font("Dubai", Font.PLAIN, 12));
		bm = new DefaultComboBoxModel();
		cbUbicaciones.setModel(bm);
		cbUbicaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbUbicaciones.getSelectedItem() != null) {
					String direccion = cbUbicaciones.getSelectedItem().toString();
					lblSeleccionarParquimetros.setVisible(true);
					cbParquimetros.setVisible(true);
					String[] calleAltura = tratarDireccion(direccion);
					cargarParquimetros(calleAltura[0], calleAltura[1]);		
				}
			}
		});
		panelUbicacionParquimetro.add(cbUbicaciones);
		
		btnConfirmarUbicacionParquimetro = new JButton("Confirmar");
		btnConfirmarUbicacionParquimetro.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnConfirmarUbicacionParquimetro.setBounds(160, 319, 89, 23);
		btnConfirmarUbicacionParquimetro.setEnabled(false);
		btnConfirmarUbicacionParquimetro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int opcion = JOptionPane.showConfirmDialog(null, "¿Confirma la calle y ubicación?", "Confirmación", JOptionPane.YES_NO_OPTION);
								
				if (opcion == JOptionPane.OK_OPTION) {
					String[] calleAltura = tratarDireccion(cbUbicaciones.getSelectedItem().toString());
	                String id_parquimetro = cbParquimetros.getSelectedItem().toString();
	                
		        	ArrayList<ArrayList<String>> datos = inspector.conectarParquimetro(calleAltura[0], calleAltura[1], id_parquimetro, patentes);
		        	
		        	if (datos == null) {
		        		JOptionPane.showMessageDialog(null, "Ubicación no asignada", "Error", JOptionPane.CANCEL_OPTION);
		        	}
		        	else {
		        		patentes = new HashSet<String>();
		        		cargarTablaMulta(datos);
		        		panelUbicacionParquimetro.setVisible(false);
		        		panelMulta.setVisible(true);
		        		frame.setBounds(100, 100, 983, 489); //Agrando el frame a tamaño de la tabla
		        	}
		        }
			}
		});
		panelUbicacionParquimetro.add(btnConfirmarUbicacionParquimetro);
		
		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnVolver.setBounds(30, 319, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelUbicacionParquimetro.setVisible(false);				
				bm.removeAllElements();
				cbParquimetros.setVisible(false);
				modeloParquimetros.removeAllElements();
				lblSeleccionarParquimetros.setVisible(false);
				btnConfirmarUbicacionParquimetro.setEnabled(false);
				
				panelPatentes.setVisible(true);	
			}
		});
		panelUbicacionParquimetro.add(btnVolver);
		
		cbParquimetros = new JComboBox();
		cbParquimetros.setToolTipText("Ubicaciones");
		cbParquimetros.setFont(new Font("Dubai", Font.PLAIN, 12));
		cbParquimetros.setBounds(25, 140, 230, 20);
		cbParquimetros.setVisible(false);
		modeloParquimetros = new DefaultComboBoxModel();
		cbParquimetros.setModel(modeloParquimetros);
		cbParquimetros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConfirmarUbicacionParquimetro.setEnabled(true);
			}
		});
		panelUbicacionParquimetro.add(cbParquimetros);
		
		lblSeleccionarParquimetros = new JLabel("Seleccionar parquimetro");
		lblSeleccionarParquimetros.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccionarParquimetros.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblSeleccionarParquimetros.setBounds(72, 116, 140, 14);
		lblSeleccionarParquimetros.setVisible(false);
		panelUbicacionParquimetro.add(lblSeleccionarParquimetros);
		

		


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
		
		lblMultasErroneas = new JLabel("Multas no creadas");
		lblMultasErroneas.setBounds(712, 15, 265, 35);
		panelMulta.add(lblMultasErroneas);
		lblMultasErroneas.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultasErroneas.setFont(new Font("Dubai", Font.PLAIN, 30));
		
		scrollPaneListaErronea = new JScrollPane();
		scrollPaneListaErronea.setBounds(772, 56, 130, 297);
		panelMulta.add(scrollPaneListaErronea);
		
		listaErroneas = new JList();
		listaErroneas.setFont(new Font("Dubai", Font.PLAIN, 12));
		listaErroneas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String mensajeError = mapaErrores.get(listaErroneas.getSelectedValue().toString());
				JOptionPane.showMessageDialog(null, mensajeError,"Mensaje", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		scrollPaneListaErronea.setViewportView(listaErroneas);
		
		lblMultasErroneasPorError = new JLabel("(Click en la patente para ver el error)");
		lblMultasErroneasPorError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultasErroneasPorError.setFont(new Font("Dubai", Font.PLAIN, 12));
		lblMultasErroneasPorError.setBounds(720, 35, 200, 27);
		modeloListaErronea = new DefaultListModel();
		listaErroneas.setModel(modeloListaErronea);
		panelMulta.add(lblMultasErroneasPorError);
	}
	
	/*
	 * Carga las ubicaciones en el comboBoxUbicaciones
	 */	
	private void cargarUbicaciones() {
		ArrayList<String> ubicaciones = inspector.getUbicaciones();
		
		for(int i = 0; i < ubicaciones.size(); i++) {
			bm.addElement(ubicaciones.get(i));
		}
	}
	
	/*
	 * Carga los parquimetros al comboBoxParquimetros
	 * Dependiendo de la calle y altura pasadas por parametros.
	 */
	private void cargarParquimetros(String calle, String altura) {
		modeloParquimetros.removeAllElements();
		ArrayList<String> parquimetros = inspector.getParquimetros(calle, altura);
		
		for(int i = 0; i < parquimetros.size(); i++) {
			modeloParquimetros.addElement(parquimetros.get(i));
		}
	}
	
	/*
	 * Carga la tabla multa
	 */
	private void cargarTablaMulta(ArrayList<ArrayList<String>> datos) {
		
		//Reseteo la tabla
		modeloTabla.setRowCount(0);
		modeloTabla.fireTableDataChanged();
		
	    // Nombres de la columna
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = 7; //Por las cantidad de columnas
	    columnNames.add("Nro multa");
	    columnNames.add("Fecha");
	    columnNames.add("Hora");
	    columnNames.add("Calle");
	    columnNames.add("Altura");
	    columnNames.add("Patente");
	    columnNames.add("Legajo inspector");

	    
	    // Datos de la tabla
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    for(ArrayList<String> al : datos) {
	    	Vector<Object> vector = new Vector<Object>();
	    	for (String s : al) {
	    		vector.add(s);
	    	}
	    	data.add(vector);
	    }

	    modeloTabla.setDataVector(data, columnNames);
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
	 * Cargo la lista de las patentes con errores
	 */
	public void cargarListaErrores(HashMap<String,String> multasErroneas) {
		int i = 0;
		modeloListaErronea.removeAllElements();
		
		this.mapaErrores = multasErroneas;

		for(String p : multasErroneas.keySet()) {
			modeloListaErronea.add(i++, p);
		}	
	}
}
