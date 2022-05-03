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
            JFileChooser jf = new JFileChooser();  //SELECCIÓN DE ARCHIVOS
            jf.setMultiSelectionEnabled(true);
            int r = jf.showOpenDialog(null);
            if ( r == JFileChooser.APPROVE_OPTION){
                File[] files = jf.getSelectedFiles();  //OBTENEMOS UN ARRAY CON LOS ARCHIVOS SELECCIONADOS


                String [] nameProducts = new String[files.length];  //DEFINIMOS ARRAYS PARA GUARDAR LOS DATOS DE LOS PRODUCTOS 
                Float [] priceProducts = new Float[files.length];  //PRECIO
                String [] descriptionProducts = new String[files.length];  //DESCRIPCION
                Integer [] existenceProducts = new Integer[files.length];  //EXISTENCIA


                for(int i = 0; i < files.length; i++)  //HACEMOS UN LOOP PARA EXTRAER CADA DATO DE LOS PRODUCTOS
                {
                    File f = files[i];  //RECORREMOS EL ARRAY QUE TIENE LOS ARCHIVOS
                    
                    String nameFile = f.getName(); //OBTENEMOS NOMBRE
                    nameFile = nameFile.replace(".jpeg", "");  //AL NOMBRE LE QUIATMOS LA EXTENCION DEL ARCHIVO
                    String sql = "SELECT * FROM Product WHERE Name = \'" + nameFile + "\';";  //CREAMOS EL QUERY PARA LA EXTRACCION DE DATOS EN SQL
                    Connection cn = null;  //VARIABLE DE CONEXION A BD
                    PreparedStatement pst = null; //VARIABLE DE CONEXION A BD
                    ResultSet rs = null; //VARIABLE DE CONEXION A BD
                    cn = Conexion.conectar(); //VARIABLE DE CONEXION A BD
                    pst = cn.prepareStatement(sql); //VARIABLE DE CONEXION A BD          
                    rs = pst.executeQuery();  //EJECUCIÓN DEL QUERY DE SQL
                    if(rs.next()){  //INGRESAMOS VALORES A LOS ARRAYS
                        nameProducts[i] = rs.getString(2); //NOMBRE
                        priceProducts[i] = rs.getFloat(3);  //PRECIO 
                        descriptionProducts[i] = rs.getString(4);  //DESCRIPCION
                        existenceProducts[i] = rs.getInt(5);  //EXISTENCIA
                    }

                }
                
                //*************INICIO DE SOCKET*************************
                ServerSocket s = new ServerSocket(3080);  //INICIAMOS EL SOCKET EN EL PUERTO 3080
                System.out.println("Esperando cliente....");
                for(;;){
                    Socket cl = s.accept();
                    System.out.println("Conexión establecida desde" + cl.getInetAddress() + ": " + cl.getPort());
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));  //INICIAMOS FLUJO DE DATOS
                //*************INICIO DE SOCKET*************************    


                //*************ENVIO DE ARCHIVOS AL CLIENTE*************
                //*************ENVIO DE ARCHIVOS AL CLIENTE*************


                //*************ENVIO DE DATOS AL CLIENTE*************
                    pw.println(files.length);  //ENVIAMOS LA LONGITUD DE ARCHIVOS SELECCIONADOS
                    pw.flush();
                    
                    for (int j = 0; j<nameProducts.length; j++)  //MANDAMOS LOS DATOS DE CADA ARRARY
                    {
            
                        pw.println(nameProducts[j]);  //NOMBRE
                        pw.flush();
                        pw.println(priceProducts[j]);  //PRECIO
                        pw.flush();
                        pw.println(descriptionProducts[j]);   //DESCRIPCIÓN
                        pw.flush();
                        pw.println(existenceProducts[j]);  //EXISTENCIA
                        pw.flush();
                    
                    }
                    
                    pw.close();  //CERRAMOS FLUJO DE DATOS
                //*************ENVIO DE DATOS AL CLIENTE*************

                }
                
                
            }
            else{
                System.out.println("\n\nHubo un error con el archivo");  //ERROR AL SELECCIONAR EL ARCHIVO
            }
            

            
            
            
           //cl.close();
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
            
        }

    
        

     


        
            
            

        
        
    

    }
}