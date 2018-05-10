package com.hse.raven;

import java.util.ArrayList;
import java.util.Map;

public class ScheduleModel {
    private String dayOftheWeek;
    private ArrayList<Lesson> lessons;
    private String[] days = {"Понедельник",  "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};


    public ScheduleModel(int dayOftheWeek, ArrayList<Lesson> lessons) {
        this.dayOftheWeek = days[dayOftheWeek];
        this.lessons = lessons;
    }

    public String getDayOftheWeek() {
        return dayOftheWeek;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setDayOftheWeek(int dayOftheWeek) {

        this.dayOftheWeek = days[dayOftheWeek];
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(dayOftheWeek);
        if (lessons.isEmpty()){
            output.append("занятий нет");
            return output.toString();
        }
        for (Lesson lesson : lessons) {
            output.append(" ");
            output.append(lesson.toString());
        }
        return output.toString();
    }
}
