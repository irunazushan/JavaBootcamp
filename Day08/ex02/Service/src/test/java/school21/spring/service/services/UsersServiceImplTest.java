package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

public class UsersServiceImplTest {
    @Test
    public void usersServiceShouldReturnString() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        UsersService us = context.getBean("usersServiceImpl", UsersService.class);
        Assertions.assertInstanceOf(String.class, us.signUp("email@mail.ru"));
    }
}
