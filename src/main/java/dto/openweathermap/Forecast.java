package dto.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @JsonProperty("dt")
    protected long unixForecastDate;

    @JsonProperty("dt_txt")
    protected String textForecastDate;

    @JsonProperty("visibility")
    protected int visibility;

    @JsonProperty("main")
    protected WeatherMain weather;

    @JsonProperty("weather")
    protected List<WeatherInfo> weatherInfo;

    @JsonProperty("wind")
    protected WeatherWind wind;
}
