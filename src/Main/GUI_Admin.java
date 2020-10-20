package Main;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private JTextField textField;
	private JLabel lblAtributos, lblTablas; 
	private JList ListaTablas, ListaAtributos;
	private JButton btnEjecutar;
	
		
	
	public GUI_Admin(Connection cnx) {
		admin = new Admin(this, cnx);
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
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				admin.sentenciaSQL(textField.getText());
			
			}
		});
		btnEjecutar.setFont(new Font("Dubai", Font.PLAIN, 12));
		btnEjecutar.setBounds(335, 32, 89, 37);
		frame.getContentPane().add(btnEjecutar);
		
		textField = new JTextField();
		textField.setText("SELECT * FROM multas");
		textField.setFont(new Font("Dubai", Font.PLAIN, 12));
		textField.setBounds(10, 12, 315, 77);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		ListaTablas = new JList();
		ListaTablas.setToolTipText("Lista de tablas");
		ListaTablas.setFont(new Font("Dubai", Font.PLAIN, 12));
		ListaTablas.setBounds(463, 70, 110, 352);
		frame.getContentPane().add(ListaTablas);
		
		//En construccion
		ListaTablas.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                  //label.setText(ListaTablas.getSelectedValue().toString());
                }
            }
        });
		
		
		
		
		ListaAtributos = new JList();
		ListaAtributos.setToolTipText("Lista de atributos de una tabla");
		ListaAtributos.setFont(new Font("Dubai", Font.PLAIN, 12));
		ListaAtributos.setBounds(599, 70, 110, 352);
		frame.getContentPane().add(ListaAtributos);
		
		lblTablas = new JLabel("Tablas");
		lblTablas.setFont(new Font("Dubai", Font.PLAIN, 14));
		lblTablas.setBounds(494, 43, 46, 14);
		frame.getContentPane().add(lblTablas);
		
		lblAtributos = new JLabel("Atributos");
		lblAtributos.setFont(new Font("Dubai", Font.PLAIN, 14));
		lblAtributos.setBounds(626, 43, 64, 14);
		frame.getContentPane().add(lblAtributos);
        
		tabla = new DBTable();		
		tabla.getTable().setFont(new Font("Dubai", Font.PLAIN, 12));
		tabla.setBounds(70, 400, 284, -244);
		tabla.getTable().setBounds(1, -1, 399, 0);
		tabla.setEditable(false);
		//tabla.setVisible(true);
		frame.getContentPane().add(tabla, BorderLayout.CENTER); 
		tabla.setLayout(null);
			
		
	}	
	
	
	public void actualizarListaTablas() {
		
		DefaultListModel listModel = new DefaultListModel();
		
		ArrayList<String> lista = admin.getTablas();
		
		for(int i = 0; i < lista.size(); i++) {
			listModel.add(i, lista.get(i));
		}
		
		ListaTablas.setModel(listModel);
	}
	
	public void resfrescar(ResultSet rs) {
		try {
			tabla.refresh(rs);
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
