package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

         userService.createUsersTable();

        userService.saveUser("Kakje", "Ya", (byte) 1);
        userService.saveUser("Hochu", "Chtobi", (byte) 2);
        userService.saveUser("Eta", "Proga", (byte) 3);
        userService.saveUser("Nakonets", "Zarabotala", (byte) 4);

        userService.removeUserById(1);

        List<User> allUsers = userService.getAllUsers();

        for (User user : allUsers) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
