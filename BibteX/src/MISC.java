/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Misc category.
 * Contains required and optional fields of Misc.
 */
public class MISC extends Entry {
    private String [] required_fields={};
    private String [] optional_fields={"author", "title", "howpublished", "month", "year", "note", "key"};
    MISC(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
