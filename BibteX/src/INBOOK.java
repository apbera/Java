/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Inbook category.
 * Contains required and optional fields of Inbook.
 */
public class INBOOK extends Entry {
    private String [] required_fields={"author|editor", "title", "publisher", "year"}; //chapter lub pages
    private String [] optional_fields={"series", "type", "address", "edition", "month", "note", "key"};//volume lub number
    INBOOK(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
