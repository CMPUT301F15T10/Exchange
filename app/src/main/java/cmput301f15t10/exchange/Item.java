package cmput301f15t10.exchange;

/**
 * Created by touqir on 29/10/15.
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
