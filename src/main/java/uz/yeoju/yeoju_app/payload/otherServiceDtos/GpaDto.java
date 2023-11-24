package uz.yeoju.yeoju_app.payload.otherServiceDtos;

public class GpaDto {
    public String id;
    public String studentId;
    public String passport;
    public String stGroup;
    public String unv;
    public String educationType;
    public String fullName;
    public String course;
    public String yunalish;
    public Integer fails;
    public String stGpa;

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

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getStGroup() {
        return stGroup;
    }

    public void setStGroup(String stGroup) {
        this.stGroup = stGroup;
    }

    public String getUnv() {
        return unv;
    }

    public void setUnv(String unv) {
        this.unv = unv;
    }

    public String getEducationType() {
        return educationType;
    }

    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYunalish() {
        return yunalish;
    }

    public void setYunalish(String yunalish) {
        this.yunalish = yunalish;
    }

    public Integer getFails() {
        return fails;
    }

    public void setFails(Integer fails) {
        this.fails = fails;
    }

    public String getStGpa() {
        return stGpa;
    }

    public void setStGpa(String stGpa) {
        this.stGpa = stGpa;
    }

    public GpaDto(){
    }

    public GpaDto(String studentId, String passport, String stGroup, String unv, String educationType, String fullName, String course, String yunalish, Integer fails, String stGpa) {
        this.studentId = studentId;
        this.passport = passport;
        this.stGroup = stGroup;
        this.unv = unv;
        this.educationType = educationType;
        this.fullName = fullName;
        this.course = course;
        this.yunalish = yunalish;
        this.fails = fails;
        this.stGpa = stGpa;
    }

    public GpaDto(String id, String studentId, String passport, String stGroup, String unv, String educationType, String fullName, String course, String yunalish, Integer fails, String stGpa) {
        this.id = id;
        this.studentId = studentId;
        this.passport = passport;
        this.stGroup = stGroup;
        this.unv = unv;
        this.educationType = educationType;
        this.fullName = fullName;
        this.course = course;
        this.yunalish = yunalish;
        this.fails = fails;
        this.stGpa = stGpa;
    }

    @Override
    public String toString() {
        return "AbiturDto{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", passport='" + passport + '\'' +
                ", stGroup='" + stGroup + '\'' +
                ", unv='" + unv + '\'' +
                ", educationType='" + educationType + '\'' +
                ", fullName='" + fullName + '\'' +
                ", course='" + course + '\'' +
                ", yunalish='" + yunalish + '\'' +
                ", fails=" + fails +
                ", stGpa='" + stGpa + '\'' +
                '}';
    }
}
