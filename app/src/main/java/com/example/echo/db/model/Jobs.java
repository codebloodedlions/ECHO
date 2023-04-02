package com.example.echo.db.model;

public class Jobs {
    public String job_name;
    public String job_description;
    public String job_location;
    public String job_id;

    @Override
    public String toString() {
        return "Jobs{" +
                "job_name='" + job_name + '\'' +
                ", job_description='" + job_description + '\'' +
                ", job_location='" + job_location + '\'' +
                ", job_id='" + job_id + '\'' +
                '}';
    }
}
