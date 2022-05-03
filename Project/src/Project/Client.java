package Project;

import java.net.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import java.io.*;



public class Client {
    public static void main(String[] args) {
        try{

            //**************LECTURA DE DATOS PARA LA CONEXION**************
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Escriba la direccion del servidor: ");
            String host = br1.readLine();
            System.out.printf("\n\nEscriba el puerto: ");
            int pto = Integer.parseInt(br1.readLine());
            Socket cl = new Socket(host,pto);
            //**************LECTURA DE DATOS PARA LA CONEXION************** 

            //**************LECTURA DE ARCHIVOS************** 
            //**************LECTURA DE ARCHIVOS************** 


            //**************LECTURA DE DATOS DE PRODUCTO************** 

            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            String fileslengthstr = br2.readLine();
            int fileslength = Integer.parseInt(fileslengthstr);
            
    

            String [] nameProducts = new String[fileslength];
            String [] priceProducts = new String[fileslength];
            String [] descriptionProducts = new String[fileslength];
            String [] existenceProducts = new String[fileslength];
            

            for(int j = 0; j<nameProducts.length; j++){
                String data = br2.readLine();
                nameProducts[j] = data;
                data = br2.readLine();
                priceProducts[j] = data;
                data = br2.readLine();
                descriptionProducts[j] = data;
                data = br2.readLine();
                existenceProducts[j] = data;

            } 

            
            System.out.println("id \t Nombre \t Precio \t Descripcion \t Existencia");
            for(int i = 0; i< nameProducts.length ; i++){
                System.out.println(i + "\t" + nameProducts[i] + "\t" + priceProducts[i] + "\t" + descriptionProducts[i] + "\t" + existenceProducts[i]);
            }
            
            
            
            //**************LECTURA DE DATOS DE PRODUCTOS************** 

                



        br1.close();
        br2.close();
        cl.close();
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
        
    }
}
 