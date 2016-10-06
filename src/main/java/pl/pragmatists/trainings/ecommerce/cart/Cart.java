package pl.pragmatists.trainings.ecommerce.cart;


import pl.pragmatists.trainings.ecommerce.common.Money;
import pl.pragmatists.trainings.ecommerce.product.persistence.Product;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private long id;
    private long userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<>();

    private Cart() {
    }

    public Cart(Long userId) {
        this.userId = userId;
    }

    public long userId() {
        return userId;
    }

    public Set<CartItem> items() {
        return items;
    }

    public void add(Product product, int quantity) {
        CartItem cartItem = items.stream()
                .filter(item -> item.getProductId() == product.getId())
                .findFirst()
                .orElse(new CartItem(product, 0, this));
        items.add(cartItem);
        cartItem.increaseQuantity(quantity);
    }


    public Money total() {
        Money total = items.stream().map(item -> item.getPrice().multiply(item.getQuantity())).reduce(new Money(0, 0), Money::add);
        return total.add(shipping(total));
    }

    public Money shipping(Money total) {
        return total.isGreaterThan(new Money(100, 0)) ? new Money(0, 0) : new Money(15, 0);
    }
}
