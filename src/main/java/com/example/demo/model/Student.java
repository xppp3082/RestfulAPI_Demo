package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @JsonProperty("student_id")
    private Long studentId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
}
