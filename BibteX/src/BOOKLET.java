/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Booklet category.
 * Contains required and optional fields of Booklet.
 */
public class BOOKLET extends Entry {
    private String [] required_fields={"title"};
    private String [] optional_fields={"author", "howpublished", "address", "month", "year", "note", "key"};

    BOOKLET(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}
