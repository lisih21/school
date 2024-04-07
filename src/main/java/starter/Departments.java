package starter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Departments {

    private Integer id;
    private String departmentName;
    private Integer managerId;
    private Locations locations;

    public Departments(Integer id, String departmentName, Integer managerId, Locations locations) {
        this.id = id;
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.locations = locations;
    }

    public Departments() {
    }

}
