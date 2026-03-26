package views;

import javax.swing.*;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import views.PrintTest;

import connection.SqliteConnection;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
	private JFrame frame; //ventana principal
    private JButton btnMigration;
    private JMenuBar barraMenu;
    private JMenu configuracionMenu;    
    private JMenuItem opSeleccionarSucursal, opSeleccionarImpresora, opProbarImpresora, opConfigurarCopias;
    private JPanel panel;
    private JLabel imageLabel;
    private ImageIcon printerIcon;
    private ImageIcon originalIcon;

    public MainWindow() {
    	 
       // buttonConfig();        
        //imageLabelConfig();
       
       // panelConfig();
        
        frameConfig();
        configurarBarraMenu();
        
        frame.setVisible(true);
      //  eventsConfig();
    }
    
    private void configurarBarraMenu() {
    	barraMenu=new JMenuBar();
    	configurarConfiguracionMenu();
        barraMenu.add(configuracionMenu);
        frame.setJMenuBar(barraMenu);
        initSystemTray();
    }
    
    
    private void configurarConfiguracionMenu() {
    	configuracionMenu = new JMenu("Configuraciones");
    	
    	opSeleccionarSucursal = new JMenuItem("Seleccionar Sucursal");
    	 opSeleccionarSucursal.addActionListener(new ActionListener(){
         	@Override
             public void actionPerformed(ActionEvent e) {
         		JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(configuracionMenu);
         		SeleccionarSucursal sucursal=new SeleccionarSucursal(parent);
         		sucursal.setVisible(true);
         		System.out.println("seleccionar sucursal");
             }
         });
    	 
    	 opSeleccionarImpresora = new JMenuItem("Seleccionar Impresora");
    	 opSeleccionarImpresora.addActionListener(new ActionListener(){
         	@Override
             public void actionPerformed(ActionEvent e) {
         		
         		JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(configuracionMenu);
         		SeleccionarImpresora imp = new SeleccionarImpresora(parent);
         		imp.setVisible(true);
             }
         });
    	 
    	 opProbarImpresora = new JMenuItem("Imprimir Prueba de Impresora");
    	 opProbarImpresora.addActionListener(new ActionListener(){
    		    @Override
    		    public void actionPerformed(ActionEvent e) {

    		        boolean ok = PrintTest.printTestPage();

    		        if (ok) {
    		            JOptionPane.showMessageDialog(null, "Prueba enviada a la impresora");
    		        } else {
    		            JOptionPane.showMessageDialog(null, "Error al imprimir");
    		        }
    		    }
    		});
    	 
    	 opConfigurarCopias = new JMenuItem("Configurar Cantidad de Copias");
    	 opConfigurarCopias.addActionListener(new ActionListener(){
    		    @Override
    		    public void actionPerformed(ActionEvent e) {

    		    	JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(configuracionMenu);
             		ConfigurarCopias copias=new ConfigurarCopias(parent);
             		copias.setVisible(true);
             		System.out.println("seleccionar sucursal");
    		    }
    		});
    	 
    	 
    	 
    	 configuracionMenu.add(opSeleccionarSucursal);
    	 configuracionMenu.add(opSeleccionarImpresora);
    	 configuracionMenu.add(opProbarImpresora);
    	 configuracionMenu.add(opConfigurarCopias);
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
    
    private void frameConfig() { 

        String sucursal = SqliteConnection.getUUID();

        if (sucursal == null || sucursal.trim().isEmpty() || sucursal.equalsIgnoreCase("sin sucursal")) {
            sucursal = "Sin sucursal";
        }

        frame = new JFrame("TuVet Printer Service - " + sucursal);

        try {
            frame.setIconImage(new ImageIcon(getClass().getResource("/images/logo-color.png")).getImage());
        } catch (Exception e) {
            frame.setIconImage(new ImageIcon("src/images/logo-color.png").getImage());
        }

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                frame.setVisible(false);
                System.out.println("Minimizado a bandeja");
            }
        });
        frame.setSize(800, 600); 
        frame.setLocationRelativeTo(null); 
    }
    
    private void panelConfig() {
        panel=new JPanel();
        panel.setLayout(null);        
        panel.add(imageLabel).setBounds(50, 10, 200, 100);      
        panel.add(btnMigration).setBounds(50, 130, 200, 40);
       
        

    }
    
    private void initSystemTray() {

        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray no soportado");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();

        // Icono (usa el mismo logo)
        Image image;
        try {
            image = new ImageIcon(getClass().getResource("/images/logo-color.png")).getImage();
        } catch (Exception e) {
            image = new ImageIcon("src/images/logo-color.png").getImage();
        }

        PopupMenu popup = new PopupMenu();

        MenuItem abrir = new MenuItem("Abrir");
        MenuItem salir = new MenuItem("Salir");

        popup.add(abrir);
        popup.addSeparator();
        popup.add(salir);

        TrayIcon trayIcon = new TrayIcon(image, "TuVet Printer Service", popup);
        trayIcon.setImageAutoSize(true);

        // Acción abrir ventana
        abrir.addActionListener(e -> {
            frame.setVisible(true);
            frame.setState(JFrame.NORMAL);
        });

        // Acción salir
        salir.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });

        // Doble click en icono
        trayIcon.addActionListener(e -> {
            frame.setVisible(true);
            frame.setState(JFrame.NORMAL);
        });

        try {
            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void eventsConfig() {
    	
/*
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
        }); */


    }}