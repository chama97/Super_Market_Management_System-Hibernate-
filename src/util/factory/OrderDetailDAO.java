package util.factory;

import entity.Order;
import entity.OrderDetail;
import org.hibernate.Session;
import util.FactoryConfiguration;

import java.util.List;

public class OrderDetailDAO {

    public static boolean addOrderDetail(OrderDetail order) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();

        } catch (Exception e) {
        } finally {
            session.close();
        }
        return true;
    }

    public static List<OrderDetail> getOrderDetail() {
        List<OrderDetail> orders = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            orders = session.createQuery("from OrderDetail").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return orders;
    }

}
