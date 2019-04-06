import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class Entry is used to describe every entry in bibtex file.
 * It contains methods which are checking if fields are correct and fixes authors or editors.
 */
public abstract class Entry {
    /**
     * Variable type is for the category of entry
     */
    protected String type;
    /**
     * Variable key is for the key of entry
     */
    protected String key;
    /**
     * required_fields array contains required fields for this entry.
     */
    protected String [] required_fields;
    /**
     * optional_fields array contains optional fields for this entry.
     */
    protected String [] optional_fields;

    private Map<String, String>requiredFields=new LinkedHashMap<>();
    private Map<String, String>optionalFields=new LinkedHashMap<>();

    private Pattern optionPattern;
    private Matcher optionMatcher;
    private StringTokenizer PLine;

    /**
     * Method which returns type of entry.
     * @return type of this entry
     */
    public String getType(){
        return type;
    }

    /**
     * Method which returns key of entry.
     * @return key of this entry
     */
    public String getKey(){
        return key;
    }

    /**
     * Method which returns a map of required fields
     * @return map of required fields
     */
    public Map<String, String> getRequired(){
        return requiredFields;
    }

    /**
     * Method which returns a map of optional fields
     * @return map of optional fields
     */
    public Map<String, String> getOptional(){
        return optionalFields;
    }
    /**
     * Method which is used to check, if given field is required for this entry.
     * @param name - name of field
     * @return True, if field is required. False otherwise.
     */
    public boolean isRequired(String name){
        for (String el:required_fields) {
            optionPattern=Pattern.compile(name);
            optionMatcher=optionPattern.matcher(el);
            if(optionMatcher.find()) return true;
        }
        return false;
    }

    /**
     * Method which is used to check, if given field is optional for this entry.
     * @param name - name of field
     * @return True, if field is optional. False otherwise.
     */
    public boolean isOptional(String name){
        for (String el:optional_fields) {
            optionPattern=Pattern.compile(name);
            optionMatcher=optionPattern.matcher(el);
            if(optionMatcher.find()) return true;
        }
        return false;
    }

    /**
     * Method which add given field to LinkedHashMap
     * @param name - name of field
     * @param value - value of field
     * @param i - 1 if field is required, 2 if field is optional
     */
    public void addField(String name, String value, int i){
        if(i==1) requiredFields.put(name, value);
        else optionalFields.put(name, value);
    }

    /**
     * Method which checks if this entry have the field given in parameters.
     * @param search - key of wanted field
     * @param val - value of wanted field
     * @return True if field exists, otherwise False.
     */
    public boolean isField(String search, String val){
        if(requiredFields.containsKey(search)){
            String tmp=requiredFields.get(search);
            if(search.equals("author") || search.equals("editor")){
                optionPattern=Pattern.compile(val);
                optionMatcher=optionPattern.matcher(tmp);
                if(optionMatcher.find()) return true;
            }
            else if(tmp.equals(val)){
                return true;
            }
        }
        if(optionalFields.containsKey(search)){
            String tmp=optionalFields.get(search);
            if(search.equals("author") || search.equals("editor")){
                optionPattern=Pattern.compile(tmp);
                optionMatcher=optionPattern.matcher(val);
                if(optionMatcher.find()) return true;
            }
            else if(tmp.equals(val)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to check, if this entry has all the required fields.
     * @return True, if entry has all required fields. False otherwise.
     */
    public boolean checkRequired(){
        for (String name:required_fields) {
            optionPattern=Pattern.compile("\\|");
            optionMatcher=optionPattern.matcher(name);
            if(optionMatcher.find()){
                PLine=new StringTokenizer(name, "|");
                String tmp1=(String) PLine.nextElement();
                String tmp2=(String) PLine.nextElement();
                if(!requiredFields.containsKey(tmp1) && !requiredFields.containsKey(tmp2));
            } else if(!requiredFields.containsKey(name))return false;
        }
        return true;
    }

    /**
     * Method which is used to re-write entire entry to one String.
     * @return String which contains entire entry.
     */
    public String toString(){
        StringBuilder entry=new StringBuilder();
        String fieldFormat="| %-15s | %-60s | %n";
        String typeFormat="| %-78s | %n";
        entry.append(String.format("|--------------------------------------------------------------------------------|\n"));//65
        entry.append(String.format(typeFormat, getType()+" ("+getKey()+")"));
        entry.append(String.format("|--------------------------------------------------------------------------------|\n"));//65
        for (Map.Entry<String, String> entry2 : requiredFields.entrySet()) {
            String key = entry2.getKey();
            String value = entry2.getValue();
            if(key.equals("author") || key.equals("editor")){
                boolean first=true;
                String [] names=value.split(",");
                for (String name:names) {
                    if(first) entry.append(String.format(fieldFormat, key, name));
                    else entry.append(String.format(fieldFormat, "", name));
                    first=false;
                }
            }
            else {
                if(value.length()>60){
                    String fieldName=key;
                    int i;
                    for(i=0; i<value.length()/60; i++){
                        entry.append(String.format(fieldFormat, fieldName, value.substring(i*60,i*60+59)));
                        fieldName="";
                    }
                    entry.append(String.format(fieldFormat, "", value.substring(i*60, i*60+value.length()%60)));
                }
                else{
                    entry.append(String.format(fieldFormat, key, value));
                }
            }
            entry.append(String.format("|--------------------------------------------------------------------------------|\n"));
        }
        for (Map.Entry<String, String> entry2 : optionalFields.entrySet()) {
            String key = entry2.getKey();
            String value = entry2.getValue();
            if(key.equals("author") || key.equals("editor")){
                boolean first=true;
                String [] names=value.split(",");
                for (String name:names) {
                    if(first) entry.append(String.format(fieldFormat, key, name));
                    else entry.append(String.format(fieldFormat, "", name));
                    first=false;
                }
            }
            else {
                if(value.length()>60){
                    String fieldName=key;
                    int i;
                    for(i=0; i<value.length()/60; i++){
                        entry.append(String.format(fieldFormat, fieldName, value.substring(i*60,i*60+59)));
                        fieldName="";
                    }
                    entry.append(String.format(fieldFormat, "", value.substring(i*60, i*60+value.length()%60)));
                }
                else{
                    entry.append(String.format(fieldFormat, key, value));
                }
            }
            entry.append(String.format("|--------------------------------------------------------------------------------|\n"));
        }
        return entry.toString();
    }

    /**
     * Method which is used to re-write the authors or editors to an acceptable form.
     * For each editor or author is used other method fixAuthorEditor.
     */
    public void editorAuthor(){
        if(requiredFields.containsKey("author")){
            requiredFields.put("author",fixAuthorEditor(requiredFields.get("author")));
            return;
        }
        if(requiredFields.containsKey("editor")){
            requiredFields.put("editor",fixAuthorEditor(requiredFields.get("editor")));
            return;
        }
        if(optionalFields.containsKey("author")){
            optionalFields.put("author",fixAuthorEditor(optionalFields.get("author")));
            return;
        }
        if(optionalFields.containsKey("editor")){
            optionalFields.put("editor",fixAuthorEditor(optionalFields.get("editor")));
            return;
        }
    }

    private String fixAuthorEditor(String value){
        String [] names=value.split(" and ");
        StringBuilder fullAuthor=new StringBuilder();
        int i=0;
        boolean first=true;
        while(i<names.length){
            if(!first) fullAuthor.append(",");
            optionPattern=Pattern.compile("\\|");
            String name=names[i];
            optionMatcher=optionPattern.matcher(name);
            String fullname;
            if(optionMatcher.find()){
                String [] name2=name.split("\\|");
                if(name2.length==3) fullname=name2[2].trim()+" "+name2[0].trim()+" "+name2[1].trim();
                else fullname=name2[1].trim()+" "+name2[0].trim();
            }else{
                fullname=name.trim();
            }
            fullAuthor.append(fullname);
            i++;
            first=false;
        }
        return fullAuthor.toString();
    }

}
