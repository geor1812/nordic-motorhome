package com.example.nordic.Repository;

import com.example.nordic.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /***
     * Executes a query to the DB which returns all of the vehicles
     * @return
     */
    public List<Vehicle> readAll() {
        return null;
    }

    /***
     * Inserts a vehicle into the DB
     * @param vehicle the vehicle to be added
     */
    public void create(Vehicle vehicle) {
    }
}
