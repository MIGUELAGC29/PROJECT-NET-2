package Project;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Server
{
    

    public static void main(String[] args) {
        
        try
        {
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true);
            int r = jf.showOpenDialog(null);
            if ( r == JFileChooser.APPROVE_OPTION){
                File[] files = jf.getSelectedFiles();


                String [] nameProducts = new String[files.length];
                Float [] priceProducts = new Float[files.length];
                String [] descriptionProducts = new String[files.length];
                Integer [] existenceProducts = new Integer[files.length];


                for(int i = 0; i < files.length; i++)
                {
                    File f = files[i];
                    String nameFile = f.getName(); //Nombre
                    nameFile = nameFile.replace(".jpeg", "");
                    String sql = "SELECT * FROM Product";
                    Connection cn = null;
                    PreparedStatement pst = null;
                    ResultSet rs = null;
                    cn = Conexion.conectar();
                    pst = cn.prepareStatement(sql);                        
                    rs = pst.executeQuery();
                    while(rs.next())
                    {
                        nameProducts[i] = rs.getString(2);
                        priceProducts[i] = rs.getFloat(3);
                        descriptionProducts[i] = rs.getString(4);
                        existenceProducts[i] = rs.getInt(5);

                    }




                    //1.- agregar el nombre a un array
                    //2.- buscar los datos en la base de datos y obtenerlos
                    //3.- guardar cada dato de cada producto en arrays
                    //4.- recorrer cada array y mandarselo al cliente

    
                }

                for (String s : descriptionProducts){
                    System.out.println(s);
                }
               
                
    
            }
            else{
                System.out.println("\n\nHubo un error con el archivo");
            }
            

            
            
            
           
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
            
        }

    
        

     


        
            
            

        
        
    

    }
}