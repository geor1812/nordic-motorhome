package com.example.nordic.Repository;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ContractRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createContract(Contract contract){
        System.out.println(contract.getIdCustomer());
        System.out.println(contract.getIdVehicle());
        System.out.println(contract.getStartDate());
        System.out.println(contract.getEndDate());
        String sql = "INSERT INTO contract \n" +
                "(idCustomer, idVehicle, startDate, endDate) \n" +
                "VALUES( ?, ?, ?, ? );";
        jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, contract.getIdCustomer());
            ps.setInt(2, contract.getIdVehicle());
            ps.setString(3, contract.getStartDate());
            ps.setString(4, contract.getEndDate());


            return ps;
        });
    }

    public List<Contract> readAll() {
        String sql = "SELECT * FROM contract\n" +
                "ORDER BY idContract DESC;";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Contract> readSearch(String search) {
        String queryItem = "%" + search + "%";
        String sql = "SELECT * FROM contract\n" +
                "INNER JOIN vehicle ON contract.idVehicle = vehicle.idVehicle\n" +
                "INNER JOIN customer ON contract.idCustomer = customer.idCustomer\n" +
                "INNER JOIN model ON vehicle.idModel = model.idModel\n" +
                "WHERE contract.idContract LIKE ? OR \n" +
                "contract.startDate LIKE ? OR\n" +
                "contract.endDate LIKE ? OR \n" +
                "model.brand LIKE ? OR \n" +
                "model.modelType LIKE ? OR \n" +
                "customer.firstName LIKE ? OR \n" +
                "customer.lastName LIKE ?\n" +
                "ORDER BY contract.idContract DESC";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return jdbcTemplate.query(sql, rowMapper, queryItem, queryItem, queryItem,
                queryItem, queryItem, queryItem, queryItem);
    }

    public Contract findContractById(int id) {
        String sql = "SELECT * FROM contract\n" +
                "INNER JOIN accessories ON accessories.idContract = contract.idContract\n" +
                "WHERE contract.idContract = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract c = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return c;
    }

    public void updateContract(int id, Contract c) {
        Contract contract = findContractById(id);
        String sql = "UPDATE contract INNER JOIN accessories ON accessories.idContract = contract.idContract\n" +
                "SET contract.startDate = ?, contract.endDate = ?, accessories.bedLinen = ?,\n " +
                " accessories.bikeRack = ?,accessories.childSeat = ?, accessories.grill = ?, accessories.chair = ?,\n" +
                " accessories.tble = ? WHERE contract.idContract = ?";
        jdbcTemplate.update(sql, c.getStartDate(), c.getEndDate(), c.getBedLinen(), c.getBikeRack(), c.getChildSeat(),
                c.getGrill(), c.getChair(), c.getTble(), id);
    }


    public void deleteContract(int idContract) {
        String sql = "DELETE FROM contract WHERE idContract = ?";
        jdbcTemplate.update(sql, idContract);
    }
}
