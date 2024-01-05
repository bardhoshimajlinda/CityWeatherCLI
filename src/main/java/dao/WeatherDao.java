package dao;

import entities.City;
import entities.Weather;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.DatabaseUtils;
import utils.DateUtils;
import java.util.ArrayList;
import java.util.List;

public class WeatherDao extends BaseDao {
    public WeatherDao() {
        super();
    }

    public List<Weather> searchWeatherBy(City city, java.util.Date searchDate) {
        SessionFactory sessionFactory = DatabaseUtils.getDbSession();

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<Weather> result = new ArrayList<>();

        try {
            tx = session.beginTransaction();

            result = session
                    .createQuery("from entities.Weather w where (w.validAt between :startAt and :endAt) and w.cityId = :cityId order by w.validAt asc")
                    .setParameter("startAt", DateUtils.getDateWithDayStartTime(searchDate))
                    .setParameter("endAt", DateUtils.getDateWithMidnightTime(searchDate))
                    .setParameter("cityId", city.getId())
                    .getResultList();
            tx.commit();
        }
        catch (RuntimeException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return result;
    }
}
