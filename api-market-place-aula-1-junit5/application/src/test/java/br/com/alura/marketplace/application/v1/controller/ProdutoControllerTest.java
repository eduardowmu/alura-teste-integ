package br.com.alura.marketplace.application.v1.controller;

import br.com.alura.marketplace.application.v1.config.RestControllerTestConfig;
import br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory;
import br.com.alura.marketplace.domain.usecase.CadastroProdutoUseCase;
import br.com.alura.marketplace.domain.usecase.ConsultaProdutoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(classes = ProdutoController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = RestControllerTestConfig.class)
class ProdutoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CadastroProdutoUseCase cadastroCarrinhoUseCase;

    @MockitoBean
    ConsultaProdutoUseCase consultaProdutoUseCase;

    @DisplayName("Quando cadastrar um produto")
    @Nested
    class CadastrarProduto {
        @DisplayName("Entao deve cadastrar com sucesso")
        @Nested
        class Success {
            @DisplayName("Dado um produto com todos os campos")
            @Test
            void test1() throws Exception {
                //Given
                var requestBody = ProdutoDtoFactory.criarProdutoDtoRequest()
                        .comTodosOsCampos();

                //When
                mockMvc.perform(post("/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .content(objectMapper.writeValueAsString(requestBody)))

                //Then
                        .andDo(print())
                        .andExpect(status().isCreated());
            }
        }
    }
}