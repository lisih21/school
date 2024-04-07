package starter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class StudentEntity {

    private Integer id;
    private ClassEntity classId;
    private String name;
    private String fatherName;
    private String surname;
    private SexStatus sex;
    private ParentEntity parentId;

    public StudentEntity(Integer id,
                         ClassEntity classId,
                         String name,
                         String fatherName,
                         String surname,
                         SexStatus sex,
                         ParentEntity parentId) {
        this.id = id;
        this.parentId = parentId;
        this.sex = sex;
        this.surname = surname;
        this.fatherName = fatherName;
        this.name = name;
        this.classId = classId;
    }
}
