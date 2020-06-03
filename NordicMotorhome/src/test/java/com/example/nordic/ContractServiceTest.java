/**
 * Created by Team
 */
package com.example.nordic;

import com.example.nordic.Service.ContractService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractServiceTest {
    @Autowired
    ContractService contractService;

    @Test
    public void accessoriesPriceTest() {
        assertEquals(12,contractService.accesoriesPricePerDay(1));
        assertEquals(13, contractService.accesoriesPricePerDay(2));
        assertEquals(14,contractService.accesoriesPricePerDay(3));
        assertEquals(3,contractService.accesoriesPricePerDay(4));
    }

    @Test
    public void totalPriceTest() {
        assertEquals(114,contractService.totalPrice(1));
        assertEquals(664,contractService.totalPrice(2));
        assertEquals(816,contractService.totalPrice(3));
        assertEquals(415,contractService.totalPrice(4));
    }

    @Test
    public void theNumberOfDaysShouldBePositive() {
        String date1 = "2020-06-09";
        String date2 = "2020-06-02";
        assertEquals(7,contractService.daysBetweenDays(date1,date2));
    }

    @Test
    public void theNumberOfDaysShouldBeNegative() {
        String date1 = "2020-05-25";
        String date2 = "2020-06-02";
        assertEquals(-8,contractService.daysBetweenDays(date1, date2));
    }

    @Test
    public void cancellationTest() {
        assertEquals(200.0,contractService.cancellation(1,"2020-03-20"),0.00);
        assertEquals(332.0,contractService.cancellation(4,"2020-04-01"),0.00);
    }

    @Test
    public void checkOutTest() {
        int endOdometer = 2600;
        int pickUpKm = 200;
        boolean fuelCharge = true;
        assertEquals(1959.0,contractService.checkout(5,endOdometer,pickUpKm,fuelCharge),0.00);
    }
}
