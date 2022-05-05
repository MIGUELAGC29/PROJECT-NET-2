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

    

    public static String [] carNameProducts = new String[10]; 
    public static Float [] carPriceProducts = new Float[10];
    public static String [] carDescriptionProducts = new String[10];;
    public static Integer [] carExistenceProducts = new Integer[10];;


    
     

    public void addProduct(int counterCar){

        int optionProduct = 0;
        System.out.flush();
        System.out.println("\n\n\n------------- TIENDA ---------------");
        System.out.println("------------------------------------");
        System.out.println("----------- PRODUCTO ---------------\n");
        
        int counter = 0;
        System.out.println("     Nombre \t\t Precio \t\t Descripcion \t\t Existencia\n");
        for(int i = 0; i<nameProducts.length; i++)
            {
                Product p = new Product(this.nameProducts[i], this.priceProducts[i], this.descriptionProducts[i], this.existenceProducts[i]);
                System.out.println((i+1) + ")   " + p.getName() + "\t\t  " + p.getPrice() + "\t\t\t" + p.getDescription() + "\t\t   " + p.getExistence());
                
                counter = i;
            }
        System.out.println("\n\n" + ( counter + 2) + ") SALIR");
        System.out.print("\nSELECCIONE SU PRODUCTO: ");
        Scanner in = new Scanner(System.in);
        optionProduct = in.nextInt();
            
        if (optionProduct > nameProducts.length){
            System.out.println("DATO INGRESALO INVALIDAMENTE");
            optionProduct = 0;
            addProduct(counterCar);
            
        } else if (optionProduct <= 0){
            System.out.println("DATO INGRESALO INVALIDAMENTE");
            optionProduct = 0;
            addProduct(counterCar);
        } else{
            System.out.println("\n\n\n------------- PRODUCTO A COMPRAR -------------");
            System.out.println("\n" + nameProducts[optionProduct - 1] + "\t\t" + priceProducts[optionProduct - 1] + "\t\t" + descriptionProducts[optionProduct - 1] + "\t\t" + existenceProducts[optionProduct - 1]);
            System.out.print("\n CUANTAS UNIDADES DESEA COMPRAR?: ");
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
                    //Car c = new Car(this.nameProducts[optionProduct - 1], this.priceProducts[optionProduct - 1], this.descriptionProducts[optionProduct - 1], unitBuy);
                    
                    System.out.println("\n\n***********************************************");
                    System.out.println("***********************************************");
                    System.out.println("************ COMPRADO EXITOSAMENTE ************");
                    System.out.println("***********************************************");
                    System.out.println("***********************************************");

                    for(int i = 0; i<existenceProducts.length; i++){
                        if(optionProduct - 1 == i){
                            existenceProducts[i] = existenceProducts[i] - unitBuy;
                        }
                    }
                    
                    
                    carNameProducts[counterCar] = nameProducts[optionProduct-1];
                    carPriceProducts[counterCar] = priceProducts[optionProduct-1];
                    carDescriptionProducts[counterCar] = descriptionProducts[optionProduct-1];
                    carExistenceProducts[counterCar] = unitBuy;
                    counterCar = counterCar + 1;


                    Menu m = new Menu(nameProducts, priceProducts, descriptionProducts, existenceProducts);
                    m.showMenu(counterCar);
                }
        
            }
        }
            
            in.close();
    }



    public void showCar(int counterCar){
        //  Mandamos a llamar lo que este en el carrito
        Car c = new Car(Menu.carNameProducts, Menu.carPriceProducts, Menu.carDescriptionProducts, Menu.carExistenceProducts);
        String[] nombres = c.getNames();
        if(nombres.length == 0){
            System.out.println("CARRITO VACIO");
        }
        else{
            System.out.println("\n\n\n\n*************** MOSTRANDO BANDAAAAAAAAAAA ****************");
            for(int i = 0; i<nombres.length ; i++){
                System.out.println("\n" + carNameProducts[i] + "\t\t" + carPriceProducts[i] + "\t\t" + carDescriptionProducts[i] + "\t\t" + carExistenceProducts[i]);

            }
        }
        int opt = 0;
        while(opt == 0){
            System.out.println("--------------------------------");
            System.out.println("1.- COMPRAR CARRITO");
            System.out.println("2.- MENU PRINCIPAL");
            System.out.println("3.- SALIR");
            Scanner in = new Scanner(System.in);
            opt = in.nextInt();
            switch(opt){
                case 1:
                    buyCar();
                    break;
                case 2: 
                    Menu m = new Menu(this.nameProducts, this.priceProducts, this.descriptionProducts, this.existenceProducts);
                    m.showMenu(counterCar);
                    break;
                case 3: 
                    break;
                default:
                    opt = 0;
                    break;     
            }
            in.close();
            
        }
        
        
    }

    public void buyCar(){
        // generacion de reporte del carrito
    }



    public void showMenu(int counterCar){
        int optionMenu = 0;
        
        while(optionMenu == 0){
            System.out.println("\n\n\n------------- TIENDA ---------------");
            System.out.println("------------------------------------\n");
            System.out.println("1.- AGREGAR PRODUCTO AL CARRITO\n");
            System.out.println("2.- VER CARRITO\n");
            System.out.println("3.- FINALZAR COMPRA\n");
            System.out.println("4.- SALIR\n");
            System.out.print("ELIGE LA OPCION: ");
            Scanner in = new Scanner(System.in);
            optionMenu = in.nextInt();
            switch(optionMenu){
                case 1:
                    addProduct(counterCar);
                    break;
                case 2: 
                    showCar(counterCar);
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
