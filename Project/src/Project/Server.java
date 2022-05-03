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
                    //System.out.println(nameFile);
                    String sql = "SELECT * FROM Product WHERE Name = \'" + nameFile + "\';";
                    Connection cn = null;
                    PreparedStatement pst = null;
                    ResultSet rs = null;
                    cn = Conexion.conectar();
                    pst = cn.prepareStatement(sql);                        
                    rs = pst.executeQuery();
                    if(rs.next()){
                        nameProducts[i] = rs.getString(2);
                        priceProducts[i] = rs.getFloat(3);
                        descriptionProducts[i] = rs.getString(4);
                        existenceProducts[i] = rs.getInt(5);
                    }

                }
                
                //*************INICIO DE SOCKET*************************
                ServerSocket s = new ServerSocket(3080);
                System.out.println("Esperando cliente....");
                for(;;){
                    Socket cl = s.accept();
                    System.out.println("Conexión establecida desde" + cl.getInetAddress() + ": " + cl.getPort());
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                //*************INICIO DE SOCKET*************************    


                //*************ENVIO DE ARCHIVOS AL CLIENTE*************
                //*************ENVIO DE ARCHIVOS AL CLIENTE*************


                //*************ENVIO DE DATOS AL CLIENTE*************
                    pw.println(files.length);
                    pw.flush();
                    
                    for (int j = 0; j<nameProducts.length; j++)
                    {
            
                        pw.println(nameProducts[j]);
                        pw.flush();
                        pw.println(priceProducts[j]);
                        pw.flush();
                        pw.println(descriptionProducts[j]);
                        pw.flush();
                        pw.println(existenceProducts[j]);
                        pw.flush();
                    
                    }
                    
                    pw.close();
                //*************ENVIO DE DATOS AL CLIENTE*************

                }
                
                
            }
            else{
                System.out.println("\n\nHubo un error con el archivo");
            }
            

            
            
            
           //cl.close();
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
            
        }

    
        

     


        
            
            

        
        
    

    }
}