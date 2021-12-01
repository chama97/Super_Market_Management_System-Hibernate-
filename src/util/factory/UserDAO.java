package util.factory;

import entity.User;
import org.hibernate.Session;
import util.FactoryConfiguration;

import java.util.List;

public class UserDAO {

    public static List<User> getUsers() {
        List<User> users = null;
        Session session =FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return users;
    }
}
