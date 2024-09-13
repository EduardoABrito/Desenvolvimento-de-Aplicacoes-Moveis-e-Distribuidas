package org.example.local;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraLocal {
    private static BigDecimal arredondar(double valor) {
        BigDecimal bd = new BigDecimal(valor);

        return bd.setScale(2, RoundingMode.HALF_UP);
    }


    public int soma(int num1, int num2) {
        return num1 + num2;
    }

    public int subtracao(int num1, int num2) {
        return num1 - num2;
    }

    public int multiplicacao(int num1, int num2) {
        return num1 * num2;
    }

    public int divisao(int num1, int num2) {
        return num1 / num2;
    }

    public double exponencial(int num1, int num2) {
        return Math.pow(num1, num2);
    }

    public double radiciacao(int num1, int num2) {
        return  Math.pow(num1, 1.0 / num2);
    }

    public int modulo(int num1, int num2) {
        return num1 % num2;
    }

    public boolean maior(int num1, int num2) {
        return num1 > num2;
    }

    public boolean menor(int num1, int num2) {
        return num1 < num2;
    }

    public boolean igual(int num1, int num2) {
        return num1 == num2;
    }

    public boolean diferente(int num1, int num2) {
        return num1 != num2;
    }

    public int minimo(int num1, int num2) {
        return Math.min(num1, num2);
    }

    public int maximo(int num1, int num2) {
        return Math.max(num1, num2);
    }

    public double jurosSimples(double capital, double taxa, double tempo){
        double resultado = capital * taxa * tempo;

        return arredondar(resultado).doubleValue();
    }

    public double jurosCompostos(double capital, double taxa, double tempo){
        double resultado = capital * Math.pow(1 + taxa, tempo);

        return arredondar(resultado).doubleValue();
    }

    public double taxaDeJuros(double juros, double capital, double tempo){
        double resultado = juros / (capital * tempo);

        return arredondar(resultado).doubleValue();
    }

    public double valorParcela(double capital, double taxa, int tempo){
        double taxaTempoAux = Math.pow(1 + taxa, tempo);
        double resultado = capital * (taxa * taxaTempoAux) / (taxaTempoAux - 1);

        return arredondar(resultado).doubleValue();
    }

    public double amortizacao(double capital, int tempo) {
        double resultado = capital / tempo;

        return arredondar(resultado).doubleValue();
    }

    public static void main(String[] args) {
        long tempoInicio,tempoFim;

        tempoInicio = System.currentTimeMillis();

        CalculadoraLocal calc = new CalculadoraLocal();

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

        tempoFim = System.currentTimeMillis();

        double segundos = (double) (tempoFim - tempoInicio) / 1000;
        System.out.println("Tempo decorrido: " + segundos + " segundos");
    }
}
