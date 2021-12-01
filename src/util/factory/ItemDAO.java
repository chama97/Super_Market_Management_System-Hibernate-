package util.factory;

import entity.Item;
import org.hibernate.Session;
import util.FactoryConfiguration;

import java.util.List;

public class ItemDAO {

    public static List<Item> getItems() {
        List<Item> items = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            items = session.createQuery("from Item").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return items;
    }

    public static void saveItems(Item items) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            session.save(items);
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("SaveItem Error");
        } finally {
            session.close();
        }
    }

    public static boolean DeleteItems(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            int executeUpdate = session.createQuery("DELETE FROM Item WHERE code = '" + id + "'").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    public static Item searchItem(String id){
        Item items = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            items = (Item) session.createQuery("SELECT * FROM Item WHERE code = '" + id + "'").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return items;
    }
}
