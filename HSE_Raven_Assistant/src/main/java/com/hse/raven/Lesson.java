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
        return "В "+ getStartLesson() + " "+ getDiscipline()+ " в корпусе "+ getPlace()+" аудитория "+ getAuditorium()+ " преподаватель "+ getLecturer();
    }

    public Lesson(String discipline, String auditorium, String place, String startLesson, String lecturer) {
        lecturerTitle = new HashMap<>();
        lecturerTitle.put("ст.преп.", "старший преподаватель");
        lecturerTitle.put("проф.", "профессор");
        lecturerTitle.put("доц.","доцент");
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
        discipline.replace("(рус)", " ");
        discipline.replace("(анг)", " на английском языке ");
        this.discipline = discipline;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public void setPlace(String place) {
        place.replace("H.H.,", "");
        place.replace(',', ' ');
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
        String lect = new String();

        for (String word:
             split) {
            lect.concat(word);
            lect.concat(" ");
        }
        this.lecturer = lect;
    }
}
