package dao;

import entities.City;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityDao extends BaseDao {
    public CityDao() {
        super();
    }

    public List<City> getCities() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        List<City> cities = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            cities = session.createQuery("FROM entities.City").getResultList();
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            e.printStackTrace();
            }
        } finally {
            // Close session after each task with database
            session.close();
        }
        return cities;
    }

    public City getCityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        List<City> cities = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            cities = session.createQuery("from entities.City ct where ct.name = :cityName")
                    .setParameter("cityName", name)
                    .getResultList();

            transaction.commit();
        }
        catch (RuntimeException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return cities.size() > 0 ? cities.get(0) : null;
    }

    public List<City> getCitiesWithDailyWeather() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        List<City> cities = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            cities = session.createQuery("SELECT DISTINCT ct FROM entities.City ct LEFT JOIN FETCH ct.weathers w WHERE (w.validAt BETWEEN :startAt AND :endAt) ORDER BY w.validAt asc")
                    .setParameter("startAt", DateUtils.getDateWithDayStartTime(new Date()))
                    .setParameter("endAt", DateUtils.getDateWithMidnightTime(new Date()))
                    .getResultList();

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            e.printStackTrace();
            }
        } finally {
            session.close();
        }
        return cities;
    }
}
