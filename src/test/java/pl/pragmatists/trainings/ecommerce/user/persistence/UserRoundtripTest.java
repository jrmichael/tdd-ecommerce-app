package pl.pragmatists.trainings.ecommerce.user.persistence;

import static org.assertj.core.api.Assertions.*;
import static pl.pragmatists.trainings.ecommerce.user.persistence.UserBuilder.aUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.pragmatists.trainings.ecommerce.MvcBaseTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRoundtripTest extends MvcBaseTest {

    @Test
    public void save_and_load_user() {
        User product = aUser().id(1L).name("Anna").build();

        em.persistAndFlush(product);
        em.clear();

        User fetched = em.find(User.class, 1L);
        assertThat(fetched).isEqualToComparingFieldByField(product);
    }

}
