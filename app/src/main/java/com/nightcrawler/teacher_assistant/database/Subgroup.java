package com.nightcrawler.teacher_assistant.database;

public enum Subgroup {
    First(1),
    Second(2);
    public final int number;
    Subgroup(int number) {
        this.number = number;
    }
}
