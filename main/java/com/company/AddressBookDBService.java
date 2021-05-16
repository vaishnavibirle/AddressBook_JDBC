package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookDataStatement;

    private AddressBookDBService() {
    }

    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    public List<AddressBookData> readData() throws AddressBookException {
        String sql = "SELECT * FROM addressBook; ";
        return this.getAddressBookDataUsingDB(sql);
    }

    private List<AddressBookData> getAddressBookDataUsingDB(String sql) throws AddressBookException {
        List<AddressBookData> addressBookList = new ArrayList<>();
        try (Connection connection = AddressBookConnection.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
        return addressBookList;
    }

    private List<AddressBookData> getAddressBookData(ResultSet resultSet) throws AddressBookException {
        List<AddressBookData> addressBookList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String addressType = resultSet.getString("AddressType");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                long zipCode = resultSet.getLong("ZipCode");
                String mobileNumber = resultSet.getString("MobileNumber");
                String emailId = resultSet.getString("EmailId");
                addressBookList.add(new AddressBookData(id, firstName, lastName, addressType, address, city, state,
                        zipCode, mobileNumber, emailId));
            }
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
        return addressBookList;
    }

    public List<AddressBookData> getAddressBookData(String firstName) throws AddressBookException {
        List<AddressBookData> addressBookList = null;
        if (this.addressBookDataStatement == null)
            this.prepareStatementForContactData();
        try {
            addressBookDataStatement.setString(1, firstName);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
        return addressBookList;
    }

    private void prepareStatementForContactData() throws AddressBookException {
        try {
            Connection connection = AddressBookConnection.getConnection();
            String sql = "SELECT * FROM addressBook WHERE FirstName = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
    }

    public int updateContactData(String name, String city, String state) throws AddressBookException {
        try (Connection connection = AddressBookConnection.getConnection();) {
            String sql = "UPDATE addressBook SET City = ?, State = ? WHERE FirstName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, state);
            preparedStatement.setString(3, name);
            int status = preparedStatement.executeUpdate();
            return status;
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
    }

}
