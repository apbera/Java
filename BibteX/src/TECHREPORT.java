/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Techreport category.
 * Contains required and optional fields of Techreport.
 */
public class TECHREPORT extends Entry {
    private String [] required_fields={"author", "title", "institution", "year"};
    private String [] optional_fields={"volume|number", "editor", "series", "address", "month", "organization", "publisher", "note", "key"};
    TECHREPORT(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
