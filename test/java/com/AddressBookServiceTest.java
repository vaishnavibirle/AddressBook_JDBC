package com;

import com.company.AddressBookData;
import com.company.AddressBookException;
import com.company.AddressBookService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
