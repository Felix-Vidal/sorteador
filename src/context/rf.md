# Requisitos Funcionais (RF)

> As referências entre parênteses indicam as regras de negócio (RN) que cada requisito deve respeitar.

## Sessão e temas

- **RF01** – Criar sessão de sorteio. (RN12)
- **RF02** – Cadastrar, editar e remover temas da sessão. A remoção apaga o tema da sessão e é diferente de excluí-lo do sorteio (RF14). (RN02, RN03, RN05)
- **RF03** – Escolher o modo: Aleatório Puro, Voto Múltiplo ou Voto Único. (RN04, RN05)
- **RF04** – Configurar o valor de K para o Modo Voto Múltiplo e exibir o K efetivo da rodada. (RN28)

## Votação

- **RF05** – Organizador abre a votação da rodada. (RN12, RN15)
- **RF06** – Participantes entram na sessão.
- **RF07** – Participante registra sua cédula conforme o modo: 1 tema no Voto Único; de 1 até K efetivo temas diferentes no Voto Múltiplo. Apenas temas disponíveis podem ser escolhidos. (RN25, RN26, RN27, RN28, RN29)
- **RF08** – Participante substitui ou retira sua cédula enquanto a votação estiver aberta. (RN25)
- **RF09** – Organizador encerra a votação. (RN12, RN16)
- **RF10** – Exibir ao organizador a quantidade de votos por tema. (RN13)

## Sorteio

- **RF11** – Exibir ao organizador a probabilidade de cada tema disponível antes do sorteio. (RN13, RN19, RN20, RN23, RN33)
- **RF12** – Realizar o sorteio e exibir o tema sorteado. Nos modos com voto, só após o encerramento da votação; bloqueado com menos de 2 temas disponíveis. (RN01, RN16, RN41)
- **RF13** – Realizar novo sorteio, iniciando uma nova rodada. (RN18)

## Tema sorteado: manter, suspender ou excluir

- **RF14** – Após o sorteio, escolher entre manter (padrão), excluir do sorteio ou suspender por X rodadas o tema sorteado. A suspensão só é oferecida com 3 ou mais temas disponíveis. (RN31, RN32, RN35)
- **RF15** – Informar a quantidade X de rodadas, validando inteiro ≥ 1 e o teto de suspensão. (RN34, RN35)
- **RF16** – Exibir temas suspensos e rodadas restantes. (RN36, RN37)
- **RF17** – Exibir temas excluídos do sorteio. (RN38)
- **RF18** – Reativar tema suspenso ou excluído do sorteio, entre rodadas. (RN39)
- **RF19** – Alterar as rodadas restantes de um tema suspenso, entre rodadas. (RN40)
- **RF20** – Avisar o organizador antes de qualquer ação que deixe menos de 2 temas disponíveis e, quando o sorteio estiver bloqueado, oferecer a reativação de temas. (RN41, RN42)

## Sessões salvas

- **RF21** – Salvar sessão com nome, temas, estado de cada tema, modo e K configurado. (RN06, RN07, RN08, RN09)
- **RF22** – Listar as sessões salvas do organizador, sem exibir as excluídas. (RN07, RN10)
- **RF23** – Abrir sessão salva e iniciar uma nova rodada. (RN15)
- **RF24** – Editar e excluir sessão salva. A edição é bloqueada com votação aberta; a exclusão é lógica e exige confirmação. (RN05, RN10, RN12)
- **RF25** – Duplicar sessão salva, copiando temas e configurações, sem histórico e com todos os temas disponíveis. (RN11)

## Histórico

- **RF26** – Registrar no histórico cada sorteio realizado, com todos os dados previstos na RN43. (RN17, RN43)
- **RF27** – Organizador consulta o histórico completo, do mais recente para o mais antigo. (RN13, RN44, RN45)
- **RF28** – Participante consulta os resultados de todas as rodadas da sessão (tema sorteado, data e hora), incluindo novos sorteios. (RN14)
- **RF29** – Filtrar o histórico por período e por tema. (RN45)