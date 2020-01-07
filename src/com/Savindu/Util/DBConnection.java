/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savindu.Util;

import com.Savindu.Entity.Attendance;
import com.Savindu.Entity.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Savindu
 */
public class DBConnection {

//	private static final String URL = "jdbc:sqlserver://localhost:50042;DatabaseName=RIMS_Attendance";
//	
//	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	
//	private static final String USERNAME = "sa";
//	
//	private static final String PASSWORD = "12345";
//	
//	public static Connection connection = null;
    
    private static final String URL = "jdbc:ucanaccess://D:\\RAMS\\Database\\RAS.mdb";

    private static final String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    private static final String USERNAME = "Admin";

    private static final String PASSWORD = "ras258";

    public static Connection connection = null;

    public static Connection openConnection() {

//checking for the connection---------------------------
        if (connection != null) {

            return connection;

        } else {

            try {

                Class.forName(DRIVER);

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                if (connection != null) {
                    System.out.println("Successfully Connected to the Database!");

                }

            } catch (ClassNotFoundException e) {

                e.printStackTrace();

            } catch (SQLException e) {

                e.printStackTrace();

            }

            return connection;
        }
    }

    public static ArrayList<Attendance> getattL() {
        Connection connection = null;
        ResultSet rs = null;
        Statement st = null;
        Attendance att;
        ArrayList<Attendance> attL = new ArrayList<>();

        String sql = "SELECT ras_Users.pin as Employee_ID, ras_Users.UserName as User_Name, Min(clock) as Clock_In, Max(clock) as Clock_out, DATEDIFF('h', MIN(clock), MAX(clock)) - 8 AS [OT/Late_Covering_Hrs], CAST(clock AS DATE) as Date\n" +
"				  FROM ras_AttRecord, ras_Users, ras_AttTypeItem \n" +
"						where ras_Users.din = ras_AttRecord.din\n" +
"					GROUP BY CAST(clock AS DATE), ras_Users.pin, ras_Users.UserName";
//                     //"  order by usr.PIN";
//                     
//                   String sql = "select * from ras_AttRecord";

        try {
            connection = DBConnection.openConnection();
            System.out.println("Conn" + connection);
            st = connection.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {

                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }

        System.out.println(rs);
        return attL;
    }

    public static void main(String args[]) {
        System.out.println(getattL());
    }

}
