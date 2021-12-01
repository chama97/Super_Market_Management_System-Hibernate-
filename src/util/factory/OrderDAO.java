package util.factory;

import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import org.hibernate.Session;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.List;

public class OrderDAO {

    public static boolean purchaseOrder(Order dto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        Order order = new Order(dto.getOrderId(), dto.getCId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCost());
        boolean orderAdded = addOrder(order);
        if (!orderAdded) {
            session.getTransaction().rollback();
            session.getTransaction().commit();
            return false;
        }

        for (OrderDetail detail : dto.getOrderList()) {
            OrderDetail orderDetailDTO = new OrderDetail(detail.getItemCode(), dto.getOrderId(),  detail.getQty(), detail.getPrice());
            boolean orderDetailsAdded = OrderDetailDAO.addOrderDetail(orderDetailDTO);
            if (!orderDetailsAdded) {
                session.getTransaction().rollback();
                session.getTransaction().commit();
                return false;
            }

            Item search = ItemDAO.searchItem(detail.getItemCode());
            search.setQtyOnHand(search.getQtyOnHand() - detail.getQty());
            boolean update = ItemDAO.updateItems(search);
            if (!update) {
                session.getTransaction().rollback();
                session.getTransaction().commit();
                return false;
            }
        }
        session.getTransaction().rollback();
        session.getTransaction().commit();
        return true;

    }

    public static boolean addOrder(Order order) {
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

    public static List<Order> getOrder() {
        List<Order> orders = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            orders = session.createQuery("from order").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return orders;
    }

    public static boolean DeleteOrder(String id) {
        Order order = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            order = session.get(Order.class, id);
            session.delete(order);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    public static double getTotalIncome() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            session.createQuery("SELECT SUM(cost) FROM order").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return 0;
    }

    public static int getOrderCount() throws ClassNotFoundException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            session.createQuery("SELECT COUNT(orderId) FROM order").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return -1;
    }
}
