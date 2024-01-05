package dto.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {
    @JsonProperty("id")
    protected int id;

    @JsonProperty("main")
    protected String title;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("icon")
    protected String icon;
}
