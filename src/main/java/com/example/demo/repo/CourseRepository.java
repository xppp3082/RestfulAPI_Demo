package com.example.demo.repo;

import com.example.demo.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CourseRepository {
    private final JdbcTemplate jdbcTemplate;


    public CourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Course> getCourseRowMapper(){
        return new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Course course = new Course();
                course.setCourseId(rs.getLong("course_id"));
                course.setTitle(rs.getString("title"));
                return null;
            }
        };
    }
}
