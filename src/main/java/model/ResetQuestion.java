package model;

import java.io.Serializable;
public class ResetQuestion implements Serializable {
    private final int id;
    private final int userId;
    private final String question;
    private final String answer;

    // Constructor
    public ResetQuestion(int id, int userId, String question, String answer) {
        this.id = id;
        this.userId = userId;
        this.question = question;
        this.answer = answer;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect(String providedAnswer) {
        return this.answer != null && this.answer.equals(providedAnswer);
    }
}
