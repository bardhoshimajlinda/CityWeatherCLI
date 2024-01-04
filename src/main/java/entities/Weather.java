package entities;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "city_id")
    protected int cityId;

    @Column(name = "valid_at_timestamp")
    protected long validAtTimestamp;

    @Column(name = "valid_at")
    protected Date validAt;

    @Column(name = "temperature")
    protected String temperature;

    @Column(name = "feels")
    protected String feels;

    @Column(name = "max_temperature")
    protected String maxTemperature;

    @Column(name = "min_temperature")
    protected String minTemperature;

    @Column(name = "pressure")
    protected String pressure;

    @Column(name = "humidity")
    protected String humidity;

    @Column(name = "description")
    protected String description;

    @Column(name = "wind_speed")
    protected String windSpeed;

    @Column(name = "wind_deg")
    protected String windDeg;

    @Column(name = "wind_gust")
    protected String windGust;

    @Column(name = "visibility")
    protected int visibility;

    @ManyToOne
    @JoinColumn(name = "city_id", updatable = false, insertable = false)
    protected City city;
}
