package com.example.nordic.Repository;

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
public class VehicleRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Executes a query to the DB which returns all of the vehicles
     * @return result set
     */
    public List<Vehicle> readAll() {
        String sql = "SELECT * FROM vehicle\n" +
                "INNER JOIN model ON vehicle.idModel = model.idModel\n" +
                "ORDER BY idVehicle;";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Executes a query to the DB which returns all of the vehicles that
     * contain the search term in any of their attributes
     * @param search the search term
     * @return List<Vehicle> result set
     */
    public List<Vehicle> readSearch(String search) {
        String queryItem = "%" + search + "%";
        String sql = "SELECT * FROM vehicle\n" +
                "INNER JOIN model ON vehicle.idModel = model.idModel\n" +
                "WHERE vehicle.idVehicle LIKE ? OR\n" +
                "vehicle.regNo LIKE ? OR\n" +
                "vehicle.regDate LIKE ? OR\n" +
                "model.brand LIKE ? OR\n" +
                "model.modelType LIKE ? OR\n" +
                "model.noBeds LIKE ?\n" +
                "ORDER BY vehicle.idVehicle\n";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return jdbcTemplate.query(sql, rowMapper, queryItem, queryItem,
                queryItem, queryItem, queryItem, queryItem);
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

    /**
     * Queries the DB for a vehicle with the same id as the parameter
     * @param id the id of the vehicle to be returned
     * @return the vehicle retrieved from the DB
     */
    public Vehicle findVehicleById(int id){
        String sqlQuery = "SELECT vehicle.idVehicle, vehicle.regNo, vehicle.regDate, vehicle.odometer, vehicle.repairStatus, vehicle.idModel,\n" +
                "model.brand, model.modelType, model.fuelType, model.noBeds, model.pricePerDay\n" +
                "FROM vehicle\n" +
                "INNER JOIN model ON vehicle.idModel = model.idModel\n" +
                "WHERE idVehicle = ?";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        Vehicle v = jdbcTemplate.queryForObject(sqlQuery, rowMapper, id);
        return v;
    }

    /**
     * Updates a vehicle from the DB
     * @param id the id of the vehicle
     * @param v a Vehicle object containing the updated information
     */
    public void updateVehicle(int id, Vehicle v) {
        Vehicle vehicle = findVehicleById(id);
        String sqlQuery1 = "UPDATE vehicle SET odometer = ?, repairStatus = ? WHERE idVehicle = ?";
        //String sqlQuery2 = "UPDATE model SET pricePerDay = ? WHERE idModel = ?";
        jdbcTemplate.update(sqlQuery1, v.getOdometer(), v.getRepairStatus(), id);
        //jdbcTemplate.update(sqlQuery2, v.getPricePerDay(), v.getIdModel());
    }

    /**
     * Deletes a vehicle from the DB based on id
     * @param idVehicle the id of the vehicle to be deleted
     */
    public void deleteVehicle(int idVehicle) {
        String sql = "DELETE FROM vehicle WHERE idVehicle = ?";
        return jdbcTemplate.update(sql, idVehicle) < 0;
    }
}
