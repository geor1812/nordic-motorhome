package com.example.nordic.Repository;

import com.example.nordic.Model.Contract;
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
public class ContractRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Executes a query to the DB which inserts a new contract
     * @param contract the contract to be inserted
     * @return key returned by jdbc template
     */
    public String createContract(Contract contract){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO contract \n" +
                "(idCustomer, idVehicle, startDate, endDate) \n" +
                "VALUES( ?, ?, ?, ? );";
        jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, contract.getIdCustomer());
            ps.setInt(2, contract.getIdVehicle());
            ps.setString(3, contract.getStartDate());
            ps.setString(4, contract.getEndDate());
            return ps;
        },keyHolder);

        String sql2 = "INSERT INTO accessories \n" +
                "( bedLinen, bikeRack, childSeat, grill, chair, tble, idContract) \n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql2, contract.getBedLinen(), contract.getBikeRack(), contract.getChildSeat(),
                contract.getGrill(), contract.getChair(), contract.getTble(), keyHolder.getKey());
        return String.valueOf(keyHolder.getKey());
    }

    /**
     * Executes a query to the DB which returns all of the contracts
     * @return contracts mapped by rowmapper
     */
    public List<Contract> readAll() {
        String sql = "SELECT * FROM contract\n" +
                "ORDER BY idContract DESC;";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * Executes a query to the DB which returns all of the contracts that contains search
     * @param search the term of which the query is executed on
     * @return list mapped by the rowmapper containing contracts with search term
     */
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

    /**
     * Executes a query to the DB which returns contract of given ID
     * @param id of the contract to search after
     * @return the contract of the given ID
     */
    public Contract findContractById(int id) {
        String sql = "SELECT * FROM contract\n" +
                "INNER JOIN accessories ON accessories.idContract = contract.idContract\n" +
                "WHERE contract.idContract = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract c = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return c;
    }

    /**
     * Executes a query to the DB which updates the Contract connected to the id with the information of the new contract
     * @param id id of the contract to be updated
     * @param c contract with the information to be updated.
     */
    public void updateContract(int id, Contract c) {
        Contract contract = findContractById(id);
        String sql = "UPDATE contract INNER JOIN accessories ON accessories.idContract = contract.idContract\n" +
                "SET contract.startDate = ?, contract.endDate = ?, accessories.bedLinen = ?,\n " +
                " accessories.bikeRack = ?,accessories.childSeat = ?, accessories.grill = ?, accessories.chair = ?,\n" +
                " accessories.tble = ? WHERE contract.idContract = ?";
        jdbcTemplate.update(sql, c.getStartDate(), c.getEndDate(), c.getBedLinen(), c.getBikeRack(), c.getChildSeat(),
                c.getGrill(), c.getChair(), c.getTble(), id);
    }

    /**
     * Executes a query to the DB which deletes a contract by id
     * @param idContract id of the contract to be removed in the DB
     */
    public void deleteContract(int idContract) {
        String sql = "DELETE FROM contract WHERE idContract = ?";
        jdbcTemplate.update(sql, idContract);
    }

    /**
     * Executes a query to the DB which moves the given contract into the archive
     * @param contract  contract to be archived
     * @param fee fee of the contract to be archived
     * @param odometerCharge price of the odometer charge
     * @param pickUpCharge price of the pickup charge
     * @param fuelCharge price of the fuel charge
     */
    public void archiveContract(Contract contract, double fee, double odometerCharge, double pickUpCharge, boolean fuelCharge) {
        String sql = "INSERT INTO archive\n" +
                "(startDate, endDate, idVehicle, idCustomer, totalCost, fuelCharge, pickUpCharge, odometerCharge)\n" +
                "VALUES\n" +
                "(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contract.getStartDate(), contract.getEndDate(), contract.getIdVehicle(),
                contract.getIdCustomer(), fee, fuelCharge, odometerCharge, pickUpCharge);
    }

    /**
     * Executes a query to the DB which inserts a contract and a licence to the LC table, thereby "connecting" them
     * @param idContract the contract to be connected
     * @param idLicence the licence to be connected
     */
    public void createLc(int idContract, int idLicence) {
        String sql = "INSERT INTO lc \n " +
                "(idContract, idLicence) \n" +
                "VALUES (?, ?);";
        jdbcTemplate.update(sql, idContract, idLicence);
    }

    /**
     * Executes a query to the DB which returns all of the vehicles
     * @return list of vehicles with dates attached
     */
    public List<Vehicle> readAllVehiclesWithDates(){
        String sql =
                "SELECT * FROM vehicle \n" +
                "JOIN contract ON vehicle.idVehicle = contract.idVehicle \n" +
                "JOIN model ON vehicle.idModel = model.idModel \n ";

        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
