package br.com.iartes.sorteador.services;

import br.com.iartes.sorteador.models.Sessao;
import br.com.iartes.sorteador.models.Sorteio;
import br.com.iartes.sorteador.models.Tema;
import br.com.iartes.sorteador.models.Voto;
import br.com.iartes.sorteador.models.enums.ModoSorteio;
import br.com.iartes.sorteador.models.enums.StatusTema;
import org.springframework.stereotype.Service;

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
        if (sessao.getModo() == null) {
            throw new IllegalArgumentException("O modo do sorteio é obrigatório");
        }
        if (sessao.getPesoBase() < 0) {
            throw new IllegalArgumentException("O peso base não pode ser negativo");
        }

        List<Tema> disponiveis = sessao.getTemas() == null ? List.of() : sessao.getTemas().stream()
                .filter(tema -> tema.getStatus() == StatusTema.DISPONIVEL)
                .toList();

        if (disponiveis.isEmpty()) {
            throw new IllegalStateException("Não há temas disponíveis para sortear");
        }

        Map<String, Double> pesos = calcularPesos(sessao, disponiveis);
        Tema sorteado = escolher(disponiveis, pesos);

        return new Sorteio(sorteado, pesos);
    }

    private Map<String, Double> calcularPesos(Sessao sessao, List<Tema> disponiveis) {
        Map<String, Double> pesos = new LinkedHashMap<>();
        for (Tema tema : disponiveis) {
            pesos.put(tema.getNome(), sessao.getPesoBase());
        }

        if (sessao.getModo() == ModoSorteio.ALEATORIO_PURO || sessao.getVotos() == null) {
            return pesos;
        }

        for (Voto voto : sessao.getVotos()) {
            if (voto.getTemas() == null) {
                continue;
            }
            // Um participante não soma peso duas vezes votando no mesmo tema
            Set<String> temasDoVoto = new HashSet<>(voto.getTemas());
            if (sessao.getModo() == ModoSorteio.VOTO_UNICO && temasDoVoto.size() > 1) {
                throw new IllegalArgumentException(
                        "No modo VOTO_UNICO cada participante só pode votar em um tema: "
                                + voto.getNomeParticipante());
            }
            // Votos em temas suspensos, excluídos ou inexistentes são ignorados
            for (String nomeTema : temasDoVoto) {
                pesos.computeIfPresent(nomeTema, (nome, peso) -> peso + 1);
            }
        }

        return pesos;
    }

    private Tema escolher(List<Tema> disponiveis, Map<String, Double> pesos) {
        double total = pesos.values().stream().mapToDouble(Double::doubleValue).sum();
        if (total <= 0) {
            throw new IllegalStateException("Nenhum tema tem peso positivo para ser sorteado");
        }

        double alvo = random.nextDouble(total);
        double acumulado = 0;
        for (Tema tema : disponiveis) {
            acumulado += pesos.get(tema.getNome());
            if (alvo < acumulado) {
                return tema;
            }
        }
        // Proteção contra arredondamento de ponto flutuante
        return disponiveis.getLast();
    }
}
