package Project;

import java.util.HashMap;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class Menu { 

    //ARRAYS PARA DEFINIR LOS PRODUCTOS QUE COMPRA EL USUARIO
    String [] nameProducts;  
    Float [] priceProducts; 
    String [] descriptionProducts;
    Integer [] existenceProducts;
    public static Integer [] stockProducts = new Integer[100];
    public static HashMap<Integer, Integer> orele = new HashMap<Integer, Integer>();   //CREAMOS UN HASH MAP PARA GUARDAR LA CANTIDAD DE PRODUCTOS 

    

    public Menu(String[] nameProducts, Float[] priceProducts, String[] descriptionProducts, Integer[] existenceProducts){
        this.nameProducts = nameProducts;
        this.priceProducts = priceProducts;
        this.descriptionProducts = descriptionProducts;
        this.existenceProducts = existenceProducts;
    }
    
    public void updateProduct(String[] nameProducts,  Integer[] existenceProducts)
    {
        
        try{
            for(int i = 0; i< nameProducts.length; i++)
            {
                String select = "SELECT Existence FROM Product WHERE Name = \'" + nameProducts[i] + "\';";
                Connection cn = null; // VARIABLE DE CONEXION A BD
                PreparedStatement pst = null; // VARIABLE DE CONEXION A BD
                ResultSet rs = null; // VARIABLE DE CONEXION A BD
                cn = Conexion.conectar(); // VARIABLE DE CONEXION A BD
                pst = cn.prepareStatement(select); // VARIABLE DE CONEXION A BD
                rs = pst.executeQuery(); // EJECUCIÓN DEL QUERY DE SQL
                if (rs.next()) 
                { // INGRESAMOS VALORES A LOS ARRAYS
    
                    int stock = rs.getInt("Existence");
                    int res = stock - existenceProducts[i];
                    
                    //Actualizar
                    String update = "UPDATE Product SET Existence = \'" + res + "\' WHERE Name = \'" + nameProducts[i] + "\';";
                    Connection cn1 = null; // VARIABLE DE CONEXION A BD
                    PreparedStatement pst1 = null; // VARIABLE DE CONEXION A BD
                    ResultSet rs1 = null; // VARIABLE DE CONEXION A BD
                    cn1 = Conexion.conectar(); // VARIABLE DE CONEXION A BD
                    pst1 = cn1.prepareStatement(update); // VARIABLE DE CONEXION A BD
                    pst1.executeUpdate(); // EJECUCIÓN DEL QUERY DE SQL
                    
                  
                    
                }
    
    
            }
        }catch(Exception e)
        {
            System.out.println("ERROR AL ACTUALIZAR STOCK: " + e);
        }
        
    }
    
    public void generateReport(String[] names, Float[] prices, String[] descriptions, Integer[] stockProducts){   //FUNCION PARA HACER REPORTES 
        updateProduct(names, stockProducts);
        
        try{
            PDDocument document = new PDDocument();  //CREAMOS NUEVO DOCUMENTO
            PDPage page = new PDPage(PDRectangle.A6);  //CREAMOS UNA PAGINA 
            document.addPage(page); //AGREGAMOS LA PAGINA
            PDPageContentStream content = new PDPageContentStream(document, page); //CREAMOS OBJETO

            content.beginText();  //COMIENZO DE ESCRIBIR TEXTO
            content.setFont(PDType1Font.TIMES_BOLD,30);  //TAMAÑO Y TIPO DE LETRA
            content.newLineAtOffset(90, page.getMediaBox().getHeight()-50); //POSICION
            content .showText("TICKET"); //TEXTO
            content.endText();  //CERRAMOS EL TEXTO

            float total = 0.0f;   //FUNCION PARA OBTENER EL TOTAL DE LO COMPRADO
            for(int i = 0; i<this.nameProducts.length; i++){
                total = total + (this.priceProducts[i] * stockProducts[i]);
                
            }
 
            content.beginText();   //TEXTO PARA DEFNIR EL TOTAL
            content.setFont(PDType1Font.TIMES_BOLD,20);
            content.newLineAtOffset(33, page.getMediaBox().getHeight()-340);
            content .showText("TOTAL: $" + total);
            content.endText();

            content.beginText();   //CARACTERISTICAS DEL PRODUCTO
            content.setFont(PDType1Font.TIMES_BOLD,10);
            content.newLineAtOffset(33, page.getMediaBox().getHeight()-100);
            content .showText("Nombre          Precio          Descripción            Cantidad");
            content.endText();
            int counter = 10;
            for(int i = 0; i< names.length; i++) //RECORREMOS EL ARRAY QUE CONTIENE TODOS LOS DATOS DE LOS PRODUCTOS COMPRADOS
            {
                if(stockProducts[i] == 0)
                {
                    continue;
                }
                else
                {
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(33, page.getMediaBox().getHeight()-(130 + counter));  //NOMBRES DE LOS PRODUCTOS
                    content .showText(this.nameProducts[i]);
                    content.endText();
    
    
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(98, page.getMediaBox().getHeight()-(130 + counter));   //PRECIO DE LOS PRODUCTOS
                    content .showText("" + this.priceProducts[i]);
                    content.endText();
    
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(145, page.getMediaBox().getHeight()-(130 + counter));   //DESCRIPCION DE LOS PRODUCTOS
                    content .showText("" +this.descriptionProducts[i]);
                    content.endText();
    
                    content.beginText();
                    content.setFont(PDType1Font.TIMES_BOLD,10);
                    content.newLineAtOffset(240, page.getMediaBox().getHeight()-(130 + counter));  //CANTIDAD QUE EL USUARIO COMPRO
                    content .showText("" + stockProducts[i]);
                    content.endText();
    
                    counter = counter + 10;  //COUNTER PARA AUMETAR EL ESPECIO ENTRE PRODUCTO Y PRODUCTO
                }
    
                
            }
            content.close();  //CERRAMOS EL CONTENIDO
                
                

            document.save("/Users/miguelagc/Desktop/REDES 2/Project/Client/report.pdf");  //ESPECIFICAMOS LA RUTA


        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
    
    public void buyCar(int counterCar){   //FUNCION PARA COMPRAR EL CARRITO
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
                System.out.println(counter + ")  " + this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);  //MOSTRAMOS LOS PRODUCTOS
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
                        editCar(counterCar);  //MANDA A LLAMAR PARA EDITAR EL CARRITO
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
        for(Integer key:orele.keySet())   //LENAMOS UN HASH MAP CON UN ID Y EL NOMBRE DEL PRODUCTO
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
                System.out.println(i + ")  " + this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);  //MOSTRAMOS PRODUCTOS
            }
            
        }
        System.out.print("\nELIGE EL PRODUCTO PARA EDITAR UNIDADES: "); //SELECCION DEL PRODUCTO A EDITAR
        Scanner in = new Scanner(System.in);
        optionMenu = in.nextInt();
        if(optionMenu >4 || optionMenu<0){ //VALIDACION DE LOS DATOS
            System.out.println("\n\n**** OPCION INCORRECTA ****");
            editUnits(counterCar);
        }else{
            this.existenceProducts[optionMenu] = this.existenceProducts[optionMenu] + stockProducts[optionMenu]; //EL STOCK TOTAL SE REESTABLECE
            int cantUnit = 0;
            System.out.print("\nNUEVAS UNIDADES: "); //SELECCION DEL PRODUCTO A EDITAR
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
            inn.close();
        }


        in.close();
        
    }

    public void deleteProduct(int counterCar){  //FUNCION DE ELIMINAR EL PRODUCTO
        int optionMenu = 0;
        System.out.println("\n\n\n-------------- TIENDA ---------------");
        System.out.println("------------ ELIMINAR PRODUCTOS ------------\n");
        for(Integer key:orele.keySet())  ////LENAMOS UN HASH MAP CON UN ID Y EL NOMBRE DEL PRODUCTO
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
                System.out.println(i + ")  " + this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);  //MOSTRAMOS PRODUCTOS
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

        in.close();




    }
    
    public void editCar(int counterCar){   //FUNCION PARA EDITAR EL CARRITO
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
                System.out.println(this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]); //MOSTRAMOS LOS PRODUCTOS
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
                        editUnits(counterCar); //MANDA A LLAMAR LA FUNCION QUE EDITA EL PRODUCTO AGREGADO AL CARRITO
                        break;
                    case 2: 
                        addProduct(counterCar);  //AGREGAR PRODUCTOS
                        break;
                    case 3:
                        deleteProduct(counterCar); //ELIMINAR PRODUCTOS
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

    public void showCar(int counterCar){   //FUNCION PARA MOSTRAR LOS PRODUCTOS QUE LLEVA EL USUARIO EN EL  CARRITO
        if(counterCar == 0)
        {
            System.out.println("\n\n\n---------------- TIENDA ----------------");
            System.out.println("-------------- CARRITO VACIO ---------------\n");
        }
        else
        {
            System.out.println("\n\n\n---------------- TIENDA ----------------");
            System.out.println("-------------- TU CARRITO ---------------\n");
            System.out.println("Nombre \t\tPrecio \tDescripcion \t\tUnidades\n");   //MOSTRAMOS LOS PRODUCTOS
            int counter = 0;
            for(int i = 0; i<nameProducts.length; i++)
            {
                if(stockProducts[i] == 0)  //COMO EN LOS ARRAY HAY ESPACIOS EN 0 SE HACE UNA VERIFICACION
                {
                    continue;
                }
                else
                {
                    counter = counter + 1;
                    System.out.println(this.nameProducts[i] + "\t\t" + this.priceProducts[i] + "\t\t" + this.descriptionProducts[i] + "\t\t" + stockProducts[i]);  //MOSTRAMOS PRODUCTOS
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
                        editCar(counterCar);  //MANDA A LLAMAR PARA EDITAR EL CARRITO
                        break;
                    case 3: 
                        buyCar(counterCar); //MANDA A LLAMR PARA FINALIZAR LA COMPRA
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
                if(p.getExistence() == 0)
                {
                    continue;
                }
                else
                {
                    System.out.println((i+1) + ")   " + p.getName() + "\t\t  " + p.getPrice() + "\t\t\t" + p.getDescription() + "\t\t   " + p.getExistence()); //  //SE MUESTRAN  LOS OBJETOS
                }
                

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
                        addProduct(counterCar); //MANDA A LLAMAR A AGREGAR LOS PRODUCTOS
                        break;
                    case 2: 
                        showCar(counterCar);  //MANDA A LLAMAR PARA MOSTRAR EL CARRITO
                        break;
                    case 3: 
                        buyCar(counterCar);  //MANDA A LLAMAR PARA CONCLUIR LA COMPRA 
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
