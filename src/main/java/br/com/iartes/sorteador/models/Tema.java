package br.com.iartes.sorteador.models;

import br.com.iartes.sorteador.models.enums.StatusTema;

public class Tema {

    private String nome;
    private StatusTema status = StatusTema.DISPONIVEL;
    private int rodadasRestantes = 0;

    public Tema() {
    }

    public Tema(String nome) {
        this.nome = nome;
    }

    public Tema(String nome, StatusTema status, int rodadasRestantes) {
        this.nome = nome;
        this.status = status;
        this.rodadasRestantes = rodadasRestantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusTema getStatus() {
        return status;
    }

    public void setStatus(StatusTema status) {
        this.status = status;
    }

    public int getRodadasRestantes() {
        return rodadasRestantes;
    }

    public void setRodadasRestantes(int rodadasRestantes) {
        this.rodadasRestantes = rodadasRestantes;
    }
}
