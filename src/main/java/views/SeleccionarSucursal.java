package views;


import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.DataBaseConnection;
import connection.SqliteConnection;


public class SeleccionarSucursal extends JDialog {
	private JComboBox<String> sucursales;
	private JButton btnSalir;
	private JButton btnGuardar;
	private JPanel panelSucursal;	
	private String nombreSucursal;
	
	
	public SeleccionarSucursal(JFrame parent) {
		super(parent, "Seleccione Sucursal", true);
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		crearCampos();
		
		crearPanel();
		add(panelSucursal);
		
		
	}
	
	private void crearCampos() {
		
		sucursales = new JComboBox<String>();
		listaSucursales();
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(e -> dispose());

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        if (sucursales.getSelectedItem() == null) {
		            JOptionPane.showMessageDialog(null, "Seleccione una sucursal");
		            return;
		        }

		        Recv.stopConsumer();

		        nombreSucursal = sucursales.getSelectedItem().toString();
		        guardarSucursal(nombreSucursal);

		        try {
		            Recv.startConsumer(nombreSucursal);
		            
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }

		        dispose();
		    }
		
		});
	}
	
	private void guardarSucursal(String nombreSucursal) {		
		
		SqliteConnection.guardarSucursal(nombreSucursal);
		 
	}
	
	private void crearPanel() {
		panelSucursal = new JPanel();
		panelSucursal.setLayout(null);
		
		panelSucursal.add(sucursales).setBounds(20,20,300,25);
		panelSucursal.add(btnGuardar).setBounds(20, 150, 150, 25);
		panelSucursal.add(btnSalir).setBounds(200,150,150,25);
		
	}
	
	private void listaSucursales() {

	    String sucursalGuardada = SqliteConnection.getUUID(); // la actual

	    String sqlSucursales = "select id, name from branches order by name";

	    try {
	        Connection connection = DataBaseConnection.connection();
	        PreparedStatement stmt = connection.prepareStatement(sqlSucursales);
	        ResultSet result = stmt.executeQuery();

	        boolean encontrada = false;

	        while (result.next()) {
	            String nombreSucursal = result.getString("name");

	            sucursales.addItem(nombreSucursal);

	            // ✔ Si coincide, la seleccionamos
	            if (sucursalGuardada != null &&
	                nombreSucursal.trim().equals(sucursalGuardada.trim())) {

	                sucursales.setSelectedItem(nombreSucursal);
	                encontrada = true;
	            }
	        }

	        // ⚠️ Si no hay sucursal guardada o no coincide
	        if (!encontrada) {
	            sucursales.setSelectedIndex(-1); // deja vacío
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
}
