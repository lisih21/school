package starter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class TeacherEntity {
    private Integer id;
    private String name;
    private String fatherName;
    private String surname;
    private SexStatus sex;

    public TeacherEntity(Integer id,
                         String name,
                         String fatherName,
                         String surname,
                         SexStatus sex) {
        this.id = id;
        this.sex = sex;
        this.surname = surname;
        this.fatherName = fatherName;
        this.name = name;
    }
}
