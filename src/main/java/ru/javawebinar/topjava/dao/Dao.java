package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao {

    void add(Meal meal);

    void delete(int mealId);

    Meal get(int mealId);

    void update(Meal meal);

    List<Meal> getAll();

    Meal getMealById(int mealId);
}
