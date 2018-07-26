package com.proj4.rakshithramesh.myapplication;

import java.io.Serializable;

/**
 * Created by rakshithramesh on 4/2/18.
 */

public class MajorListModel implements Serializable {

    private String title;
    private int id;
    private String college;
    private int classes;

    public MajorListModel(String title, String college) {
        this.title = title;
        this.college = college;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }
}
