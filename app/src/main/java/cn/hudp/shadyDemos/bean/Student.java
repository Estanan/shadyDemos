package cn.hudp.shadyDemos.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class Student {
    private String name;
    private List<Courses> courses;

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    public Student(String name, List<Courses> courses) {
        this.name = name;
        this.courses = courses;
    }

    public Student() {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
