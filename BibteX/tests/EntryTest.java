import org.junit.Test;

import java.io.BufferedReader;

import static org.junit.Assert.*;

public class EntryTest {
    private String filePath="C:\\PO\\pliktestowy.bib";
    BufferedReader fileReader= null;
    @Test
    public void isRequiredTest() {
        Entry entry=new BOOK("BOOK", "whole-collection");
        assertTrue(entry.isRequired("title"));
        Entry entry2=new ARTICLE("ARTICLE", "article-full");
        assertFalse(entry2.isRequired("volume"));
    }

    @Test
    public void isOptionalTest() {
        Entry entry=new BOOK("BOOK", "whole-collection");
        assertTrue(entry.isOptional("volume"));
        Entry entry2=new ARTICLE("ARTICLE", "article-full");
        assertFalse(entry.isOptional("title"));
    }

    @Test
    public void checkRequiredTest() {
        Entry entry=new BOOK("BOOK","whole-collection");
        entry.addField("author", "Donald E. Knuth", 1);
        entry.addField("title", "Seminumerical Algorithms", 1);
        entry.addField("publisher", "Addison-Wesley", 1);
        assertFalse(entry.checkRequired());
        entry.addField("year", "1981", 1);
        assertTrue(entry.checkRequired());
    }

    @Test
    public void editorAuthorTest() {
        Entry entry=new BOOK("BOOK","whole-collection");
        entry.addField("author", "Lipcoll | Jr |David J. and Lawrie | David H. and A. H. Sameh", 1);
        entry.editorAuthor();
        System.out.println(entry.toString());
    }

    @Test
    public void getTypeTest() {
        Entry entry=new BOOK("BOOK","whole-collection");
        assertEquals("BOOK", entry.getType());
    }

    @Test
    public void getKeyTest() {
        Entry entry=new BOOK("BOOK","whole-collection");
        assertEquals("whole-collection", entry.getKey());
    }

    @Test
    public void addIsFieldTest() {
        Entry entry=new BOOK("BOOK","whole-collection");
        entry.addField("title", "Seminumerical Algorithms", 1);
        assertTrue(entry.isField("title", "Seminumerical Algorithms"));
    }

    @Test
    public void toStringTest() {
        Entry entry=new BOOK("BOOK","whole-collection");
        entry.addField("title", "Seminumerical Algorithms", 1);
        entry.addField("author", "Donald E. Knuth", 1);
        entry.addField("year","1981",1);
        entry.addField("publisher","Addison-Wesley",1);
        entry.addField("volume", "2", 0);
        entry.addField("edition", "Second", 0);
        System.out.println(entry.toString());
    }
}
