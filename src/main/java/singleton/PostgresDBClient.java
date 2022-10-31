package singleton;

/*import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;*/
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PostgresDBClient {
    public static PostgresDBClient client = null;
    public static Connection postgresClient;

    private PostgresDBClient()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            postgresClient = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testbootcampdb","postgres", "123456");
        }
        catch( Exception  me)
        {
            me.printStackTrace();
            System.err.println(me.getClass().getName()+": "+me.getMessage());
            System.exit(0);
        }
    }
    public static PostgresDBClient getClient()
    {
        if (client == null){
            System.out.println("Primera vez");
            client = new PostgresDBClient();
        }

        return client;
    }

    public String getData(){
        try {
            Statement st = postgresClient.createStatement();
            ResultSet rs = st.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
        }catch(SQLException ex){
            Logger lgr = Logger.getLogger(PostgresDBClient.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
