package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
//    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private Session session = null;
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sqlCreate = "CREATE TABLE IF NOT EXISTS Users " +
                    "(ID BIGINT           NOT NULL AUTO_INCREMENT, " +
                    " NAME                VARCHAR(45), " +
                    " LASTNAME            VARCHAR(45), " +
                    " AGE                 BIGINT, " +
                    " PRIMARY KEY         ( ID ))";
            session.createSQLQuery(sqlCreate).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (session != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sqlDrop = "DROP TABLE IF EXISTS Users";
            session.createSQLQuery(sqlDrop).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (session != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sqlSave = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
            session.saveOrUpdate(sqlSave, new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (session != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (session != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sqlGetAll = "SELECT * FROM  Users";
            list = session.createSQLQuery(sqlGetAll).addEntity(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (session != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sqlClean = "TRUNCATE TABLE Users";
            session.createSQLQuery(sqlClean).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (session != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}