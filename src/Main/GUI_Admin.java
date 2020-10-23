package Main;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 

public class GUI_Admin {

	private JFrame frame;
	
	private DBTable tabla;
	
	private Admin admin;
	private JTextField txtSelectFrom;
	private JLabel lblAtributos, lblTablas; 
	private JList ListaTablas, ListaAtributos;
	private JButton btnEjecutar;
	
	private DefaultListModel modeloLT,modeloLA;
		
	private final Connection conexion;
	
	public GUI_Admin(Connection cnx) {
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
		
		tabla = new DBTable();		
		//tabla.getTable().setFont(new Font("Dubai", Font.PLAIN, 12));
		tabla.setBounds(70, 400, 284, -244);
		//tabla.getTable().setBounds(1, -1, 399, 0);
		tabla.setEditable(false);
		tabla.setVisible(true);
		frame.getContentPane().add(tabla, BorderLayout.CENTER); 
		//tabla.setLayout(null);
		
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Statement stmt;
				try {
					stmt = conexion.createStatement();
					String sql = "SELECT legajo FROM accede";
					ResultSet rs = stmt.executeQuery(sql);
					tabla.refresh(rs);
				} catch (SQLException e1) {	e1.printStackTrace();}

		        


		        
		       
		                 
		        //actualiza el contenido de la tabla con los datos del resulset rs
		         //tabla.refresh(rs);
				
				
				
				/*
				ResultSet rs = admin.sentenciaSQL(txtSelectFrom.getText());
				if (rs != null) {
					try {
						tabla.refresh(rs);
						rs.close();
					} catch (SQLException d) { d.printStackTrace(); }
				}
				
				*/
				actualizarListaTablas();
				modeloLA.removeAllElements();
				
			}
		});
		btnEjecutar.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnEjecutar.setBounds(335, 32, 89, 37);
		frame.getContentPane().add(btnEjecutar);
		
		txtSelectFrom = new JTextField();
		txtSelectFrom.setText("SELECT * FROM multa");
		txtSelectFrom.setFont(new Font("Dubai", Font.PLAIN, 12));
		txtSelectFrom.setBounds(10, 12, 315, 77);
		frame.getContentPane().add(txtSelectFrom);
		txtSelectFrom.setColumns(10);
		
		ListaTablas = new JList();
		ListaTablas.setToolTipText("Lista de tablas");
		ListaTablas.setFont(new Font("Dubai", Font.PLAIN, 12));
		ListaTablas.setBounds(463, 70, 110, 352);
		modeloLT = new DefaultListModel();
		ListaTablas.setModel(modeloLT);
		frame.getContentPane().add(ListaTablas);
		
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {
		           String nombreTabla = ListaTablas.getSelectedValue().toString();
		           actualizarListaAtributos(nombreTabla); 
		         }
		    }
		};
		ListaTablas.addMouseListener(mouseListener);
		
		
		ListaAtributos = new JList();
		ListaAtributos.setToolTipText("Lista de atributos de una tabla");
		ListaAtributos.setFont(new Font("Dubai", Font.PLAIN, 12));
		ListaAtributos.setBounds(599, 70, 110, 352);
		modeloLA = new DefaultListModel();
		ListaAtributos.setModel(modeloLA);		
		frame.getContentPane().add(ListaAtributos);
		
		lblTablas = new JLabel("Tablas");
		lblTablas.setFont(new Font("Dubai", Font.PLAIN, 14));
		lblTablas.setBounds(494, 43, 46, 14);
		frame.getContentPane().add(lblTablas);
		
		lblAtributos = new JLabel("Atributos");
		lblAtributos.setFont(new Font("Dubai", Font.PLAIN, 14));
		lblAtributos.setBounds(626, 43, 64, 14);
		frame.getContentPane().add(lblAtributos); 
		
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
	
	

}
