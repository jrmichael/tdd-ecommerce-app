package pl.pragmatists.trainings.ecommerce.addtocart;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.pragmatists.trainings.ecommerce.addtocart.CartItemBuilder.aCartItem;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import pl.pragmatists.trainings.ecommerce.MvcBaseTest;
import pl.pragmatists.trainings.ecommerce.cart.Cart;
import pl.pragmatists.trainings.ecommerce.cart.CartItem;
import pl.pragmatists.trainings.ecommerce.common.Money;
import pl.pragmatists.trainings.ecommerce.product.persistence.Product;
import pl.pragmatists.trainings.ecommerce.product.persistence.ProductBuilder;
import pl.pragmatists.trainings.ecommerce.product.persistence.ProductRepository;

@RunWith(SpringRunner.class)
public class AddToCartControllerTest extends MvcBaseTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void add_one_product() throws Exception {
        Product product = ProductBuilder.aProduct().id(1L).name("cup").price(3, 50).build();
        em.persistAndFlush(product);

        mvc.perform(post("/user/5/cart/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        new JSONObject()
                                .put("productId", 1L)
                                .put("quantity", 3)
                                .toString()
                )
        );

        assertThat(firstCart().userId()).isEqualTo(5L);
        assertThat(firstCart().items()).containsOnly(aCartItem().product(product).quantity(3).build());
    }

    @Test
    public void add_two_different_products() throws Exception {
        Product cup = ProductBuilder.aProduct().id(1L).name("cup").price(3, 50).build();
        Product plate = ProductBuilder.aProduct().id(123L).name("plate").price(1, 23).build();
        em.persistAndFlush(cup);
        em.persistAndFlush(plate);

        mvc.perform(post("/user/5/cart/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        new JSONObject()
                                .put("productId", 1L)
                                .put("quantity", 3)
                                .toString()
                )
        );

        mvc.perform(post("/user/5/cart/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        new JSONObject()
                                .put("productId", 123L)
                                .put("quantity", 8)
                                .toString()
                )
        );

        assertThat(firstCart().userId()).isEqualTo(5L);
        assertThat(firstCart().items()).containsOnly(aCartItem().product(cup).quantity(3).build(), aCartItem().product(plate).quantity(8).build());
    }

    @Test
    public void add_two_same_products() throws Exception {
        Product cup = ProductBuilder.aProduct().id(1L).name("cup").price(3, 50).build();
        em.persistAndFlush(cup);

        mvc.perform(post("/user/5/cart/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        new JSONObject()
                                .put("productId", 1L)
                                .put("quantity", 3)
                                .toString()
                )
        );

        mvc.perform(post("/user/5/cart/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        new JSONObject()
                                .put("productId", 1L)
                                .put("quantity", 5)
                                .toString()
                )
        );

        assertThat(firstCart().userId()).isEqualTo(5L);
        assertThat(firstCart().items()).containsOnly(aCartItem().product(cup).quantity(8).build());
    }

    @Test
    public void add_not_existing_product() throws Exception {
        mvc.perform(post("/user/5/cart/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        new JSONObject()
                                .put("productId", 112421L)
                                .put("quantity", 7)
                                .toString()
                )
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void add_negative_quantity_product() throws Exception {
        Product cup = ProductBuilder.aProduct().id(1L).name("cup").price(3, 50).build();
        em.persistAndFlush(cup);

        mvc.perform(post("/user/5/cart/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        new JSONObject()
                                .put("productId", 1L)
                                .put("quantity", -7)
                                .toString()
                )
        ).andExpect(status().isBadRequest());
    }

    private Cart firstCart() {
        return cartRepository.findAll().iterator().next();
    }

}
