package com.example;


import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;


@Entity(name = "teacher")
@Table(name = "teacher")
public class Teacher {

    @Id
    @Column(name = "nip", nullable = false, unique = true)
    private String nip;

    @Column(name = "name")
    private String name;

    @Column(name = "classes", nullable = false)
    private String classes;

    @Column(name = "dob")
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dob;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name = "email")
    @Email
    private String email;

    @CollectionTable(name = "teacher_subjects", joinColumns = @JoinColumn(name = "teacher_nip"))
    @Column(name = "subjects")
    private String subjects;

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
