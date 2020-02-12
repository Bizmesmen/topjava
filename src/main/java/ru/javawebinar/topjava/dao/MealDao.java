package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDao implements Dao{
    final Map<Integer, Meal> idMeal = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void add(Meal meal) {
        Integer id = counter.getAndIncrement();
        meal.setId(id);
        idMeal.put(id, new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public void delete(int mealId) {
        idMeal.remove(mealId);
    }

    @Override
    public Meal get(int mealId) {
        return idMeal.get(mealId);
    }

    @Override
    public void update(Meal meal) {
        idMeal.replace(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(idMeal.values());
    }

    @Override
    public Meal getMealById(int userId) {
        return idMeal.get(userId);
    }

}
