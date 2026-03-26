package views;

import javax.swing.*;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import connection.SqliteConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeleccionarImpresora extends JDialog {

    private JComboBox<String> impresoras;
    private JButton btnSalir;
    private JButton btnGuardar;
    private JPanel panelImpresora;
    private String nombreImpresora;

    public SeleccionarImpresora(JFrame parent) {
        super(parent, "Seleccione Impresora", true);

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        crearCampos();
        crearPanel();
        add(panelImpresora);
    }

    private void crearCampos() {

        impresoras = new JComboBox<>();
        listaImpresoras();

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> dispose());

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                nombreImpresora = impresoras.getSelectedItem().toString();

                guardarImpresora(nombreImpresora);

                System.out.println("Impresora guardada: " + nombreImpresora);

                dispose();
            }
        });
    }

    private void guardarImpresora(String impresora) {
        SqliteConnection.guardarImpresora(impresora);
    }

    private void crearPanel() {
        panelImpresora = new JPanel();
        panelImpresora.setLayout(null);

        panelImpresora.add(impresoras).setBounds(20, 20, 300, 25);
        panelImpresora.add(btnGuardar).setBounds(20, 150, 150, 25);
        panelImpresora.add(btnSalir).setBounds(200, 150, 150, 25);
    }

    private void listaImpresoras() {

        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        if (services.length == 0) {
            impresoras.addItem("No hay impresoras disponibles");
            return;
        }

        for (PrintService ps : services) {
            impresoras.addItem(ps.getName());
        }

        // Seleccionar la guardada si existe
        String impresoraGuardada = SqliteConnection.getImpresora();
        if (impresoraGuardada != null) {
            impresoras.setSelectedItem(impresoraGuardada);
        }
    }
}