package starter;

import starter.dao.*;
import starter.dto.ClassEntityFilter;
import starter.entity.ClassEntity;
import starter.entity.SchoolSubjectEntity;
import starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoRunner {
    public static void main(String[] args) throws SQLException {


//        SchoolSubjectDao schoolSubjectDao = SchoolSubjectDao.getInstance();
//        System.out.println(schoolSubjectDao.findById(1));
//
//        ClassDao classDao = ClassDao.getInstance();
//        System.out.println(classDao.findById(1));
//
        ScheduledLessonDao scheduledLessonDao = ScheduledLessonDao.getInstance();


//
        LessonDao lessonDao = LessonDao.getInstance();
        System.out.println(lessonDao.findById(13));
        ConnectionManager.closePool();
    }

    private static void extracted1() {
        SchoolSubjectDao schoolSubjectEntity = SchoolSubjectDao.getInstance();
        System.out.println(schoolSubjectEntity.findById(1));

        ActivityDao activityDao = ActivityDao.getInstance();
        System.out.println(activityDao.findById(1));
    }

    private static void extracted() {
        Connection connection = ConnectionManager.get();
        TeacherDao teacherDao = TeacherDao.getInstance();
        System.out.println(teacherDao.findAll());

        ClassDao classDao = ClassDao.getInstance();
        ClassEntityFilter classEntityFilter =
                ClassEntityFilter.builder()
                        .offset(2).build();
        System.out.println(classDao.findAll(classEntityFilter));
    }
}
