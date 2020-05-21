package com.example.nordic.Repository;

import com.example.nordic.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VehicleRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Executes a query to the DB which returns all of the vehicles
     * @return
     */
    public List<Vehicle> readAll() {
        return null;
    }

    /**
     * Inserts a vehicle into the DB
     * @param vehicle the vehicle to be added
     */
    public void create(Vehicle vehicle) {
        /*
        To deal with the vehicle being split into two tables in the database
        we use a KeyHolder to retrieve the primary key, which is auto-incremented,
        of the "model" we just added to the database
         */
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql1 = "INSERT INTO model\n" +
                "(brand, modelType, fuelType, noBeds, pricePerDay)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getModelType());
            ps.setString(3, vehicle.getFuelType());
            ps.setString(4, String.valueOf(vehicle.getNoBeds()));
            ps.setString(5, String.valueOf(vehicle.getPricePerDay()));
            return ps;
        }, keyHolder);

        /*
        Now inserting into the vehicle table
         */
        String sql2 = "INSERT INTO vehicle\n" +
                "(regNo, regDate, odometer, repairStatus, idModel)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql2, vehicle.getRegNo(), vehicle.getRegDate(), vehicle.getOdometer(),
                vehicle.getRepairStatus(), keyHolder.getKey());
    }
}
