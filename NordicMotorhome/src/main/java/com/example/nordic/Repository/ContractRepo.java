package com.example.nordic.Repository;

import com.example.nordic.Model.Contract;
import com.example.nordic.Model.Vehicle;
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

    public List<Contract> readAll() {
        String sql = "SELECT * FROM contract\n" +
                "ORDER BY idContract DESC;";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return jdbcTemplate.query(sql, rowMapper);
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
