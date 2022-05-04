package Project;

public class Product {
    public String name;
    public float price;
    public String description;
    public int existence;

    public Product(String name, float price, String description, int existence)
    {
        this.name = name;
        this.price = price;
        this.description = description;
        this.existence = existence;

        
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(Float price){
        this.price = price;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getExistence(){
        return existence;
    }

    public void setExistence(int existence){
        this.existence = existence;
    }
    
}
