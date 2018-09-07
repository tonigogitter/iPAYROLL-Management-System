/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iPAYROLL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Administrator
 */
public class CompensationRecordsAdapter {
    
    Connection connection;

    public CompensationRecordsAdapter(Connection conn, Boolean reset) throws SQLException {
        System.out.println("CompensationAdapter reached!");
        connection = conn;
        if (reset) {
            Statement stmt = connection.createStatement();
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                // We drop Matches first because it refrences the table Teams
                //    stmt.execute("DROP TABLE TimeWorkedSheets");
                //    stmt.execute("DROP TABLE Earnings");
                //    stmt.execute("DROP TABLE Deuctions");
                    stmt.execute("DROP TABLE CompensationRecords");  
                //    stmt.execute("DROP TABLE UserAccounts");            
               
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                // Create the table for compensation records/wages for each employee
                stmt.execute("CREATE TABLE CompensationRecords ("
                        + "ID VARCHAR(9) NOT NULL PRIMARY KEY, "
                        + "payDate DATE, "
                        + "payBegin DATE, "
                        + "payEnd DATE,"
                        + "netPay DOUBLE,"
                        + "payCheckNo VARCHAR(20),"
                        + "payAccountNo VARCHAR(20)"
                        + ")");
               // populateSampls();
            }
        }
    }

    private void populateSampls() throws SQLException {
        // Add some teams
        CompensationRecords oneRecord= new CompensationRecords();
        java.util.Date uDate = new java.util.Date();
    
        oneRecord.setID("123456789");
        oneRecord.setPayDate(new java.sql.Date(uDate.getTime()));
        oneRecord.setPayBegin(new java.sql.Date(uDate.getTime()));
        oneRecord.setPayEnd(new java.sql.Date(uDate.getTime()));
        oneRecord.setNetPay(111);
        oneRecord.setPayCheckNo("111 111 1111");
        oneRecord.setPayAccountNo("333 333 333");
        
        

        this.insertCompensationRecord(oneRecord);
    }

    public void insertCompensationRecord(CompensationRecords data) throws SQLException {
        System.out.println("Compensation insert reached!");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO CompensationRecords (ID, payDate, payBegin, payEnd, netPay, payCheckNo, payAccountNo) "
                + "VALUES ('"
                + data.getID() + "', '"
                + data.getPayDate() + "', '"
                + data.getPayBegin() + "', '"
                + data.getPayEnd() + "', '"
                + data.getNetPay() + "', '"
                + data.getPayCheckNo() + "', '"
                + data.getPayAccountNo() + "')"
               
        );
        System.out.println("Compensation insert ended!");
    }

//    public void updateCompRecords(CompensationRecords data) throws SQLException {
//        Statement stmt = connection.createStatement();
//        stmt.executeUpdate("UPDATE CompensationRecords "
//                + "SET payDate = '" + data.getPayDate() + "', "
//                + "PayBegin = '" + data.getPayBegin() + "', "
//                + "payEnd = '" + data.getPayEnd() + "', "
//                + "netPay = '" + data.getNetPay() + "', "
//                + "CheckNo = '" + data.getPayCheckNo() + "', "
//                + "AccountNo = '" + data.getPayAccountNo() + "', "
//                + "WHERE ID = "
//                + "'" + data.getID() + "'");
//    }
//
//        public void updateCompensation(CompensationRecords data) throws SQLException {
//        Statement stmt = connection.createStatement();
//        stmt.executeUpdate("UPDATE EmployeeProfiles "
//                + "SET payDate = " + data.getPayDate() + ", "
//                + "netPay = " + data.getNetPay() + ", "
//                + "CheckNo = '" + data.getPayCheckNo() + "', "
//                + "WHERE ID = "
//                + "'" + data.getID() + "'");
//    }
    

    public ObservableList<String> getEmployeeIDs() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT ID FROM EmployeeProfiles";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);

        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    public EmployeeProfiles findCompensationRecord(String ID) throws SQLException {
        CompensationRecords record = new CompensationRecords();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM CompensationRecords WHERE ID = '" + ID + "'";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            record.setID(rs.getString("ID"));
            record.setPayDate(rs.getDate("payDate"));
            record.setPayBegin(rs.getDate("payBegin"));
            record.setPayEnd(rs.getDate("payEnd"));
            record.setNetPay(rs.getDouble("netPay"));
            record.setPayCheckNo(rs.getString("payCheckNo"));
            record.setPayAccountNo(rs.getString("AccountNo"));
        }
        return record;
    }
}
    

