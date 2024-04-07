package starter.entity;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ScheduledLessonEntity {
    private Integer id;
    private SchoolSubjectEntity subjectId;
    private ClassEntity classId;
    private Integer numberLesson;
    private Integer dayOfWeek;

    public ScheduledLessonEntity(Integer id,
                                 SchoolSubjectEntity subjectId,
                                 ClassEntity classId,
                                 Integer numberLesson,
                                 Integer dayOfWeek) {
        this.id = id;
        this.subjectId = subjectId;
        this.classId = classId;
        this.numberLesson = numberLesson;
        this.dayOfWeek = dayOfWeek;
    }

}
