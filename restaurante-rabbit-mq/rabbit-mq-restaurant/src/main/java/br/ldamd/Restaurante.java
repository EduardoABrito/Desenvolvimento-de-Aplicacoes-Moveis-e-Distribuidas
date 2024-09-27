package br.ldamd;

public class Restaurante {
    public static void main(String[] argv) throws Exception {
        Cozinha cozinhaPizza = new Cozinha("Pizza");
        Cozinha cozinhaHamburguer = new Cozinha("Hamburguer");
        Cozinha cozinhaSushi = new Cozinha("Sushi");
        Cozinha cozinhaSalada = new Cozinha("Salada");
        Cozinha cozinhaTacos = new Cozinha("Tacos");

        cozinhaPizza.iniciar();
        cozinhaHamburguer.iniciar();
        cozinhaSushi.iniciar();
        cozinhaSalada.iniciar();
        cozinhaTacos.iniciar();
    }
}
