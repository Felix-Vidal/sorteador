package br.com.iartes.sorteador.services;

import br.com.iartes.sorteador.models.Sessao;
import br.com.iartes.sorteador.models.Sorteio;
import br.com.iartes.sorteador.models.Tema;
import br.com.iartes.sorteador.models.Voto;
import br.com.iartes.sorteador.models.enums.ModoSorteio;
import br.com.iartes.sorteador.models.enums.StatusTema;
import br.com.iartes.sorteador.models.enums.StatusVotacao;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.random.RandomGenerator;

@Service
public class SorteioService {

    private final RandomGenerator random;

    public SorteioService(RandomGenerator random) {
        this.random = random;
    }

    public Sorteio sortear(Sessao sessao) {
        validar(sessao);

        List<Tema> disponiveis = disponiveis(sessao);
        if (disponiveis.isEmpty()) {
            throw new IllegalStateException("Não há temas disponíveis para sortear");
        }

        Map<String, Double> pesos = calcularPesos(sessao, disponiveis);
        double totalDosPesos = somar(pesos);
        if (totalDosPesos <= 0) {
            throw new IllegalStateException("Nenhum tema tem peso positivo para ser sorteado");
        }

        Map<String, Double> probabilidades = calcularProbabilidades(pesos, totalDosPesos);
        Tema sorteado = escolher(disponiveis, pesos, totalDosPesos);

        return new Sorteio(sorteado, pesos, probabilidades);
    }

    private void validar(Sessao sessao) {
        if (sessao.getModo() == null) {
            throw new IllegalArgumentException("O modo do sorteio é obrigatório");
        }
        if (sessao.getPesoBase() < 0) {
            throw new IllegalArgumentException("O peso base não pode ser negativo");
        }
        // RN16: nos modos com voto, o sorteio só acontece depois de encerrar a votação.
        if (aceitaVotos(sessao) && sessao.getStatusVotacao() != StatusVotacao.ENCERRADA) {
            throw new IllegalStateException("A votação precisa ser encerrada antes do sorteio");
        }
    }

    // RN21: o Aleatório Puro não tem votação.
    private boolean aceitaVotos(Sessao sessao) {
        return sessao.getModo() != ModoSorteio.ALEATORIO_PURO;
    }

    // RN33: tema suspenso ou excluído do sorteio não é considerado disponível.
    private List<Tema> disponiveis(Sessao sessao) {
        if (sessao.getTemas() == null) {
            return List.of();
        }
        return sessao.getTemas().stream()
                .filter(tema -> tema.getStatus() == StatusTema.DISPONIVEL)
                .toList();
    }

    private Map<String, Double> calcularPesos(Sessao sessao, List<Tema> disponiveis) {
        // RN22: todo tema disponível começa com o peso base, então ninguém fica com chance zero.
        Map<String, Double> pesos = new LinkedHashMap<>();
        for (Tema tema : disponiveis) {
            pesos.put(tema.getNome(), sessao.getPesoBase());
        }

        // RN24: votação encerrada sem votos cai no mesmo caso do Aleatório Puro, todos com o peso base.
        if (!aceitaVotos(sessao) || sessao.getVotos() == null) {
            return pesos;
        }

        // RN23: peso final = peso base + peso dos votos.
        for (Voto cedula : ultimasCedulas(sessao)) {
            if (cedula.getTemas() == null) {
                continue;
            }
            // RN29: o mesmo tema escolhido duas vezes na cédula conta uma só vez.
            Set<String> temasDaCedula = new HashSet<>(cedula.getTemas());
            if (sessao.getModo() == ModoSorteio.VOTO_UNICO && temasDaCedula.size() > 1) {
                // RN27: no Voto Único a cédula tem exatamente 1 tema.
                throw new IllegalArgumentException(
                        "No modo VOTO_UNICO cada participante só pode votar em um tema: "
                                + cedula.getNomeParticipante());
            }
            // RN30: cada tema da cédula recebe +1. RN26: voto em tema fora do sorteio não conta.
            for (String nomeTema : temasDaCedula) {
                pesos.computeIfPresent(nomeTema, (nome, peso) -> peso + 1);
            }
        }

        return pesos;
    }

    // RN25: uma cédula por participante na rodada; a última registrada substitui as anteriores.
    private Collection<Voto> ultimasCedulas(Sessao sessao) {
        Map<String, Voto> porParticipante = new LinkedHashMap<>();
        for (Voto voto : sessao.getVotos()) {
            porParticipante.put(voto.getNomeParticipante(), voto);
        }
        return porParticipante.values();
    }

    // RN19: a chance de um tema é o peso dele dividido pela soma dos pesos dos temas disponíveis.
    private Map<String, Double> calcularProbabilidades(Map<String, Double> pesos, double totalDosPesos) {
        Map<String, Double> probabilidades = new LinkedHashMap<>();
        pesos.forEach((nome, peso) -> probabilidades.put(nome, peso / totalDosPesos));
        return probabilidades;
    }

    private Tema escolher(List<Tema> disponiveis, Map<String, Double> pesos, double totalDosPesos) {
        double alvo = random.nextDouble(totalDosPesos);
        double acumulado = 0;
        for (Tema tema : disponiveis) {
            acumulado += pesos.get(tema.getNome());
            if (alvo < acumulado) {
                return tema;
            }
        }
        return disponiveis.getLast();
    }

    private double somar(Map<String, Double> valores) {
        return valores.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
