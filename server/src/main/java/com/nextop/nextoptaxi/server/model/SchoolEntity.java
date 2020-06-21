package com.nextop.nextoptaxi.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity(name="school")
public class SchoolEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public int totalStudent;
    public int totalTeacher;
}
