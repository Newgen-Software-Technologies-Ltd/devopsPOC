import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// Vulnerable class

class SQLInjection
{
   public String filename;
   public String filecontent;

  // Function called during deserialization

  public void readObject()
  {
	    Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		DataSource ds = null;

		try {
		InitialContext ctxt = new InitialContext();
		ds = (DataSource) ctxt.lookup("DBName");
		
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get username from parameters
		String username = "Admin";
		// Create a statement from database connection
		Statement statement = null;
		try {
			statement = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		// Create unsafe query by concatenating user defined data with query string
		String query = "SELECT secret FROM Users WHERE (username = '" + username + "' AND NOT role = 'admin')";
		// Execute query and return the results
		try {
			ResultSet result = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
}