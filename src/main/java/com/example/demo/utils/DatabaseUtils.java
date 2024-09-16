package com.example.demo.utils;

import com.example.demo.exception.CustomDatabaseException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

public class DatabaseUtils {
    public static void executeUpdate(JdbcTemplate jdbcTemplate, PreparedStatementCreator psc, KeyHolder keyHolder) {
        try {
            jdbcTemplate.update(psc, keyHolder);
        } catch (DataIntegrityViolationException e) {
            throw new CustomDatabaseException("資料庫約束違反，請檢查輸入資料。", e);
        } catch (BadSqlGrammarException e) {
            throw new CustomDatabaseException("SQL 語法錯誤，請聯絡管理員。", e);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("資料庫連接失敗，請稍後再試。", e);
        } catch (Exception e) {
            throw new CustomDatabaseException("發生未知錯誤，請聯絡管理員。", e);
        }
    }
}
