package Project;

class Car extends Menu{

    public Car(String[] nameProducts, Float[] priceProducts, String[] descriptionProducts, Integer[] existenceProducts) {
        super(nameProducts, priceProducts, descriptionProducts, existenceProducts);
        this.nameProducts = nameProducts;
        this.priceProducts = priceProducts;
        this.descriptionProducts = descriptionProducts;
        this.existenceProducts = existenceProducts;
    }


    public String[] getNames(){
        
        return this.nameProducts;
    } 



}
