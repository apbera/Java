/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Incollection category.
 * Contains required and optional fields of Incollection.
 */
public class INCOLLECTION extends Entry {
    private String [] required_fields={"author", "title", "booktitle", "publisher", "year"};
    private String [] optional_fields={"volume|number", "editor", "series", "type", "chapter", "pages", "address", "edition", "month", "note", "key"};
    INCOLLECTION(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
