import dao.CityDao;
import dao.WeatherDao;
import entities.City;
import entities.Weather;
import services.CityService;
import services.WeatherService;
import utils.DateUtils;
import java.util.List;

public class WeatherApp {
    public static void main(String[] args) {

        CityDao cityDao = new CityDao();
        WeatherDao weatherDao = new WeatherDao();

        WeatherService weatherService = new WeatherService(cityDao, weatherDao);
        CityService cityService = new CityService(cityDao, weatherDao);

        // addTiranaToDatabase(cityDao);

        weatherService.syncDatabaseWithOpenWeatherApi();

        String cityQuery = null;
        String dateQuery = null;
        if (args.length > 0) {
            cityQuery = args[0];
        }

        if (args.length > 1) {
            dateQuery = args[1];
        }

        System.out.println("====================================== PARASHIKIMI I MOTIT ==========================================");

        List<Weather> searchResult = weatherService.searchWeatherInfoBy(cityQuery, dateQuery);
        String columnFormat = "%-10s%-25s%-15s%-15s%-15s%-25s%n";
        String[] columns = {"Qyteti", "Parashikimi", "Aktualisht", "Min", "Max", "Perditësimi i fundi"};
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

