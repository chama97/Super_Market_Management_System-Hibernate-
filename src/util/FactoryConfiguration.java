package util;

import entity.Customer;
import entity.Item;
import entity.User;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {

    public static List<User> getUsers() {
        List<User> users = null;
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();

        } finally {
            factory.close();
        }
        return users;
    }


    public static List<Customer> getCustomers() {
        List<Customer> customers = null;
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class).buildSessionFactory();

        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            customers = session.createQuery("from Customer").list();
            session.getTransaction().commit();

        } finally {
            factory.close();
        }
        return customers;
    }

    public static void saveCustomer(Customer customer) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class).buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("SaveCustomer Error");
        } finally {
            factory.close();
        }
    }

    public static boolean DeleteCustomer(String cId) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            int executeUpdate = session.createQuery("DELETE FROM Customer WHERE id = '" + cId + "'").executeUpdate();
            session.getTransaction().commit();
            System.out.println("deleteGuest() Done!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
        return true;
    }

    public static List<Item> getItems() {
        List<Item> items = null;
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Item.class).buildSessionFactory();

        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            items = session.createQuery("from Item").list();
            session.getTransaction().commit();

        } finally {
            factory.close();
        }
        return items;
    }

    public static void saveItems(Item items) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Item.class).buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(items);
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("SaveItem Error");
        } finally {
            factory.close();
        }
    }

    public static boolean DeleteItems(String id) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Item.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            int executeUpdate = session.createQuery("DELETE FROM Item WHERE code = '" + id + "'").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
        return true;
    }

}
