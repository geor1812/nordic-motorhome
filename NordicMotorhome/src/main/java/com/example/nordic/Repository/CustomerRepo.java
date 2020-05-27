package com.example.nordic.Repository;


import com.example.nordic.Model.Customer;
import com.example.nordic.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CustomerRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createCustomer(Customer customer){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String modelQuery = "INSERT INTO address\n" +
                "(addressDetails, city, country, state, zip)\n" +
                "VALUES(?, ?, ?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(modelQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, String.valueOf(customer.getAddressDetails()));
            ps.setString(2, customer.getCity());
            ps.setString(2, customer.getCountry());
            ps.setString(2, customer.getState());
            ps.setString(3, customer.getZip());
            return ps;
        }, keyHolder);


        String insert = "INSERT INTO customer\n" +
                "(firstName, lastName, phoneNo, email, idAddress)\n" +
                "VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(insert, customer.getFirstName(), customer.getLastName(), customer.getPhoneNo(), customer.getEmail(),
                keyHolder.getKey());
    }

    public Customer findCustomerById(int id){
        String sqlQuery = "SELECT * FROM customer\n" +
                "INNER JOIN address ON customer.idAddress = address.idAddress\n" +
                "WHERE idCustomer = ?;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = jdbcTemplate.queryForObject(sqlQuery, rowMapper, id);
        return c;
    }

}
