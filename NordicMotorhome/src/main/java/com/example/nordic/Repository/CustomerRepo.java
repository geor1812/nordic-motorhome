package com.example.nordic.Repository;


import com.example.nordic.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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


    }
}
