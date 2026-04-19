package com.zukuk.model;

import java.util.List;

public class QuizQuestion {

    private String question;
    private String correctAnswer;
    private List<String> allAnswers;

    public QuizQuestion(String question, String correctAnswer, List<String> allAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.allAnswers = allAnswers;
    }

    public String getQuestion()         { return question; }
    public String getCorrectAnswer()    { return correctAnswer; }
    public List<String> getAllAnswers()  { return allAnswers; }
}