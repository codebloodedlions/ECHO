package com.example.echo.db.model;

public class Candidate {
    public String name;
    public String gender;
    public String email;
    public String phone;
    public String potential_job;
    public String location;

    @Override
    public String toString() {
        return "Candidate{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", potential_job='" + potential_job + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
