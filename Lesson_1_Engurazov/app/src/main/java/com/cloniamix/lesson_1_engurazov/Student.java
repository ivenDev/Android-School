package com.cloniamix.lesson_1_engurazov;

public class Student {

    private long id;
    private String name;
    private String surname;
    private String grade;
    private String birthdayYear;

    public Student(String name, String surname, String grade, String birthdayYear) {
        id = System.currentTimeMillis();
        this.name = name;
        this.surname = surname;
        this.grade = grade;
        this.birthdayYear = birthdayYear;
    }

    //region getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGrade() {
        return grade;
    }

    public String getBirthdayYear() {
        return birthdayYear;
    }

    public String getAllData(){
        return id + "\n" + name + " " + surname + " " + grade + " " + birthdayYear;
    }
    //endregion
}
