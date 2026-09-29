package br.com.iartes.sorteador.models;

import java.util.Map;

public class Sorteio {

    private Tema temaSorteado;
    private Map<String, Double> pesos;
    private Map<String, Double> probabilidades;

    public Sorteio() {
    }

    public Sorteio(Tema temaSorteado, Map<String, Double> pesos, Map<String, Double> probabilidades) {
        this.temaSorteado = temaSorteado;
        this.pesos = pesos;
        this.probabilidades = probabilidades;
    }

    public Tema getTemaSorteado() {
        return temaSorteado;
    }

    public void setTemaSorteado(Tema temaSorteado) {
        this.temaSorteado = temaSorteado;
    }

    public Map<String, Double> getPesos() {
        return pesos;
    }

    public void setPesos(Map<String, Double> pesos) {
        this.pesos = pesos;
    }

    public Map<String, Double> getProbabilidades() {
        return probabilidades;
    }

    public void setProbabilidades(Map<String, Double> probabilidades) {
        this.probabilidades = probabilidades;
    }
}
