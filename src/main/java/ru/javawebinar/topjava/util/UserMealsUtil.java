package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Пробегаем по meals, собираем все даты и считаем кол-во калорий для каждой даты
        Map<Integer, Integer> dateCalories = new HashMap<>();
        for (UserMeal newMeal : meals) {
            int currentCalories = dateCalories.getOrDefault(newMeal.getDateTime().getDayOfMonth(), 0);
            dateCalories.put(newMeal.getDateTime().getDayOfMonth(), currentCalories + newMeal.getCalories());
        }

        //Переделываем каждый UserMeal в UserMealWithExcess
        List<UserMealWithExcess> completedResult = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (dateCalories.getOrDefault(meal.getDateTime().getDayOfMonth(), 0) > caloriesPerDay) {
                completedResult.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
            } else {
                completedResult.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
            }
        }

        //Проходимся по финальному списку и берем от туда только записи отфильтрованные по времени (между startTime и endTime)
        List<UserMealWithExcess> mealsFilteredByTime = new ArrayList<>();
        for (UserMealWithExcess userMealWithExcess : completedResult) {
            if (isBetweenInclusive(userMealWithExcess.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsFilteredByTime.add(userMealWithExcess);
            }
        }
        return mealsFilteredByTime;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }

    public static boolean isBetweenInclusive(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }




}
