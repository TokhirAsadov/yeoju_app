package uz.yeoju.yeoju_app.payload.moduleV2;

public class AiResponseDto {
    private int score;
    private String feedback;

    public AiResponseDto() {
    }

    public AiResponseDto(int score, String feedback) {
        this.score = score;
        this.feedback = feedback;
    }

    // Getter va Setterlar
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "AiResponseDto{" +
                "score=" + score +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}

