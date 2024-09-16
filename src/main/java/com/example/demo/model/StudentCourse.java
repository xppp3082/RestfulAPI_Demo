package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
    @JsonProperty("student_id")
    private Long studentId;
    @JsonProperty("course_id")
    private int courseId;
    @JsonProperty("enrollment_date")
    private Timestamp enrollmentDate;
}
