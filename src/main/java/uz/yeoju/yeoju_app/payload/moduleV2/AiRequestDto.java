package uz.yeoju.yeoju_app.payload.moduleV2;


public class AiRequestDto {
    private String question;
    private String correctAnswer;
    private String studentAnswer;
    private Integer maxScore;

    public AiRequestDto() {
    }

    public AiRequestDto(String question, String correctAnswer, String studentAnswer, Integer maxScore) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
        this.maxScore = maxScore;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", studentAnswer='" + studentAnswer + '\'' +
                ", maxScore=" + maxScore +
                '}';
    }
}
