package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(1, "admin", "email@email.ru1", "password1", Role.ROLE_ADMIN),
            new User(2, "user1", "email@email.ru2", "password2", Role.ROLE_USER),
            new User(3, "user2", "email@email.ru3", "password3", Role.ROLE_USER)
    );
}