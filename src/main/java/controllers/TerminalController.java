package controllers;

import entities.City;
import entities.Weather;
import services.CityService;
import services.WeatherService;
import utils.DateUtils;

import java.util.List;
import java.util.Scanner;

public class TerminalController {
    protected WeatherService weatherService;
    protected CityService cityService;

    public TerminalController(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    public void loadAppInteraction() {

        Scanner scanner = new Scanner(System.in);

        String city = null;
        String date = null;

        System.out.println("Zgjidhni mënyren e kërkimit:");
        System.out.println("1. Kërko sipas qytetit");
        System.out.println("2. Kërko sipas datës");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.println("Shkruani qytetin për të cilin dëshironi të kontrolloni motin: ");
                city = scanner.nextLine();
                cityService.addCity(city);
                break;
            case "2":
                System.out.println("Vendosni datën për të cilën dëshironi të kontrolloni motin (dd/mm/yyyy): ");
                date = scanner.nextLine();
                break;
            default:
                System.out.println("Zgjedhje e pavlefshme.");
                return;
        }


        System.out.println("Ju lutem prisni!");

        weatherService.syncDatabaseWithOpenWeatherApi();


        System.out.println("====================================== PARASHIKIMI I MOTIT ==========================================");

        List<Weather> searchResult = weatherService.searchWeatherInfoBy(city, date);
        String columnFormat = "%-10s%-25s%-15s%-15s%-15s%-25s%n";
        String[] columns = {"Qyteti", "Parashikimi", "Aktualisht", "Min", "Max", "Perditësimi i fundit"};
        if (searchResult.size() > 0) {
            System.out.printf(columnFormat, columns);
            searchResult.forEach(o -> System.out.printf(columnFormat,
                    o.getCity().getName(),
                    o.getDescription(),
                    o.getTemperature() + " °C",
                    o.getMinTemperature() + " °C",
                    o.getMaxTemperature() + " °C",
                    DateUtils.getMinusDelimeterdateFormat().format(o.getValidAt())));
        } else {
            List<City> cities = cityService.getCitiesForInstantInformation();

            if (cities != null && cities.size() > 0) {
                System.out.printf(columnFormat, columns);
                cities.forEach(o -> System.out.printf(columnFormat,
                        o.getName(),
                        o.getWeathers().get(0).getDescription(),
                        o.getWeathers().get(0).getTemperature() + " °C",
                        o.getWeathers().get(0).getMinTemperature() + " °C",
                        o.getWeathers().get(0).getMaxTemperature() + " °C",
                        DateUtils.getMinusDelimeterdateFormat().format(o.getWeathers().get(0).getValidAt()))
                );
            } else {
                System.out.println("--- Aktualisht nuk u gjend asnje informacion mbi motin ---");
            }
        }
        System.out.println("=====================================================================================================");
    }
}
