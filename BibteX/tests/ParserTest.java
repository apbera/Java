import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class ParserTest {
    private String filePath="C:\\PO\\pliktestowy.bib";


    @Test
    public void parserExampleTest() throws IOException {
        BufferedReader fileReader= null;
        try {
            fileReader = new BufferedReader(new FileReader(filePath));
            Parser parser=new Parser(fileReader);
            parser.parse();
            System.out.println(parser.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(fileReader!=null) {
                fileReader.close();
            }
        }
    }

    @Test
    public void toStringByCategory() throws IOException {
        BufferedReader fileReader= null;
        try {
            fileReader = new BufferedReader(new FileReader(filePath));
            Parser parser=new Parser(fileReader);
            parser.parse();
            String [] args={filePath ,"category","article"};
            System.out.println(parser.toString(args));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(fileReader!=null) {
                fileReader.close();
            }
        }
    }

    @Test
    public void toStringByAuthor() throws IOException {
        BufferedReader fileReader= null;
        try {
            fileReader = new BufferedReader(new FileReader(filePath));
            Parser parser=new Parser(fileReader);
            parser.parse();
            String [] args={filePath, "author","Donald E. Knuth"};
            System.out.println(parser.toString(args));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(fileReader!=null) {
                fileReader.close();
            }
        }
    }
}