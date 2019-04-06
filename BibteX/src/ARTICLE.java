/**
 * Class which extends abstract class Entry.
 * It's used to describe Entry of Article category.
 * Contains required and optional fields of Article.
 */
public class ARTICLE extends Entry {
    private String [] required_fields={"author", "title", "journal", "year"};
    private String [] optional_fields={"volume", "number", "pages", "month", "note", "key"};

    ARTICLE(String type, String key){
        super.type=type;
        super.key=key;
        super.required_fields=this.required_fields;
        super.optional_fields=this.optional_fields;
    }

}
