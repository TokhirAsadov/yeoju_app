//package uz.yeoju.yeoju_app.entity.module;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import uz.yeoju.yeoju_app.entity.Lesson;
//import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
//import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
//
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//import javax.validation.constraints.Size;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ThemeOfSubjectForGradeByTeacher extends AbsEntity {
//
//
//    @Size(min = 3)
//    private String name;
//
//    @ManyToOne
//    private Lesson lesson;
//
//    @ManyToOne
//    private EducationYear educationYear;
//
//    public ThemeOfSubjectForGradeByTeacher(String id, String name, Lesson lesson, EducationYear educationYear) {
//        super(id);
//        this.name = name;
//        this.lesson = lesson;
//        this.educationYear = educationYear;
//    }
//}