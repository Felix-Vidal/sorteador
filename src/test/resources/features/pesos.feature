# language: pt
Funcionalidade: Calculo dos pesos do sorteio
  Como participante de uma sessao de sorteio
  Quero que os pesos respeitem o peso base e os votos
  Para que a chance de cada tema seja justa

  @RN22
  Cenario: Todo tema disponivel comeca com peso base 1 mesmo sem votos
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 2.0  |
      | Series | 1.0  |
      | Musica | 1.0  |

  @RN23
  Cenario: Peso final e o peso base somado ao peso dos votos
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
      | Bruno        | Filmes |
      | Carla        | Filmes |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 4.0  |
      | Series | 1.0  |

  @RN26
  Cenario: Voto em tema suspenso ou excluido nao conta
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | SUSPENSO   |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
      | Bruno        | Series |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Series | 2.0  |
      | Musica | 1.0  |

  @RF07
  Cenario: No voto unico o participante escolhe um tema disponivel e ele ganha peso
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Series |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 1.0  |
      | Series | 2.0  |
    E as chances devem ser:
      | tema   | chance |
      | Filmes | 0.3333333333 |
      | Series | 0.6666666667 |
