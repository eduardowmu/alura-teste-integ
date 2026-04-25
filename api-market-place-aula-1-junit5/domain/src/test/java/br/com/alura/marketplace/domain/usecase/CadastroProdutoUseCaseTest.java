package br.com.alura.marketplace.domain.usecase;

import br.com.alura.marketplace.domain.entity.Foto;
import br.com.alura.marketplace.domain.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CadastroProdutoUseCaseTest {
    CadastroProdutoUseCase cadastroProdutoUseCase;

    @BeforeEach
    void beforeEach() {
        cadastroProdutoUseCase = new CadastroProdutoUseCase();
    }

    @DisplayName("Quando cadastrar produto")
    @Nested
    class Cadastrar {
        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {
            @DisplayName("Dado um produto com todos os campos")
            @Test
            void teste1() {
                //GIVEN
                var produto = Produto.builder()
                        .nome("Produto1")
                        .categoria("Categoria 1")
                        .status(Produto.Status.AVAILABLE)
                        .descricao("Descricao 1")
                        .valor(new BigDecimal("1.99"))
                        .foto(Foto.builder()
                                .fileName("file-name-1.jpg")
                                .base64("Y29udGVudC0x")
                                .build())
                        .build();

                //WHEN
                var atual = cadastroProdutoUseCase.cadastrar(produto);

                //THEN
                assertEquals(produto.getNome(), atual.getNome());
            }
        }
    }
}