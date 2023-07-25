package com.example.diary.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

    public Diary(){

    }

    public Diary(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public long getId() {
        return id;
    }

    public boolean isPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return "Diary = [id"+id+", title= "+title+", description " + description +", published "+published+" ]";
    }
}
