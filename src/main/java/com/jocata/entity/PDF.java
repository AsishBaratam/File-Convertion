package com.jocata.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "user_table1")
public class PDF implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_table1_seq")
    @SequenceGenerator(name = "user_table1_seq", sequenceName = "user_table1_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
