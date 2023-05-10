package me.ding.expressionspbt.service;

public interface ExpSer {
    String getQuestion();

    String checkAnswer(String question, String input);

    String getAskedMap();

    String pass(String question);

    String strange(String question);

    String reset();
}
