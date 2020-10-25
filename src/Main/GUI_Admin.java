package Main;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Component;

import quick.dbtable.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder; 

public class GUI_Admin {

	private JFrame frame;

	private Admin admin;
	private GUI_Login guiLogin;
	
	private JTextField txtSelectFrom;
	private JLabel lblAtributos, lblTablas; 
	private JList ListaTablas, ListaAtributos;
	private JButton btnEjecutar;
	
	private DefaultListModel modeloLT,modeloLA;
	private DefaultTableModel modeloTabla;
		
	private JTable TablaDatos;
	private JScrollPane scrollPane;
	
	private final Connection conexion; //Para que lo vean los Listener
	private JButton btnVolver;
	
	
	
	public GUI_Admin(GUI_Login guiLogin, Connection cnx) {
		this.guiLogin = guiLogin;
		conexion = cnx;
		admin = new Admin(cnx);
		inicializarGUI();
		actualizarListaTablas();
		this.frame.setVisible(true);
	}
	


	private void inicializarGUI() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		frame.setBounds(100, 100, 754, 506);
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
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setBounds(20, 113, 415, 309);
		frame.getContentPane().add(scrollPane);
		
		TablaDatos = new JTable();
		TablaDatos.setRowSelectionAllowed(false);
		TablaDatos.setFont(new Font("Dubai", Font.PLAIN, 14));
		TablaDatos.setEnabled(false);
		TablaDatos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
	           String nombreTabla = ListaTablas.getSelectedValue().toString();
	           actualizarListaAtributos(nombreTabla); 
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
		lblTablas.setBounds(495, 88, 46, 14);
		frame.getContentPane().add(lblTablas);
		
		lblAtributos = new JLabel("Atributos");
		lblAtributos.setFont(new Font("Dubai", Font.PLAIN, 14));
		lblAtributos.setBounds(623, 88, 64, 14);
		frame.getContentPane().add(lblAtributos); 
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiLogin.getFrame().setVisible(true);
				guiLogin.desconectarBD();
				frame.dispose();
			}
		});
		btnVolver.setToolTipText("Volver");
		btnVolver.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnVolver.setBounds(10, 429, 89, 37);
		frame.getContentPane().add(btnVolver);
	}	
	
	
	private void ejecutarSentencia(String sql) {
		ResultSet rs = admin.sentenciaSQL(sql);
		if (rs != null) {
			try {
				actualizarTabla(rs);
				rs.close();	
			} catch (SQLException ex) {
				System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
				System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
				System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
			}
		}
		else {
			modeloTabla.setRowCount(0);
			modeloTabla.fireTableDataChanged();
		}
	}
	
	
	private void actualizarTabla(ResultSet rs) throws SQLException{
		
		DefaultTableModel dtm = (DefaultTableModel) TablaDatos.getModel();
				
	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    dtm.setDataVector(data, columnNames);
	    
	    //resizeColumnWidth(TablaDatos);
	    
	 }
	
	
	
	private void actualizarListaTablas() {
		modeloLT.removeAllElements();
		
		ArrayList<String> lista = admin.getTablas();
		
		for(int i = 0; i < lista.size(); i++) {
			modeloLT.add(i, lista.get(i));
		}
	}
	
	private void actualizarListaAtributos(String tabla) {
		modeloLA.removeAllElements();
		
		ArrayList<String> lista = admin.getAtributos(tabla);
		
		for(int i = 0; i < lista.size(); i++) {
			modeloLA.add(i, lista.get(i));
		}
	}
	
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 30; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width + 1 , width);
	        }
	        if(width > 200)
	            width = 200;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	
}




/*
		tabla = new DBTable();		
		//tabla.getTable().setFont(new Font("Dubai", Font.PLAIN, 12));
		tabla.setBounds(70, 400, 284, -244);
		//tabla.getTable().setBounds(1, -1, 399, 0);
		tabla.setEditable(false);
		tabla.setVisible(true);
		frame.getContentPane().add(tabla, BorderLayout.CENTER); 
		//tabla.setLayout(null);
		
	Statement stmt;
	try {
		stmt = conexion.createStatement();
		String sql = "SELECT legajo FROM accede";
		ResultSet rs = stmt.executeQuery(sql);
		tabla.refresh(rs);
	} catch (SQLException e1) {	e1.printStackTrace();}
	
	
	
	------------------------------------------------
	
	         
	//actualiza el contenido de la tabla con los datos del resulset rs
	 //tabla.refresh(rs);
	
	ResultSet rs = admin.sentenciaSQL(txtSelectFrom.getText());
	if (rs != null) {
		try {
			tabla.refresh(rs);
			rs.close();
		} catch (SQLException d) { d.printStackTrace(); }
	}

*/