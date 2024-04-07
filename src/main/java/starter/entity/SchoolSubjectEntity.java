package starter.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SchoolSubjectEntity {
    private Integer id;
    private String name;

    public SchoolSubjectEntity(Integer id,
                               String name) {
        this.id = id;
        this.name = name;
    }
}
