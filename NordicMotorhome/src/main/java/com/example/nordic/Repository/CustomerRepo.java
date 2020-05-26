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
