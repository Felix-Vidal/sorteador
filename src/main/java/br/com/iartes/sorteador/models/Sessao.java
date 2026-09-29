package br.com.iartes.sorteador.models;

import br.com.iartes.sorteador.models.enums.ModoSorteio;

import java.util.List;

public class Sessao {

    private ModoSorteio modo;
    private List<Tema> temas;
    private List<Voto> votos;
    private double pesoBase = 1;

    public Sessao() {
    }

    public Sessao(ModoSorteio modo, List<Tema> temas, List<Voto> votos) {
        this.modo = modo;
        this.temas = temas;
        this.votos = votos;
    }

    public Sessao(ModoSorteio modo, List<Tema> temas, List<Voto> votos, double pesoBase) {
        this.modo = modo;
        this.temas = temas;
        this.votos = votos;
        this.pesoBase = pesoBase;
    }

    public ModoSorteio getModo() {
        return modo;
    }

    public void setModo(ModoSorteio modo) {
        this.modo = modo;
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public double getPesoBase() {
        return pesoBase;
    }

    public void setPesoBase(double pesoBase) {
        this.pesoBase = pesoBase;
    }
}
