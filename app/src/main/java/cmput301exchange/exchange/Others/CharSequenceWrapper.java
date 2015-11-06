package cmput301exchange.exchange.Others;

/**
 * Created by touqir on 29/10/15.
 */
public class CharSequenceWrapper implements CharSequence{
    private String text;

    public CharSequenceWrapper(String text) {
        this.text=text;
    }

    public void setText(String text){
        this.text=text;
    }

    public String toString(){
        return text;
    }

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public char charAt(int i) {
        try {
            return text.charAt(i);
        }
        catch(IndexOutOfBoundsException e){
            throw new RuntimeException("Index out of range from CharSequenceWrapper object,charAt method");
        }
    }

    @Override
    public CharSequence subSequence(int i, int i1) {
        try {
            return new CharSequenceWrapper(text.substring(i, i1));
        }
        catch(IndexOutOfBoundsException e){
            throw new RuntimeException("Indices out of range from CharSequenceWrapper object,subSequence method");
        }
    }
}
