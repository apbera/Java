import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader fileReader=null;
        try{
            if(args.length==0) {
                System.out.println("First argument of program have to be a filepath to the bibtex file\n" +
                        "Second argument:\nif there is no 2nd argument, then print all the entries" +
                        "\ncategory-prints entries which belong to given categories" +
                        "\nauthor-prints entries where the authors are given arguments after the 2nd\n" +
                        "Other arguments from 3rd are arguments for category or author");
            }
            else{
                fileReader=new BufferedReader(new FileReader(args[0]));
                Parser parser=new Parser(fileReader);
                parser.parse();
                System.out.println(parser.toString(args));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileReader!=null){
                fileReader.close();
            }
        }



    }
}
