package com.example.nordic.Repository;

import com.example.nordic.Model.Licence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LicenceRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Licence> readFromContractId(int id) {
        String sql = "SELECT * FROM lc\n" +
                "INNER JOIN licence ON licence.idLicence = lc.idLicence\n" +
                "WHERE idContract = ?";
        RowMapper<Licence> rowMapper = new BeanPropertyRowMapper<>(Licence.class);
        return jdbcTemplate.query(sql, rowMapper, id);
    }
}
