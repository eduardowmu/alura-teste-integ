package br.com.alura.marketplace.application.errado;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest(classes = ExemploComponenteNoLugarErrado.ExemploController.class)
@AutoConfigureMockMvc
class ExemploControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ExemploComponenteNoLugarErrado.CadastrarExemploUseCase cadastrarExemploUseCase;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void teste1() throws Exception {
        //Dado
        when(cadastrarExemploUseCase.cadastrar(any()))
                .thenAnswer((InvocationOnMock invocationOnMock) -> {
                    var exemplo = invocationOnMock.getArgument(0);
                    setField(exemplo, "id", UUID.fromString("c6da6587-4b79-49e7-8c02-aa0aecfec574"));
                    return exemplo;
                });
        // E
        var exemplo = ExemploComponenteNoLugarErrado
                .CadastrarExemploUseCase.Exemplo.builder()
                .nome("Nome 1")
                .descricao("Descricao 1")
                .build();

        // Quando
        mockMvc.perform(post("/v1/exemplos")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8.name())
                .content(objectMapper.writeValueAsString(exemplo))
        )
                // Então
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.id", is( "c6da6587-4b79-49e7-8c02-aa0aecfec574")));
    }
}