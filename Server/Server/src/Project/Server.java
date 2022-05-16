package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Server {
    static int a;

   

    
    public static void main(String[] args) {
        server();

    }

    public static void server()
    {
        try 
        {
            System.out.println("\n\nEsperando cliente....");
            ServerSocket s = new ServerSocket(3080); // INICIAMOS EL SOCKET EN EL PUERTO 3080
            Socket cr = s.accept();
            cr.close();
            int e = 0;
            for(;;)
            {
                
                File [] files = selectFiles();
                a = files.length;
                
                
                for(int i = 0; i<files.length; i++)
                {
                    Socket cl = s.accept();
                    File f = files[i];
                    String archivo = f.getAbsolutePath(); //Direccion
                    String nombre = f.getName(); //Nombre
                    long tam = f.length(); //Tamaño

                    DataOutputStream dos = new DataOutputStream(cl.getOutputStream());          //crea un flujo de datos para la salida de datos (escribir)
                    DataInputStream dis = new DataInputStream(new FileInputStream(archivo));    //crea un flujo de datos para leer el archivo    (leer)
                    dos.writeInt(files.length);
                    dos.flush();
                    dos.writeUTF(nombre);              //envia el nombre al servidor 
                    dos.flush();                       //limpia el flujo de datos
                    dos.writeLong(tam);                //envia el tamaño del archivo
                    dos.flush();  

                    byte[] b = new byte[1024];         //array donde se guardara lo leido del archivo
                    long enviados = 0;
                    int n;
                    while(enviados < tam)              //mientras los bytes enviados sean menor que el tamaño
                    {
                        n = dis.read(b);               //lee lo que tiene de contenido el archivo en bytes
                        dos.write(b,0,n);          //escribe lo que tiene el array 
                        dos.flush();
                        enviados = enviados + n;       
                        
                    }
                    System.out.println("Archivo " + nombre + " enviado" );
                    cl.close();
                    dis.close();
                    dos.close();
                    e = e +1;
                }
                if(e == files.length)
                {
                    s.close();
                    sendData(files);
                    break;
                    
                }

            }

            



            /*System.out.println("\n\nSelecciona los archivos: ");
            // *************INICIO DE SOCKET*************************
            ServerSocket s = new ServerSocket(3080); // INICIAMOS EL SOCKET EN EL PUERTO 3080



            JFileChooser jf = new JFileChooser(); // SELECCIÓN DE ARCHIVOS
            jf.setMultiSelectionEnabled(true);
            int r = jf.showOpenDialog(null);
            for (;;) 
            {
                
                if (r == JFileChooser.APPROVE_OPTION) 
                {
                    File[] files = jf.getSelectedFiles();
                    a = files.length;
                    int e = 0;
                    System.out.println("\nEsperando cliente....\n");
                    for(int i = 0; i<files.length; i++)
                    {
                        Socket cl = s.accept();
                        
                        //System.out.println("Conexión establecida desde" + cl.getInetAddress() + ": " + cl.getPort());
                        File f = files[i];
                        String archivo = f.getAbsolutePath(); //Direccion
                        String nombre = f.getName(); //Nombre
                        long tam = f.length(); //Tamaño

                        DataOutputStream dos = new DataOutputStream(cl.getOutputStream());          //crea un flujo de datos para la salida de datos (escribir)
                        DataInputStream dis = new DataInputStream(new FileInputStream(archivo));    //crea un flujo de datos para leer el archivo    (leer)
                        dos.writeInt(files.length);
                        dos.flush();
                        dos.writeUTF(nombre);              //envia el nombre al servidor 
                        dos.flush();                       //limpia el flujo de datos
                        dos.writeLong(tam);                //envia el tamaño del archivo
                        dos.flush();  

                        byte[] b = new byte[1024];         //array donde se guardara lo leido del archivo
                        long enviados = 0;
                        int n;
                        while(enviados < tam)              //mientras los bytes enviados sean menor que el tamaño
                        {
                            n = dis.read(b);               //lee lo que tiene de contenido el archivo en bytes
                            dos.write(b,0,n);          //escribe lo que tiene el array 
                            dos.flush();
                            enviados = enviados + n;       
                            
                        }
                        System.out.println("Archivo " + nombre + " enviado" );
                        cl.close();
                        dis.close();
                        dos.close();
                        e = e +1;
                    }
                    if(e == files.length)
                    {
                        s.close();
                        sendData(files, args);
                        break;
                        
                    }
                    
                }
                else 
                {
                    System.out.println("\n\nHubo un error con el archivo"); // ERROR AL SELECCIONAR EL ARCHIVO
                }
                
            }*/
        }catch (Exception e)
        {
            //System.out.println("Error: " + e);
        }
    }

    public static File[] selectFiles()
    {
        System.out.println("\nSelecciona los archivos....\n");
        JFileChooser jf = new JFileChooser(); // SELECCIÓN DE ARCHIVOS
        jf.setMultiSelectionEnabled(true);
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
            ServerSocket s = new ServerSocket(3090); // INICIAMOS EL SOCKET EN EL PUERTO 3080
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
                Socket cl = s.accept();
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

        server();
        
        
    }


    
}

