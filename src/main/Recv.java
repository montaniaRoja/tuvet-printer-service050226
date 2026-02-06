
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import Invoice.PrintInvoice;
import connection.SqliteConnection;
import views.MainWindow;

import java.nio.charset.StandardCharsets;



public class Recv {

	public static void main(String[] argv) throws Exception {		
		
		// Inicializar base de datos y UUID
        SqliteConnection.initializeDatabase();

        // Obtener UUID único de esta máquina
        String uuid = SqliteConnection.getUUID();
        if (uuid == null) {
            System.err.println("No se pudo obtener el UUID. Saliendo...");
            return;
        }

        // Nombre de la cola única para esta máquina
        String queueName = "facturas_queue_" + uuid;       
        System.out.println("Usando la cola: " + queueName);
        
       

        // Configuración de conexión RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("35.208.101.55");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("ofloda01");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declarar la cola (durable)
            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
           
            // Callback para recibir mensajes
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                //System.out.println(message);

                try {
                    PrintInvoice.printInvoice(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };            

            // Consumir la cola
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });            
            // Abrir ventana principal
            new MainWindow();
            // Mantener el programa corriendo
            Thread.currentThread().join();
        }
    }

	}


