package br.com.alura.marketplace.application.v1.mapper;

import br.com.alura.marketplace.application.v1.dto.ProdutoDto;
import br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory;
import br.com.alura.marketplace.domain.entity.assertions.ProdutoAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoDtoMapperTest {
    ProdutoDtoMapper produtoDtoMapper = Mappers.getMapper(ProdutoDtoMapper.class);

    @DisplayName("Quando converter ProdutoDto.Request")
    @Nested
    class Converter {
        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {
            @DisplayName("Dado um ProdutoDto.Request com todos os campos")
            @Test
            void teste1() {
                //GIVEN
                var produtoDto = ProdutoDtoFactory.criarProdutoDtoRequest().comTodosOsCampos();

                //WHEN
                var atual = produtoDtoMapper.converter(produtoDto);

                //THEN
                ProdutoAssertions.afirmaQue_Produto(atual)
                        .foiConvertidoDe_ProdutoDto_Request();
            }
        }
    }
}