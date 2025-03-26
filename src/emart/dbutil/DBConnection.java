
package emart.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DBConnection {
    //static block java main sirf static chijon ka wright rakhta hai 
     private static Connection conn;
     static{
         try{
             Class.forName("oracle.jdbc.OracleDriver");
             String url = "jdbc:oracle:thin:@//SHIVAMSENPC:1521/XE";
            conn = DriverManager.getConnection(url, "system", "shivam");
            JOptionPane.showMessageDialog(null, "Connection Opened Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
         }
         catch(ClassNotFoundException ex){
             JOptionPane.showMessageDialog(null, "Error in Loading the Driver","Driver Error",JOptionPane.ERROR_MESSAGE);
             ex.printStackTrace();
             System.exit(1); ///termination
             ///
             
         }
            catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Error in Opening Connection","DB Error",JOptionPane.ERROR_MESSAGE);
             ex.printStackTrace();
             System.exit(1); ///termination
             ///
             
         }
         
         
     }
    // getter method
     //agar apke class ke andar nonstatc data nhi hai to method ko static banana padta hai 
    public static Connection getConnection() throws SQLException {
        // Check if the connection is closed before returning it
        if (conn == null || conn.isClosed()) {
            // If the connection is closed, try to re-establish it
            String url = "jdbc:oracle:thin:@//SHIVAMSENPC:1521/XE";
            conn = DriverManager.getConnection(url, "system", "shivam");
        }
        return conn;
    }
     public static void closeConnection(){
         try{
               if (conn != null && !conn.isClosed()) {
                conn.close();
                JOptionPane.showMessageDialog(null, "Connection Closed Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
         }
         catch(SQLException ex){
             JOptionPane.showMessageDialog(null, "Error in Closing the Connection","DB Error",JOptionPane.ERROR_MESSAGE);
             ex.printStackTrace();
             System.exit(1); ///termination
             ///
             
         }
     }
}
