package starter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ActivityEntity {
    private Integer id;
    private String name;

    public ActivityEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
