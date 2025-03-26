/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.EmployeePOJO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class EmployeeDAO {

    public static String getNextEmpId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT MAX(empid) FROM employees");
        rs.next();
        String empid = rs.getString(1);
        int empno = Integer.parseInt(empid.substring(1));
        empno = empno + 1;
        return "E" + empno;
    }

    public static boolean addEmployee(EmployeePOJO emp) throws SQLException {
        Connection conn = DBConnection.getConnection();
        //query dynamic hai to preparedstatement laga agar static ho to sirf statement
        PreparedStatement ps = conn.prepareStatement("Insert into employees values(?,?,?,?)");
        ps.setString(1, emp.getEmpid());
        ps.setString(2, emp.getEmpname());
        ps.setString(3, emp.getJob());
        ps.setDouble(4, emp.getSalary());
        //insert karne ke liye record ko
        int result = ps.executeUpdate();
        return result == 1;

    }

    public static List<EmployeePOJO> getAllEmployee() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM employees order by empid");
        ArrayList<EmployeePOJO> empList = new ArrayList<>();
        while (rs.next()) {
            EmployeePOJO emp = new EmployeePOJO();
            emp.setEmpid(rs.getString(1));
            emp.setEmpname(rs.getString(2));
            emp.setJob(rs.getString(3));
            emp.setSalary(rs.getDouble(4));
            empList.add(emp);
        }
        return empList;
    }

    public static List<String> getAllEpId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT empid FROM employees order by empid");
        ArrayList<String> allId = new ArrayList<String>();
        while (rs.next()) {
            allId.add(rs.getString(1));

        }
        return allId;
    }

    public static EmployeePOJO findEmpById(String empid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        //query dynamic hai to preparedstatement laga agar static ho to sirf statement
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM employees where empid=?");
        ps.setString(1, empid);
        ResultSet rs = ps.executeQuery();
        EmployeePOJO e = null;
        if (rs.next()) {
            e = new EmployeePOJO();
            e.setEmpid(rs.getString(1));
            e.setEmpname(rs.getString(2));
            e.setJob(rs.getString(3));
            e.setSalary(rs.getDouble(4));
        }

        return e;

    }

    public static boolean updateEmployee(EmployeePOJO e) throws SQLException {
        Connection conn = DBConnection.getConnection();
        //query dynamic hai to preparedstatement laga agar static ho to sirf statement
        PreparedStatement ps = conn.prepareStatement("UPDATE employees SET empname = ?, job = ?, salary = ? WHERE empid = ?");
        ps.setString(1, e.getEmpname());
        ps.setString(2, e.getJob());
        ps.setDouble(3, e.getSalary());
        ps.setString(4, e.getEmpid());
        int x = ps.executeUpdate();
        if (x == 0) {
            return false;
        } else {
            boolean result = UserDAO.isUserPresent(e.getEmpid());
            if (result == false) {
                return true;
            }

            ps = conn.prepareStatement("Update users set empname=?,usertype=?,where empid=?");
            ps.setString(1, e.getEmpname());
            ps.setString(2, e.getJob());
            ps.setString(3, e.getEmpid());
            int y = ps.executeUpdate();
            return y == 1;
        }
    }
    public static boolean deleteEmployee(String empid) throws SQLException{
        Connection conn = DBConnection.getConnection();
        //query dynamic hai to preparedstatement laga agar static ho to sirf statement
        PreparedStatement ps = conn.prepareStatement("delete from employees WHERE empid = ?");
        ps.setString(1,empid);
        int x = ps.executeUpdate();
        return x==1;
    }

}
