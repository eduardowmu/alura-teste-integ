package br.com.alura.marketplace.domain.usecase;

import br.com.alura.marketplace.domain.entity.Foto;
import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.exception.BusinessException;
import br.com.alura.marketplace.domain.repository.BucketRepository;
import br.com.alura.marketplace.domain.repository.PetStoreRepository;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import br.com.alura.marketplace.domain.repository.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class CadastroProdutoUseCaseTest {
    @InjectMocks
    CadastroProdutoUseCase cadastroProdutoUseCase;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PetStoreRepository petStoreRepository;

    @Mock
    BucketRepository bucketRepository;

    @Mock
    QueueRepository queueRepository;

    @DisplayName("Quando cadastrar produto")
    @Nested
    class Cadastrar {
        @DisplayName("Entao deve executar com sucesso")
        @Nested
        class Sucesso {
            @BeforeEach
            void beforeEach() {
                when(produtoRepository.save(any()))
                        .thenAnswer(invocationOnMock -> {
                            Produto produto = invocationOnMock.getArgument(0);
                            setField(produto, "produtoId",
                                    UUID.fromString("d4d3483a-7c23-4ee4-b92b-f83fc1a66f58"));
                            return produto;
                        });
            }

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
                assertEquals(produto.getCategoria(), atual.getCategoria());
                assertEquals(produto.getStatus(), atual.getStatus());
                assertEquals(produto.getDescricao(), atual.getDescricao());
                assertEquals(produto.getValor(), atual.getValor());
            }
        }

        @DisplayName("Entao deve retornar erro")
        @Nested
        class falha {
            @DisplayName("Dado um produto com o nome que comece com -")
            @Test
            void teste1() {
                //GIVEN
                var produto = Produto.builder()
                        .nome("-Produto1")
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
                var atual = assertThrows(BusinessException.class,
                        () -> cadastroProdutoUseCase.cadastrar(produto));

                //THEN
                assertThat(atual).hasMessage("Nome não pode comçar com -");
            }
        }
    }
}