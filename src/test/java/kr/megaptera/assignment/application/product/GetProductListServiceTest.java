package kr.megaptera.assignment.application.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.megaptera.assignment.infrastructure.ProductDtoFetcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetProductListServiceTest {
    private ProductDtoFetcher productDtoFetcher;

    private GetProductListService getProductListService;

    @BeforeEach
    void setUp() {
        productDtoFetcher = mock(ProductDtoFetcher.class);

        getProductListService = new GetProductListService(productDtoFetcher);
    }

    @Test
    void getProductList() {
        getProductListService.getProductListDto();

        verify(productDtoFetcher).fetchProductListDto();
    }
}