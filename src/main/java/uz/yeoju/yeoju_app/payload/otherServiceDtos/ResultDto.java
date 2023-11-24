package uz.yeoju.yeoju_app.payload.otherServiceDtos;

public class ResultDto {
    public String id;
    public String studentId;
    public String year;
    public String semester;
    public String subject;
    public Integer credit;
    public String score;
    public String grade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ResultDto() {
    }

    public ResultDto(String studentId, String year, String semester, String subject, Integer credit, String score, String grade) {
        this.studentId = studentId;
        this.year = year;
        this.semester = semester;
        this.subject = subject;
        this.credit = credit;
        this.score = score;
        this.grade = grade;
    }

    public ResultDto(String id, String studentId, String year, String semester, String subject, Integer credit, String score, String grade) {
        this.id = id;
        this.studentId = studentId;
        this.year = year;
        this.semester = semester;
        this.subject = subject;
        this.credit = credit;
        this.score = score;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", subject='" + subject + '\'' +
                ", credit=" + credit +
                ", score='" + score + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
