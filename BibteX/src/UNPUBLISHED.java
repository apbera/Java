/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Unpublished category.
 * Contains required and optional fields of Unpublished.
 */
public class UNPUBLISHED extends Entry {
    private String [] required_fields={"author", "title", "note"};
    private String [] optional_fields={"month", "year", "key"};
    UNPUBLISHED(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
