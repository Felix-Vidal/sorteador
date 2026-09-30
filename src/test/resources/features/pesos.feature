# language: pt
Funcionalidade: Calculo dos pesos do sorteio
  Como participante de uma sessao de sorteio
  Quero que os pesos respeitem o peso base e os votos
  Para que a chance de cada tema seja justa

  @RN22
  Cenario: Todo tema disponivel comeca com peso base 1 mesmo sem votos
    Dada uma sessao de pesos no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos de pesos:
      | participante | temas  |
      | Ana          | Filmes |
    E a votacao de pesos encerrada
    Quando o sorteio de pesos e realizado
    Entao o peso de pesos de cada tema deve ser:
      | tema   | peso |
      | Filmes | 2.0  |
      | Series | 1.0  |
      | Musica | 1.0  |

  @RN23
  Cenario: Peso final e o peso base somado ao peso dos votos
    Dada uma sessao de pesos no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos de pesos:
      | participante | temas  |
      | Ana          | Filmes |
      | Bruno        | Filmes |
      | Carla        | Filmes |
    E a votacao de pesos encerrada
    Quando o sorteio de pesos e realizado
    Entao o peso de pesos de cada tema deve ser:
      | tema   | peso |
      | Filmes | 4.0  |
      | Series | 1.0  |

  @RN26
  Cenario: Voto em tema suspenso ou excluido nao conta
    Dada uma sessao de pesos no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | SUSPENSO   |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos de pesos:
      | participante | temas  |
      | Ana          | Filmes |
      | Bruno        | Series |
    E a votacao de pesos encerrada
    Quando o sorteio de pesos e realizado
    Entao o peso de pesos de cada tema deve ser:
      | tema   | peso |
      | Series | 2.0  |
      | Musica | 1.0  |