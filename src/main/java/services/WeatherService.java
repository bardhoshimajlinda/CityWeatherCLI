package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CityDao;
import dao.WeatherDao;
import entities.City;
import entities.Weather;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherService {
    CityDao cityDao;
    WeatherDao weatherDao;
    public WeatherService(CityDao cityDao, WeatherDao weatherDao) {
        if (cityDao == null)
            throw new RuntimeException("CityDao instance must not be null");

        if (weatherDao == null)
            throw new RuntimeException("WeatherDao instance must not be null");

        this.cityDao = cityDao;
        this.weatherDao = weatherDao;
    }
    public List<Weather> searchWeatherInfoBy(String cityQuery, String dateQuery) {
        Date searchDate = new Date();

        if (dateQuery != null && !dateQuery.isBlank()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                searchDate = formatter.parse(dateQuery);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("Formati i datës duhet të jetë: dd/MM/yyyy");
            }
        }

        if (cityQuery == null || cityQuery.isBlank())
            return new ArrayList<>();

        City city = cityDao.getCityByName(cityQuery);
        if (city == null)
            throw new RuntimeException(String.format("Qyteti %s nuk u gjend ne database", cityQuery));
        return weatherDao.searchWeatherBy(city, searchDate);
    }
}

