package com.cosmos.util;

/**
 * Created	:	15.04.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Formats given string according to another 'formatting' string.
 * The formatting string is composed from '#' characters and any other characters.
 * When formatting the '#' characters are replaced by the corresponding 'source'
 * string characters. This is done in the same order.
 * For example:
 * Formatting string - '#-###-(##)'
 * Input '123456' results in '1-234-(56)'
 * Input 'abcdefghijk' results in 'a-bcd-(efghijk)'
 * Input 'goon' results in 'g-oon-(XX)' where 'X' is configurable placehold 
 * character.
 * 
 */
public class CodeFormatter {
    public static char DEFAULT_PLACEHOLDER = 'X';
    
    private static char WILD = '#';
    
    private String format;
    private char placeholder = DEFAULT_PLACEHOLDER;
    
    /**
     * @param format
     * @param placeholder
     */
    public CodeFormatter(String format, char placeholder) {
        super();
        this.format = format;
        this.placeholder = placeholder;
    }
    
    /**
     * @param format
     */
    public CodeFormatter(String format) {
        super();
        this.format = format;
    }
    
    /** Formats given string according to another 'formatting' string.
     * The formatting string is composed from '#' characters and any other characters.
     * While formatting, the '#' characters are replaced by the corresponding 'source'
     * string characters. This is done one-by-one in order, from left to right.
     * For example:
     * Formatting string - '#-###-(##)'
     * Input '123456' results in '1-234-(56)'
     * Input 'abcdefghijk' results in 'a-bcd-(efghijk)'
     * Input 'goon' results in 'g-oon-(XX)' where 'X' is configurable placehold 
     * character.
     */
    public String getDisplayValue(String sourceString){
        return format(sourceString);
    }
    
    private String format(String src) {
        
        StringBuilder f = new StringBuilder(format);
        int srcIdx = 0;
        int lastWildIdx = f.lastIndexOf(""+WILD);
        for (int i = 0; i < f.length(); i++) {
            if ( f.charAt(i)==WILD ){
                //the source string ended
                if ( srcIdx>=src.length() ){
                    replaceWithPlaceHolder(f, i);
                    break;
                //the format string ended so don't bother to continue
                }else if ( i>lastWildIdx ){
                    break;
                //still working
                }else{
                    f.replace(i, i+1, ""+src.charAt(srcIdx));
                    srcIdx++;
                }
            }
        }
        
        //if still remaining source characters - append them at the index of
        //the last WILD (if available)
        if ( srcIdx<src.length() && lastWildIdx!=-1 ){
            
            String remainingSrc = src.substring(srcIdx);
            f.insert(lastWildIdx+1, remainingSrc);
        }
        
        return f.toString();
    }
    
    private void replaceWithPlaceHolder(StringBuilder f, int fromIdx) {
        for (int i = fromIdx; i < f.length(); i++) {
            if ( f.charAt(i)==WILD ){
                f.replace(i, i+1, ""+placeholder);
            }
        }
    }

    /**
     * Getter for placeholder
     * @return char
     */
    public char getPlaceholder() {
        return placeholder;
    }

    /**
     * Setter for placeholder
     * @param placeholder - char
     */
    public void setPlaceholder(char placeholder) {
        this.placeholder = placeholder;
    }
    
    /**
     * test it
     * @param args
     */
//    public static void main(String[] args) throws Exception{
//        CodeFormatter cf = new CodeFormatter("#-###-(##)");
//        System.out.println(cf.format("123456"));
//        System.out.println(cf.format("abcdefghijk"));
//        System.out.println(cf.format("goon"));
//    }
}
