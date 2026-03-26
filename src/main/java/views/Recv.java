package views;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import Invoice.PrintInvoice;
import connection.SqliteConnection;


public class Recv {

    private static Connection connection;
    private static Channel channel;
    public static int copias=SqliteConnection.getCopias();

    public static void startConsumer(String uuid) throws Exception {

        String queueName = "facturas_queue_" + uuid;
        System.out.println("Usando la cola: " + queueName);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("35.208.101.55");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("ofloda01");
       

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Mensaje recibido: " + message);

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(message);

                JsonNode branchNode = root.get("branchName");

                // ⚠️ Validar que exista
                if (branchNode == null || branchNode.isNull()) {
                    System.out.println("⚠️ Mensaje sin branchName, ignorado");
                    return;
                }

                String branchName = branchNode.asText();

                System.out.println("Sucursal recibida: " + branchName);
                System.out.println("Sucursal local: " + uuid);

                if (branchName.equals(uuid)) {
                    System.out.println("✅ imprimir");
                   
                    PrintInvoice.printInvoice(message);
                    
                } else {
                    System.out.println("❌ No corresponde a esta sucursal");
                }

            } catch (Exception e) {
                System.out.println("❌ Error procesando mensaje");
                e.printStackTrace();
            }
        };
        
        

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
    public static void stopConsumer() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
            channel = null;
            connection = null;

            System.out.println("Consumer detenido.");            
            // pequeña pausa para asegurar cierre real
            Thread.sleep(300);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


}

