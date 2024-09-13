package org.example.rmi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraImpl extends UnicastRemoteObject implements ICalculadora {

    public CalculadoraImpl() throws RemoteException {
        super();
    }

    private static BigDecimal arredondar(double valor) {
        BigDecimal bd = new BigDecimal(valor);

        return bd.setScale(2, RoundingMode.HALF_UP);
    }


    public int soma(int num1, int num2) throws RemoteException {
        return num1 + num2;
    }

    public int subtracao(int num1, int num2) throws RemoteException{
        return num1 - num2;
    }

    public int multiplicacao(int num1, int num2) throws RemoteException{
        return num1 * num2;
    }

    public int divisao(int num1, int num2) throws RemoteException{
        return num1 / num2;
    }

    public double exponencial(int num1, int num2) throws RemoteException{
        return Math.pow(num1, num2);
    }

    public double radiciacao(int num1, int num2) throws RemoteException{
        return  Math.pow(num1, 1.0 / num2);
    }

    public int modulo(int num1, int num2) throws RemoteException{
        return num1 % num2;
    }

    public boolean maior(int num1, int num2) throws RemoteException{
        return num1 > num2;
    }

    public boolean menor(int num1, int num2) throws RemoteException{
        return num1 < num2;
    }

    public boolean igual(int num1, int num2) throws RemoteException{
        return num1 == num2;
    }

    public boolean diferente(int num1, int num2) throws RemoteException{
        return num1 != num2;
    }

    public int minimo(int num1, int num2) throws RemoteException{
        return Math.min(num1, num2);
    }

    public int maximo(int num1, int num2) throws RemoteException{
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

    public double amortizacao(double capital, int tempo) throws RemoteException{
        double resultado = capital / tempo;

        return arredondar(resultado).doubleValue();
    }
}
