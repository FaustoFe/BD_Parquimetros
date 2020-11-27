package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

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


public GUI_Parquimetro(GUI_Login guiLogin) {
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
	
	scrollPane = new JScrollPane();
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	scrollPane.setBounds(20, 113, 415, 309);
	frame.getContentPane().add(scrollPane);

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
	
	/*
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
*/
}

	
	
