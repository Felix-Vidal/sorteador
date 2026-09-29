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
import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class SorteioSteps {

    @Autowired
    private SorteioService sorteioService;

    private Sessao sessao;
    private Sorteio sorteio;
    private RuntimeException erro;

    @Dado("uma sessao no modo {string} com os temas:")
    public void uma_sessao_no_modo_com_os_temas(String modo, DataTable tabela) {
        List<Tema> temas = tabela.asMaps().stream()
                .map(linha -> new Tema(linha.get("nome"), StatusTema.valueOf(linha.get("status")), 0))
                .toList();
        sessao = new Sessao(ModoSorteio.valueOf(modo), temas, List.of());
    }

    @E("os votos:")
    public void os_votos(DataTable tabela) {
        List<Voto> votos = tabela.asMaps().stream()
                .map(linha -> new Voto(linha.get("participante"),
                        Arrays.stream(linha.get("temas").split(",")).map(String::trim).toList()))
                .toList();
        sessao.setVotos(votos);
    }

    @E("a votacao esta {string}")
    public void a_votacao_esta(String statusVotacao) {
        sessao.setStatusVotacao(StatusVotacao.valueOf(statusVotacao));
    }

    @Quando("o sorteio e realizado")
    public void o_sorteio_e_realizado() {
        executar(sorteioService);
    }

    @Quando("o sorteio e realizado com o valor aleatorio {double}")
    public void o_sorteio_e_realizado_com_o_valor_aleatorio(double fracao) {
        executar(new SorteioService(new RandomGenerator() {
            @Override
            public long nextLong() {
                throw new UnsupportedOperationException();
            }

            @Override
            public double nextDouble(double bound) {
                return fracao * bound;
            }
        }));
    }

    @Entao("o tema sorteado deve ser {string}")
    public void o_tema_sorteado_deve_ser(String nome) {
        assertThat(erro).isNull();
        assertThat(sorteio.getTemaSorteado().getNome()).isEqualTo(nome);
    }

    @Entao("os pesos devem ser:")
    public void os_pesos_devem_ser(DataTable tabela) {
        Map<String, Double> esperado = new LinkedHashMap<>();
        tabela.asMaps().forEach(linha -> esperado.put(linha.get("tema"), Double.valueOf(linha.get("peso"))));

        assertThat(erro).isNull();
        assertThat(sorteio.getPesos()).containsExactlyEntriesOf(esperado);
        assertThat(esperado).containsKey(sorteio.getTemaSorteado().getNome());
    }

    @Entao("as chances devem ser:")
    public void as_chances_devem_ser(DataTable tabela) {
        assertThat(erro).isNull();

        Map<String, Double> esperado = new LinkedHashMap<>();
        tabela.asMaps().forEach(linha -> esperado.put(linha.get("tema"), Double.valueOf(linha.get("chance"))));

        Map<String, Double> chances = sorteio.getProbabilidades();
        assertThat(chances).containsOnlyKeys(esperado.keySet().toArray(String[]::new));
        esperado.forEach((tema, chance) -> assertThat(chances.get(tema)).isCloseTo(chance, within(1e-9)));

        double soma = chances.values().stream().mapToDouble(Double::doubleValue).sum();
        assertThat(soma).isCloseTo(1.0, within(1e-9));
    }

    @Entao("o sorteio deve falhar com a mensagem {string}")
    public void o_sorteio_deve_falhar_com_a_mensagem(String mensagem) {
        assertThat(sorteio).isNull();
        assertThat(erro).hasMessage(mensagem);
    }

    private void executar(SorteioService service) {
        try {
            sorteio = service.sortear(sessao);
        } catch (IllegalArgumentException | IllegalStateException e) {
            erro = e;
        }
    }
}
