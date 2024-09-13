package org.example.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculadoraClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 3000);
            ICalculadora calc = (ICalculadora) registry.lookup("calculadora");
            long tempoInicio,tempoFim;

            tempoInicio = System.currentTimeMillis();

            executarMetodos(calc);

            tempoFim = System.currentTimeMillis();

            double segundos = (double) (tempoFim - tempoInicio) / 1000;
            System.out.println("\nTempo decorrido: " + segundos + " segundos");

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void executarMetodos(ICalculadora calc) throws RemoteException {
        // Operações Aritméticas Básicas
        System.out.println("\nOperações Aritméticas Básicas\n");
        int num1 = 10, num2 = 5;
        System.out.println("Soma: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.soma(num1, num2));
        System.out.println("Subtração: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.subtracao(num1, num2));
        System.out.println("Multiplicação: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.multiplicacao(num1, num2));
        System.out.println("Divisão: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.divisao(num1, num2));

        // Operações Aritméticas Avançadas
        System.out.println("\nOperações Aritméticas Avançadas \n");
        System.out.println("Exponencial: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.exponencial(num1, num2));
        System.out.println("Radiciação: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.radiciacao(num1, num2));
        System.out.println("Módulo: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.modulo(num1, num2));

        // Operações Lógicas e Comparativas
        System.out.println("\nOperações Lógicas e Comparativas \n");
        System.out.println("Maior: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.maior(num1, num2));
        System.out.println("Menor: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.menor(num1, num2));
        System.out.println("Igual: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.igual(num1, num2));
        System.out.println("Diferente: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.diferente(num1, num2));

        // Outras Operações Matemáticas
        System.out.println("\nOutras Operações Matemáticas \n");
        System.out.println("Mínimo: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.minimo(num1, num2));
        System.out.println("Máximo: num1 = " + num1 + ", num2 = " + num2 + " -> Resultado: " + calc.maximo(num1, num2));

        // Operações de Cálculo de Juros
        System.out.println("\nOperações de Cálculo de Juros \n");
        double capital = 10000.0, taxa = 0.05, tempo = 12.0, juros = 6000.0;
        System.out.println("Juros Simples: capital = " + capital + ", taxa = " + taxa + ", tempo = " + tempo + " -> Resultado: " + calc.jurosSimples(capital, taxa, tempo));
        System.out.println("Juros Compostos: capital = " + capital + ", taxa = " + taxa + ", tempo = " + tempo + " -> Resultado: " + calc.jurosCompostos(capital, taxa, tempo));
        System.out.println("Taxa de Juros: juros = " + juros + ", capital = " + capital + ", tempo = " + tempo + " -> Resultado: " + calc.taxaDeJuros(juros, capital, tempo));

        // Operações para Amortização e Financiamentos
        System.out.println("\nOperações para Amortização e Financiamentos \n");
        int tempoFinanciamento = 12; // em meses
        System.out.println("Valor da Parcela: capital = " + capital + ", taxa = " + taxa + ", tempo = " + tempoFinanciamento + " -> Resultado: " + calc.valorParcela(capital, taxa, tempoFinanciamento));
        System.out.println("Amortização: capital = " + capital + ", tempo = " + tempoFinanciamento + " -> Resultado: " + calc.amortizacao(capital, tempoFinanciamento));
    }
}
