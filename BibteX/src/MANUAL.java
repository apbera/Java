/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Manual category.
 * Contains required and optional fields of Manual.
 */
public class MANUAL extends Entry {
    private String[] required_fields = {"title"};
    private String[] optional_fields = {"author", "organization", "address", "edition", "month", "year", "note", "key"};
    MANUAL(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }
}

