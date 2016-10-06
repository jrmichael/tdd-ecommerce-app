package pl.pragmatists.trainings.ecommerce.user.persistence;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * Created by mszynwelski on 06.10.16.
 */
public class UserBuilder {

    private long id;
    private String name;

    static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder id(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public User build() {
        return new User(id, name);
    }
}
