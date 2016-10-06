package pl.pragmatists.trainings.ecommerce.product.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.pragmatists.trainings.ecommerce.MvcBaseTest;
import pl.pragmatists.trainings.ecommerce.common.Money;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRoundtripTest extends MvcBaseTest {

    @Test
    public void save_and_load_product() {
        Product product = ProductBuilder.aProduct().id(1L).name("cup").price(1,25).build();

        em.persistAndFlush(product);
        em.clear();

        Product fetched = em.find(Product.class, 1L);
        assertThat(fetched).isEqualToComparingFieldByFieldRecursively(product);
    }
}
