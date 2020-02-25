package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL1 = new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 25, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 26, 10, 0), "Завтрак", 500);
    public static final Meal MEAL3 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 27, 10, 0), "Завтрак", 500);
    public static final Meal MEAL4 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 28, 10, 0), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0), "Завтрак", 500);
    public static final Meal MEAL6 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL7 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "newDescription", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL4);
        updated.setCalories(400);
        updated.setDescription("Ужин");
        updated.setId(MEAL4.getId());
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(Arrays.asList(expected));
    }
}
