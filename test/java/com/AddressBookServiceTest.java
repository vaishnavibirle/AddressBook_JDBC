package com;

import com.company.AddressBookData;
import com.company.AddressBookException;
import com.company.AddressBookService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AddressBookServiceTest {

    static AddressBookService addressBookService;

    @BeforeClass
    public static void AddressBookServiceObj() {
        addressBookService = new AddressBookService();
    }

    @Test
    public void givenAddressBookContactsInDB_WhenRetrieved_ShouldMatchContactsCount() throws AddressBookException {
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        Assertions.assertEquals(3, addressBookData.size());
    }

    @Test
    public void givenNewCityAndStateForContact_WhenUpdated_ShouldMatch() throws AddressBookException {
        addressBookService.updateContactCityAndState("Sakshi","Pune", "Maharashtra");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Sakshi");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldmatchEmployeeCount() throws AddressBookException {
        addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<AddressBookData> employeePayrollData = addressBookService.readAddressBookForDateRange(AddressBookService.IOService.DB_IO,
                startDate, endDate);
        Assertions.assertEquals(3, employeePayrollData.size());
    }

    @Test
    public void givenAddressBookDB_WhenRetrievedCountByState_ShouldReturnCountGroupedByState()
            throws AddressBookException {
        Map<String, Integer> count = addressBookService.countContactsByState(AddressBookService.IOService.DB_IO, "State");
        Assertions.assertEquals(1, count.get("AndhraPradesh"), 0);
        Assertions.assertEquals(1, count.get("Telangana"), 0);
        Assertions.assertEquals(1, count.get("Karnataka"), 0);
    }

    @Test
    public void givenAddressBookDB_WhenRetrievedCountByCity_ShouldReturnCountGroupedByCity()
            throws AddressBookException {
        Map<String, Integer> count = addressBookService.countContactsByCity(AddressBookService.IOService.DB_IO, "City");
        Assertions.assertEquals(1, count.get("Ponnur"), 0);
        Assertions.assertEquals(1, count.get("Mysore"), 0);
        Assertions.assertEquals(1, count.get("Hyderabad"), 0);
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
        addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.addContactToAddressBook(4, "Anudeep", "Betha", Date.valueOf("2020-05-12"), "Office",
                "Electronic City", "Bangalore", "Karnataka", 536429, "8796589899", "deepu123@gmail.com");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Anudeep");
        Assertions.assertTrue(result);
    }
}
