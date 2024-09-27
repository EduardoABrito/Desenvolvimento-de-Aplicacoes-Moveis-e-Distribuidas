package br.ldamd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;

public class Cozinha extends Thread {
    public Cozinha(String tipo){
        this.tipo = tipo;
    }

    private final static String EXCHANGE_NAME = "restaurante-eduardo";
    private final ArrayList<String> pedidos = new ArrayList<String>();
    private final String tipo;

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (pedidos) {
                    while (pedidos.isEmpty()) {
                        System.out.println("Cozinha " + this.tipo + " aguardando pedidos...");
                        pedidos.wait();
                    }

                    String pedido = pedidos.remove(0);
                    this.processarPedido(pedido);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrompida.");
        }
    }

    public void iniciar() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Dotenv dotenv = Dotenv.load();
        String host = dotenv.get("RABBITMQ_HOST");

        factory.setUri(host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        String bindingKey = "pedido." + this.tipo.toLowerCase();

        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String pedido = new String(delivery.getBody(), "UTF-8");

            synchronized (pedidos) {
                pedidos.add(pedido);
                System.out.println("Novo pedido recebido: " + pedido + " (" + pedidos.size() + " na fila)");
                pedidos.notify();
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

        this.start();
    }

    private void processarPedido(String pedido){
        try{
            System.out.println("Cozinha " + this.tipo + " preparando: " + pedido);
            Thread.sleep((2 + (int) (Math.random() * 4)) * 1000);
            System.out.println("Cozinha " + this.tipo + " finalizou: " + pedido);
        }catch (Exception e){
            System.out.println("Erro ao processar pedido.");
        }
    }
}
