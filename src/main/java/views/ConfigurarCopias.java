package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connection.SqliteConnection;

public class ConfigurarCopias extends JDialog {

    private JTextField txtCopias;
    private JButton btnGuardar;
    private JButton btnSalir;
    private JPanel panel;

    public ConfigurarCopias(JFrame parent) {
        super(parent, "Configurar Copias", true);

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        crearCampos();
        crearPanel();
        add(panel);
    }

    private void crearCampos() {

        txtCopias = new JTextField();
        txtCopias.setBounds(20, 30, 240, 25);

        // 🔥 Cargar valor actual
        int copiasActual = SqliteConnection.getCopias();
        txtCopias.setText(String.valueOf(copiasActual));

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(20, 100, 100, 25);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(160, 100, 100, 25);

        btnSalir.addActionListener(e -> dispose());

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int copias = Integer.parseInt(txtCopias.getText());

                    if (copias <= 0) {
                        JOptionPane.showMessageDialog(null, "Ingrese un número mayor a 0");
                        return;
                    }

                    SqliteConnection.guardarCopias(copias);

                    JOptionPane.showMessageDialog(null, "Copias guardadas correctamente");

                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un número válido");
                }
            }
        });
    }

    private void crearPanel() {
        panel = new JPanel();
        panel.setLayout(null);

        JLabel lbl = new JLabel("Cantidad de copias:");
        lbl.setBounds(20, 10, 200, 20);

        panel.add(lbl);
        panel.add(txtCopias);
        panel.add(btnGuardar);
        panel.add(btnSalir);
    }
}