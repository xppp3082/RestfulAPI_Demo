package com.example.demo.repo;

import com.example.demo.exception.CustomDatabaseException;
import com.example.demo.model.Student;
import com.example.demo.utils.DatabaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    public Student insertStudent(Student student) throws CustomDatabaseException {
        String sql = """
                INSERT INTO students (first_name,last_name,email) VALUES (?,?,?)
                """;

        // implement keyholder for holding the generate key
        KeyHolder keyHolder = new GeneratedKeyHolder();
//        try{
//            //jdbcTemplate.update(sql,student.getFirstName(),student.getLastName(),student.getEmail());
//            jdbcTemplate.update(con -> {
//                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, student.getFirstName());
//                ps.setString(2,student.getLastName());
//                ps.setString(3,student.getEmail());
//                return ps;
//            },keyHolder);
//
//            // get generated Id from keyholder
//            Number generatedId = keyHolder.getKey();
//            if(generatedId!=null){
//                student.setStudentId(generatedId.longValue());
//            }
//        }catch (Exception e){
//            log.error("error on inserting student: {}",e.getMessage());
//            throw e;
//        }

        PreparedStatementCreator psc = (Connection con) ->{
          PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
          ps.setString(1,student.getFirstName());
          ps.setString(2,student.getLastName());
          ps.setString(3,student.getEmail());
          return ps;
        };
try {
    DatabaseUtils.executeUpdate(jdbcTemplate,psc,keyHolder);
    Number generatedId = keyHolder.getKey();
    if (generatedId != null) {
        student.setStudentId(generatedId.longValue());
    } else {
        log.warn("自動生成的 student ID 為空");
    }
}catch (CustomDatabaseException e){
    throw e;
}


        return student;
    }



    public Student findStudentById(Long id){
        String sql = """
                SELECT * FROM students WHERE student_id = ?
                """;
        try{
            return jdbcTemplate.queryForObject(sql,getStudentRowMapper(),id);
        }catch (EmptyResultDataAccessException e){
            //log when there's no corresponding result/
            log.warn("Can't find student with id : {}",id);
            return null;
        }
    }

    public void updateStudent(Student student){
        String sql = """
                UPDATE students SET first_name = ? ,last_name = ?, email = ?
                WHERE student_id = ?
                """;
        try{
            jdbcTemplate.update(sql,student.getFirstName(),student.getLastName(),student.getEmail(),student.getEmail());
        }catch (Exception e){
            log.error("error on updating student: {}",e.getMessage());
        }
    }

    public void deleteStudentById(Long id){
        String sql = """
                DELETE FROM students WHERE id = ?
                """;
        try {
            jdbcTemplate.update(sql,id);
        }catch (Exception e){
            log.error("error on deleting the student with id: {}",id);
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
                return student;
            }
        };
    }
}
