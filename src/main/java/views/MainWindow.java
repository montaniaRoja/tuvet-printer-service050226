package views;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import connection.SqliteConnection;

public class MainWindow extends JFrame {
    private JFrame frame;
    private JButton btnMigration;
    private JPanel panel;
    private JLabel imageLabel;
    private ImageIcon printerIcon;
    private ImageIcon originalIcon;

    public MainWindow() {
        buttonConfig();        
        imageLabelConfig();
        panelConfig();
        frameConfig();
        frame.setVisible(true);
        eventsConfig();
    }
    
    private void imageLabelConfig() { 
    	
    	try {
    	originalIcon = new ImageIcon(getClass().getResource("/images/impresora.png"));
    	}catch(Exception e){
    		originalIcon = new ImageIcon("src/images/impresora.png");
    	}
    	Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); 
    	
    	printerIcon = new ImageIcon(scaledImage); 
    	
    	imageLabel = new JLabel(printerIcon); imageLabel.setBounds(100, 10, 80, 80); 
    	
    	}
    
    
    private void buttonConfig() {
    	btnMigration=new JButton("Ver Id de esta computadora"); 
    
    }
    
    private void frameConfig() { frame = new JFrame("TuVet Printer Service V0502261655"); 
    //ImageIcon icon = new ImageIcon("src/images/logo-color.png"); 
    try {
    frame.setIconImage(new ImageIcon(getClass().getResource("/images/logo-color.png")).getImage());
    }catch(Exception e) {
    	
    	frame.setIconImage(new ImageIcon("src/images/logo-color.png").getImage());
    }
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(370, 350); frame.setLocationRelativeTo(null); 
    frame.getContentPane().add(panel); 
    }
    
    private void panelConfig() {
        panel=new JPanel();
        panel.setLayout(null);        
        panel.add(imageLabel).setBounds(50, 10, 200, 100);      
        panel.add(btnMigration).setBounds(50, 130, 200, 40);

    }
    
    private void eventsConfig() {
    	

        btnMigration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uuid = SqliteConnection.getUUID();
                if (uuid != null) {
                    JTextField textField = new JTextField(uuid);
                    textField.setEditable(false); 
                    textField.setBorder(null); 
                    textField.setBackground(null); 
                    textField.setOpaque(false); 
                    textField.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12)); // Fuente monoespaciada para mejor lectura

                    JOptionPane.showMessageDialog(frame, textField, "UUID de la computadora", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No se pudo obtener el UUID.");
                }
            }
        });


    }}