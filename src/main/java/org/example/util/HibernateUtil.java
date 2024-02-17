package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.model.Good;
import org.example.model.Order;
import org.example.model.Role;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Good.class);
                configuration.addAnnotatedClass(Order.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }

}
