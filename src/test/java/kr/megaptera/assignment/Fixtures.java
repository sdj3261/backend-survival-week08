package kr.megaptera.assignment;

import kr.megaptera.assignment.models.Cart;
import kr.megaptera.assignment.models.CartId;
import kr.megaptera.assignment.models.LineItem;
import kr.megaptera.assignment.models.LineItemId;
import kr.megaptera.assignment.models.Money;
import kr.megaptera.assignment.models.Product;
import kr.megaptera.assignment.models.ProductId;

import java.util.List;

public class Fixtures {
    public static Product product() {
        return product(1);
    }

    public static Product product(int number) {
        ProductId productId = new ProductId("012300000000" + number);
        return new Product(
                productId, "Product #" + number, new Money(123_000L));
    }

    public static Cart cart() {
        return cart(List.of());
    }

    public static Cart cart(List<Product> products) {
        CartId cartId = new CartId("0124000000001");
        Cart cart = new Cart(cartId);

        products.forEach(product -> {
            cart.addProduct(product, 1);
        });

        return cart;
    }

    public static LineItem lineItem() {
        Product product = product();
        LineItemId lineItemid = new LineItemId("0125000000001");
        return new LineItem(lineItemid, product, 1);
    }
}
