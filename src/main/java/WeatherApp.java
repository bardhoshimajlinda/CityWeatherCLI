import controllers.TerminalController;
import dao.CityDao;
import dao.WeatherDao;
import entities.City;
import entities.Weather;
import services.CityService;
import services.WeatherService;
import utils.DateUtils;
import java.util.List;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {

        CityDao cityDao = new CityDao();
        WeatherDao weatherDao = new WeatherDao();

        WeatherService weatherService = new WeatherService(cityDao, weatherDao);
        CityService cityService = new CityService(cityDao, weatherDao);

        TerminalController terminalController = new TerminalController(weatherService,cityService);
        terminalController.loadAppInteraction();

    }
}

