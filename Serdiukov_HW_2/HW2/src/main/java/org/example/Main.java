package org.example;

import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.utill.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
        UserService userService = new UserServiceImpl(userDao);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Прочитать пользователя по ID");
            System.out.println("3. Обновить пользователя");
            System.out.println("4. Удалить пользователя по ID");
            System.out.println("5. Прочитать всех пользователей");
            System.out.println("6. Выход");
            System.out.print("Выберите опцию: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите email: ");
                    String email = scanner.nextLine();
                    System.out.print("Введите возраст: ");
                    int age = scanner.nextInt();
                    User newUser = new User(name, email, age);
                    userService.createUser(newUser);
                    System.out.println("Пользователь создан: " + newUser);
                    break;
                case 2:
                    System.out.print("Введите ID: ");
                    Long id = scanner.nextLong();
                    User user = userService.getUserById(id);
                    if (user != null) {
                        System.out.println("Пользователь: " + user);
                    } else {
                        System.out.println("Пользователь не найден");
                    }
                    break;
                case 3:
                    System.out.print("Введите ID для обновления: ");
                    Long updateId = scanner.nextLong();
                    scanner.nextLine(); // Consume newline
                    User updateUser = userService.getUserById(updateId);
                    if (updateUser != null) {
                        System.out.print("Новое имя (текущее: " + updateUser.getName() + "): ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty()) updateUser.setName(newName);
                        System.out.print("Новый email (текущий: " + updateUser.getEmail() + "): ");
                        String newEmail = scanner.nextLine();
                        if (!newEmail.isEmpty()) updateUser.setEmail(newEmail);
                        System.out.print("Новый возраст (текущий: " + updateUser.getAge() + "): ");
                        String newAgeStr = scanner.nextLine();
                        if (!newAgeStr.isEmpty()) updateUser.setAge(Integer.parseInt(newAgeStr));
                        userService.updateUser(updateUser);
                        System.out.println("Пользователь обновлен: " + updateUser);
                    } else {
                        System.out.println("Пользователь не найден");
                    }
                    break;
                case 4:
                    System.out.print("Введите ID для удаления: ");
                    Long deleteId = scanner.nextLong();
                    userService.deleteUser(deleteId);
                    System.out.println("Пользователь удален");
                    break;
                case 5:
                    List<User> allUsers = userService.getAllUsers();
                    System.out.println("Все пользователи:");
                    for (User u : allUsers) {
                        System.out.println(u);
                    }
                    break;
                case 6:
                    running = false;
                    HibernateUtil.shutdown();
                    System.out.println("Выход");
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
        scanner.close();
    }
}