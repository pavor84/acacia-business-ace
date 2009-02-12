/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx.text;

import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Miro
 */
public class XMaskFormatter extends MaskFormatter {

    public enum MaskKey {
        Anything('*'),      // 'X'
        UpperCase('U'),
        LowerCase('L'),
        Digit('#'),
        Hex('H'),
        Literal('\'', true),
        AlphaNumeric('A'),
        SingleCharacter('?'),
        ;

        private MaskKey(char keyChar) {
            this(keyChar, false);
        }

        private MaskKey(char keyChar, boolean literal) {
            this(keyChar, literal, !literal);
        }

        private MaskKey(char keyChar, boolean literal, boolean allowPlaceholderCharacter) {
            this.keyChar = keyChar;
            this.literal = literal;
            this.allowPlaceholderCharacter = allowPlaceholderCharacter;
        }

        private char keyChar;
        private boolean literal;
        private boolean allowPlaceholderCharacter;

        public char getKeyChar() {
            return keyChar;
        }

        public boolean isLiteral() {
            return literal;
        }

        public boolean isAllowPlaceholderCharacter() {
            return allowPlaceholderCharacter;
        }
    }

    private static final MaskCharacter[] EMPTY_MASK_CHARACTERS = new MaskCharacter[0];
    /** The user specified mask. */
    private transient MaskCharacter[] maskCharacters;
    private Map<Character, MaskCharacter> keyMaskCharacterMap;
    private Map<Character, LiteralCharacter> literalCharactersMap;
    private MaskCharacter defaultMaskCharacter;
    private boolean completeMatch;


    public XMaskFormatter(String mask) throws ParseException {
        this();
        setMask(mask);
    }

    public XMaskFormatter() {
        init();
    }

    private void init() {
        maskCharacters = EMPTY_MASK_CHARACTERS;
        setValueContainsLiteralCharacters(false);
    }

    @Override
    public void setMask(String mask) throws ParseException {
        updateInternalMask(mask);
        super.setMask(toMask());
    }

    public boolean isCompleteMatch() {
        return completeMatch;
    }

    public void setCompleteMatch(boolean completeMatch) {
        this.completeMatch = completeMatch;
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        String sValue = (value == null) ? "" : value.toString();
        StringBuilder result = new StringBuilder();
        String placeholder = getPlaceholder();
        int[] valueCounter = { 0 };

        append(result, sValue, valueCounter, placeholder, maskCharacters);
        return result.toString();
    }

    @Override
    public Object stringToValue(String value) throws ParseException {
        return stringToValue(value, completeMatch);
        //return super.stringToValue(value);
    }

    protected Object stringToValue(String value, boolean completeMatch) throws
                         ParseException {
        int errorOffset = -1;

        if ((errorOffset = getInvalidOffset(value, completeMatch)) == -1) {
            if (!getValueContainsLiteralCharacters()) {
                value = stripLiteralChars(rightTrim(value));
            }

            return stringToClassValue(value);
        }

        throw new ParseException("stringToValue passed invalid value",
                                 errorOffset);
    }

    protected Object stringToClassValue(String string) throws ParseException {
        Class vc = getValueClass();
        JFormattedTextField ftf = getFormattedTextField();

        if (vc == null && ftf != null) {
            Object value = ftf.getValue();

            if (value != null) {
                vc = value.getClass();
            }
        }
        if (vc != null) {
            Constructor cons;

            try {
                cons = vc.getConstructor(new Class[] { String.class });

            } catch (NoSuchMethodException ex) {
                cons = null;
            }

            if (cons != null) {
                try {
                    return cons.newInstance(new Object[] { string });
                } catch (Throwable ex) {
                    throw new ParseException("Error creating instance", 0);
                }
            }
        }

        return string;
    }

    protected int getInvalidOffset(String string, boolean completeMatch) {
        int iLength = string.length();

        if (completeMatch && iLength != getMaxLength()) {
            // trivially false
            return iLength;
        }
        for (int counter = 0, max = string.length(); counter < max; counter++){
            char aChar = string.charAt(counter);

            if (!isValidCharacter(counter, aChar) &&
                (completeMatch || !isPlaceholder(counter, aChar))) {
                return counter;
            }
        }
        return -1;
    }

    protected String rightTrim(String source) {
        int size;
        if(source == null || (size = source.length()) == 0)
            return source;

        while(--size >= 0) {
            if(isLiteral(size))
                continue;

            char ch = source.charAt(size);
            if(getPlaceholderCharacter() != ch) {
                size++;
                break;
            }
        }

        if(size < 0)
            return "";

        if(size < source.length())
            return source.substring(0, size);

        return source;
    }

    protected String stripLiteralChars(String string) {
        StringBuilder sb = null;
        int last = 0;

        for (int counter = 0, max = string.length(); counter < max; counter++) {
            if (isLiteral(counter)) {
                if (sb == null) {
                    sb = new StringBuilder();
                    if (counter > 0) {
                        sb.append(string.substring(0, counter));
                    }
                    last = counter + 1;
                }
                else if (last != counter) {
                    sb.append(string.substring(last, counter));
                }
                last = counter + 1;
            }
        }

        if (sb == null) {
            // Assume the mask isn't all literals.
            return string;
        }
        else if (last != string.length()) {
            if (sb == null) {
                return string.substring(last);
            }
            sb.append(string.substring(last));
        }

        return sb.toString();
    }

    protected boolean isLiteral(int index) {
        return getMaskCharacter(index).isLiteral();
    }

    /**
     * Returns the maximum length the text can be.
     */
    protected int getMaxLength() {
        return maskCharacters.length;
    }

    protected MaskCharacter getMaskCharacter(int index) {
        if (index >= maskCharacters.length) {
            return null;
        }
        return maskCharacters[index];
    }

    protected boolean isValidCharacter(int index, char aChar) {
        return getMaskCharacter(index).isValidCharacter(aChar);
    }

    protected boolean isPlaceholder(int index, char aChar) {
        return (getPlaceholderCharacter() == aChar);
    }




    /**
     * Updates the internal representation of the mask.
     */
    protected void updateInternalMask(String maskFormat) throws ParseException {
        int size;
        if (maskFormat == null || (size = maskFormat.length()) == 0) {
            maskCharacters = EMPTY_MASK_CHARACTERS;
            return;
        }

        ArrayList<MaskCharacter> maskFormatArray;
        if (size <= 10) {
            maskFormatArray = new ArrayList<MaskCharacter>();
        } else {
            maskFormatArray = new ArrayList<MaskCharacter>(size);
        }

        StringBuilder numberString = new StringBuilder();
        int number = 0;
        char[] charArray = maskFormat.toCharArray();
        for (int i = 0; i < size; i++) {
            char maskChar = charArray[i];
            while (i < size && maskChar >= '0' && maskChar <= '9') {
                numberString.append(maskChar);
                if (++i < size) {
                    maskChar = charArray[i];
                }
            }

            boolean hasNext = !(i >= size || maskChar >= '0' && maskChar <= '9');

            if (numberString.length() > 0) {
                number = Integer.parseInt(numberString.toString());

                MaskCharacter mc = null;
                if (hasNext) {
                    mc = getMaskCharacter(maskChar);
                    if (mc != null) {
                        if (++i < size) {
                            maskChar = charArray[i];
                        } else {
                            hasNext = false;
                        }
                    }
                }
                if (mc == null) {
                    mc = getDefaultMaskCharacter();
                }

                while (number-- > 0) {
                    maskFormatArray.add(mc);
                }
                numberString.setLength(0);
            }

            if (!hasNext) {
                break;
            }

            MaskCharacter mc = getMaskCharacter(maskChar);
            if (mc != null) {
                maskFormatArray.add(mc);
                continue;
            }

            if (maskChar == MaskKey.Literal.getKeyChar()) {
                if (++i < size) {
                    maskChar = charArray[i];
                    maskFormatArray.add(getLiteralCharacter(maskChar));
                }
            } else {
                maskFormatArray.add(getLiteralCharacter(maskChar));
            }
        }

        maskCharacters = new MaskCharacter[maskFormatArray.size()];
        maskFormatArray.toArray(maskCharacters);
    }

    protected LiteralCharacter getLiteralCharacter(char maskChar) {
        if (literalCharactersMap == null) {
            literalCharactersMap = new TreeMap<Character, LiteralCharacter>();
        }

        LiteralCharacter literalCharacter = literalCharactersMap.get(maskChar);
        if (literalCharacter == null) {
            literalCharacter = new LiteralCharacter(maskChar);
            literalCharactersMap.put(maskChar, literalCharacter);
        }

        return literalCharacter;
    }

    protected MaskCharacter getDefaultMaskCharacter() {
        if(defaultMaskCharacter == null)
            defaultMaskCharacter = getMaskCharacter(MaskKey.Anything.getKeyChar());

        return defaultMaskCharacter;
    }

    protected MaskCharacter getMaskCharacter(char keyChar) {
        if (keyMaskCharacterMap == null) {
            keyMaskCharacterMap = new TreeMap<Character, MaskCharacter>();
            keyMaskCharacterMap.put(MaskKey.Anything.getKeyChar(), new MaskCharacter());
            keyMaskCharacterMap.put(MaskKey.UpperCase.getKeyChar(), new UpperCaseCharacter());
            keyMaskCharacterMap.put(MaskKey.LowerCase.getKeyChar(), new LowerCaseCharacter());
            keyMaskCharacterMap.put(MaskKey.Digit.getKeyChar(), new DigitCharacter());
            keyMaskCharacterMap.put(MaskKey.Hex.getKeyChar(), new HexCharacter());
            keyMaskCharacterMap.put(MaskKey.AlphaNumeric.getKeyChar(), new AlphaNumericCharacter());
            keyMaskCharacterMap.put(MaskKey.SingleCharacter.getKeyChar(), new SingleCharacter());
        }

        return keyMaskCharacterMap.get(keyChar);
    }

    protected String toMask() {
        if (maskCharacters == null || maskCharacters.length == 0) {
            return "";
        }

        int size = maskCharacters.length;
        StringBuilder sb = new StringBuilder(size);
        for (MaskCharacter mc : maskCharacters) {
            sb.append(mc.getKeyChar());
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return toMask();
    }


    /**
     * Invokes <code>append</code> on the mask characters in
     * <code>mask</code>.
     */
    private void append(StringBuilder result, String value, int[] index,
                        String placeholder, MaskCharacter[] mask)
                          throws ParseException {
        for (int counter = 0, maxCounter = mask.length;
             counter < maxCounter; counter++) {
            mask[counter].append(result, value, index, placeholder);
        }
    }

    /**
     * Interal classes used to represent the mask.
     */
    public class MaskCharacter {

        private MaskKey maskKey;
        private char keyChar;
        private String keyString;

        public MaskCharacter() {
            this(MaskKey.Anything);
        }

        protected MaskCharacter(MaskKey maskKey) {
            this(maskKey, maskKey.getKeyChar());
        }

        protected MaskCharacter(MaskKey maskKey, char keyChar) {
            this.maskKey = maskKey;
            this.keyChar = keyChar;
        }

        /**
         * Subclasses should override this returning true if the instance
         * represents a literal character. The default implementation
         * returns false.
         */
        public boolean isLiteral() {
            return maskKey.isLiteral();
        }

        public boolean isAllowPlaceholderCharacter() {
            return maskKey.isAllowPlaceholderCharacter();
        }

        /**
         * Returns the character to insert for <code>aChar</code>. The
         * default implementation returns <code>aChar</code>. Subclasses
         * that wish to do some sort of mapping, perhaps lower case to upper
         * case should override this and do the necessary mapping.
         */
        public char getChar(char aChar) {
            return aChar;
        }

        public MaskKey getMaskKey() {
            return maskKey.Anything;
        }

        public char getKeyChar() {
            return keyChar;
        }

        /**
         * Returns true if <code>aChar</code> is a valid reprensentation of
         * the receiver. The default implementation returns true if the
         * receiver represents a literal character and <code>getChar</code>
         * == aChar. Otherwise, this will return true is <code>aChar</code>
         * is contained in the valid characters and not contained
         * in the invalid characters.
         */
        public boolean isValidCharacter(char aChar) {
            if (isLiteral()) {
                return (getChar(aChar) == aChar);
            }

            aChar = getChar(aChar);

            String filter = getValidCharacters();

            if (filter != null && filter.indexOf(aChar) == -1) {
                return false;
            }
            filter = getInvalidCharacters();
            if (filter != null && filter.indexOf(aChar) != -1) {
                return false;
            }
            return true;
        }

        /**
         * Appends the necessary character in <code>formatting</code> at
         * <code>index</code> to <code>buff</code>.
         */
        public void append(StringBuilder buff, String formatting, int[] index,
                String placeholder)
                throws ParseException {
            boolean inString = index[0] < formatting.length();
            char aChar = inString ? formatting.charAt(index[0]) : 0;

            if (isLiteral()) {
                buff.append(getChar(aChar));
                if (getValueContainsLiteralCharacters()) {
                    if (inString && aChar != getChar(aChar)) {
                        throw new ParseException("Invalid character: " +
                                aChar, index[0]);
                    }
                    index[0] = index[0] + 1;
                }
            } else if (index[0] >= formatting.length()) {
                if (placeholder != null && index[0] < placeholder.length()) {
                    buff.append(placeholder.charAt(index[0]));
                } else {
                    buff.append(getPlaceholderCharacter());
                }
                index[0] = index[0] + 1;
            } else if (isValidCharacter(aChar)) {
                buff.append(getChar(aChar));
                index[0] = index[0] + 1;
            } else if(getPlaceholderCharacter() == aChar && isAllowPlaceholderCharacter()) {
                buff.append(getChar(aChar));
                index[0] = index[0] + 1;
            } else {
                throw new ParseException("Invalid character: " + aChar,
                        index[0]);
            }
        }

        @Override
        public String toString() {
            if (keyString == null) {
                keyString = new String(new char[]{getKeyChar()});
            }

            return keyString;
        }
    }

    /**
     * Represents a number, uses <code>Character.isDigit</code>.
     */
    public class DigitCharacter extends MaskCharacter {

        public DigitCharacter() {
            super(MaskKey.Digit);
        }

        @Override
        public boolean isValidCharacter(char aChar) {
            return (Character.isDigit(aChar) &&
                    super.isValidCharacter(aChar));
        }
    }

    /**
     * Represents a character, lower case letters are mapped to upper case
     * using <code>Character.toUpperCase</code>.
     */
    public class UpperCaseCharacter extends MaskCharacter {

        public UpperCaseCharacter() {
            super(MaskKey.UpperCase);
        }

        @Override
        public char getChar(char aChar) {
            return Character.toUpperCase(aChar);
        }
    }

    /**
     * Represents a character, upper case letters are mapped to lower case
     * using <code>Character.toLowerCase</code>.
     */
    public class LowerCaseCharacter extends MaskCharacter {

        public LowerCaseCharacter() {
            super(MaskKey.LowerCase);
        }

        @Override
        public char getChar(char aChar) {
            return Character.toLowerCase(aChar);
        }
    }

    /**
     * Used to represent a fixed character in the mask.
     */
    public class LiteralCharacter extends MaskCharacter {

        public LiteralCharacter(char keyChar) {
            super(MaskKey.Literal, keyChar);
        }

        @Override
        public char getChar(char aChar) {
            return getKeyChar();
        }
    }

    /**
     * Represents a hex character, 0-9a-fA-F. a-f is mapped to A-F
     */
    public class HexCharacter extends MaskCharacter {

        public HexCharacter() {
            super(MaskKey.Hex);
        }

        @Override
        public boolean isValidCharacter(char aChar) {
            return (aChar >= '0' && aChar <= '9' ||
                    aChar >= 'A' && aChar <= 'F' ||
                    aChar >= 'a' && aChar <= 'f') &&
                    super.isValidCharacter(aChar);
        }

        @Override
        public char getChar(char aChar) {
            if (Character.isDigit(aChar)) {
                return aChar;
            }

            return Character.toUpperCase(aChar);
        }
    }

    /**
     * Represents either a character or digit, uses
     * <code>Character.isLetterOrDigit</code>.
     */
    public class AlphaNumericCharacter extends MaskCharacter {

        public AlphaNumericCharacter() {
            super(MaskKey.AlphaNumeric);
        }

        @Override
        public boolean isValidCharacter(char aChar) {
            return (Character.isLetterOrDigit(aChar) &&
                    super.isValidCharacter(aChar));
        }
    }

    /**
     * Represents a letter, uses <code>Character.isLetter</code>.
     */
    public class SingleCharacter extends MaskCharacter {

        public SingleCharacter() {
            super(MaskKey.SingleCharacter);
        }

        @Override
        public boolean isValidCharacter(char aChar) {
            return (Character.isLetter(aChar) &&
                    super.isValidCharacter(aChar));
        }
    }


    public static void main(String[] args) throws Exception {
        XMaskFormatter formatter = new XMaskFormatter("3*-2U-1L-2#-1-'9");
        System.out.println("mask: " + formatter.getMask() + " : " + formatter);

        formatter = new XMaskFormatter("1");
        System.out.println("mask: " + formatter.getMask() + " : " + formatter);

        formatter = new XMaskFormatter("3");
        System.out.println("mask: " + formatter.getMask() + " : " + formatter);

        formatter = new XMaskFormatter("(+3#-3#) 3#-2#-2#");
        formatter.setPlaceholderCharacter('_');
        System.out.println("mask: " + formatter.getMask() + " : " + formatter);

        String initialValue = "1234567890";
        System.out.println("initialValue: \"" + initialValue + "\"");
        String string = formatter.valueToString(initialValue);
        System.out.println("formatter.valueToString(value): " + string);

        String value = (String)formatter.stringToValue(string);
        System.out.println("formatter.stringToValue(string): \"" + value + "\"");

        System.out.println("initialValue.equals(value): " + initialValue.equals(value));
        System.out.flush();


        initialValue = "123_4567890";
        System.out.println("initialValue: \"" + initialValue + "\"");
        string = formatter.valueToString(initialValue);
        System.out.println("formatter.valueToString(value): " + string);

        value = (String)formatter.stringToValue(string);
        System.out.println("formatter.stringToValue(string): \"" + value + "\"");

        System.out.println("initialValue.equals(value): " + initialValue.equals(value));
    }

}
