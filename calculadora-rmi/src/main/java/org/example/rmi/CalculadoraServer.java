package org.example.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculadoraServer {
    public static void main(String[] args) {
        try {
            ICalculadora calc = new CalculadoraImpl();
            Registry registry = LocateRegistry.createRegistry(3000);
            registry.rebind("calculadora", calc);
            System.out.println("Servidor de calculadora está pronto!");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.fillInStackTrace();
        }
    }
}
