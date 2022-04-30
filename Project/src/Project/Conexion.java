package Project;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class Conexion 
{
private static Connection con;
    
    private static boolean conectado = false;

    public static Connection conectar()
    {
        
        try 
        {
            
            if (conectado == false)
            {               
                ResourceBundle rb = ResourceBundle.getBundle("jdbc");
                
                String driver = rb.getString("diver");
                String url = rb.getString("url");
                String pwd = rb.getString("pwd");
                String usr = rb.getString("usr");
                               
                Class.forName(driver);
            
                con = DriverManager.getConnection(url, usr, pwd); 
             
            }

            return con; 
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            JOptionPane.showMessageDialog(null,"Error al crear la conexión "+e.getMessage());
            
            throw new RuntimeException("Error al crear la conexión");
        }
    }
}