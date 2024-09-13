package org.example;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ChatApp {
    private static final int PORT = 6789;
    private static String user;
    private static MulticastSocket mSocket = null;
    private static InetAddress groupIp = null;
    private static InetSocketAddress group = null;
    private static final String[] rooms = {
            "230.0.0.1",
            "230.0.0.2",
            "230.0.0.3",
    };

    public static void main(String[] args){
        try{
            boolean sendMessageFlag = true;
            String message = null;
            Scanner scanner = new Scanner(System.in);
            Thread receiveMessages = new Thread(ChatApp::receiveMessages);

            System.out.println("Digite o usuario:");
            user = scanner.next();

            System.out.println("Digite a posição da sala que deseja entrar:");
            IntStream.range(0, rooms.length).forEachOrdered(i ->
                    System.out.println("Sala " + (i + 1) + ": " + rooms[i])
            );

            int room = scanner.nextInt();

            enterRoom(room, user);
            receiveMessages.start();
            System.out.println("Digite sua mensagem ou '/sair': ");

            while (sendMessageFlag) {
                message = scanner.nextLine();

                if(message.isEmpty())
                    continue;

                sendMessage(user + ": " + message);

                if(message.equalsIgnoreCase("/sair")){
                    sendMessageFlag = false;
                    leaveRoom();
                }
            }
        }catch (Exception e){
            System.out.println("Ocorreu um erro inesperado." + e.getMessage());
            leaveRoom();
        }
    }

    public static boolean enterRoom(int room, String user){
        try {
            groupIp = InetAddress.getByName(rooms[room - 1]);
            group = new InetSocketAddress(groupIp, PORT);
            mSocket = new MulticastSocket(PORT);

            mSocket.joinGroup(group, null);

            sendMessage(user + " entrou na sala");

            return true;
        }catch (IOException error){
            System.out .println("Não foi possivel entrar na sala escolhida." + rooms[room - 1]);
            return false;
        }
    }

    public static boolean leaveRoom() {
        try {
            sendMessage(user + " saiu da sala");
            mSocket.leaveGroup(group, null);

            if (mSocket != null){
                mSocket.close();
                return true;
            }

            return false;
        } catch (Exception e){
            System.out.println("Erro ao sair da sala.");
            return false;
        }
    }

    public static void sendMessage(String message) {
        try{
            byte[] sendMessage = message.getBytes();

            DatagramPacket messageOut = new DatagramPacket(sendMessage, sendMessage.length, groupIp, PORT);

            mSocket.send(messageOut);
        }catch (IOException error){
            System.out .println("Não foi possivel enviar a mensagem.");
        }
    }

    public static void receiveMessages() {
        try{
            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                mSocket.receive(messageIn);
                System.out.println(new String(messageIn.getData()).trim());
                buffer = new byte[1000];
            }
        }catch (Exception e){
            System.out.println("Socket: " + e.getMessage());
        }
    }
}