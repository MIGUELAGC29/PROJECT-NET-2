

import java.net.*;
import java.io.*;

public class Client {
    static String host;
    static int pto;
    static int fileslength;
    public static void main(String[] args) {
        try {
            // **************LECTURA DE DATOS PARA LA CONEXION**************
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); // DEFINIMOS EL FLUJO PARA EL INGRESO DE DATOS
            System.out.printf("\n\nEscriba la direccion del servidor: "); // SERVIDOR
            host = br1.readLine();
            System.out.printf("\nEscriba el puerto: "); // PUERTO
            pto = Integer.parseInt(br1.readLine());
            System.out.println("\n\n");

            int counter = 0;
            int c = 0;
            while (counter == 0) 
            {
                
                Socket cl = new Socket(host, pto);
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];
                fileslength = dis.readInt();
                int record = fileslength;
                
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo: " + nombre);
                long tam = dis.readLong();
                
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos = 0;
                int n;

                while(recibidos < tam)
                {
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    recibidos = recibidos + n;
                    
                }
                dos.close();
                dis.close();
                cl.close();
                
                if(c == record)
                {
                    
                    counter = 1;
                }
                c = c +1;
            }
        } catch (Exception e) {
            //System.out.println("Error: " + e); // ERROR EN EL SOCKET
        }
        getData(host, pto);
    }


    public static void getData(String host, int pto)
    {
        
        System.out.println("\nQUIUBOLE");
        try{
            Socket cl = new Socket(host, pto);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            String fileslengthstr = br2.readLine(); // LEEMOS LOS DATOS ENVIADOS POR EL SERVIDOR
            int fileslength = Integer.parseInt(fileslengthstr); // LE HACEMOS UN CAMBIO DE STRING A ENTERO
            
            
            String[] nameProducts = new String[fileslength]; // DEFINIMOS ARRAYS PARA GUARDAR DATOS (NOMBRE)
            String[] priceProducts = new String[fileslength]; // (PRECIO)
            String[] descriptionProducts = new String[fileslength]; // (DESCRIPCIÓN)
            String[] existenceProducts = new String[fileslength]; // (EXISTENCIA)
            
            
            for (int j = 0; j < nameProducts.length; j++) 
            { // HACEMOS UN LOOP PARA GUARDAR LOS DATOS
                String data = br2.readLine(); // LEEMOS EL FLUJO DE DATOS
                nameProducts[j] = data; // GUARDAMOS NOMBRE
                data = br2.readLine();
                priceProducts[j] = data; // GUARDAMOS PRECIO
                data = br2.readLine();
                descriptionProducts[j] = data; // GUARDAMOS DESCRIPCION
                data = br2.readLine();
                existenceProducts[j] = data; // GURDAMOS LA EXISTENCIA

            }

            Float[] priceProductsf = new Float[fileslength];
            Integer[] existenceProductsi = new Integer[fileslength];
    
            for (int i = 0; i < fileslength; i++) 
            {
                priceProductsf[i] = Float.parseFloat(priceProducts[i]);
            }
    
            for (int i = 0; i < fileslength; i++)
            {
                existenceProductsi[i] = Integer.parseInt(existenceProducts[i]);
            }
    
            /*
                * ARRAYS FINALES
                * nameProducts String
                * priceProductsf Float
                * descriptionProducts String
                * existenceProducts Integer
            */

            int counterCar = 0;
            Menu m = new Menu(nameProducts, priceProductsf, descriptionProducts, existenceProductsi);
            m.showMenu(counterCar);


        }catch(Exception e){
            System.out.println("Error: ");
        }
        

    }
}






   /*if( c == record)
                {
                    cl.close();
                    System.out.println("\nEEEEMPEZAMOSSSSS");
                    Socket cr = new Socket(host, pto);
                     // DEFINIMOS LA LECTUR                                                                                    
                    //String fileslengthstr = br2.readLine(); // LEEMOS LOS DATOS ENVIADOS POR EL SERVIDOR
                    //int fileslength = Integer.parseInt(fileslengthstr); // LE HACEMOS UN CAMBIO DE STRING A ENTERO
                    
                    
                    int fileslength = record;
                    String[] nameProducts = new String[fileslength]; // DEFINIMOS ARRAYS PARA GUARDAR DATOS (NOMBRE)
                    String[] priceProducts = new String[fileslength]; // (PRECIO)
                    String[] descriptionProducts = new String[fileslength]; // (DESCRIPCIÓN)
                    String[] existenceProducts = new String[fileslength]; // (EXISTENCIA)
    
                    
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(cr.getInputStream()));
                    for (int j = 0; j < nameProducts.length; j++) { // HACEMOS UN LOOP PARA GUARDAR LOS DATOS
                        String data = br2.readLine(); // LEEMOS EL FLUJO DE DATOS
                        nameProducts[j] = data; // GUARDAMOS NOMBRE
                        data = br2.readLine();
                        priceProducts[j] = data; // GUARDAMOS PRECIO
                        data = br2.readLine();
                        descriptionProducts[j] = data; // GUARDAMOS DESCRIPCION
                        data = br2.readLine();
                        existenceProducts[j] = data; // GURDAMOS LA EXISTENCIA
    
                    }
                    for(String s : nameProducts) System.out.println("producto: " );
                    // **************LECTURA DE DATOS DE PRODUCTOS**************
    
                    /*Float[] priceProductsf = new Float[fileslength];
                    Integer[] existenceProductsi = new Integer[fileslength];
    
                    for (int i = 0; i < fileslength; i++) {
                        priceProductsf[i] = Float.parseFloat(priceProducts[i]);
                    }
    
                    for (int i = 0; i < fileslength; i++) {
                        existenceProductsi[i] = Integer.parseInt(existenceProducts[i]);
                    }
    
                    /*
                    * ARRAYS FINALES
                    * nameProducts String
                    * priceProductsf Float
                    * descriptionProducts String
                    * existenceProducts Integer
                    */
                    //System.out.println("TERMINAMOSSSSSSS");
                    //for(String s : nameProducts) System.out.println(s);
                    //br2.close();
                    //break;
    
            
                //c = c +1;
           //}   
            
                
                
                // **************MOSTRANDO MENU*******************
                /*int counterCar = 0;
                Menu m = new Menu(nameProducts, priceProductsf, descriptionProducts, existenceProductsi);
                m.showMenu(counterCar);*/
                // **************MOSTRANDO MENU*********************
                
