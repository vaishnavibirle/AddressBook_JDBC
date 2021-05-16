package com;

import com.company.AddressBookData;
import com.company.AddressBookException;
import com.company.AddressBookService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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
}
