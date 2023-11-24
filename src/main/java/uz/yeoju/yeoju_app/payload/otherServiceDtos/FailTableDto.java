package uz.yeoju.yeoju_app.payload.otherServiceDtos;

public class FailTableDto {
    public String id;
    public String studentId;
    public String year;
    public String semester;
    public String subject;
    public Integer credit;
    public String currents;
    public String finals;
    public String totals;
    public String penalty;
    public String retakeN;
    public String retakeData;
    public String note;

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

    public String getCurrents() {
        return currents;
    }

    public void setCurrents(String currents) {
        this.currents = currents;
    }

    public String getFinals() {
        return finals;
    }

    public void setFinals(String finals) {
        this.finals = finals;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getRetakeN() {
        return retakeN;
    }

    public void setRetakeN(String retakeN) {
        this.retakeN = retakeN;
    }

    public String getRetakeData() {
        return retakeData;
    }

    public void setRetakeData(String retakeData) {
        this.retakeData = retakeData;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public FailTableDto() {
    }

    public FailTableDto(String studentId, String year, String semester, String subject, Integer credit, String currents, String finals, String totals, String penalty, String retakeN, String retakeData, String note) {
        this.studentId = studentId;
        this.year = year;
        this.semester = semester;
        this.subject = subject;
        this.credit = credit;
        this.currents = currents;
        this.finals = finals;
        this.totals = totals;
        this.penalty = penalty;
        this.retakeN = retakeN;
        this.retakeData = retakeData;
        this.note = note;
    }

    public FailTableDto(String id, String studentId, String year, String semester, String subject, Integer credit, String currents, String finals, String totals, String penalty, String retakeN, String retakeData, String note) {
        this.id = id;
        this.studentId = studentId;
        this.year = year;
        this.semester = semester;
        this.subject = subject;
        this.credit = credit;
        this.currents = currents;
        this.finals = finals;
        this.totals = totals;
        this.penalty = penalty;
        this.retakeN = retakeN;
        this.retakeData = retakeData;
        this.note = note;
    }

    @Override
    public String toString() {
        return "FailTableDto{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", subject='" + subject + '\'' +
                ", credit=" + credit +
                ", currents='" + currents + '\'' +
                ", finals='" + finals + '\'' +
                ", totals='" + totals + '\'' +
                ", penalty='" + penalty + '\'' +
                ", retakeN='" + retakeN + '\'' +
                ", retakeData='" + retakeData + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
