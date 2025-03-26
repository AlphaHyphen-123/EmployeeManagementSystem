/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.ReceptionistPojo;
import emart.pojo.UserPojo;
import java.sql.Connection;          // Correct import
import java.sql.Statement;          // Correct import
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement; 
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class ReceptionistDAO {

    public static Map<String, String> getNonRegisteredReceptionists() throws SQLException {

        Connection con = DBConnection.getConnection();

        // Correct way to create statement
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery("SELECT empid, empname FROM employees WHERE job = 'Receptionist' AND empid NOT IN  (SELECT empid FROM users WHERE usertype = 'Receptionist')");
        
        HashMap<String,String> receptionistList = new HashMap<>();
        
        while(rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            receptionistList.put(id, name);
        }
        return receptionistList;
    }
    public static boolean addReceptionist(UserPojo user) throws SQLException {
        String query = "INSERT INTO users  VALUES (?, ?, ?, ?,?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getUserid());
            ps.setString(2, user.getEmpid());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUsertype());
            ps.setString(5, user.getUsername());

            int result = ps.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<ReceptionistPojo> getAllRegisteredDetails() throws SQLException{
        
        Connection con = DBConnection.getConnection();

        // Correct way to create statement
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery("SELECT USERS.empid,empname,userid,job,salary from users,employees where usertype = 'Receptionist' and users.empid = employees.empid");
        
        ArrayList<ReceptionistPojo> al = new ArrayList<>();
        
        while(rs.next()){
            ReceptionistPojo recep = new ReceptionistPojo();
            recep.setEmpid(rs.getString(1));
            recep.setEmpname(rs.getString(2));
            recep.setUserid(rs.getString(4));
            recep.setJob(rs.getString(4));
            recep.setSalary(rs.getDouble(5));
            al.add(recep);
            
        }
        return al;
    }
   public static Map<String,String> getAllRecetionistId() throws SQLException{
         Connection con = DBConnection.getConnection();

        // Correct way to create statement
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery("SELECT userid , username FROM users WHERE usertype = 'Receptionist' ORDER BY userid");
        
        HashMap<String,String> receptionistList = new HashMap<>();
        
        while(rs.next()){
            String id = rs.getString(1);
            String name = rs.getString(2);
            receptionistList.put(id, name);
        }
        return receptionistList;
   
   }
   public static boolean updatePassword(String userid, String pwd) throws SQLException{
        Connection con = DBConnection.getConnection();
        String query = ( "update users set password=? where userid=?");
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, pwd);
        ps.setString(2, userid);
        return ps.executeUpdate()==1;
   }
   public static List<String> getAllREceptionistUserId() throws SQLException{
        
         Connection con = DBConnection.getConnection();

        // Correct way to create statement
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery("SELECT userid FROM users WHERE usertype = 'Receptionist' ORDER BY userid");
        
        List<String> receptionistList = new ArrayList<>();
        
        while(rs.next()){
            String id = rs.getString(1);
//            String name = rs.getString(2);
            receptionistList.add(id);
        }
        return receptionistList;
   }
   public static boolean deleteReceptionist(String userid) throws SQLException{
                 Connection con = DBConnection.getConnection();
       String query = ( "delete from users where userid=?");
       PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, userid);
      
        return ps.executeUpdate()==1;
   }
        
}
