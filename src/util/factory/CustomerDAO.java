package util.factory;

import entity.Customer;
import org.hibernate.Session;
import util.FactoryConfiguration;

import java.util.List;

public class CustomerDAO {

    public static List<Customer> getCustomers() {
        List<Customer> customers = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            customers = session.createQuery("from Customer").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return customers;
    }

    public static void saveCustomer(Customer customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("SaveCustomer Error");
        } finally {
            session.close();
        }
    }

    public static boolean DeleteCustomer(String cId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            int executeUpdate = session.createQuery("DELETE FROM Customer WHERE id = '" + cId + "'").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    public static Customer searchCustomer(String id){
        Customer customer = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            customer = (Customer) session.createQuery("SELECT * FROM Customer WHERE id = '" + id + "'").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return customer;
    }

}
