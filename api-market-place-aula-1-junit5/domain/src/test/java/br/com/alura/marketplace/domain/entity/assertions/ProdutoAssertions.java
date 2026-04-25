package br.com.alura.marketplace.domain.entity.assertions;

import br.com.alura.marketplace.domain.entity.Produto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
@RequiredArgsConstructor(access = PRIVATE)
public final class ProdutoAssertions {
    private final Produto atual;

    public static ProdutoAssertions afirmaQue_Produto(Produto atual) {
        return new ProdutoAssertions(atual);
    }

    /**
     * @see package br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory
     * .comTodosOsCampos
     * */
    public void foiConvertidoDe_ProdutoDto_Request() {
        assertThat(atual.getNome())
                .isNotNull();

        assertThat(atual.getDescricao())
                .isNotNull();

        //E
        var fotos = atual.getFotos();

        assertThat(fotos.get(0)).isNotNull();
    }
}