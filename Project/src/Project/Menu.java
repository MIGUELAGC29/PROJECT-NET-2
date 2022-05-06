package Project;

import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


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
    public static String [] carDescriptionProducts = new String[10];
    public static Integer [] carExistenceProducts = new Integer[10];


    public void showCar(int counterCar){
        //  Mandamos a llamar lo que este en el carrito
        Car c = new Car(Menu.carNameProducts, Menu.carPriceProducts, Menu.carDescriptionProducts, Menu.carExistenceProducts);
        String [] names = c.getNames();
        Float [] prices = c.getPrices();
        String [] descriptions = c.getDescriptions();
        Integer [] existences = c.getexistences();
        

        
        if(names[0] == null)
        {
            System.out.println("\n\nCARRITO VACIO\n\n");
            int opt = 0;
            while(opt == 0){
                System.out.println("--------------------------------");
                System.out.println("1.- MENU PRINCIPAL");
                System.out.println("2.- SALIR");
                System.out.print("\n\nSELECCIONE LA OPCION: ");
                Scanner in = new Scanner(System.in);
                opt = in.nextInt();
                switch(opt){
                    case 1: 
                        Menu m = new Menu(this.nameProducts, this.priceProducts, this.descriptionProducts, this.existenceProducts);
                        m.showMenu(counterCar);
                        break;
                    case 2: 
                        break;
                    default:
                        opt = 0;
                        break;     
                }in.close();
            }
                
        }
        else{
            float total = 0.0f;
            System.out.println("\n\nNombre\t\tPrecio\t\tDescripcion\t\tCantidad\t\tTotal");
            for(int i = 0; i < names.length ; i ++)
            {
                if(names[i] != null)
                {
                    System.out.println(names[i] + "\t\t" + prices[i] + "\t\t" + descriptions[i] + "\t\t" + existences[i] + "\t\t" + prices[i]*existences[i]); 
                }
                else
                {
                    break;
                }
            }


            int opt = 0;
            while(opt == 0){
                System.out.println("\n\n--------------------------------");
                System.out.println("1.- EDITAR");
                System.out.println("2.- COMPRAR CARRITO");
                System.out.println("3.- MENU PRINCIPAL");
                System.out.println("4.- SALIR");
                System.out.print("\n\nSELECCIONE LA OPCION: ");
                Scanner in = new Scanner(System.in);
                opt = in.nextInt();
                switch(opt){
                    case 1:
                        editCar(names, prices, descriptions, existences, counterCar);
                        break;
                    case 2:
                        buyCar(counterCar);
                        break;
                    case 3: 
                        Menu m = new Menu(this.nameProducts, this.priceProducts, this.descriptionProducts, this.existenceProducts);
                        m.showMenu(counterCar);
                        break;
                    case 4: 
                        break;
                    default:
                        opt = 0;
                        break;     
                    }in.close();
            }
            
        
        }
    
            
    }

    public void generateReport(String[] names, Float[] prices, String[] descriptions, Integer[] existences, int counterCar){
        try{
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD,30);
            content.newLineAtOffset(90, page.getMediaBox().getHeight()-50);
            content .showText("TICKET");
            content.endText();

            float total = 0.0f;
            for(int i = 0; i<names.length; i++){
                if(prices[i] != null){
                    total = total + prices[i] * existences[i];
                    
                }
                else{
                    break;
                }
                
            }

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD,20);
            content.newLineAtOffset(33, page.getMediaBox().getHeight()-340);
            content .showText("TOTAL: $" + total);
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD,10);
            content.newLineAtOffset(33, page.getMediaBox().getHeight()-100);
            content .showText("Nombre          Precio          Descripción            Cantidad");
            content.endText();
            int counter = 10;
            for(int i = 0; i< names.length; i++)
            {
                
                content.beginText();
                content.setFont(PDType1Font.TIMES_BOLD,10);
                content.newLineAtOffset(33, page.getMediaBox().getHeight()-(130 + counter));
                if(names[i] != null)
                {
                    content .showText(names[i]);
                }
                else{
                    break;
                }
                content.endText();


                content.beginText();
                content.setFont(PDType1Font.TIMES_BOLD,10);
                content.newLineAtOffset(98, page.getMediaBox().getHeight()-(130 + counter));
                if(names[i] != null)
                {
                    content .showText("" + prices[i]);
                }
                else{
                    break;
                }
                content.endText();

                content.beginText();
                content.setFont(PDType1Font.TIMES_BOLD,10);
                content.newLineAtOffset(145, page.getMediaBox().getHeight()-(130 + counter));
                if(names[i] != null)
                {
                    content .showText("" + descriptions[i]);
                }
                else{
                    break;
                }
                content.endText();

                content.beginText();
                content.setFont(PDType1Font.TIMES_BOLD,10);
                content.newLineAtOffset(240, page.getMediaBox().getHeight()-(130 + counter));
                if(names[i] != null)
                {
                    content .showText("" + existences[i]);
                }
                else{
                    break;
                }
                content.endText();

                counter = counter + 10;
            }

            
            
            
        
            content.close();

            document.save("/Users/miguelagc/Desktop/REDES 2/Project/Project/src/Project/report.pdf");


        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
        
    public void buyCar(int counterCar){
        // generacion de reporte del carrito
        Car c = new Car(Menu.carNameProducts, Menu.carPriceProducts, Menu.carDescriptionProducts, Menu.carExistenceProducts);
        String [] names = c.getNames();
        Float [] prices = c.getPrices();
        String [] descriptions = c.getDescriptions();
        Integer [] existences = c.getexistences();

        if(names[0] == null)
        {
            System.out.println("\n\nCARRITO VACIO\n\n");
            int opt = 0;
            while(opt == 0){
                System.out.println("--------------------------------");
                System.out.println("1.- MENU PRINCIPAL");
                System.out.println("2.- SALIR");
                System.out.print("\n\nSELECCIONE LA OPCION: ");
                Scanner in = new Scanner(System.in);
                opt = in.nextInt();
                switch(opt){
                    case 1: 
                        Menu m = new Menu(this.nameProducts, this.priceProducts, this.descriptionProducts, this.existenceProducts);
                        m.showMenu(counterCar);
                        break;
                    case 2: 
                        break;
                    default:
                        opt = 0;
                        break;     
                }in.close();
            }
                
        }
        else{
            float total = 0.0f;
            System.out.println("\n\nNombre\t\tPrecio\t\tDescripcion\t\tCantidad\t\tTotal");
            for(int i = 0; i < names.length ; i ++)
            {
                if(names[i] != null)
                {
                    System.out.println(names[i] + "\t\t" + prices[i] + "\t\t" + descriptions[i] + "\t\t" + existences[i] + "\t\t" + prices[i]*existences[i]); 
                }
                else
                {
                    break;
                }
            }


            int opt = 0;
            while(opt == 0){
                System.out.println("\n\n--------------------------------");
                System.out.println("1.- COMPRAR CARRITO (SI)");
                System.out.println("2.- COMPRAR CARRITO (NO)");
                System.out.println("3.- MENU PRINCIPAL");
                System.out.println("4.- SALIR");
                System.out.print("\n\nSELECCIONE LA OPCION: ");
                Scanner in = new Scanner(System.in);
                opt = in.nextInt();
                switch(opt){
                    case 1:
                        generateReport(names, prices, descriptions, existences, counterCar);
                        break;
                    case 2:
                        buyCar(counterCar);
                        break;
                    case 3: 
                        Menu m = new Menu(this.nameProducts, this.priceProducts, this.descriptionProducts, this.existenceProducts);
                        m.showMenu(counterCar);
                        break;
                    case 4: 
                        break;
                    default:
                        opt = 0;
                        break;     
                    }in.close();
            }
            
        
        }
    }

    public void editCar(String[] names, Float[] prices, String[] descriptions, Integer[] existences, int counterCar){
        int optionMenu = 0;
        System.out.println("\n\nNombre\t\tPrecio\t\tDescripcion\t\tCantidad\t\tTotal");
            for(int i = 0; i < names.length ; i ++)
            {
                if(names[i] != null)
                {
                    System.out.println(names[i] + "\t\t" + prices[i] + "\t\t" + descriptions[i] + "\t\t" + existences[i] + "\t\t" + prices[i]*existences[i] + "\n\n"); 
                }
                else
                {
                    break;
                }
            }

        System.out.println("1.- AGREGAR PRODUCTOS");
        System.out.println("2.- EDITAR PRODUCTOS");
        System.out.println("3.- ELIMINAR PRODUCTOS");
        System.out.print("ELIGE TU POCION: ");
        Scanner in = new Scanner(System.in);
        optionMenu = in.nextInt();
        switch(optionMenu){
            case 1:
                addProduct(counterCar);
                break;
            case 2: 
                editProduct(names, prices, descriptions, existences, counterCar);
                break;
            case 3: 
                deleteProduct(names, prices, descriptions, existences, counterCar);
                break;
            case 4:
                break;
            default:
                optionMenu = 0;
                break;     
        }
        in.close();



    }

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

    public void editProduct(String[] names, Float[] prices, String[] descriptions, Integer[] existences, int counterCar)
    {
        System.out.println("    \n\nNombre\t\tPrecio\t\tDescripcion\t\tCantidad\t\tTotal\n");
        for(int i = 0; i < names.length ; i ++)
        {
            if(names[i] != null)
            {
                System.out.println(i+1 + ")  " + names[i] + "\t\t" + prices[i] + "\t\t" + descriptions[i] + "\t\t" + existences[i] + "\t\t" + prices[i]*existences[i]); 
            }
            else
            {
                break;
            }
        }
        System.out.print("\n\nSELECCIONA EL NUMERO DEL PRODUCTO A EDITAR: ");
        int optionProduct = 0;
        Scanner on = new Scanner(System.in);
        optionProduct = on.nextInt();
        optionProduct = optionProduct - 1;
        System.out.println("\n\n" + optionProduct + ")  " + names[optionProduct] + "\t\t" + prices[optionProduct] + "\t\t" + descriptions[optionProduct] + "\t\t" + existences[optionProduct] + "\t\t" + prices[optionProduct]*existences[optionProduct]);
        System.out.print("INGRESA LA NUEVA CANTIDAD DE PRODUCTOS A COMPRAR: ");
        int newCant = 0;
        Scanner or = new Scanner(System.in);
        newCant = or.nextInt();
        existences[optionProduct] = newCant;
        System.out.println("\n\n***********************************************");
        System.out.println("***********************************************");
        System.out.println("************ EDITADO EXITOSAMENTE *************");
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        showMenu(counterCar);


    }

    public void deleteProduct(String[] names, Float[] prices, String[] descriptions, Integer[] existences, int counterCar)
    {
        System.out.println("    \n\nNombre\t\tPrecio\t\tDescripcion\t\tCantidad\t\tTotal\n");
        for(int i = 0; i < names.length ; i ++)
        {
            if(names[i] != null)
            {
                System.out.println(i+1 + ")  " + names[i] + "\t\t" + prices[i] + "\t\t" + descriptions[i] + "\t\t" + existences[i] + "\t\t" + prices[i]*existences[i]); 
            }
            else
            {
                break;
            }
        }
        System.out.print("\n\nSELECCIONA EL NUMERO DEL PRODUCTO A ELIMINAR: ");
        int optionProduct = 0;
        Scanner on = new Scanner(System.in);
        optionProduct = on.nextInt();
        optionProduct = optionProduct - 1;
        names[optionProduct] = null;
        prices[optionProduct] = null;
        descriptions[optionProduct] = null;
        existences[optionProduct] = null;

        System.out.println("\n\n***********************************************");
        System.out.println("***********************************************");
        System.out.println("************ ELIMINADO EXITOSAMENTE ***********");
        System.out.println("***********************************************");
        System.out.println("***********************************************");
        showMenu(counterCar);
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
                    buyCar(counterCar);
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
