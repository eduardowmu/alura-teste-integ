package br.com.alura.marketplace.application.v1.dto.factory;

import br.com.alura.marketplace.application.v1.dto.FotoDto;
import br.com.alura.marketplace.application.v1.dto.ProdutoDto;
import br.com.alura.marketplace.domain.entity.Produto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;
@NoArgsConstructor(access = PRIVATE)
public final class ProdutoDtoFactory {
    public static Request criarProdutoDtoRequest() {
        return new Request(ProdutoDto.Request.builder());
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public static class Request {
        private final ProdutoDto.Request.RequestBuilder builder;

        public ProdutoDto.Request comTodosOsCampos() {
            return builder
                    .nome("Produto1")
                    .categoria("Categoria 1")
                    .status(Produto.Status.AVAILABLE)
                    .descricao("Descricao 1")
                    .valor(new BigDecimal("1.99"))
                    .foto(FotoDto.Request.builder()
                            .fileName("file-name-1.jpg")
                            .base64("Y29udGVudC0x")
                            .build())
                    .build();
        }
    }
}