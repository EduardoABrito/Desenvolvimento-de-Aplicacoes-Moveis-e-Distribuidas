package org.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculadora extends Remote {
    // Operações Aritméticas Básicas
    public int soma(int num1, int num2) throws RemoteException;
    public int subtracao(int num1, int num2) throws RemoteException;
    public int multiplicacao(int num1, int num2) throws RemoteException;
    public int divisao(int num1, int num2) throws RemoteException;

    // Operações Aritméticas Avançadas
    public double exponencial(int num1, int num2) throws RemoteException;
    public double radiciacao(int num1, int num2) throws RemoteException;
    public int modulo(int num1, int num2) throws RemoteException;

    // Operações Lógicas e Comparativas
    public boolean maior(int num1, int num2) throws RemoteException;
    public boolean menor(int num1, int num2) throws RemoteException;
    public boolean igual(int num1, int num2) throws RemoteException;
    public boolean diferente(int num1, int num2) throws RemoteException;

    // Outras Operações Matemáticas
    public int minimo(int num1, int num2) throws RemoteException;
    public int maximo(int num1, int num2) throws RemoteException;

    // Operações de Cálculo de Juros
    public double jurosSimples(double capital, double taxa, double tempo) throws RemoteException;
    public double jurosCompostos(double capital, double taxa, double tempo) throws RemoteException;
    public double taxaDeJuros(double juros, double capital, double tempo) throws RemoteException;

    // Operações para Amortização e Financiamentos
    public double valorParcela(double capital, double taxa, int tempo) throws RemoteException;
    public double amortizacao(double capital, int tempo) throws RemoteException;
}
