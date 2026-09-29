# language: pt
Funcionalidade: Sorteio
  Como participante de uma sessao de sorteio
  Quero que o tema seja sorteado de acordo com o modo da sessao
  Para que os votos influenciem a chance de cada tema

  Cenario: Aleatorio puro da o mesmo peso a todos os temas disponiveis
    Dado uma sessao no modo "ALEATORIO_PURO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 1.0  |
      | Series | 1.0  |
      | Musica | 1.0  |

  Cenario: Voto multiplo soma um ao peso por participante que votou no tema
    Dado uma sessao no modo "VOTO_MULTIPLO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos:
      | participante | temas                 |
      | Ana          | Filmes, Series        |
      | Bruno        | Filmes, Filmes        |
      | Carla        | Filmes, Inexistente   |
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 4.0  |
      | Series | 2.0  |
      | Musica | 1.0  |

  Cenario: Temas suspensos ou excluidos nao participam do sorteio
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | SUSPENSO   |
      | Series | DISPONIVEL |
      | Musica | EXCLUIDO   |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
    Quando o sorteio e realizado
    Entao o tema sorteado deve ser "Series"
    E os pesos devem ser:
      | tema   | peso |
      | Series | 1.0  |

  Esquema do Cenario: A escolha respeita a proporcao dos pesos
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
      | Bruno        | Filmes |
    Quando o sorteio e realizado com o valor aleatorio <fracao>
    Entao o tema sorteado deve ser "<tema>"

    Exemplos:
      | fracao | tema   |
      | 0,0    | Filmes |
      | 0,7    | Filmes |
      | 0,8    | Series |
      | 0,99   | Series |

  Cenario: Voto unico nao aceita mais de um tema por participante
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas          |
      | Ana          | Filmes, Series |
    Quando o sorteio e realizado
    Entao o sorteio deve falhar com a mensagem "No modo VOTO_UNICO cada participante só pode votar em um tema: Ana"

  Cenario: Sessao sem temas disponiveis nao pode ser sorteada
    Dado uma sessao no modo "ALEATORIO_PURO" com os temas:
      | nome   | status   |
      | Filmes | SUSPENSO |
    Quando o sorteio e realizado
    Entao o sorteio deve falhar com a mensagem "Não há temas disponíveis para sortear"
