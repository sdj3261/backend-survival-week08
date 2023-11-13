package kr.megaptera.assignment.controllers;

import kr.megaptera.assignment.application.cart.AddProductToCartService;
import kr.megaptera.assignment.application.cart.ChangeCartItemQuantityService;
import kr.megaptera.assignment.application.cart.GetCartService;
import kr.megaptera.assignment.dtos.CartDto;
import kr.megaptera.assignment.models.LineItemId;
import kr.megaptera.assignment.models.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineItemController.class)
@ActiveProfiles("test")
class LineItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCartService getCartService;

    @MockBean
    private AddProductToCartService addProductToCartService;

    @MockBean
    private ChangeCartItemQuantityService changeCartItemQuantityService;

    @Test
    @DisplayName("GET /cart-line-items")
    void list() throws Exception {
        given(getCartService.getCartDto()).willReturn(new CartDto(List.of()));

        mockMvc.perform(get("/cart-line-items"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /cart-line-items")
    void create() throws Exception {
        ProductId productId = new ProductId("test-id");

        String json = String.format(
            """
                {
                    "productId": "%s",
                    "quantity": 3
                }
                """,
            productId
        );

        mockMvc.perform(post("/cart-line-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());

        verify(addProductToCartService).addProduct(productId, 3);
    }

    @Test
    @DisplayName("PATCH /cart-line-items/{id} - with correct ID")
    void update() throws Exception {
        LineItemId lineItemId = new LineItemId("test-id");

        String json = "{\"quantity\": 3}";

        mockMvc.perform(patch("/cart-line-items/" + lineItemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNoContent());

        verify(changeCartItemQuantityService).changeQuantity(lineItemId, 3);
    }

    @Test
    @DisplayName("PATCH /cart-line-items/{id} - with incorrect ID")
    void updateWithIncorrectID() throws Exception {
        LineItemId lineItemId = new LineItemId("BAD");

        String json = "{\"quantity\": 3}";

        doThrow(new NoSuchElementException())
            .when(changeCartItemQuantityService)
            .changeQuantity(lineItemId, 3);

        mockMvc.perform(patch("/cart-line-items/" + lineItemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());
    }
}
