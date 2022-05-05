package Project;

import java.net.*;
import java.io.*;



public class Client {
    public static void main(String[] args) {
        try{
            //**************LECTURA DE DATOS PARA LA CONEXION**************
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); //DEFINIMOS EL FLUJO PARA EL INGRESO DE DATOS
            System.out.printf("Escriba la direccion del servidor: ");  //SERVIDOR
            String host = br1.readLine();
            System.out.printf("\n\nEscriba el puerto: ");  //PUERTO
            int pto = Integer.parseInt(br1.readLine());
            Socket cl = new Socket(host,pto);  //INICIAMOS SOCKET
            //**************LECTURA DE DATOS PARA LA CONEXION************** 

            //**************LECTURA DE ARCHIVOS************** 
            //**************LECTURA DE ARCHIVOS************** 


            //**************LECTURA DE DATOS DE PRODUCTO************** 

            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));  //DEFINIMOS LA LECTURA DEL FLUJO
            String fileslengthstr = br2.readLine(); //LEEMOS LOS DATOS ENVIADOS POR EL SERVIDOR
            int fileslength = Integer.parseInt(fileslengthstr);  //LE HACEMOS UN CAMBIO DE STRING A ENTERO
            
    

            String [] nameProducts = new String[fileslength];  //DEFINIMOS ARRAYS PARA GUARDAR DATOS (NOMBRE)
            String [] priceProducts = new String[fileslength];  //(PRECIO)
            String [] descriptionProducts = new String[fileslength];  //(DESCRIPCIÓN)
            String [] existenceProducts = new String[fileslength];  //(EXISTENCIA)
            

            for(int j = 0; j<nameProducts.length; j++){  //HACEMOS UN LOOP PARA GUARDAR LOS DATOS
                String data = br2.readLine();  //LEEMOS EL FLUJO DE DATOS
                nameProducts[j] = data; //GUARDAMOS NOMBRE
                data = br2.readLine();
                priceProducts[j] = data;  //GUARDAMOS PRECIO
                data = br2.readLine();
                descriptionProducts[j] = data;  //GUARDAMOS DESCRIPCION
                data = br2.readLine();
                existenceProducts[j] = data;  //GURDAMOS LA EXISTENCIA

            } 

            
            //**************LECTURA DE DATOS DE PRODUCTOS************** 

            Float [] priceProductsf = new Float[fileslength];
            Integer [] existenceProductsi = new Integer[fileslength];

            for (int i = 0; i<fileslength; i++)
            {
                priceProductsf[i] = Float.parseFloat(priceProducts[i]);
            }

            for (int i = 0; i<fileslength; i++)
            {
                existenceProductsi[i] = Integer.parseInt(existenceProducts[i]);
            }

            /*    ARRAYS FINALES
                nameProducts          String
                priceProductsf        Float
                descriptionProducts   String
                existenceProducts     Integer
            */

            //**************MOSTRANDO MENU*********************
            int counterCar = 0;
            Menu m = new Menu(nameProducts, priceProductsf, descriptionProducts, existenceProductsi);
            m.showMenu(counterCar);
            //**************MOSTRANDO MENU*********************
                



        br1.close();  //CERRAMOS FLUJO
        br2.close();  //CERRAMOS FLUJO
        cl.close();  
        }catch (Exception e){
            System.out.println("Error: " + e);  //ERROR EN EL SOCKET
        }
        
    }
}
 