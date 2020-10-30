package Main;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import java.awt.Color; 

public class GUI_Admin {

	private JFrame frame;

	private Admin admin;
	private GUI_Login guiLogin;
	
	//Componentes graficos
	private JTextField txtSelectFrom;
	private JLabel lblAtributos, lblTablas; 
	private JList ListaTablas, ListaAtributos;
	private JButton btnEjecutar, btnVolver;
	private JTable TablaDatos;
	private JScrollPane scrollPane;
	
	//Modelos para la tabla, y listas
	private DefaultListModel modeloLT,modeloLA;
	private DefaultTableModel modeloTabla;

	
	public GUI_Admin(GUI_Login guiLogin) {
		this.guiLogin = guiLogin;
		admin = new Admin(); //Crea al administrador
		inicializarGUI();
		actualizarListaTablas();
		this.frame.setVisible(true);
	}
	
	private void inicializarGUI() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		frame.setResizable(false);
		frame.getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setBounds(100, 100, 754, 533);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);		
		frame.setLocationRelativeTo(null);
		
		txtSelectFrom = new JTextField();
		txtSelectFrom.setText("SELECT * FROM conductores");
		txtSelectFrom.setFont(new Font("Dubai", Font.PLAIN, 14));
		txtSelectFrom.setBounds(20, 11, 689, 30);
		frame.getContentPane().add(txtSelectFrom);
		txtSelectFrom.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setBounds(20, 113, 415, 309);
		frame.getContentPane().add(scrollPane);
		
		TablaDatos = new JTable();
		TablaDatos.setRowSelectionAllowed(false);
		TablaDatos.setFont(new Font("Dubai", Font.PLAIN, 14));
		TablaDatos.setEnabled(false);
		TablaDatos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modeloTabla = new DefaultTableModel();
		TablaDatos.setModel(modeloTabla);
		ejecutarSentencia(txtSelectFrom.getText());
		scrollPane.setViewportView(TablaDatos);		
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnEjecutar.setBounds(20, 52, 89, 37);
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ejecutarSentencia(txtSelectFrom.getText());
				actualizarListaTablas();
				modeloLA.removeAllElements();
				
			}
		});
		frame.getContentPane().add(btnEjecutar);
		

		
		ListaTablas = new JList();
		ListaTablas.setToolTipText("Lista de tablas");
		ListaTablas.setFont(new Font("Dubai", Font.PLAIN, 12));
		ListaTablas.setBounds(463, 113, 110, 309);		
		modeloLT = new DefaultListModel();
		ListaTablas.setModel(modeloLT);
		ListaTablas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(ListaTablas.getSelectedValue() != null) {
		           String nombreTabla = ListaTablas.getSelectedValue().toString();
		           actualizarListaAtributos(nombreTabla); 
				}
			}
		});
		frame.getContentPane().add(ListaTablas);
		
		
		ListaAtributos = new JList();
		ListaAtributos.setToolTipText("Lista de atributos de una tabla");
		ListaAtributos.setFont(new Font("Dubai", Font.PLAIN, 12));
		ListaAtributos.setBounds(599, 113, 110, 309);
		modeloLA = new DefaultListModel();
		ListaAtributos.setModel(modeloLA);		
		frame.getContentPane().add(ListaAtributos);
		
		lblTablas = new JLabel("Tablas");
		lblTablas.setFont(new Font("Dubai", Font.PLAIN, 14));
		lblTablas.setBounds(495, 88, 64, 14);
		frame.getContentPane().add(lblTablas);
		
		lblAtributos = new JLabel("Atributos");
		lblAtributos.setFont(new Font("Dubai", Font.PLAIN, 14));
		lblAtributos.setBounds(623, 88, 86, 14);
		frame.getContentPane().add(lblAtributos); 
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiLogin.getFrame().setVisible(true);
				Login.desconectarBD();
				frame.dispose();
			}
		});
		btnVolver.setToolTipText("Volver");
		btnVolver.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnVolver.setBounds(20, 446, 89, 37);
		frame.getContentPane().add(btnVolver);
	}	
	
	
	/*
	 * Envia la sentencia al administrador, y me retorna el ResultSet
	 * Para actualizar la tabla con los resultados!
	 */
	private void ejecutarSentencia(String sql) {
		ResultSet rs = admin.sentenciaSQL(sql);
		if (rs != null) {
			try {
				actualizarTabla(rs);
				rs.close();
			} catch (SQLException ex) {
//				System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
//				System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
//				System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
			}
		}
		else {
			modeloTabla.setRowCount(0);
			modeloTabla.fireTableDataChanged();
		}
	}
	
	/*
	 * Se actualiza la tabla con los datos del ResultSet
	 */
	private void actualizarTabla(ResultSet rs) throws SQLException{
		
		DefaultTableModel dtm = (DefaultTableModel) TablaDatos.getModel();
				
	    ResultSetMetaData metaData = rs.getMetaData();

	    // Nombres de las columnas
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // Datos de la tabla
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    dtm.setDataVector(data, columnNames);	    
	 }
	
	
	/*
	 * Actualiza la tabla donde se muestran los nombres de las tablas de la bd
	 */
	private void actualizarListaTablas() {
		modeloLT.removeAllElements();
		
		ArrayList<String> lista = admin.getTablas();
		
		for(int i = 0; i < lista.size(); i++) {
			modeloLT.add(i, lista.get(i));
		}
	}
	
	/*
	 * Actualiza la tabla donde se muestran los atributos
	 */
	private void actualizarListaAtributos(String tabla) {
		modeloLA.removeAllElements();
		
		ArrayList<String> lista = admin.getAtributos(tabla);
		
		for(int i = 0; i < lista.size(); i++) {
			modeloLA.add(i, lista.get(i));
		}
	}
	
	/*
	 * Estatico para que de cualquier clase puedan mostrar el mensaje por parametro.
	 */
	public static void mostrarMensaje(String s) {
		JOptionPane.showMessageDialog(null, s,"Mensaje", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
}



