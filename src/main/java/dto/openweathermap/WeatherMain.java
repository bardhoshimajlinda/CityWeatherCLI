package dto.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMain {
    @JsonProperty("temp")
    protected String temperature;

    @JsonProperty("feels_like")
    protected String feels;

    @JsonProperty("pressure")
    protected String pressure;

    @JsonProperty("temp_min")
    protected String min;

    @JsonProperty("temp_max")
    protected String max;

    @JsonProperty("humidity")
    protected String humidity;

}
