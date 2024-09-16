package com.example.demo.repo;

import com.example.demo.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class StudentRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> getAllStudents(){
        String sql = """
                SELECT * FROM students
                """;
        return jdbcTemplate.query(sql,getStudentRowMapper());
    }

    public void insertStudent(Student student){
        String sql = """
                INSERT INTO students (first_name,last_name,email) VALUES (?)
                """;
        try{
            jdbcTemplate.update(sql,student.getFirstName(),student.getLastName(),student.getEmail());
        }catch (Exception e){
            log.error("error on inserting student");
        }
    }

    public RowMapper<Student> getStudentRowMapper(){
        return new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = new Student();
                student.setStudentId(rs.getLong("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                return null;
            }
        };
    }
}
