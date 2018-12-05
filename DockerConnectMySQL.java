import java.sql.*;

public class DockerConnectMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/LW";

   static final String USER = "gagarin";
   static final String PASS = "123qwe";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   Boolean baseExist = false;
   String sql;
  
   try{
      Class.forName("com.mysql.cj.jdbc.Driver");
	   
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);	   
      	   
      System.out.println("Check if table in base exist");
      DatabaseMetaData md = conn.getMetaData();
      ResultSet rs = md.getTables(null, null, "Persons", null);
      while (rs.next()) {
            System.out.println("Table Exist");
	    baseExist = true;
      }
      rs = null;  
      if(!baseExist){
      	System.out.println("Creating Table");
      	stmt = conn.createStatement();
      	sql = "CREATE TABLE Persons (PersonID int, LastName varchar(255), FirstName varchar(255), City varchar(255) )";
      	stmt.executeUpdate(sql);
      	stmt = null;
      }
	   
      stmt = conn.createStatement();
      System.out.println("Inserting Data to Table");
      sql = "INSERT INTO Persons (PersonID, LastName, FirstName,  City) VALUES (1, 'Kowalski', 'Jan', 'Lublin'), (2, 'Nowak', 'Aga', 'Lublin'), (3, 'Martyniuk', 'Zenon', 'Lublin')";
      stmt.executeUpdate(sql);	 
      stmt = null;
	   
      stmt = conn.createStatement();
      sql = "SELECT PersonID, FirstName, LastName, City FROM Persons";
      rs = stmt.executeQuery(sql);

      while(rs.next()){
         int id  = rs.getInt("PersonID");
         String first = rs.getString("FirstName");
         String last = rs.getString("LastName");
	 String city = rs.getString("City");

         System.out.println("ID: " + id);
         System.out.println(", First: " + first);
         System.out.println(", Last: " + last);
	 System.out.println(", City: " + city);
      }
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
 }
}
