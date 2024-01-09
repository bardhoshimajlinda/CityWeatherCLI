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

        Scanner scanner = new Scanner(System.in);

        String city = null;
        String date = null;

        System.out.println("Choose search criteria:");
        System.out.println("1. Search by City");
        System.out.println("2. Search by Date");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.println("Enter the city for which you want to check the weather: ");
                city = scanner.nextLine();
                cityService.addCity(city);
                break;
            case "2":
                System.out.println("Enter the date for which you want to check the weather (dd/MM/yyyy): ");
                date = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid choice. Exiting program.");
                return;
        }

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

