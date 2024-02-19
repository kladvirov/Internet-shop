package by.kladvirov.bpp;

import by.kladvirov.annotation.MyTransactional;
import by.kladvirov.exception.RepositoryException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@MyTransactional
@Component
public class TransactionalHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap();

    private SessionFactory sessionFactory;

    @Autowired
    TransactionalHandlerBeanPostProcessor (SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if(beanClass.isAnnotationPresent(MyTransactional.class)){
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if(beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                System.out.println("something..");
                try(Session session = sessionFactory.getCurrentSession()) {
                    try {
                        Transaction transaction = session.getTransaction();
                        Object retVal = method.invoke(bean, args);
                        transaction.commit();
                        System.out.println("something after..");
                        return retVal;
                    } catch (HibernateException e){
                        session.getTransaction().rollback();
                        throw new RepositoryException("There was an exception during ...");
                    }
                }
            });
        }
        return bean;
    }

}
