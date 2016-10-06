package pl.pragmatists.trainings.ecommerce.carttotal;

import static com.google.common.collect.Lists.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.pragmatists.trainings.ecommerce.product.persistence.ProductBuilder.aProduct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import pl.pragmatists.trainings.ecommerce.MvcBaseTest;
import pl.pragmatists.trainings.ecommerce.addtocart.CartItemBuilder;
import pl.pragmatists.trainings.ecommerce.cart.Cart;
import pl.pragmatists.trainings.ecommerce.cart.CartItem;
import pl.pragmatists.trainings.ecommerce.common.Money;
import pl.pragmatists.trainings.ecommerce.product.persistence.Product;
import pl.pragmatists.trainings.ecommerce.product.persistence.ProductBuilder;

@RunWith(SpringRunner.class)
public class CartTotalTest extends MvcBaseTest {

    @Test
    public void total_for_one_product() throws Exception {
        Product handkerchief = em.persistAndFlush(aProduct().id(1L).name("handkerchief").price(3, 50).build());
        Cart cart = new Cart(5L);
        cart.add(handkerchief, 1);
        em.persistAndFlush(cart);

        mvc.perform(get("/user/5/cart"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value("18,50"))
                .andExpect(jsonPath("$.shipping").value("15,0"));

    }

    @Test
    public void total_for_two_same_products() throws Exception {
        Product handkerchief = em.persistAndFlush(aProduct().id(1L).name("handkerchief").price(3, 50).build());
        CartItem handkerchiefInCart = CartItemBuilder.aCartItem().product(handkerchief).quantity(2).build();
        Cart cart = new Cart(5L);
        cart.add(handkerchief, 2);
        em.persistAndFlush(cart);

        mvc.perform(get("/user/5/cart"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value("22,0"))
                .andExpect(jsonPath("$.shipping").value("15,0"));

    }

    @Test
    public void total_for_two_different_products() throws Exception {
        Product handkerchief = em.persistAndFlush(aProduct().id(1L).name("handkerchief").price(3, 50).build());
        Product cup = em.persistAndFlush(aProduct().id(6L).name("cup").price(2, 50).build());

        Cart cart = new Cart(5L);
        cart.add(handkerchief, 2);
        cart.add(cup, 6);
        em.persistAndFlush(cart);

        mvc.perform(get("/user/5/cart"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value("37,0"))
                .andExpect(jsonPath("$.shipping").value("15,0"));
    }

    @Test
    public void total_with_no_shipping() throws Exception {
        Product handkerchief = em.persistAndFlush(aProduct().id(1L).name("handkerchief").price(3, 50).build());
        Cart cart = new Cart(5L);
        cart.add(handkerchief, 30);
        em.persistAndFlush(cart);

        mvc.perform(get("/user/5/cart"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value("105,0"))
                .andExpect(jsonPath("$.shipping").value("0,0"));

    }

}
