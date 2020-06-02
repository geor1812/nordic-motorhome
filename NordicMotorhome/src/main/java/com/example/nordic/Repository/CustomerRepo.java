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
import java.util.List;

@Repository
public class CustomerRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Executes a query to the DB which creates a new customer, and gives them a generated key
     * @param customer customer to be inserted to DB
     */
    public void createCustomer(Customer customer){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String addressQuery = "INSERT INTO address\n" +
                "(addressDetails, city, country, state, zip)\n" +
                "VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(addressQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getAddressDetails());
            ps.setString(2, customer.getCity());
            ps.setString(3, customer.getCountry());
            ps.setString(4, customer.getState());
            ps.setString(5, customer.getZip());
            return ps;
        }, keyHolder);

        String insert = "INSERT INTO customer\n" +
                "(firstName, lastName, phoneNo, email, idAddress)\n" +
                "VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(insert, customer.getFirstName(), customer.getLastName(), customer.getPhoneNo(), customer.getEmail(),
                keyHolder.getKey());


    }

    /**
     * Executes a query to the DB which returns the latest customer id in the table
     * @return id of the latest customer
     */
    public int getLatestCustomerId(){
        String sqlQuery = "SELECT idCustomer FROM customer\n" +
                "ORDER BY idCustomer DESC LIMIT 1 ;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer customer =  jdbcTemplate.queryForObject(sqlQuery, rowMapper);
        int idCustomer = customer.getIdCustomer();
        return idCustomer;
    }

    /**
     * Executes a query to the DB which returns customer with a given id
     * @param id of the customer to return from the DB
     * @return customer with the given id
     */
    public Customer findCustomerById(int id){
        String sqlQuery = "SELECT * FROM customer\n" +
                "INNER JOIN address ON customer.idAddress = address.idAddress\n" +
                "WHERE idCustomer = ?;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = jdbcTemplate.queryForObject(sqlQuery, rowMapper, id);
        return c;
    }

    /**
     * Executes a query to the DB which returns all of the customers
     * @return list of customers, mapped by the rowmapper
     */
    public List<Customer> readAll() {
        String sql = "SELECT * FROM customer\n" +
                "INNER JOIN address ON customer.idAddress = address.idAddress \n" +
                "ORDER by idCustomer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
