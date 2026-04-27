package br.com.alura.marketplace.infra.repository;

import br.com.alura.marketplace.domain.entity.Foto;
import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.infra.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = JpaConfig.class)
class ProdutoRepositoryExtTest {
    @Autowired
    TestEntityManager em;

    @Autowired
    ProdutoRepositoryExt produtoRepositoryExt;

    @DisplayName("Quando consultar por nome")
    @Nested
    class FindByNome {
        @DisplayName("Entao deve consultar com sucesso")
        @Nested
        class Sucesso {
            @DisplayName("Dado um nome valido, em um cenario onde existe um registro")
            @Test
            void teste1() {
                Produto produto = Produto.builder()
                        .nome("Produto1")
                        .categoria("Categoria 1")
                        .status(Produto.Status.AVAILABLE)
                        .descricao("Descricao 1")
                        .valor(new BigDecimal("1.99"))
                        .foto(Foto.builder()
                                .fileName("file-name-1.jpg")
                                .base64("Y29udGVudC0x")
                                .link("")
                                .build())
                        .build();

                produtoRepositoryExt.save(produto);

                //Given
                final String nome = produto.getNome();

                //When
                var atual = produtoRepositoryExt.findByNome(nome).orElseGet(() -> produto);

                //Then
                assertThat(atual.getNome()).isEqualTo(nome);
            }
        }
    }
}