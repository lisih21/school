package starter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString


public class Locations {
    private Integer id;
    private String countryId;

    public Locations(Integer id, String countryId) {
        this.id = id;
        this.countryId = countryId;
    }
}
