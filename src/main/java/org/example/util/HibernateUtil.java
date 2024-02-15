package org.example.util;

import org.example.model.Good;
import org.example.model.Order;
import org.example.model.Role;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public HibernateUtil(){}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Good.class);
                configuration.addAnnotatedClass(Order.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }
}
