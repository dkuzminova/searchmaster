package ru.sfedu.searchmaster;

import ru.sfedu.searchmaster.models.enums.Outcomes;

public class Outcome<T> {
    private T data;
    private String answer;
    private Outcomes status;

    public Outcome(Outcomes status,T data, String answer ) {
        this.status = status;
        this.answer = answer;
        this.data = data;
    }

    public Outcome(Outcomes status, T data) {
        this.status = status;
        this.data = data;
    }

    public Outcome(Outcomes status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Outcomes getStatus() {
        return status;
    }

    public void setStatus(Outcomes status) {
        this.status = status;
    }
}
