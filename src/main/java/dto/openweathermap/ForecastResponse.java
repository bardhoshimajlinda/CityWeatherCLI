package dto.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    @JsonProperty("cod")
    protected String code;

    @JsonProperty("message")
    protected String message;

    @JsonProperty("cnt")
    protected String count;

    @JsonProperty("list")
    protected List<Forecast> items;

}
