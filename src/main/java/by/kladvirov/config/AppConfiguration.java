package by.kladvirov.config;

import by.kladvirov.Quote;
import by.kladvirov.Quoter;
import by.kladvirov.model.Good;
import by.kladvirov.model.Order;
import by.kladvirov.model.Role;
import by.kladvirov.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "by.kladvirov.*")
@Configuration
public class AppConfiguration {

    @Bean
    public SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Good.class);
        configuration.addAnnotatedClass(Order.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    @Bean
    public Quote Quote(){
        return new Quoter();
    }
}

