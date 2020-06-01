package com.example.nordic.Repository;

import com.example.nordic.Model.Licence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LicenceRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String createLicence(Licence licence){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO licence \n" +
                "(licenceNo, firstName, lastName, birthDate, " +
                "country, issueDate, expiryDate, originator, " +
                "cpr, idContract)" +
                "VALUES( ?, ?, ?, ? , ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(licence.getLicenceNo()));
            ps.setString(2, licence.getFirstName());
            ps.setString(3, licence.getLastName());
            ps.setString(4, licence.getBirthDate());
            ps.setString(5, licence.getCountry());
            ps.setString(6, licence.getIssueDate());
            ps.setString(7, licence.getExpiryDate());
            ps.setString(8, licence.getOriginator());
            ps.setString(9, licence.getCpr());
            ps.setString(10,String.valueOf(licence.getIdContract()));
            return ps;
        }, keyHolder);

        return String.valueOf(keyHolder.getKey());

    }

    public List<Licence> readFromContractId(int id) {
        String sql = "SELECT * FROM licence\n" +
                "WHERE idContract = ?";
        RowMapper<Licence> rowMapper = new BeanPropertyRowMapper<>(Licence.class);
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    public Licence findLicenceById(int idLicence) {
        String sql = "SELECT * FROM licence WHERE idLicence = ?";
        RowMapper<Licence> rowMapper = new BeanPropertyRowMapper<>(Licence.class);
        Licence licence =  jdbcTemplate.queryForObject(sql, rowMapper, idLicence);
        return licence;
    }
}
