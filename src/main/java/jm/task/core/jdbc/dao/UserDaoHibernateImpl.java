package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sqlCreate = "CREATE TABLE IF NOT EXISTS Users " +
                "(ID BIGINT           NOT NULL AUTO_INCREMENT, " +
                " NAME                VARCHAR(45), " +
                " LASTNAME            VARCHAR(45), " +
                " AGE                 BIGINT, " +
                " PRIMARY KEY         ( ID ))";
        session.createSQLQuery(sqlCreate).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sqlDrop = "DROP TABLE IF EXISTS Users";
        session.createSQLQuery(sqlDrop).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sqlSave = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        session.saveOrUpdate(sqlSave, new User(name, lastName, age));
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sqlGetAll = "SELECT * FROM  Users";
        List<User> list = session.createSQLQuery(sqlGetAll).addEntity(User.class).list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sqlClean = "delete FROM Users";
        session.createSQLQuery(sqlClean).executeUpdate();
        transaction.commit();
        session.close();
    }
}