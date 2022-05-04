package Project;

class Car extends Product{
    int total;
    
    public Car(String name, float price, String description, int existence) {
        super(name, price, description, existence);
        
    }

    

    public int showTotal(){
        return total;
    }
    
}
