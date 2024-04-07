package starter.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ClassEntityFilter {
    private Integer limit;
    private Integer offset;
    private String className;
    private String classroomTeacher;
}
