package starter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class MarkEntity {
    private Integer id;
    private StudentEntity studentId;
    private LessonEntity lessonId;
    private ActivityEntity activityId;
    private Integer grade;

    public MarkEntity(Integer id,
                      StudentEntity studentId,
                      LessonEntity lessonId,
                      ActivityEntity activityId,
                      Integer grade) {
        this.id = id;
        this.studentId = studentId;
        this.lessonId = lessonId;
        this.activityId = activityId;
        this.grade = grade;
    }
}
