

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
            Socket cr = new Socket(host, pto); //SOCKET SOLO PARA ESTABLECER CONEXION
            cr.close();
            while (counter == 0)  //BUCLE PARA RECIBIR ARCHIVOS
            {
                
                Socket cl = new Socket(host, pto);  //POR CADA ARCHIVO RECIVIDO ABRIMOS UN SOCKET
                DataInputStream dis = new DataInputStream(cl.getInputStream()); //FLUJO PARA ESCIBIR EL ARCHIVOS
                byte[] b = new byte[1024];
                fileslength = dis.readInt(); //LEEMOS LOS ARCHIVOS
                int record = fileslength;   //IGULAMOS LA CANTIDAD DE ARCHIVOS A LA VARIABLE RECORD
                
                String nombre = dis.readUTF();   //RECIBIMOS NOMBRE
                System.out.println("Recibimos el archivo: " + nombre);  
                long tam = dis.readLong();  //RECIMOS TAMAÑO
                
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos = 0;
                int n;

                while(recibidos < tam)  //BUCLE PARA ESCRIBIR EL CONTENIO DE LOS ARCHIVOS
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
                    cl.close();
                    counter = 1;
                }
                c = c +1;
                cl.close();
            }
        } catch (Exception e) {
            //System.out.println("Error: " + e); // ERROR EN EL SOCKET
        }
        getData(host, pto);  //MANDAMOS A LLAMAR LA FUNCION PARA RECIBIR LOS DATOS DE LOS PRODUCTOS
    }


    public static void getData(String host, int pto)
    {
        
        System.out.println("\n");
        try{
            try {  //TRY PARA ESPERAR 2 SEGUNDOS ANTES DE INICIAR
                for (int i = 0; i < 2; i++) {  
                    Thread.sleep(1000);
                    System.out.println("Cargando....");
                }
            }catch(Exception e) {
                System.out.println("Error Timer: " + e);
            }


            
            Socket cl = new Socket(host, 3090);  //ESTABLECEMOS CONEXION EN SOCKET 3090
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

            Float[] priceProductsf = new Float[fileslength];  //BUCLES PARA CAMBIAR DE DATO EL ARRAY
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

            int counterCar = 0;  //CONTADOR PARA SABER EL NUMERO DE VECES QUE INGRESA UN USUARIO AL SISTEMA
            br2.close();
            cl.close();
            Menu m = new Menu(nameProducts, priceProductsf, descriptionProducts, existenceProductsi); //MANDAMOS A LLAMAR AL MENU Y LE ENVIAMOS LOS ARRAYS CON LOS DATOS
            m.showMenu(counterCar); //EJECUTAMOS LA FUNCION PARA QUE MUESTRE EL MENU


        }catch(Exception e){
            System.out.println("Error: " + e);
        }
        

    }
}



