package br.ldamd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.github.cdimascio.dotenv.Dotenv;

import java.nio.charset.StandardCharsets;

public class Produtor {
    private final static String EXCHANGE_NAME = "restaurante-eduardo";
    private static final String[] pedidos = {"Pizza", "Hamburguer", "Sushi", "Salada", "Tacos"};

    public static void main(String[] arg) throws Exception {
        Dotenv dotenv = Dotenv.load();
        String host = dotenv.get("RABBITMQ_HOST");
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri(host);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            for (int i = 0; i < 10; i++) {
                String tipo= pedidos[(int) (Math.random() * pedidos.length)];
                int quantidade = (int )(Math.random() * 10 + 1);
                String pedido = quantidade + " " + tipo;

                String routingKey = "pedido." + tipo.toLowerCase();
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, pedido.getBytes(StandardCharsets.UTF_8));
                System.out.println("Pedido enviado: " + pedido);
            }
        }
    }
}
