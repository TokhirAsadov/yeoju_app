package uz.yeoju.yeoju_app.payload.educationYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonsOneGroup {
    private String lessonName;
    private Set<String> teachers;
    private Object statistics;
    private Object lessons;
    private String student;
    private String login;

    public LessonsOneGroup(Object statistics, Object lessons, String student,String login) {
        this.statistics = statistics;
        this.lessons = lessons;
        this.student = student;
        this.login = login;
    }

    public LessonsOneGroup(String lessonName, Set<String> teachers, Object statistics, Object lessons) {
        this.lessonName = lessonName;
        this.teachers = teachers;
        this.statistics = statistics;
        this.lessons = lessons;
    }
}
