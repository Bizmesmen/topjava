package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL1.getId(), USER_ID);
        assertMatch(meal, USER_MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundGet() {
        service.get(USER_MEAL1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL3.getId(), USER_ID);
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, USER_MEAL2, USER_MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(USER_MEAL3.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> all = service.getBetweenHalfOpen(LocalDate.of(2020, Month.JANUARY, 30),
                                                    LocalDate.of(2020, Month.MARCH, 30), ADMIN_ID);
        assertMatch(all, ADMIN_MEAL4, ADMIN_MEAL3);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, USER_MEAL3, USER_MEAL2, USER_MEAL1);
    }

    @Test
    public void update() {
        Meal updatedMeal = getUpdated();
        service.update(updatedMeal, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL4, ADMIN_MEAL3, ADMIN_MEAL2, updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        Meal updatedMeal = getUpdated();
        service.update(updatedMeal, USER_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL4, ADMIN_MEAL3, ADMIN_MEAL2, updatedMeal);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, USER_MEAL3, USER_MEAL2, USER_MEAL1);
        assertMatch(service.create(newMeal, USER_ID), newMeal);
    }
}