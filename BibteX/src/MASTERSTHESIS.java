/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Masterthesisi category.
 * Contains required and optional fields of Masterthesis.
 */
public class MASTERSTHESIS extends Entry {
    private String [] required_fields={"author", "title", "school", "year"};
    private String [] optional_fields={"type", "address", "month", "note", "key"};
    MASTERSTHESIS(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
