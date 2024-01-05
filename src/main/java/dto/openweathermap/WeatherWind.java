package dto.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherWind {

    @JsonProperty("speed")
    protected String speed;

    @JsonProperty("deg")
    protected String deg;

    @JsonProperty("gust")
    protected String gust;
}
