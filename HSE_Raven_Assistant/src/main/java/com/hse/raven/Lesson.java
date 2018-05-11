package com.hse.raven;

import java.util.HashMap;
import java.util.Map;

public class Lesson {
    private String discipline;
    private String auditorium;
    private String place;
    private String startLesson;
    private String lecturer;
    private Map<String, String> lecturerTitle;

    @Override
    public String toString() {
        return "В "+ getStartLesson() + " "+ getDiscipline()+ " в корпусе "+ getPlace()+" аудитория ."+ getAuditorium()+ ". "+ getLecturer() + "; ";
    }

    public Lesson(String discipline, String auditorium, String place, String startLesson, String lecturer) {
        lecturerTitle = new HashMap<>();
        lecturerTitle.put("ст.преп.", "старший преподаватель");
        lecturerTitle.put("проф.", "профессор");
        lecturerTitle.put("доц.","доцент");
        lecturerTitle.put("преп.", "преподаватель ");
        this.setDiscipline(discipline);
        this.setAuditorium(auditorium);
        this.setPlace(place);
        this.setStartLesson(startLesson);
        this.setLecturer(lecturer);
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public String getPlace() {
        return place;
    }

    public String getStartLesson() {
        return startLesson;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setDiscipline(String discipline) {
        discipline = discipline.replace("(рус)", " ");
        discipline = discipline.replace("(анг)", "на английском языке");
        this.discipline = discipline;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public void setPlace(String place) {
        switch(place)
        {
            case "Н.Н., Б. Печерская, 25/12":
                place = "улица Большая Печёрская дом 25 дробь 12.";
                break;
            case "Н.Н., Львовская, 1в":
                place = "улица Львовская дом 1в.";
                break;
            case "Н.Н., Сормовское ш., 30":
                place = "Сормовское шоссе дом 30.";
                break;
            case "Н.Н., Родионова 136":
                place = "улица Родионова дом 136.";
                break;
        }
        this.place = place;
    }

    public void setStartLesson(String startLesson) {
        startLesson.replace(":", " ");
        this.startLesson = startLesson;
    }

    public void setLecturer(String lecturer) {
        String[] split = lecturer.split(" ");
        String title = lecturerTitle.get(split[0]);
        split[0] = title;
        StringBuilder lect = new StringBuilder();
        for (String word:
             split) {
            lect.append(word);
            lect.append(" ");
        }
        this.lecturer = lect.toString();
    }
}
