package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Server {
    static int a;

   

    
    public static void main(String[] args) {  //METODO MAIN
        server();

    }

    public static void server()  //INICIAMOS SERVER
    {
        try 
        {
            System.out.println("\n\nEsperando cliente....");
            ServerSocket s = new ServerSocket(3080); // INICIAMOS EL SOCKET EN EL PUERTO 3080
            Socket cr = s.accept(); //ACEPTAMOS LA CONEXION 
            cr.close(); //CERRAMOS EL SOCKET, SOLO SE HACE PARA ESPERAR LA CONEXION DEL USUARIO
            int e = 0;
            for(;;)  //INICIAMOS UN BUCLE PARA EL ENVIIO DE DATOS
            {
                
                File [] files = selectFiles(); //OBTENEMOS LOS ARCHIVOS QUE ENVIAREMOS AL USUARIO
                a = files.length; //CANTIDAD DE ARCHIVOS
                
                
                for(int i = 0; i<files.length; i++) //RECORREMOS EL ARRAY DE ARCHIVOS PARA ENVIARLOS
                {
                    Socket cl = s.accept();  //ACEPTAMOS CONEXION DE OTRO UN SOCKET PARA ENVIAR 1 ARCHIVO
                    File f = files[i];
                    String archivo = f.getAbsolutePath(); //OBTENEMOS DIRECCION
                    String nombre = f.getName(); //OBTENEMOS NOMBRE
                    long tam = f.length(); //OBTENEMOS TAMAÑO

                    DataOutputStream dos = new DataOutputStream(cl.getOutputStream());          //FLUJO DE DATOS PARA ESCRIBIR
                    DataInputStream dis = new DataInputStream(new FileInputStream(archivo));    //FLUJO DE DATOS PARA LEER
                    dos.writeInt(files.length); //ENVIAMOS LA CANTIDAD DE ARCHIVOS
                    dos.flush();
                    dos.writeUTF(nombre); //ENVIAMOS EL NOMBRE              
                    dos.flush();                       
                    dos.writeLong(tam);  //ENVIAMOS EL TAMAÑO
                    dos.flush();  

                    byte[] b = new byte[1024];         //GUARDAMOS EL CONTENIDO EN UN ARRAY TIPO BYTE
                    long enviados = 0;
                    int n;
                    while(enviados < tam)              //HACEMOS UN LOOP PARA ENVIAR EL CONTENIDO
                    {
                        n = dis.read(b);               
                        dos.write(b,0,n);          //MANDA EL CONTENIDO DEL ARCHIVO
                        dos.flush();
                        enviados = enviados + n;       
                        
                    }
                    System.out.println("Archivo " + nombre + " enviado" );  //ANUNCIAMOS QUE SE ENVIO EL ARCHIVO
                    cl.close();
                    dis.close();
                    dos.close();
                    e = e +1;  //CONTADOR PARA SABER CUANDO YA TERMINO DE ENVIAR LOS ARCHIVOS
                }
                if(e == files.length)   //SI YA TERMINA DE ENVIAR LOS ARCHIVOS CIERRA EL SOCKET Y MANDA A LLAMAR LA FUNCION QUE ENVIARA LOS DATOS DE LOS PRODUCTOS
                {
                    s.close(); //CERRAMOS SOCKET
                    sendData(files);  //FUNCION PARA ENVIAR DATOS DE PRODUCTOS OBTENIDOS EN UNA BASE DE DATOS
                    break; //TERMINAMOS LOOP
                    
                }

            }

            
        }catch (Exception e)
        {
            //System.out.println("Error: " + e);
        }
    }

    public static File[] selectFiles()   //FUNCION PARA SELECCIONAR LOS ARCHIVOS, REGRESA UN ARRAY DE TIPO FILE
    {
        System.out.println("\nSelecciona los archivos....\n");
        JFileChooser jf = new JFileChooser(); // SELECCIÓN DE ARCHIVOS
        jf.setMultiSelectionEnabled(true); //MULTIPLES ARCHIVOS
        int r = jf.showOpenDialog(null);
        if( r == JFileChooser.APPROVE_OPTION)
        {
            File[] files = jf.getSelectedFiles();
            return files;
            
        }
        return null;
        
        
    }

    public static void sendData(File[] files)
    {
        String[] nameProducts = new String[files.length]; // DEFINIMOS ARRAYS PARA GUARDAR LOS DATOS DE LOS PRODUCTOS
        Float[] priceProducts = new Float[files.length]; // PRECIO
        String[] descriptionProducts = new String[files.length]; // DESCRIPCION
        Integer[] existenceProducts = new Integer[files.length]; // EXISTENCIA
    
        try{
            ServerSocket s = new ServerSocket(3090); // INICIAMOS EL SOCKET EN EL PUERTO 3090
            for (int h =  0; h < files.length; h++) // HACEMOS UN LOOP PARA EXTRAER CADA DATO DE LOS PRODUCTOS
            {
                File f = files[h]; // RECORREMOS EL ARRAY QUE TIENE LOS ARCHIVOS
                String nameFile = f.getName(); // OBTENEMOS NOMBRE
                nameFile = nameFile.replace(".jpeg", ""); // AL NOMBRE LE QUIATMOS LA EXTENCION DEL ARCHIVO
                String sql = "SELECT * FROM Product WHERE Name = \'" + nameFile + "\';"; // CREAMOS EL QUERY PARA LA EXTRACCION DE DATOS EN SQL
                Connection cn = null; // VARIABLE DE CONEXION A BD
                PreparedStatement pst = null; // VARIABLE DE CONEXION A BD
                ResultSet rs = null; // VARIABLE DE CONEXION A BD
                cn = Conexion.conectar(); // VARIABLE DE CONEXION A BD
                pst = cn.prepareStatement(sql); // VARIABLE DE CONEXION A BD
                rs = pst.executeQuery(); // EJECUCIÓN DEL QUERY DE SQL
                if (rs.next()) 
                { // INGRESAMOS VALORES A LOS ARRAYS
                    nameProducts[h] = rs.getString(2); // NOMBRE
                    priceProducts[h] = rs.getFloat(3); // PRECIO
                    descriptionProducts[h] = rs.getString(4); // DESCRIPCION
                    existenceProducts[h] = rs.getInt(5); // EXISTENCIA
                }

            }
            for(;;)
            {
                Socket cl = s.accept(); //ACEPTAMOS CONEXION DEL USUARIO DE NUEVO
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream())); // INICIAMOS FLUJO DE DATOS
                pw.println(files.length); // ENVIAMOS LA LONGITUD DE ARCHIVOS SELECCIONADOS
                pw.flush();

                for (int j = 0; j < nameProducts.length; j++) // MANDAMOS LOS DATOS DE CADA ARRARY
                {
                    pw.println(nameProducts[j]); // NOMBRE
                    pw.flush();
                    pw.println(priceProducts[j]); // PRECIO
                    pw.flush();
                    pw.println(descriptionProducts[j]); // DESCRIPCIÓN
                    pw.flush();
                    pw.println(existenceProducts[j]); // EXISTENCIA
                    pw.flush();
    
                }
                pw.close(); // CERRAMOS FLUJO DE DATOS
                cl.close();
                s.close();
            }
            
        }catch(Exception e)
        {
            //System.out.println("Error: " + e);
        }

        server(); //YA CONCLUIDO EL ENVIO DE ARCHIVOS Y DATOS VOLVEMOS A MANDAR A LLAMAR LA FUNCION SERVIDOR PARA QUE OTRO USUARIO PUEDA CONECTARSE
        
        
    }


    
}

