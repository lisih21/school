package starter.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ParentEntity {
    private Integer id;
    private String name;
    private String fatherName;
    private String surname;
    private SexStatus sex;
    private String phoneNumber;
    private String email;

    public ParentEntity(Integer id,
                        String name,
                        String fatherName,
                        String surname,
                        SexStatus sex,
                        String phoneNumber,
                        String email) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.surname = surname;
        this.fatherName = fatherName;
        this.name = name;
    }
}
