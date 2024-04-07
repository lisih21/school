package starter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ClassEntity {
    private Integer id;
    private String className;
    private TeacherEntity classroomTeacher;

    public ClassEntity(Integer id,
                       String className,
                       TeacherEntity classroomTeacher) {
        this.id = id;
        this.className = className;
        this.classroomTeacher = classroomTeacher;
    }
}
