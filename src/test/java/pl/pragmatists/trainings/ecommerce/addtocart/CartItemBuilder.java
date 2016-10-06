package pl.pragmatists.trainings.ecommerce.addtocart;

import pl.pragmatists.trainings.ecommerce.cart.CartItem;
import pl.pragmatists.trainings.ecommerce.product.persistence.Product;

/**
 * Created by mszynwelski on 06.10.16.
 */
public class CartItemBuilder {
    private Product product;
    private int quantity;

    public static CartItemBuilder aCartItem() {
        return new CartItemBuilder();
    }

    public CartItemBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public CartItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartItem build() {
        return new CartItem(product, quantity, null);
    }
}
