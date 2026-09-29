# language: pt
Funcionalidade: Sorteio
  Como participante de uma sessao de sorteio
  Quero que o tema seja sorteado de acordo com o modo da sessao
  Para que os votos influenciem a chance de cada tema

  @RN20 @RN21
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

  @RN28 @RN29 @RN30
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
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 4.0  |
      | Series | 2.0  |
      | Musica | 1.0  |

  @RN19 @RN33
  Cenario: Temas suspensos ou excluidos nao participam do sorteio
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | SUSPENSO   |
      | Series | DISPONIVEL |
      | Musica | EXCLUIDO   |
      | Livros | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Series | 1.0  |
      | Livros | 1.0  |
    E as chances devem ser:
      | tema   | chance |
      | Series | 0.5    |
      | Livros | 0.5    |

  @RN19
  Esquema do Cenario: A escolha respeita a proporcao dos pesos
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
      | Bruno        | Filmes |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado com o valor aleatorio <fracao>
    Entao o tema sorteado deve ser "<tema>"

    Exemplos:
      | fracao | tema   |
      | 0,0    | Filmes |
      | 0,7    | Filmes |
      | 0,8    | Series |
      | 0,99   | Series |

  @RN27
  Cenario: Voto unico nao aceita mais de um tema por participante
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas          |
      | Ana          | Filmes, Series |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao o sorteio deve falhar com a mensagem "No modo VOTO_UNICO cada participante só pode votar em um tema: Ana"

  @RN01 @RN41
  Esquema do Cenario: Sorteio bloqueado com menos de 2 temas disponiveis
    Dado uma sessao no modo "ALEATORIO_PURO" com os temas:
      | nome   | status    |
      | Filmes | <filmes>  |
      | Series | <series>  |
      | Musica | <musica>  |
    Quando o sorteio e realizado
    Entao o sorteio deve falhar com a mensagem "São necessários no mínimo 2 temas disponíveis para sortear"

    Exemplos:
      | filmes     | series   | musica   |
      | SUSPENSO   | EXCLUIDO | SUSPENSO |
      | DISPONIVEL | SUSPENSO | EXCLUIDO |
      | DISPONIVEL | EXCLUIDO | EXCLUIDO |

  @RN01
  Cenario: Sessao sem temas nao pode ser sorteada
    Dado uma sessao no modo "ALEATORIO_PURO" sem temas
    Quando o sorteio e realizado
    Entao o sorteio deve falhar com a mensagem "São necessários no mínimo 2 temas disponíveis para sortear"

  @RN01
  Cenario: Sorteio permitido com exatamente 2 temas disponiveis
    Dado uma sessao no modo "ALEATORIO_PURO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | SUSPENSO   |
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 1.0  |
      | Series | 1.0  |

  @RN19
  Cenario: A chance de cada tema e o peso dividido pela soma dos pesos
    Dado uma sessao no modo "VOTO_MULTIPLO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos:
      | participante | temas          |
      | Ana          | Filmes, Series |
      | Bruno        | Filmes, Musica |
      | Carla        | Filmes         |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 4.0  |
      | Series | 2.0  |
      | Musica | 2.0  |
    E as chances devem ser:
      | tema   | chance |
      | Filmes | 0.5    |
      | Series | 0.25   |
      | Musica | 0.25   |

  @RN24
  Cenario: Votacao encerrada sem votos funciona como no Aleatorio Puro
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
      | Livros | DISPONIVEL |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 1.0  |
      | Series | 1.0  |
      | Musica | 1.0  |
      | Livros | 1.0  |
    E as chances devem ser:
      | tema   | chance |
      | Filmes | 0.25   |
      | Series | 0.25   |
      | Musica | 0.25   |
      | Livros | 0.25   |

  @RN16
  Esquema do Cenario: Nos modos com voto o sorteio exige a votacao encerrada
    Dado uma sessao no modo "<modo>" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
    E a votacao esta "<votacao>"
    Quando o sorteio e realizado
    Entao o sorteio deve falhar com a mensagem "A votação precisa ser encerrada antes do sorteio"

    Exemplos:
      | modo          | votacao    |
      | VOTO_UNICO    | NAO_ABERTA |
      | VOTO_UNICO    | ABERTA     |
      | VOTO_MULTIPLO | NAO_ABERTA |
      | VOTO_MULTIPLO | ABERTA     |

  @RN16 @RN21
  Cenario: Aleatorio puro nao depende de votacao
    Dado uma sessao no modo "ALEATORIO_PURO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E a votacao esta "NAO_ABERTA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 1.0  |
      | Series | 1.0  |
    E as chances devem ser:
      | tema   | chance |
      | Filmes | 0.5    |
      | Series | 0.5    |

  @RN25
  Cenario: Nova cedula do participante substitui a anterior
    Dado uma sessao no modo "VOTO_UNICO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
    E os votos:
      | participante | temas  |
      | Ana          | Filmes |
      | Ana          | Series |
      | Bruno        | Series |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 1.0  |
      | Series | 3.0  |
    E as chances devem ser:
      | tema   | chance |
      | Filmes | 0.25   |
      | Series | 0.75   |

  @RN25
  Cenario: Cada participante conta uma vez por rodada no voto multiplo
    Dado uma sessao no modo "VOTO_MULTIPLO" com os temas:
      | nome   | status     |
      | Filmes | DISPONIVEL |
      | Series | DISPONIVEL |
      | Musica | DISPONIVEL |
    E os votos:
      | participante | temas          |
      | Ana          | Filmes, Series |
      | Ana          | Filmes, Musica |
    E a votacao esta "ENCERRADA"
    Quando o sorteio e realizado
    Entao os pesos devem ser:
      | tema   | peso |
      | Filmes | 2.0  |
      | Series | 1.0  |
      | Musica | 2.0  |
