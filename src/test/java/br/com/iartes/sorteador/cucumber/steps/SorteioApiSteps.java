package br.com.iartes.sorteador.cucumber.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SorteioApiSteps {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resposta;

    @Quando("envio para {string} o corpo:")
    public void envio_para_o_corpo(String caminho, String corpo) throws Exception {
        resposta = mockMvc.perform(post(caminho)
                .contentType(MediaType.APPLICATION_JSON)
                .content(corpo));
    }

    @Entao("a resposta deve ter status {int}")
    public void a_resposta_deve_ter_status(int codigo) throws Exception {
        resposta.andExpect(status().is(codigo));
    }

    @E("o campo {string} da resposta deve ser {string}")
    public void o_campo_da_resposta_deve_ser(String caminho, String valor) throws Exception {
        resposta.andExpect(jsonPath(caminho).value(valor));
    }

    @E("o campo {string} da resposta deve ser {double}")
    public void o_campo_da_resposta_deve_ser_numero(String caminho, double valor) throws Exception {
        resposta.andExpect(jsonPath(caminho).value(valor));
    }

    @E("a resposta nao deve ter o campo {string}")
    public void a_resposta_nao_deve_ter_o_campo(String caminho) throws Exception {
        resposta.andExpect(jsonPath(caminho).doesNotExist());
    }
}
