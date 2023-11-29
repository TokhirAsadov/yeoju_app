package uz.yeoju.yeoju_app.payload.otherServiceDtos;

public class FinalDto {
    private String id;
    private String studentId;
    private String subject;
    private String forms;
    private String datas;
    private String times;
    private String rooms;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getForms() {
        return forms;
    }

    public void setForms(String forms) {
        this.forms = forms;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }
    public FinalDto() {
    }
    public FinalDto(String studentId, String subject, String forms, String datas, String times, String rooms) {
        this.studentId = studentId;
        this.subject = subject;
        this.forms = forms;
        this.datas = datas;
        this.times = times;
        this.rooms = rooms;
    }

    public FinalDto(String id, String studentId, String subject, String forms, String datas, String times, String rooms) {
        this.id = id;
        this.studentId = studentId;
        this.subject = subject;
        this.forms = forms;
        this.datas = datas;
        this.times = times;
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "FinalDto{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", subject='" + subject + '\'' +
                ", forms='" + forms + '\'' +
                ", datas='" + datas + '\'' +
                ", times='" + times + '\'' +
                ", rooms='" + rooms + '\'' +
                '}';
    }
}
