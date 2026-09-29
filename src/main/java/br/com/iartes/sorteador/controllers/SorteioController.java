package br.com.iartes.sorteador.controllers;

import br.com.iartes.sorteador.models.Sessao;
import br.com.iartes.sorteador.models.Sorteio;
import br.com.iartes.sorteador.services.SorteioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sorteios")
public class SorteioController {

    private final SorteioService sorteioService;

    public SorteioController(SorteioService sorteioService) {
        this.sorteioService = sorteioService;
    }

    @PostMapping
    public Sorteio sortear(@RequestBody Sessao sessao) {
        return sorteioService.sortear(sessao);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail sessaoInvalida(IllegalArgumentException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail sorteioImpossivel(IllegalStateException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, e.getMessage());
    }
}
