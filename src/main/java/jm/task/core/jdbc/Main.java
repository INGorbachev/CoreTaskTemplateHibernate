package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 34);
        System.out.println("User Ivan added");
        userService.saveUser("Petr", "Petrov", (byte) 44);
        System.out.println("User Petr added");
        userService.saveUser("Semen", "Semenov", (byte) 28);
        System.out.println("User Semen added");
        userService.saveUser("Andrey", "Andreev", (byte) 55);
        System.out.println("User Andrey added");

        List<User> users = userService.getAllUsers();

        for (User user: users) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
