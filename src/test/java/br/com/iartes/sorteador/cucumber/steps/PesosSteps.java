package br.com.iartes.sorteador.cucumber.steps;

import br.com.iartes.sorteador.models.Sessao;
import br.com.iartes.sorteador.models.Sorteio;
import br.com.iartes.sorteador.models.Tema;
import br.com.iartes.sorteador.models.Voto;
import br.com.iartes.sorteador.models.enums.ModoSorteio;
import br.com.iartes.sorteador.models.enums.StatusTema;
import br.com.iartes.sorteador.models.enums.StatusVotacao;
import br.com.iartes.sorteador.services.SorteioService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PesosSteps {

    @Autowired
    private SorteioService sorteioService;

    private Sessao sessao;
    private Sorteio sorteio;

    @Dado("uma sessao de pesos no modo {string} com os temas:")
    public void uma_sessao_de_pesos_no_modo_com_os_temas(String modo, DataTable tabela) {
        List<Tema> temas = tabela.asMaps().stream()
                .map(linha -> new Tema(linha.get("nome"), StatusTema.valueOf(linha.get("status")), 0))
                .toList();
        sessao = new Sessao(ModoSorteio.valueOf(modo), temas, List.of());
    }

    @E("as cedulas de pesos:")
    public void as_cedulas_de_pesos(DataTable tabela) {
        List<Voto> votos = tabela.asMaps().stream()
                .map(linha -> new Voto(linha.get("participante"),
                        Arrays.stream(linha.get("temas").split(",")).map(String::trim).toList()))
                .toList();
        sessao.setVotos(votos);
    }

    @E("a votacao de pesos encerrada")
    public void a_votacao_de_pesos_encerrada() {
        sessao.setStatusVotacao(StatusVotacao.ENCERRADA);
    }

    @Quando("o sorteio de pesos e realizado")
    public void o_sorteio_de_pesos_e_realizado() {
        sorteio = sorteioService.sortear(sessao);
    }

    @Entao("o peso de pesos de cada tema deve ser:")
    public void o_peso_de_pesos_de_cada_tema_deve_ser(DataTable tabela) {
        Map<String, Double> esperado = new LinkedHashMap<>();
        tabela.asMaps().forEach(linha -> esperado.put(linha.get("tema"), Double.valueOf(linha.get("peso"))));

        assertThat(sorteio.getPesos()).containsExactlyEntriesOf(esperado);
        assertThat(esperado).containsKey(sorteio.getTemaSorteado().getNome());
    }
}
