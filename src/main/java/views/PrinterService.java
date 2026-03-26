package views;

import connection.SqliteConnection;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class PrinterService {

    private static FileLock lock;

    public static void main(String[] args) throws Exception {

        try {
            RandomAccessFile raf = new RandomAccessFile("app.lock", "rw");
            FileChannel channel = raf.getChannel();

            lock = channel.tryLock();

            if (lock == null) {
                System.out.println("⚠️ La aplicación ya está en ejecución");
                javax.swing.JOptionPane.showMessageDialog(null, "La aplicación ya está abierta");
                return;
            }

        } catch (Exception e) {
            System.out.println("⚠️ No se pudo obtener lock");
            return;
        }

        // 🔥 tu código normal
        SqliteConnection.initializeDatabase();

        String uuid = SqliteConnection.getUUID();

        if (uuid == null) {
            System.err.println("No se pudo obtener el UUID.");
            return;
        }

        Recv.startConsumer(uuid);

        new MainWindow();
    }
}
