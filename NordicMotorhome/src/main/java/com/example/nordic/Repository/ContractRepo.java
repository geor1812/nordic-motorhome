package com.example.nordic.Repository;

import com.example.nordic.Model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createContract(Contract contract){}

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
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteContract(int idContract) {
        String sql = "DELETE FROM contract WHERE idContract = ?";
        jdbcTemplate.update(sql, idContract);
    }
}
