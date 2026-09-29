package br.com.iartes.sorteador.cucumber.steps;

import br.com.iartes.sorteador.models.Tema;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;

import static org.assertj.core.api.Assertions.assertThat;

public class TemaSteps {

    private Tema tema;

    @Dado("que crio um tema chamado {string}")
    public void que_crio_um_tema_chamado(String nome) {
        tema = new Tema(nome);
    }

    @Entao("o tema {string} deve estar com status {string}")
    public void o_tema_deve_estar_com_status(String nome, String status) {
        assertThat(tema.getNome()).isEqualTo(nome);
        assertThat(tema.getStatus().name()).isEqualTo(status);
    }
}
