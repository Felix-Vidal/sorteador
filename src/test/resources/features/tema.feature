# language: pt
Funcionalidade: Tema
  Como participante de uma sessao de sorteio
  Quero que um tema criado comece com um estado padrao
  Para que ele fique disponivel para ser sorteado

  Cenario: Tema criado sem informar status fica disponivel
    Dado que crio um tema chamado "Filmes"
    Entao o tema "Filmes" deve estar com status "DISPONIVEL"

  @RN03
  Cenario: Impossibilitar a criação de Tema com nome vazio
    Dado que crio um tema chamado ""
    Entao a criação falha com a mensagem "O nome do tema não pode ser vazio"

  @RN03
  Cenario: Impossibilitar a criação de Tema com nome com sequência de espaços
    Dado que crio um tema chamado "     "
    Entao a criação falha com a mensagem "O nome do tema não pode ser vazio"
