

class Car extends Menu{
    String [] carNameProducts;  
    Float [] carPriceProducts; 
    String [] carDescriptionProducts;
    Integer [] carStockProducts;

    

    public Car(String[] carNameProducts, Float[] carPriceProducts, String[] carDescriptionProducts, Integer[] carExistenceProducts) {
        super(carNameProducts, carPriceProducts, carDescriptionProducts, carExistenceProducts);
        this.carNameProducts = carNameProducts;
        this.carPriceProducts = carPriceProducts;
        this.carDescriptionProducts = carDescriptionProducts;
        this.carStockProducts = carStockProducts;
    }


    public String[] getNames(){
    
        return this.carNameProducts;
    } 

    public Float[] getPrices(){
    
        return this.carPriceProducts;
    } 
    public String[] getDescriptions(){
    
        return this.carDescriptionProducts;
    } 
    public Integer[] getexistences(){
    
        return this.carStockProducts;
    } 
    



}
