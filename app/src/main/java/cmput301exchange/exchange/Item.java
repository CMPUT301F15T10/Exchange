package cmput301exchange.exchange;

/**
 * @deprecated
 * old class that was originally intended to be a superclass of book.
 * There was no point in having a superclass with only one object inhereting.
 */
public class Item {


    private String comment=null,Name=null,Type=null; // should be null
    private Double Quality=null;
    private Integer Quantity=null;

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment=comment;
    }

    public void saveItem(){
        // Logic for sav
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getQuality() {
        return Quality;
    }

    public String getQuality_String(){
        return Quality.toString();
    }

    public void setQuality(Double quality) {
        Quality = quality;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public String getQuantity_String(){
        return Quality.toString();
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
