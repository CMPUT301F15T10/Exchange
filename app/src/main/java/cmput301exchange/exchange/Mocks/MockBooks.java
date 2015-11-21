package cmput301exchange.exchange.Mocks;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;

/**
 * Created by touqir on 20/11/15.
 */

// This has a list of sharable and non sharable books intended to be used for testing trading use cases.
public class MockBooks {
    private ArrayList<Book> sharableBooks = new ArrayList<>();
    private ArrayList<Book> nonSharableBooks = new ArrayList<>();

    public MockBooks(){
        Book book1=new Book();
        book1.updateTitle("Book1");
        book1.updateAuthor("Author1");
        book1.updateQuantity(1);
        book1.updateCategory("None");
        book1.updateComment("Comment1");
        book1.updateQuality(1);
        book1.setShareable(true);

        Book book2=new Book();
        book2.updateTitle("Book2");
        book2.updateAuthor("Author2");
        book2.updateQuantity(1);
        book2.updateCategory("Category1");
        book2.updateComment("Comment2");
        book2.updateQuality(2);
        book2.setShareable(true);

        Book book3=new Book();
        book3.updateTitle("Book3");
        book3.updateAuthor("Author3");
        book3.updateQuantity(1);
        book3.updateCategory("Category3");
        book3.updateComment("Comment3");
        book3.updateQuality(3);
        book3.setShareable(true);

        Book book4=new Book();
        book4.updateTitle("Book4");
        book4.updateAuthor("Author4");
        book4.updateQuantity(1);
        book4.updateCategory("Category4");
        book4.updateComment("Comment4");
        book4.updateQuality(4);
        book4.setShareable(true);

        sharableBooks.add(book1);
        sharableBooks.add(book2);
        sharableBooks.add(book3);
        sharableBooks.add(book4);

        Book book5=new Book();
        book5.updateTitle("Book5");
        book5.updateAuthor("Author5");
        book5.updateQuantity(1);
        book5.updateCategory("Category5");
        book5.updateComment("Comment5");
        book5.updateQuality(5);
        book5.setShareable(false);

        Book book6=new Book();
        book6.updateTitle("Book6");
        book6.updateAuthor("Author6");
        book6.updateQuantity(1);
        book6.updateCategory("Category6");
        book6.updateComment("Comment6");
        book6.updateQuality(5);
        book6.setShareable(false);

        Book book7=new Book();
        book7.updateTitle("Book7");
        book7.updateAuthor("Author7");
        book7.updateQuantity(1);
        book7.updateCategory("Category7");
        book7.updateComment("Comment7");
        book7.updateQuality(5);
        book7.setShareable(false);

        Book book8=new Book();
        book8.updateTitle("Book8");
        book8.updateAuthor("Author8");
        book8.updateQuantity(1);
        book8.updateCategory("Category8");
        book8.updateComment("Comment8");
        book8.updateQuality(5);
        book8.setShareable(false);

        nonSharableBooks.add(book5);
        nonSharableBooks.add(book6);
        nonSharableBooks.add(book7);
        nonSharableBooks.add(book8);

    }

    public ArrayList<Book> getSharableBooks(){
        return sharableBooks;
    }

    public ArrayList<Book> getNonSharableBooks(){
        return nonSharableBooks;
    }
}
