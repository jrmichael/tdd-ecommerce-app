package pl.pragmatists.trainings.ecommerce.product.persistence;

import pl.pragmatists.trainings.ecommerce.common.Money;
import pl.pragmatists.trainings.ecommerce.product.persistence.Product;

/**
 * Created by mszynwelski on 06.10.16.
 */
public class ProductBuilder {

    private long id;
    private String name;
    private Money price;

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder id(long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder price(Money price) {
        this.price = price;
        return this;
    }

    public Product build() {
        return new Product(id, name, price);
    }

    public ProductBuilder price(int dollars, int cents) {
        price = new Money(dollars, cents);
        return this;
    }
}
