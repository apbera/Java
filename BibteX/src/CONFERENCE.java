/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Conference category.
 * Contains required and optional fields of Conference.
 */
public class CONFERENCE extends Entry{
    private String [] required_fields={"author", "title", "booktitle", "year"};
    private String [] optional_fields={"volume|number", "editor", "series", "pages", "address", "month", "organization", "publisher", "note", "key"};
    CONFERENCE(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
