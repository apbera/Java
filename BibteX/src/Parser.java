import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Parser class is used to separate all Entries and save them into the objects.
 * Main method of this class is parse.
 */
public class Parser {

    private BufferedReader fileReader=null;
    private Map<String, Entry> records=new HashMap<>();
    private List<String>varStrings=new ArrayList<>();
    Parser(BufferedReader fileReader){
        this.fileReader=fileReader;
    }

    private void addVar(String varStr){
        this.varStrings.add(varStr);
    }
    /*
     * Method beginingOfRecord checks if the given line is from the begining of a new entry
     * @param line It's the line from loaded bibtex file
     * @return True, if given line is from the begining of entry, otherwise returns False
     */
    private boolean beginingOfRecord(String line){
        Pattern compiledPattern=Pattern.compile("@.+\\{.+,");
        Matcher matcher=compiledPattern.matcher(line);
        if(matcher.find()) return true;

        return false;
    }
    /*
     * Method endOfRecord checks if the given line is the end of record which was parsed
     * @param line It's the line from loaded bibtex file
     * @return True, if given line is from the end of entry, otherwise returns False
     */
    private boolean endOfRecord(String line){
        Pattern compiledPattern=Pattern.compile("}");
        Matcher matcher=compiledPattern.matcher(line);
        if(matcher.find()) return true;

        return false;
    }

    /*
     * stringVar sprawdza wczytaną linijke pliku i mówi, czy linijka zawiera zmienną @String
     * @param line Linia z wczytanego pliku
     * @return True, gdy jest to @String i False, gdy nie
     */
    private boolean stringVar(String line) {
        Pattern compiledPattern=Pattern.compile("@STRING");
        Matcher matcher=compiledPattern.matcher(line.toUpperCase());
        if(matcher.find()) return true;

        return false;
    }

    /*
     * Sprawdza, czy wartość jest zmienną @String
     * @param value wartość sprawdzanego pola
     * @return True, gdy pole to zmienna i False, gdy nie
     */
    private boolean isVar(String value){
        Pattern compiledPattern=Pattern.compile("\"");
        Matcher matcher=compiledPattern.matcher(value);
        if(matcher.find()) return false;
        else {
            compiledPattern = Pattern.compile("\\d+");
            Matcher matcher1 = compiledPattern.matcher(value);
            if(matcher1.matches()) return false;
        }

        return true;
    }

    /*
     * Zwraca wartość zmiennej @String
     * @param value dostaje zmienną @String
     * @return zwraca wartość zmiennej @String
     */
    private String getVarValue(String value){
        Pattern compiledPattern=Pattern.compile(value+"\\s*=.*");
        for (String el:varStrings) {
            Matcher matcher=compiledPattern.matcher(el);
            if(matcher.matches()){
                StringTokenizer PLine=new StringTokenizer(el, "=");
                PLine.nextElement();
                String result=(String) PLine.nextElement();
                result=result.trim();
                return result;
            }
        }
        return null;
    }
    /*
     * Tworzy klase w zależności od typu
     * @param type dostaje typ Rekordu
     * @return zwraca klase danego typu
     */
    private Entry checkType(String type, String key) {
        switch (type) {
            case "ARTICLE":
                return new ARTICLE(type, key);
            case "BOOK":
                return new BOOK(type, key);
            case "BOOKLET":
                return new BOOKLET(type, key);
            case "CONFERENCE":
                return new CONFERENCE(type, key);
            case "INBOOK":
                return new INBOOK(type, key);
            case "INCOLLECTION":
                return new INCOLLECTION(type, key);
            case "MANUAL":
                return new MANUAL(type, key);
            case "MASTERTHESIS":
                return new MASTERSTHESIS(type, key);
            case "MISC":
                return new MISC(type, key);
            case "INPROCEEDINGS":
                return new INPROCEEDINGS(type, key);
            case "PHDTHESIS":
                return new PHDTHESIS(type, key);
            case "TECHREPORT":
                return new TECHREPORT(type, key);
            case "UNPUBLISHED":
                return new UNPUBLISHED(type, key);
            default:
                return null;
        }
    }
    /**
     * Method parse is used to separate fields in the entries from loaded bibtex file and saves them as objects.
     * All the entries are saved in HashMap.
     * All the fields are saved as a name of field and value.
     * If there is a lack of required fields in entry, then method is throwing IllegalArgumentException.
     * Before the beginning of a new record is checked, we can find String variable in loaded file.
     * String variables are saved in ArrayList and later the method is adding them to correct fields.
     */
    public void parse() throws IOException {
        String line=fileReader.readLine();
        StringTokenizer PLine;
        while(line!=null) {
            if (stringVar(line)){
                PLine = new StringTokenizer(line, "{()}");
                PLine.nextElement();
                String tmp=(String) PLine.nextElement();
                tmp=tmp.replaceAll("\"", "");
                addVar(tmp.trim());
            }
            if (beginingOfRecord(line)) {
                //Stworzenie nowego obiektu Entry i przypisanie do niego typu oraz klucza
                PLine = new StringTokenizer(line, "{");
                String type=(String) PLine.nextElement();
                String key=(String) PLine.nextElement();
                type=type.replaceAll("@","");
                key=key.replaceAll(",","");
                type=type.trim();
                key=key.trim();
                type=type.toUpperCase();
                Entry record=checkType(type, key);
                line=fileReader.readLine();
                while (line != null && !endOfRecord(line)) {
                    line = line.trim();//Usunięcie niepotrzebnych białych znaków
                    PLine = new StringTokenizer(line, "=");//Podzielenie względem znaku =
                    String name = (String) PLine.nextElement();
                    name=name.trim();
                    int i;
                    if (record.isRequired(name)) {
                        i = 1;
                    } else if (record.isOptional(name)){
                        i = 2;
                    }
                    else if (name.equals("crossref")){
                        i=3;
                    }
                    else{
                        i=0;
                    }
                    if(i!=0) {
                        String value = (String) PLine.nextElement();
                        value = value.replaceAll(",", "");
                        value = value.trim();
                        if(name.equals("crossref")){
                            value=value.replaceAll("\"", "");
                            if(records.containsKey(value)){
                                Entry crossref=records.get(value);
                                for (Map.Entry<String, String> entry2 : crossref.getRequired().entrySet()){
                                    String key2=entry2.getKey();
                                    String val2=entry2.getValue();
                                    //if(!record.getRequired().containsKey(key2))
                                        record.addField(entry2.getKey(), entry2.getValue(),1);
                                }
                                for (Map.Entry<String, String> entry2 : crossref.getOptional().entrySet()){
                                    String key2=entry2.getKey();
                                    String val2=entry2.getValue();
                                    //if(!record.getOptional().containsKey(key2))
                                        record.addField(entry2.getKey(), entry2.getValue(),2);
                                }
                            }else{
                                throw new IllegalArgumentException("Nie mozna znalezc rekordu dla crossref");
                            }
                        }
                        else if (isVar(value)) {
                            value = getVarValue(value);
                            record.addField(name, value, i);
                        } else if (isComplex(value)){
                            value = getComplexValue(value);
                            value=value.replaceAll("\"", "");//usunięcie niepotrzebnych znaków
                            record.addField(name, value, i);
                        }else {
                            value = value.replaceAll("\"", "");//usunięcie niepotrzebnych znaków
                            record.addField(name, value, i);
                        }
                    }
                    line=fileReader.readLine();
                }
                if(!record.checkRequired()) throw new IllegalArgumentException("Brak wszystkich wymaganych pol");
                records.put(key, record);//dodanie nowego rekordu do listy rekordów
                record.editorAuthor();
            }
            line = fileReader.readLine();
        }
    }

    /*
     * Zwraca linie bez #, łączy wszystkie wartości w jednego stringa
     * @param value Wczytana linia tekstu
     * @return Zwraca połączonego Stringa
     */
    private String getComplexValue(String value) {
        String [] values=value.split("#");
        StringBuilder build=new StringBuilder();
        for (String str:values) {
            String tmp=str.trim();
            if(isVar(tmp)){
                tmp=getVarValue(tmp);
            }
            build.append(tmp);
        }
        return build.toString();
    }

    /*
     * Sprawdza, czy wartość zawiera #
     * @param value
     * @return True, gdy linia zawiera #
     */
    private boolean isComplex(String value) {
        Pattern compiledPattern=Pattern.compile("#");
        Matcher matcher=compiledPattern.matcher(value);
        if(matcher.find()) return true;

        return false;
    }

    /**
     * Method toString without arguments is used give all entries that are saved in HashMap
     * This method is using other method toString from Entry class.
     * @return All entries in the HashMap are returned as one String.
     */
    public String toString(){
        StringBuilder result=new StringBuilder();
        for (Map.Entry<String, Entry> entry : records.entrySet()) {
            Entry value = entry.getValue();
            result.append(value.toString());
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Method toString with arguments is used to give entries which are demanded by user.
     * This method includes giving entries with given category and authors.
     * If array args is empty, then method returns help to a user.
     * @param args Arguments from the main, given by a user
     * @return All entries in the HashMap,that meet the conditions, are returned as one String. Help if arguments are not given.
     */
    public String toString(String [] args){
        StringBuilder result=new StringBuilder();
        if(args.length==1){
            return toString();
        }
        else if(args[1].toLowerCase().equals("category")){
            for(int i=2; i<args.length; i++){
                for (Map.Entry<String, Entry> entry : records.entrySet()) {
                    Entry value = entry.getValue();
                    if((args[i].toUpperCase()).equals(value.getType())) {
                        result.append(value.toString());
                        result.append("\n");
                    }
                }
            }
        }
        else if(args[1].toLowerCase().equals("year")){
            args[1].toLowerCase();
            for(int i=2; i<args.length; i++){
                for (Map.Entry<String, Entry> entry : records.entrySet()) {
                    Entry value = entry.getValue();
                    if(value.isField(args[1],args[i])) {
                        result.append(value.toString());
                        result.append("\n");
                    }
                }
            }
        }
        return result.toString();
    }

}
