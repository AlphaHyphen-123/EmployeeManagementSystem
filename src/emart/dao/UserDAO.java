/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import emart.dbutil.DBConnection;
import emart.pojo.UserPojo;
import emart.pojo.UserProfile;
import java.sql.ResultSet;

/**
 *
 * @author hp
 */
public class UserDAO {

    public static boolean validateUser(UserPojo user) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from users where userid=? and password=? and usertype=?");
        ps.setString(1, user.getUserid());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUsertype());
        //query fire
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            //static method class ke name se call hote hn
            String username = rs.getString(5);
            UserProfile.setUsername(username);
            return true;
        }
        return false;
    }

    public static boolean isUserPresent(String empid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select 1 from users where empid=?");
        ps.setString(1, empid);
        ResultSet rs = ps.executeQuery();
        return rs.next();

    }
}
