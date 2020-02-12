package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private static MealDao dao = new MealDao();

    @Override
    public void init() {
        dao.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        dao.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        dao.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        dao.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        dao.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        dao.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        dao.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet doGet");

        List<Meal> meals = dao.getAll();
        List<MealTo> listToDisplay = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);

        String action = request.getParameter("action");
        String forward;
        String listOfMealView = "meals.jsp";
        String insertOrEdit = "addEditMeal.jsp";


        switch (action == null ? "" : action) {
            case "delete": {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                dao.delete(mealId);
                request.setAttribute("meal", dao.getAll());
                forward = listOfMealView;
                break;
            }
            case "edit": {
                forward = insertOrEdit;
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = dao.get(mealId);
                request.setAttribute("meal", meal);
                break;
            }
            case "insert": {
                forward = insertOrEdit;
                break;
            }
            default:
                forward = listOfMealView;
                request.setAttribute("meals", listToDisplay);
        }
        request.setAttribute("formatter", TimeUtil.FORMATTER);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("MealServlet doPost");

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"), TimeUtil.FORMATTER);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealId = request.getParameter("mealId");

        Meal meal = new Meal(localDateTime, description, calories);

        if (mealId.equals("")) {
            dao.add(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            dao.update(meal);
        }
        response.sendRedirect("meals");

    }
}
