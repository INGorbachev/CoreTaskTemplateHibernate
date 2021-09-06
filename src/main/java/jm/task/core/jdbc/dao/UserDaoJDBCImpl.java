package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = null;

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {

        String sqlCreate = "CREATE TABLE IF NOT EXISTS Users " +
                "(ID BIGINT           NOT NULL AUTO_INCREMENT, " +
                " NAME                VARCHAR(45), " +
                " LASTNAME            VARCHAR(45), " +
                " AGE                 BIGINT, " +
                " PRIMARY KEY         ( ID ))";
        try (Connection connection = Util.connectDB()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE IF EXISTS Users";
        try (Connection connection = Util.connectDB()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlDrop);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sqlSave = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.connectDB()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSave);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM Users WHERE ID = (?)";
        try (Connection connection = Util.connectDB()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRemove);
            preparedStatement.executeUpdate(sqlRemove);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sqlGetAll = "SELECT * FROM  Users";
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.connectDB()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetAll);
            while (resultSet.next()) {
                User userInTable = new User();
                userInTable.setId(resultSet.getLong(1));
                userInTable.setName(resultSet.getString(2));
                userInTable.setLastName(resultSet.getString(3));
                userInTable.setAge(resultSet.getByte(4));
                userList.add(userInTable);
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE Users";
        try (Connection connection = Util.connectDB()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlClean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
