package starter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString

public class LessonEntity {

    private Integer id;
    private Timestamp date;
    private TeacherEntity teacherId;
    private ScheduledLessonEntity lessonTableId;

    public LessonEntity(Integer id,
                        Timestamp date,
                        TeacherEntity teacherId,
                        ScheduledLessonEntity lessonTableId) {
        this.id = id;
        this.date = date;
        this.teacherId = teacherId;
        this.lessonTableId = lessonTableId;
    }
}
