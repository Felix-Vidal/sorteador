package br.com.iartes.sorteador.models;

import java.util.List;

public class Voto {

    private String nomeParticipante;
    private List<String> temas;

    public Voto() {
    }

    public Voto(String nomeParticipante, List<String> temas) {
        this.nomeParticipante = nomeParticipante;
        this.temas = temas;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }
}
