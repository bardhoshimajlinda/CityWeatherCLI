package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CityDao;
import dao.WeatherDao;
import dto.openweathermap.ForecastResponse;
import entities.City;
import entities.Weather;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherService {
    public static String OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static String FORECAST = "forecast?units=metric";
    public static String KEY = "44e8ab0753f5e1cf8b6190b9966475a1";

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

    public void syncDatabaseWithOpenWeatherApi() {
        List<City> cities = cityDao.getCities();
        if (cities == null || cities.size() < 1)
            System.out.println("No cities defined. Nothing to synchronize!");

        for (City city : cities) {
            try {

                Request request = new Request.Builder()
                        .url(OPEN_WEATHER_BASE_URL + FORECAST + String.format("&appid=%s", KEY) + String.format("&q=%s", city.getName().toLowerCase()))
                        .build();
                OkHttpClient client = new OkHttpClient();

                Call call = client.newCall(request);
                Response response = call.execute();
                String body = response.body().string();

                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println("Server response: " + body);
                ForecastResponse forecast = objectMapper.readValue(body, ForecastResponse.class);
                if (forecast != null) {
                    weatherDao.saveToDatabase(forecast, city);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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

