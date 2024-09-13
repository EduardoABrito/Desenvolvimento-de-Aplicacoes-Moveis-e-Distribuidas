import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static class RetornoMultiplos {
        RetornoMultiplos(){
            this.somatorio = 0;
            this.quantidade = 0;
        }

        public long somatorio;
        public long quantidade;
        private long tempoInicio;
        private long tempoFim;

        public void iniciarTempo(){
            this.tempoInicio = System.currentTimeMillis();
        }
        public void finalizarTempo(){
            this.tempoFim = System.currentTimeMillis();
        }
        public void adicionarResultado(RetornoMultiplos resultado) {
            this.somatorio += resultado.somatorio;
            this.quantidade += resultado.quantidade;
        }

        public BigDecimal getTempo() {
            double tempo = (this.tempoFim - this.tempoInicio) / 1000.0;

            BigDecimal decimal = new BigDecimal(tempo);
            BigDecimal arredondado = decimal.setScale(4, RoundingMode.HALF_UP);

            return arredondado;
        }
    }

    public static void main(String[] args) {
        boolean multiThread = true;
        int[] calcularThreads = { 1, 2 };
        int inicio = 0;
        int limite = 10000;

        if(multiThread){
            Map<String, RetornoMultiplos> resultados = new HashMap<>();
            int threadsDisponiveisPC = Runtime.getRuntime().availableProcessors();

            for(int thread : calcularThreads){
                System.out.println("Iniciando calculo: "+ thread + " Threads");
                resultados.put(thread+ " Threads",calcularMultiplosMultiThread(inicio,limite, thread));
                System.out.println("Finalizado calculo: "+ thread + " Threads");
            }

            resultados.put("Quantidade maxima " + threadsDisponiveisPC + " Threads",calcularMultiplosMultiThread(inicio,limite, threadsDisponiveisPC));

            for (Map.Entry<String, RetornoMultiplos> entry : resultados.entrySet()) {
                toStringResult(entry.getValue(), "Resultado - "+ entry.getKey());
            }

        }else{
            RetornoMultiplos resultado = calcularMultiplosSingleThread(inicio,limite);
            toStringResult(resultado, "Resultado single thread");
        }
    }
    public static RetornoMultiplos calcularMultiplos(int inicio,int limite){
        RetornoMultiplos result = new RetornoMultiplos();

        for (int i = inicio; i <= limite; i++) {
            if (i % 3 == 0) {
                System.out.println(i);
                result.somatorio += i;
                result.quantidade++;
            }
        }

        return result;
    }
    public static RetornoMultiplos calcularMultiplosSingleThread(int inicio,int limite){
        RetornoMultiplos result = new RetornoMultiplos();

        result.iniciarTempo();

        result.adicionarResultado(calcularMultiplos(inicio, limite));

        result.finalizarTempo();

        return result;
    }
    public static RetornoMultiplos calcularMultiplosMultiThread(int inicio, int limite, int numThreads) {
        RetornoMultiplos result = new RetornoMultiplos();
        result.iniciarTempo();

        try {
            List<Thread> threads = new ArrayList<>();
            List<RetornoMultiplos> resultadosThreads = new ArrayList<>();

            int intervalo = (limite - inicio + 1) / numThreads;

            for (int i = 0; i < numThreads; i++) {
                final int threadInicio = inicio + i * intervalo;
                final int threadLimite = (i == numThreads - 1) ? limite : threadInicio + intervalo - 1;

                RetornoMultiplos resultadoThread = new RetornoMultiplos();
                resultadosThreads.add(resultadoThread);

                Thread thread = new Thread(() -> {
                    RetornoMultiplos resultadoParcial = calcularMultiplos(threadInicio, threadLimite);
                    resultadoThread.adicionarResultado(resultadoParcial);
                });

                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            for (RetornoMultiplos resultadoThread : resultadosThreads) {
                result.adicionarResultado(resultadoThread);
            }

            result.finalizarTempo();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public static void toStringResult(RetornoMultiplos result, String title){
        NumberFormat formatador = NumberFormat.getNumberInstance(Locale.getDefault());
        title = "--------- "+title+" -----------";

        System.out.println(title);
        System.out.println("Quantidade de números: " + formatador.format(result.quantidade));
        System.out.println("Somatório dos valores: " + formatador.format(result.somatorio));
        System.out.println("Tempo: " + result.getTempo() +" Segundos");
        System.out.println("-".repeat(title.length()));
    }

}