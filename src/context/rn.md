# Regras de Negócio (RN)


## Temas

- **RN01** – São necessários no mínimo 2 temas disponíveis para sortear.
- **RN02** – Nomes de temas não se repetem dentro da sessão, considerando todos os temas em qualquer estado (disponível, suspenso ou excluído do sorteio). A comparação ignora maiúsculas/minúsculas e espaços.
- **RN03** – Nome de tema não pode ser vazio.

## Sessão

- **RN04** – O modo é uma configuração da sessão e só pode ser alterado entre rodadas, antes da abertura da votação.
- **RN05** – Com a votação aberta, a sessão não pode ser editada: temas não podem ser incluídos, alterados, removidos, suspensos, excluídos do sorteio ou reativados, e modo e K não podem ser alterados.
- **RN06** – Sessão salva precisa de nome.
- **RN07** – O nome da sessão é único por usuário, considerando apenas sessões não excluídas.
- **RN08** – A sessão pode ser salva com menos de 2 temas disponíveis, mas não pode ser sorteada nessa condição (RN01).
- **RN09** – A sessão salva guarda o estado de cada tema (disponível, suspenso com rodadas restantes, ou excluído do sorteio).
- **RN10** – A exclusão da sessão exige confirmação e é lógica: a sessão fica oculta, mas os dados são preservados.
- **RN11** – Duplicar uma sessão copia os temas e as configurações (modo e K configurado). O histórico não é copiado, e todos os temas da cópia começam como disponíveis.

## Permissões e visibilidade

- **RN12** – Apenas o organizador pode: editar a sessão; configurar modo e K; abrir e encerrar a votação; realizar o sorteio; escolher a ação sobre o tema sorteado; reativar temas e alterar suspensões; excluir ou duplicar a sessão.
- **RN13** – São visíveis apenas ao organizador: o histórico completo (RN43) e, durante a rodada, as probabilidades e a contagem de votos por tema.
- **RN14** – Participantes veem o resultado de todas as rodadas da sessão (tema sorteado, data e hora), incluindo as rodadas criadas por novo sorteio, na mesma ordem do histórico (RN45).

## Rodada e sorteio

- **RN15** – Cada rodada começa com votos zerados.
- **RN16** – Nos modos com voto, o sorteio só pode ser realizado após o encerramento da votação.
- **RN17** – O resultado é registrado no momento do sorteio e não pode ser alterado nem apagado.
- **RN18** – Após o registro do resultado, o organizador pode fazer um novo sorteio. O novo sorteio é uma nova rodada e segue todas as regras de rodada:
  - nos modos com voto, uma nova votação precisa ser aberta e encerrada antes do sorteio (RN16);
  - conta como sorteio para a contagem de suspensão (RN36);
  - o resultado anterior permanece registrado e visível a todos (RN14, RN44).
- **RN19** – A chance de cada tema disponível é igual ao seu peso final dividido pela soma dos pesos finais de todos os temas disponíveis. Temas suspensos ou excluídos do sorteio não entram no cálculo (RN33).

## Modos de sorteio

### Aleatório Puro

- **RN20** – Todos os temas disponíveis têm peso 1. Pela RN19, a chance de cada tema é 1/n, onde *n* = quantidade de temas disponíveis.
- **RN21** – Não aceita votos. A rodada consiste apenas no sorteio e na escolha da ação sobre o tema sorteado.

### Regras comuns aos modos com voto

- **RN22** – Todo tema disponível tem peso base 1 (nenhum tema disponível fica com chance zero).
- **RN23** – Peso final = peso base + peso dos votos.
- **RN24** – Se a votação for encerrada sem votos, o sorteio funciona como no Aleatório Puro.
- **RN25** – Cada participante registra apenas 1 cédula por rodada. Enquanto a votação estiver aberta, pode registrar uma nova cédula, que substitui integralmente a anterior, ou retirar sua cédula.
- **RN26** – Não é possível votar em tema suspenso ou excluído do sorteio.

### Voto Único

- **RN27** – Cada participante escolhe exatamente 1 tema, que recebe +1 de peso.

### Voto Múltiplo

- **RN28** – Cada participante escolhe de 1 até K temas diferentes.
  - O organizador configura um valor de K ≥ 1 (K configurado).
  - Na abertura de cada votação, o sistema calcula o **K efetivo** = menor valor entre o K configurado e (temas disponíveis − 1).
  - O K configurado nunca é alterado automaticamente: quando temas voltam a ficar disponíveis, o K efetivo volta a subir até o K configurado.
- **RN29** – Não é possível escolher o mesmo tema duas vezes na mesma cédula.
- **RN30** – Cada tema escolhido na cédula recebe exatamente +1 de peso. Frações não são utilizadas.

## Tema sorteado: manter, suspender ou excluir

A suspensão é o mecanismo para evitar que o mesmo tema se repita em rodadas próximas.

- **RN31** – Após o sorteio, a opção padrão para o tema sorteado é **manter**.
- **RN32** – A ação (manter, suspender ou excluir do sorteio) vale apenas para o tema sorteado na rodada atual.
- **RN33** – Tema suspenso ou excluído do sorteio não é considerado disponível e tem peso 0.
- **RN34** – A quantidade de rodadas de suspensão (X) deve ser um número inteiro ≥ 1, respeitando o teto da RN35.
- **RN35** – **Teto de suspensão:** X não pode ultrapassar a metade da quantidade de temas disponíveis no momento do sorteio (incluindo o tema sorteado), arredondada para baixo. A suspensão só é permitida se, após aplicá-la, restarem pelo menos 2 temas disponíveis; ou seja, exige no mínimo 3 temas disponíveis no momento do sorteio.
- **RN36** – A contagem começa no sorteio seguinte ao da suspensão: a cada sorteio realizado, inclusive por novo sorteio (RN18), as rodadas restantes diminuem 1.
- **RN37** – Quando as rodadas restantes chegam a 0, o tema volta automaticamente a ficar disponível para a próxima rodada.
- **RN38** – Tema excluído do sorteio fica fora até ser reativado pelo organizador.
- **RN39** – O organizador pode reativar, entre rodadas, um tema suspenso (encerrando a suspensão antes do prazo) ou excluído do sorteio.
- **RN40** – O organizador pode alterar, entre rodadas, as rodadas restantes de um tema suspenso. O novo valor deve ser inteiro ≥ 1 e não pode ultrapassar a metade da quantidade de temas disponíveis somada ao próprio tema suspenso, arredondada para baixo (mesmo critério da RN35). Para encerrar a suspensão, usa-se a reativação (RN39).
- **RN41** – Com menos de 2 temas disponíveis, o sorteio é bloqueado e o sistema oferece ao organizador a reativação de temas.
- **RN42** – O sistema deve avisar o organizador antes de qualquer ação que deixe menos de 2 temas disponíveis. A exclusão do sorteio é permitida após o aviso; a suspensão segue a RN35.

## Histórico

- **RN43** – Cada sorteio gera um registro com uma cópia do estado da sessão naquele momento: data/hora, modo, K efetivo, temas e seus estados, votos, pesos, probabilidades, resultado, ação escolhida sobre o tema sorteado e temas fora do sorteio.
- **RN44** – Registros do histórico não podem ser editados nem apagados.
- **RN45** – O histórico é exibido do mais recente para o mais antigo.