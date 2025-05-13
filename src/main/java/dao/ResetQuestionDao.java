package dao;

import model.ResetQuestion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResetQuestionDao {
    private final Connection connection;

    public ResetQuestionDao(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createResetQuestion(ResetQuestion question) throws SQLException {
        String query = "INSERT INTO reset_question (user_id, question, answer) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, question.getUserId());
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getAnswer());
            statement.executeUpdate();
        }
    }

    // READ by ID
    public ResetQuestion getResetQuestionById(int id) throws SQLException {
        String query = "SELECT * FROM reset_question WHERE reset_question_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new ResetQuestion(
                        rs.getInt("reset_question_id"),
                        rs.getInt("user_id"),
                        rs.getString("question"),
                        rs.getString("answer")
                    );
                }
            }
        }
        return null;
    }

    // READ all by user_id
    public List<ResetQuestion> getResetQuestionsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM reset_question WHERE user_id = ?";
        List<ResetQuestion> questions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    questions.add(new ResetQuestion(
                        rs.getInt("reset_question_id"),
                        rs.getInt("user_id"),
                        rs.getString("question"),
                        rs.getString("answer")
                    ));
                }
            }
        }
        return questions;
    }

    // UPDATE
    public void updateResetQuestion(ResetQuestion question) throws SQLException {
        String query = "UPDATE reset_question SET question = ?, answer = ? WHERE reset_question_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, question.getQuestion());
            statement.setString(2, question.getAnswer());
            statement.setInt(3, question.getId());
            statement.executeUpdate();
        }
    }

    // DELETE
    public void deleteResetQuestion(int id) throws SQLException {
        String query = "DELETE FROM reset_question WHERE reset_question_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}