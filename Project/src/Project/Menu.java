package Project;

import java.util.Scanner;

public class Menu {
    String [] nameProducts;  
    Float [] priceProducts; 
    String [] descriptionProducts;
    Integer [] existenceProducts;

    public Menu(String[] nameProducts, Float[] priceProducts, String[] descriptionProducts, Integer[] existenceProducts){
        this.nameProducts = nameProducts;
        this.priceProducts = priceProducts;
        this.descriptionProducts = descriptionProducts;
        this.existenceProducts = existenceProducts;
    }

     

    public void addProduct(int position, int noExist){
        int optionProduct = 0;
        System.out.flush();
        System.out.println("------------- TIENDA ---------------\n");
        System.out.println("------------------------------------\n");
        System.out.println("\n----------- PRODUCTO ---------------\n");
        
        int counter = 0;
        for(int i = 0; i<nameProducts.length; i++)
            {
                if(position == i){
                    Product p = new Product(this.nameProducts[i], this.priceProducts[i], this.descriptionProducts[i], this.existenceProducts[i] - noExist);
                    System.out.println((i+1) + ")" + p.getName());
                }
                else{
                    Product p = new Product(this.nameProducts[i], this.priceProducts[i], this.descriptionProducts[i], this.existenceProducts[i]);
                    System.out.println((i+1) + ")" + p.getName());
                }
               
                counter = i;
            }
        System.out.println("\n\n" + ( counter + 2) + ") SALIR");
        System.out.println("\nSELECCIONE SU PRODUCTO: \n\n");
        Scanner in = new Scanner(System.in);
        optionProduct = in.nextInt();
            
        if (optionProduct > nameProducts.length){
            System.out.println("DATO INGRESALO INVALIDAMENTE");
            optionProduct = 0;
            
        } else if (optionProduct <= 0){
            System.out.println("DATO INGRESALO INVALIDAMENTE");
            optionProduct = 0;
            position = -1;
            noExist = 0;
            addProduct(position, noExist);
        } else{
            System.out.println("------------- PRODUCTO A COMPRAR -------------");
            System.out.println(optionProduct + ")" + nameProducts[optionProduct - 1] + priceProducts[optionProduct - 1] + descriptionProducts[optionProduct - 1] + existenceProducts[optionProduct - 1]);
            System.out.println("\n CUANTAS UNIDADES DESEA COMPRAR? ");
            int unitBuy = 0;
            
            while(unitBuy == 0)
            {
                unitBuy = in.nextInt();
                if(unitBuy > existenceProducts[optionProduct - 1 ]){
                    System.out.println("UNIDADES EXCEDIERON EL STOCK");
                    unitBuy = 0;
                }
                else if(unitBuy <= 0){
                    System.out.println("UNIDADES DEBEN SER MAYOR A CERO");
                    unitBuy = 0;
                }
                else{
                    Car c = new Car(this.nameProducts[optionProduct - 1], this.priceProducts[optionProduct - 1], this.descriptionProducts[optionProduct - 1], unitBuy);
                    System.out.println("¡¡¡¡ COMPRADO EXITOSAMENTE!!!!!!");
                    addProduct(optionProduct - 1, unitBuy);

                    showMenu();
                }
                

            }
            
                /*
                    1.- limpiar terminal
                    2.- mostrar los datos del producto y pedir las cantidades que comprará
                    3.- mandarlas al carrito
                    4.- actualizar la existencia del producto
                    5.- volver al menu
                */
            }
            in.close();
    }



    public void showCar(){
        //  Mandamos a llamar lo que este en el carrito
    }

    public void buyCar(){
        // generacion de reporte del carrito
    }



    public void showMenu(){
        int optionMenu = 0;
        
        while(optionMenu == 0){
            System.out.println("------------- TIENDA ---------------\n");
            System.out.println("------------------------------------\n");
            System.out.println("ELIGE LA OPCION: \n\n");
            System.out.println("1.- AGREGAR PRODUCTO AL CARRITO\n");
            System.out.println("2.- VER CARRITO\n");
            System.out.println("3.- FINALZAR COMPRA\n");
            System.out.println("4.- SALIR\n");
            Scanner in = new Scanner(System.in);
            optionMenu = in.nextInt();
            switch(optionMenu){
                case 1:
                int position = -1;
                int noExist = 0;
                    addProduct(position, noExist);
                    break;
                case 2: 
                    showCar();
                    break;
                case 3: 
                    buyCar();
                    break;
                case 4:
                    break;
                default:
                    optionMenu = 0;
                    break;     
            }
            in.close();
            
        }
        
    }
    
}
