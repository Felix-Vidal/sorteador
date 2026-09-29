package br.com.iartes.sorteador.models;

import java.util.Map;

public class Sorteio {

    private Tema temaSorteado;
    private Map<String, Double> pesos;

    public Sorteio() {
    }

    public Sorteio(Tema temaSorteado, Map<String, Double> pesos) {
        this.temaSorteado = temaSorteado;
        this.pesos = pesos;
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
}
