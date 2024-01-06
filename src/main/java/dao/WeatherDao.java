package dao;

import dto.openweathermap.Forecast;
import dto.openweathermap.ForecastResponse;
import entities.City;
import entities.Weather;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.DatabaseUtils;
import utils.DateUtils;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherDao extends BaseDao {
    public WeatherDao() {
        super();
    }

    public boolean saveToDatabase(ForecastResponse forecastResponse, City city) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        boolean result = false;

        try {
            transaction = session.beginTransaction();

            for(Forecast forecast : forecastResponse.getItems()) {
                List dbItem = session
                        .createQuery("FROM entities.Weather w WHERE w.validAtTimestamp = :timestamp AND w.cityId = :cityId ")
                        .setParameter("timestamp", forecast.getUnixForecastDate())
                        .setParameter("cityId", city.getId())
                        .getResultList();

                if (dbItem == null || dbItem.size() < 1) {
                    Instant instant = Instant.ofEpochSecond(forecast.getUnixForecastDate());

                    Weather newForecast = Weather.builder()
                            .cityId(city.getId())
                            .visibility(forecast.getVisibility())
                            .humidity(forecast.getWeather().getHumidity())
                            .feels(forecast.getWeather().getFeels())
                            .temperature(forecast.getWeather().getTemperature())
                            .maxTemperature(forecast.getWeather().getMax())
                            .minTemperature(forecast.getWeather().getMin())
                            .pressure(forecast.getWeather().getPressure())
                            .validAtTimestamp(forecast.getUnixForecastDate())
                            .validAt(Date.from(instant))
                            .windSpeed(forecast.getWind().getSpeed())
                            .windDeg(forecast.getWind().getDeg())
                            .windGust(forecast.getWind().getGust())
                            .description(forecast.getWeatherInfo().stream().map(o -> o.getTitle() + ": " + o.getDescription()).collect(Collectors.joining(",")))
                            .build();

                    session.save(newForecast);
                } else {
                    System.out.println(String.format("Data for city: %s, timestamp: %s exists!", city.getName(), forecast.getUnixForecastDate()));
                }
            }
            transaction.commit();
            result = true;
        } catch (RuntimeException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

    public List<Weather> searchWeatherBy(City city, java.util.Date searchDate) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        List<Weather> result = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            result = session
                    .createQuery("FROM entities.Weather w WHERE (w.validAt BETWEEN :startAt AND :endAt) AND w.cityId = :cityId ORDER BY w.validAt ASC ")
                    .setParameter("startAt", DateUtils.getDateWithDayStartTime(searchDate))
                    .setParameter("endAt", DateUtils.getDateWithMidnightTime(searchDate))
                    .setParameter("cityId", city.getId())
                    .getResultList();
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}
