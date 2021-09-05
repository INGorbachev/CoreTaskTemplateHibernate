package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao dao = new UserDaoHibernateImpl();

    @Override
    public void createUsersTable() {
        dao.createUsersTable();
    }
    @Override
    public void dropUsersTable() {
        dao.dropUsersTable();
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name,lastName,age);
    }
    @Override
    public void removeUserById(long id) throws SQLException {
        dao.removeUserById(id);
    }
    @Override
    public List<User> getAllUsers() throws SQLException {
        return dao.getAllUsers();
    }
    @Override
    public void cleanUsersTable() {
        dao.cleanUsersTable();
    }
}
