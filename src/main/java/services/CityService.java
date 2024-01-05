package services;

import dao.CityDao;
import dao.WeatherDao;
import entities.City;

import java.util.List;

public class CityService {
    CityDao cityDao;
    WeatherDao weatherDao;

    public CityService(CityDao cityDao, WeatherDao weatherDao) {
        if (cityDao == null)
            throw new RuntimeException("CityDao instance must not be null");

        if (weatherDao == null)
            throw new RuntimeException("WeatherDao instance must not be null");

        this.cityDao = cityDao;
        this.weatherDao = weatherDao;
    }

    public List<City> getCitiesForInstantInformation() {
        return this.cityDao.getCitiesWithDailyWeather();
    }
}
