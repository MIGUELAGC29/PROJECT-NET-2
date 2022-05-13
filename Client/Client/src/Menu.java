
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static Integer [] stockProducts = new Integer[10];
    public static HashMap<Integer, Integer> orele = new HashMap<Integer, Integer>();

    

    public Menu(String[] nameProducts, Float[] priceProducts, String[] descriptionProducts, Integer[] existenceProducts){
        this.nameProducts = nameProducts;
        this.priceProducts = priceProducts;
        this.descriptionProducts = descriptionProducts;
        this.existenceProducts = existenceProducts;
    }
    
    
    
    public void generateReport(String[] names, Float[] prices, String[] descriptions, Integer[] stockProducts){
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
            for(int i = 0; i<this.nameProducts.length; i++){
                total = total + (this.priceProducts[i] * stockProducts[i]);
                
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
                if(stockProducts[i] == 0)
                {
                    continue;
                }
                else
                {
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(33, page.getMediaBox().getHeight()-(130 + counter));
                    content .showText(this.nameProducts[i]);
                    content.endText();
    
    
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(98, page.getMediaBox().getHeight()-(130 + counter));
                    content .showText("" + this.priceProducts[i]);
                    content.endText();
    
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(145, page.getMediaBox().getHeight()-(130 + counter));
                    content .showText("" +this.descriptionProducts[i]);
                    content.endText();
    
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(240, page.getMediaBox().getHeight()-(130 + counter));
                    content .showText("" + stockProducts[i]);
                    content.endText();
    
                    counter = counter + 10;
                }
    
                
            }
            content.close();
                
                

            document.save("/Users/miguelagc/Desktop/REDES 2/Project/Project/src/Project/report.pdf");


        }catch(Exception e){
            System.out.println("Errorsito: " + e);
        }
    }
    
    public void buyCar(int counterCar){
        System.out.println("\n\n\n-------------- TIENDA ---------------");
        System.out.println("------------ COMPRAR CARRITO ------------\n");
        int counter = 0;
        for(int i = 0; i<nameProducts.length; i++)
        {
            if(stockProducts[i] == 0)
            {
                continue;
            }
            else
            {
                counter = counter + 1;
                System.out.println(counter + ")  " + this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);
            }
            
        }
        int optionMenu = 0; //SE DEFINE LA VARIABLE DE MENU
        while(optionMenu == 0){
            System.out.println("\n1.- CONFIRMAR COMPRA");
            System.out.println("2.- EDITAR");
            System.out.println("3.- MENU PRINCIPAL");
            System.out.println("4.- SALIR\n");
            System.out.print("ELIGE LA OPCION: ");
            Scanner in = new Scanner(System.in);
            optionMenu = in.nextInt();
            if(optionMenu >4 || optionMenu<=0){
                System.out.println("\n\n**** OPCION INCORRECTA ****");
                showMenu(counterCar);
            }else{
                switch(optionMenu){
                    case 1:
                        generateReport(nameProducts, priceProducts, descriptionProducts, stockProducts); //MANDA A LLAMAR LA FUNCION QUE GENERA REPORTES
                        break;
                    case 3: 
                        Menu m = new Menu(nameProducts, priceProducts, descriptionProducts, existenceProducts); //MANDAMOS A LLAMAR A OTRO MENU
                        m.showMenu(counterCar);
                        break;
                    case 2:
                        editCar(counterCar);
                        break;
                    case 4:
                        break;
                    default:
                        optionMenu = 0;
                        break;     
                }
            }
            
            in.close();
        }

    }

    public void editUnits(int counterCar){
        int optionMenu = 0;
        System.out.println("\n\n\n-------------- TIENDA ---------------");
        System.out.println("------------ EDITAR UNIDADES ------------\n");
        for(Integer key:orele.keySet())
        {
            System.out.println(key + "\t\t" );
        }
        for(int i = 0; i<nameProducts.length; i++) //CICLO PARA MOSTRAR LOS PRODUCTOS QUE HA COMPRADO EL USUARIO
        {
            if(stockProducts[i] == 0)
            {
                continue;
            }
            else
            {
                System.out.println(i + ")  " + this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);
            }
            
        }
        System.out.print("\nELIGE EL PRODUCTO PARA EDITAR UNIDADES: "); //SELECCION DEL PRODUCTO A ELIMINAR
        Scanner in = new Scanner(System.in);
        optionMenu = in.nextInt();
        if(optionMenu >4 || optionMenu<0){ //VALIDACION DE LOS DATOS
            System.out.println("\n\n**** OPCION INCORRECTA ****");
            editUnits(counterCar);
        }else{
            this.existenceProducts[optionMenu] = this.existenceProducts[optionMenu] + stockProducts[optionMenu]; //EL STOCK TOTAL SE REESTABLECE
            int cantUnit = 0;
            System.out.print("\nNUEVAS UNIDADES: "); //SELECCION DEL PRODUCTO A ELIMINAR
            Scanner inn = new Scanner(System.in);
            cantUnit = inn.nextInt();
            if(cantUnit > this.existenceProducts[optionMenu])
            {
                System.out.println("*** CANTIDADES EXCEDEN EL STOCK ***");
            }
            else
            {
                stockProducts[optionMenu] = cantUnit;
                this.existenceProducts[optionMenu] = this.existenceProducts[optionMenu] - stockProducts[optionMenu]; //EL STOCK TOTAL SE REESTABLECE
            }

            
            System.out.println("\n\n***********************************************");
            System.out.println("***********************************************");
            System.out.println("************ EDITADO EXITOSAMENTE ************");
            System.out.println("***********************************************");
            System.out.println("***********************************************");

            Menu m = new Menu(nameProducts, priceProducts, descriptionProducts, existenceProducts); //MANDAMOS A LLAMAR A OTRO MENU
            m.showMenu(counterCar);
        }
    }

    public void deleteProduct(int counterCar){  //FUNCION DE ELIMINAR EL PRODUCTO
        int optionMenu = 0;
        System.out.println("\n\n\n-------------- TIENDA ---------------");
        System.out.println("------------ ELIMINAR PRODUCTOS ------------\n");
        for(Integer key:orele.keySet())
        {
            System.out.println(key + "\t\t" );
        }
        for(int i = 0; i<nameProducts.length; i++) //CICLO PARA MOSTRAR LOS PRODUCTOS QUE HA COMPRADO EL USUARIO
        {
            if(stockProducts[i] == 0)
            {
                continue;
            }
            else
            {
                System.out.println(i + ")  " + this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);
            }
            
        }
        System.out.print("\nELIGE EL PRODUCTO A ELIMINAR: "); //SELECCION DEL PRODUCTO A ELIMINAR
        Scanner in = new Scanner(System.in);
        optionMenu = in.nextInt();
        if(optionMenu >4 || optionMenu<0){ //VALIDACION DE LOS DATOS
            System.out.println("\n\n**** OPCION INCORRECTA ****");
            deleteProduct(counterCar);
        }else{
            this.existenceProducts[optionMenu] = this.existenceProducts[optionMenu] + stockProducts[optionMenu]; //EL STOCK TOTAL SE REESTABLECE
            stockProducts[optionMenu] = 0;  //DEJAMOS EL STOCK DEL USUARIO EN CERO
            
            System.out.println("\n\n***********************************************");
            System.out.println("***********************************************");
            System.out.println("************ ELIMINADO EXITOSAMENTE ************");
            System.out.println("***********************************************");
            System.out.println("***********************************************");

            Menu m = new Menu(nameProducts, priceProducts, descriptionProducts, existenceProducts); //MANDAMOS A LLAMAR A OTRO MENU
            m.showMenu(counterCar);
        }




    }
    
    public void editCar(int counterCar){
        System.out.println("\n\n\n---------------- TIENDA ----------------");
            System.out.println("------------ EDITAR CARRITO -------------\n");
        int counter = 0;
        for(int i = 0; i<nameProducts.length; i++)
        {
            if(stockProducts[i] == 0)
            {
                continue;
            }
            else
            {
                counter = counter + 1;
                System.out.println(this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);
            }
            
        }
        int optionMenu = 0; //SE DEFINE LA VARIABLE DE MENU
        while(optionMenu == 0){
            System.out.println("\n1.- EDITAR UNIDADES");
            System.out.println("2.- AGREGAR PRODUCTO");
            System.out.println("3.- ELIMINAR PRODUCTO");
            System.out.println("4.- MENU PRINCIPAL");
            System.out.println("5.- SALIR\n");
            System.out.print("ELIGE LA OPCION: ");
            Scanner in = new Scanner(System.in);
            optionMenu = in.nextInt();
            if(optionMenu >4 || optionMenu<0){
                System.out.println("\n\n**** OPCION INCORRECTA ****");
                showMenu(counterCar);
            }else{
                switch(optionMenu){
                    case 1:
                        editUnits(counterCar); //MANDA A LLAMAR A AGREGAR LOS PRODUCTS
                        break;
                    case 2: 
                        addProduct(counterCar);
                        break;
                    case 3:
                        deleteProduct(counterCar);
                        break;
                    case 4:
                        Menu m = new Menu(nameProducts, priceProducts, descriptionProducts, existenceProducts); //MANDAMOS A LLAMAR A OTRO MENU
                        m.showMenu(counterCar);
                        break;
                    case 5:
                        break;
                    default:
                        optionMenu = 0;
                        break;     
                }
            }
            
            in.close();
        }

    }

    public void showCar(int counterCar){
        if(counterCar == 0)
        {
            System.out.println("\n\n\n---------------- TIENDA ----------------");
            System.out.println("-------------- CARRITO VACIO ---------------\n");
        }
        else
        {
            System.out.println("\n\n\n---------------- TIENDA ----------------");
            System.out.println("-------------- TU CARRITO ---------------\n");
            System.out.println("Nombre \t\tPrecio \tDescripcion \t\tUnidades\n"); 
            int counter = 0;
            for(int i = 0; i<nameProducts.length; i++)
            {
                if(stockProducts[i] == 0)
                {
                    continue;
                }
                else
                {
                    counter = counter + 1;
                    System.out.println(this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);
                }
                
            }
        }
        
        int optionMenu = 0; //SE DEFINE LA VARIABLE DE MENU
        while(optionMenu == 0){
            System.out.println("\n1.- AGREGAR MAS PRODUCTOS AL CARRITO");
            System.out.println("2.- EDITAR CARRITO");
            System.out.println("3.- FINALZAR COMPRA");
            System.out.println("4.- SALIR\n");
            System.out.print("ELIGE LA OPCION: ");
            Scanner in = new Scanner(System.in);
            optionMenu = in.nextInt();
            if(optionMenu >4 || optionMenu<0){
                System.out.println("\n\n**** OPCION INCORRECTA ****");
                showMenu(counterCar);
            }else{
                switch(optionMenu){
                    case 1:
                        addProduct(counterCar); //MANDA A LLAMAR A AGREGAR LOS PRODUCTS
                        break;
                    case 2: 
                        editCar(counterCar);  //MANDA A LLAMAR PARA MOSTRAR EL CARRITO
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
            }
            
            in.close();
        }


    }

    public void addProduct(int counterCar){
        int optionProduct = 0; //OPCION DE ELECCION DE PRODUCTO
        System.out.flush();
        System.out.println("\n\n\n------------- TIENDA ---------------");
        System.out.println("--------- AGREGAR PRODUCTO ---------\n");
        
        int counter = 0;
        System.out.println("     Nombre \t\t Precio \t\t Descripcion \t\t Existencia\n"); 
        for(int i = 0; i<nameProducts.length; i++)
            {
                Product p = new Product(this.nameProducts[i], this.priceProducts[i], this.descriptionProducts[i], this.existenceProducts[i]);   //GENERAMOS UN OBJETO
                System.out.println((i+1) + ")   " + p.getName() + "\t\t  " + p.getPrice() + "\t\t\t" + p.getDescription() + "\t\t   " + p.getExistence()); //  //SE MUESTRAN  LOS OBJETOS

                counter = i;
            }
        System.out.print("\nSELECCIONE SU PRODUCTO: "); //OPCION PARA SELECCIONAR EL PRODUCTO
        Scanner in = new Scanner(System.in);
        optionProduct = in.nextInt();
            
        if (optionProduct > nameProducts.length){ //VALIDACION DE ENTRADA DE DATOS
            System.out.println("\n\n**** DATO INGRESADO INVALIDAMENTE ****");
            optionProduct = 0;
            addProduct(counterCar);
            
        } else if (optionProduct <= 0){
            System.out.println("\n\n**** DATO INGRESALO INVALIDAMENTE ****"); //VALIDACION DE ENTRADA DE DATOS
            optionProduct = 0;
            addProduct(counterCar);
        } else{
            System.out.println("\n\n\n------------- PRODUCTO A COMPRAR -------------"); 
            System.out.println("\n" + this.nameProducts[optionProduct - 1] + "\t\t" + this.priceProducts[optionProduct - 1] + "\t\t" + this.descriptionProducts[optionProduct - 1] + "\t\t" + this.existenceProducts[optionProduct - 1]);
            System.out.print("\n CUANTAS UNIDADES DESEA COMPRAR?: ");  //SE VERIFICAN LAS CANTIDADES QUE EL USUARIO VA A COMPRAR
            int unitBuy = 0;
            
            while(unitBuy == 0)
            {
                unitBuy = in.nextInt();
                if(unitBuy > existenceProducts[optionProduct - 1 ]){
                    System.out.println("\n\n**** UNIDADES EXCEDIERON EL STOCK *****"); //SE VERIFICAN EL NUMERO EN EXISTENCIA
                    addProduct(counterCar);
                    unitBuy = 0;
                }
                else if(unitBuy <= 0){
                    System.out.println("\n\n**** UNIDADES DEBEN SER MAYOR A CERO ****"); //SE VERIFICA QUE SEAN MAYORES A CERO
                    addProduct(counterCar);
                    unitBuy = 0;
                }
                else{
                    
                    System.out.println("\n\n***********************************************");
                    System.out.println("***********************************************");
                    System.out.println("************ AGREGADO EXITOSAMENTE ************");
                    System.out.println("***********************************************");
                    System.out.println("***********************************************");  //SE VERIFICA LA COMPRA

                    for(int i = 0; i<existenceProducts.length; i++){  //EN ESTE CICLO SE REDUCE A LA EXISTENCIA DEL PRODUCTO 
                        if(optionProduct - 1 == i){
                            this.existenceProducts[i] = this.existenceProducts[i] - unitBuy;  
                        }
                    }


                    if(counterCar == 0)  //CICLO PARA AGREGAR LO QUE VA COMPRANDO EL USUARIO
                    {
                        for(int i = 0; i<10; i++)
                        {
                            stockProducts[i] = 0;  //LIMPIAMOS EL ARRAY CON CEROS
                        }

                        stockProducts[optionProduct - 1] = unitBuy; //AGREGAMOS LA PRIMER COMPRA
                    }
                    else
                    {
                        stockProducts[optionProduct - 1] = stockProducts[optionProduct - 1] + unitBuy;  //SI YA HAY MAS DE UNA COMPRA SE AGREGAN O SE SUMAN LAS CANTIDADES
                        
                    }

                    counterCar = counterCar + 1;


                    Menu m = new Menu(nameProducts, priceProducts, descriptionProducts, existenceProducts); //MANDAMOS A LLAMAR A OTRO MENU
                    m.showMenu(counterCar);
                }
        
            }
        }
            
            in.close();
    }

    public void showMenu(int counterCar){  //FUNCION PARA MOSTRAR EL MENU PRINCIPAL
        int optionMenu = 0; //SE DEFINE LA VARIABLE DE MANU
        while(optionMenu == 0){
            System.out.println("\n\n\n---------------- TIENDA ----------------");
            System.out.println("------------ MENU PRINCIPAL -----------\n");
            System.out.println("1.- AGREGAR PRODUCTO AL CARRITO");
            System.out.println("2.- VER CARRITO");
            System.out.println("3.- FINALZAR COMPRA");
            System.out.println("4.- SALIR\n");
            System.out.print("ELIGE LA OPCION: ");
            Scanner in = new Scanner(System.in);
            optionMenu = in.nextInt();
            if(optionMenu >4 || optionMenu<0){
                System.out.println("\n\n**** OPCION INCORRECTA ****");
                showMenu(counterCar);
            }else{
                switch(optionMenu){
                    case 1:
                        addProduct(counterCar); //MANDA A LLAMAR A AGREGAR LOS PRODUCTS
                        break;
                    case 2: 
                        showCar(counterCar);  //MANDA A LLAMAR PARA MOSTRAR EL CARRITO
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
            }
            
            in.close();
            
        }
        
    }
    
}
