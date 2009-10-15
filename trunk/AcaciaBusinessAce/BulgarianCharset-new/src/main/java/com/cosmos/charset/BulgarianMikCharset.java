package com.cosmos.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.TreeMap;
import sun.nio.cs.SingleByteDecoder;
import sun.nio.cs.SingleByteEncoder;

public class BulgarianMikCharset extends Charset {

    private static final String CANONICAL_NAME = "Bg-Mik";
    private static final String[] ALIASES = {
        "Bulgarian-Mik",
        "Bulgarian_Mik",
        "Bg_Mik",
        "Bulgarian.Mik",
        "Bg.Mik",
        "Bulgarian:Mik",
        "Bg:Mik"
    };

    private static final int mask1 = 0xFF00;
    private static final int mask2 = 0xFF;
    private static final int shift = 8;

    private static final char[][] byteToCharArray = {
        {0x00, 0x00}, //
        {0x01, 0x01}, //
        {0x02, 0x02}, //
        {0x03, 0x03}, //
        {0x04, 0x04}, //
        {0x05, 0x05}, //
        {0x06, 0x06}, //
        {0x07, 0x07}, //
        {0x08, 0x08}, //
        {0x09, 0x09}, //
        {0x0A, 0x0A}, //
        {0x0B, 0x0B}, //
        {0x0C, 0x0C}, //
        {0x0D, 0x0D}, //
        {0x0E, 0x0E}, //
        {0x0F, 0x0F}, //
        {0x10, 0x10}, //
        {0x11, 0x11}, //
        {0x12, 0x12}, //
        {0x13, 0x13}, //
        {0x14, 0x14}, //
        {0x15, 0x15}, //
        {0x16, 0x16}, //
        {0x17, 0x17}, //
        {0x18, 0x18}, //
        {0x19, 0x19}, //
        {0x1A, 0x1A}, //
        {0x1B, 0x1B}, //
        {0x1C, 0x1C}, //
        {0x1D, 0x1D}, //
        {0x1E, 0x1E}, //
        {0x1F, 0x1F}, //
        {0x0020, 0x20}, // 	SPACE
        {0x0021, 0x21}, // 	EXCLAMATION MARK
        {0x0022, 0x22}, // 	QUOTATION MARK
        {0x0023, 0x23}, // 	NUMBER SIGN
        {0x0024, 0x24}, // 	DOLLAR SIGN
        {0x0025, 0x25}, // 	PERCENT SIGN
        {0x0026, 0x26}, // 	AMPERSAND
        {0x0027, 0x27}, // 	APOSTROPHE
        {0x0028, 0x28}, // 	LEFT PARENTHESIS
        {0x0029, 0x29}, // 	RIGHT PARENTHESIS
        {0x002A, 0x2A}, // 	ASTERISK
        {0x002B, 0x2B}, // 	PLUS SIGN
        {0x002C, 0x2C}, // 	COMMA
        {0x002D, 0x2D}, // 	HYPHEN-MINUS
        {0x002E, 0x2E}, // 	FULL STOP
        {0x002F, 0x2F}, // 	SOLIDUS
        {0x0030, 0x30}, // DIGIT ZERO
        {0x0031, 0x31}, // DIGIT ONE
        {0x0032, 0x32}, // DIGIT TWO
        {0x0033, 0x33}, // DIGIT THREE
        {0x0034, 0x34}, // DIGIT FOUR
        {0x0035, 0x35}, // DIGIT FIVE
        {0x0036, 0x36}, // DIGIT SIX
        {0x0037, 0x37}, // DIGIT SEVEN
        {0x0038, 0x38}, // DIGIT EIGHT
        {0x0039, 0x39}, // DIGIT NINE
        {0x003A, 0x3A}, // COLON
        {0x003B, 0x3B}, // SEMICOLON
        {0x003C, 0x3C}, // LESS-THAN SIGN
        {0x003D, 0x3D}, // EQUALS SIGN
        {0x003E, 0x3E}, // GREATER-THAN SIGN
        {0x003F, 0x3F}, // QUESTION MARK
        {0x0040, 0x40}, // COMMERCIAL AT
        {0x0041, 0x41}, // LATIN CAPITAL LETTER A
        {0x0042, 0x42}, // LATIN CAPITAL LETTER B
        {0x0043, 0x43}, // LATIN CAPITAL LETTER C
        {0x0044, 0x44}, // LATIN CAPITAL LETTER D
        {0x0045, 0x45}, // LATIN CAPITAL LETTER E
        {0x0046, 0x46}, // LATIN CAPITAL LETTER F
        {0x0047, 0x47}, // LATIN CAPITAL LETTER G
        {0x0048, 0x48}, // LATIN CAPITAL LETTER H
        {0x0049, 0x49}, // LATIN CAPITAL LETTER I
        {0x004A, 0x4A}, // LATIN CAPITAL LETTER J
        {0x004B, 0x4B}, // LATIN CAPITAL LETTER K
        {0x004C, 0x4C}, // LATIN CAPITAL LETTER L
        {0x004D, 0x4D}, // LATIN CAPITAL LETTER M
        {0x004E, 0x4E}, // LATIN CAPITAL LETTER N
        {0x004F, 0x4F}, // LATIN CAPITAL LETTER O
        {0x0050, 0x50}, // LATIN CAPITAL LETTER P
        {0x0051, 0x51}, // LATIN CAPITAL LETTER Q
        {0x0052, 0x52}, // LATIN CAPITAL LETTER R
        {0x0053, 0x53}, // LATIN CAPITAL LETTER S
        {0x0054, 0x54}, // LATIN CAPITAL LETTER T
        {0x0055, 0x55}, // LATIN CAPITAL LETTER U
        {0x0056, 0x56}, // LATIN CAPITAL LETTER V
        {0x0057, 0x57}, // LATIN CAPITAL LETTER W
        {0x0058, 0x58}, // LATIN CAPITAL LETTER X
        {0x0059, 0x59}, // LATIN CAPITAL LETTER Y
        {0x005A, 0x5A}, // LATIN CAPITAL LETTER Z
        {0x005B, 0x5B}, // 	LEFT SQUARE BRACKET
        {0x005C, 0x5C}, // 	REVERSE SOLIDUS
        {0x005D, 0x5D}, // 	RIGHT SQUARE BRACKET
        {0x005E, 0x5E}, // 	CIRCUMFLEX ACCENT
        {0x005F, 0x5F}, // 	LOW LINE
        {0x0060, 0x60}, // 	GRAVE ACCENT
        {0x0061, 0x61}, // LATIN SMALL LETTER A
        {0x0062, 0x62}, // LATIN SMALL LETTER B
        {0x0063, 0x63}, // LATIN SMALL LETTER C
        {0x0064, 0x64}, // LATIN SMALL LETTER D
        {0x0065, 0x65}, // LATIN SMALL LETTER E
        {0x0066, 0x66}, // LATIN SMALL LETTER F
        {0x0067, 0x67}, // LATIN SMALL LETTER G
        {0x0068, 0x68}, // LATIN SMALL LETTER H
        {0x0069, 0x69}, // LATIN SMALL LETTER I
        {0x006A, 0x6A}, // LATIN SMALL LETTER J
        {0x006B, 0x6B}, // LATIN SMALL LETTER K
        {0x006C, 0x6C}, // LATIN SMALL LETTER L
        {0x006D, 0x6D}, // LATIN SMALL LETTER M
        {0x006E, 0x6E}, // LATIN SMALL LETTER N
        {0x006F, 0x6F}, // LATIN SMALL LETTER O
        {0x0070, 0x70}, // LATIN SMALL LETTER P
        {0x0071, 0x71}, // LATIN SMALL LETTER Q
        {0x0072, 0x72}, // LATIN SMALL LETTER R
        {0x0073, 0x73}, // LATIN SMALL LETTER S
        {0x0074, 0x74}, // LATIN SMALL LETTER T
        {0x0075, 0x75}, // LATIN SMALL LETTER U
        {0x0076, 0x76}, // LATIN SMALL LETTER V
        {0x0077, 0x77}, // LATIN SMALL LETTER W
        {0x0078, 0x78}, // LATIN SMALL LETTER X
        {0x0079, 0x79}, // LATIN SMALL LETTER Y
        {0x007A, 0x7A}, // LATIN SMALL LETTER Z
        {0x007B, 0x7B}, // LEFT CURLY BRACKET
        {0x007C, 0x7C}, // VERTICAL LINE
        {0x007D, 0x7D}, // RIGHT CURLY BRACKET
        {0x007E, 0x7E}, // TILDE
        {0x007F, 0x7F}, //
        {0x0410, 0x80}, // CYRILLIC CAPITAL LETTER A
        {0x0411, 0x81}, // CYRILLIC CAPITAL LETTER BE
        {0x0412, 0x82}, // CYRILLIC CAPITAL LETTER VE
        {0x0413, 0x83}, // CYRILLIC CAPITAL LETTER GHE
        {0x0414, 0x84}, // CYRILLIC CAPITAL LETTER DE
        {0x0415, 0x85}, // CYRILLIC CAPITAL LETTER IE
        {0x0416, 0x86}, // CYRILLIC CAPITAL LETTER ZHE
        {0x0417, 0x87}, // CYRILLIC CAPITAL LETTER ZE
        {0x0418, 0x88}, // CYRILLIC CAPITAL LETTER I
        {0x0419, 0x89}, // CYRILLIC CAPITAL LETTER SHORT I
        {0x041A, 0x8A}, // CYRILLIC CAPITAL LETTER KA
        {0x041B, 0x8B}, // CYRILLIC CAPITAL LETTER EL
        {0x041C, 0x8C}, // CYRILLIC CAPITAL LETTER EM
        {0x041D, 0x8D}, // CYRILLIC CAPITAL LETTER EN
        {0x041E, 0x8E}, // CYRILLIC CAPITAL LETTER O
        {0x041F, 0x8F}, // CYRILLIC CAPITAL LETTER PE
        {0x0420, 0x90}, // CYRILLIC CAPITAL LETTER ER
        {0x0421, 0x91}, // CYRILLIC CAPITAL LETTER ES
        {0x0422, 0x92}, // CYRILLIC CAPITAL LETTER TE
        {0x0423, 0x93}, // CYRILLIC CAPITAL LETTER U
        {0x0424, 0x94}, // CYRILLIC CAPITAL LETTER EF
        {0x0425, 0x95}, // CYRILLIC CAPITAL LETTER HA
        {0x0426, 0x96}, // CYRILLIC CAPITAL LETTER TSE
        {0x0427, 0x97}, // CYRILLIC CAPITAL LETTER CHE
        {0x0428, 0x98}, // CYRILLIC CAPITAL LETTER SHA
        {0x0429, 0x99}, // CYRILLIC CAPITAL LETTER SHCHA
        {0x042A, 0x9A}, // CYRILLIC CAPITAL LETTER HARD SIGN
        {0x042B, 0x9B}, // CYRILLIC CAPITAL LETTER YERU
        {0x042C, 0x9C}, // CYRILLIC CAPITAL LETTER SOFT SIGN
        {0x042D, 0x9D}, // CYRILLIC CAPITAL LETTER E
        {0x042E, 0x9E}, // CYRILLIC CAPITAL LETTER YU
        {0x042F, 0x9F}, // CYRILLIC CAPITAL LETTER YA
        {0x0430, 0xA0}, // CYRILLIC SMALL LETTER A
        {0x0431, 0xA1}, // CYRILLIC SMALL LETTER BE
        {0x0432, 0xA2}, // CYRILLIC SMALL LETTER VE
        {0x0433, 0xA3}, // CYRILLIC SMALL LETTER GHE
        {0x0434, 0xA4}, // CYRILLIC SMALL LETTER DE
        {0x0435, 0xA5}, // CYRILLIC SMALL LETTER IE
        {0x0436, 0xA6}, // CYRILLIC SMALL LETTER ZHE
        {0x0437, 0xA7}, // CYRILLIC SMALL LETTER ZE
        {0x0438, 0xA8}, // CYRILLIC SMALL LETTER I
        {0x0439, 0xA9}, // CYRILLIC SMALL LETTER SHORT I
        {0x043A, 0xAA}, // CYRILLIC SMALL LETTER KA
        {0x043B, 0xAB}, // CYRILLIC SMALL LETTER EL
        {0x043C, 0xAC}, // CYRILLIC SMALL LETTER EM
        {0x043D, 0xAD}, // CYRILLIC SMALL LETTER EN
        {0x043E, 0xAE}, // CYRILLIC SMALL LETTER O
        {0x043F, 0xAF}, // CYRILLIC SMALL LETTER PE
        {0x0440, 0xB0}, // CYRILLIC SMALL LETTER ER
        {0x0441, 0xB1}, // CYRILLIC SMALL LETTER ES
        {0x0442, 0xB2}, // CYRILLIC SMALL LETTER TE
        {0x0443, 0xB3}, // CYRILLIC SMALL LETTER U
        {0x0444, 0xB4}, // CYRILLIC SMALL LETTER EF
        {0x0445, 0xB5}, // CYRILLIC SMALL LETTER HA
        {0x0446, 0xB6}, // CYRILLIC SMALL LETTER TSE
        {0x0447, 0xB7}, // CYRILLIC SMALL LETTER CHE
        {0x0448, 0xB8}, // CYRILLIC SMALL LETTER SHA
        {0x0449, 0xB9}, // CYRILLIC SMALL LETTER SHCHA
        {0x044A, 0xBA}, // CYRILLIC SMALL LETTER HARD SIGN
        {0x044B, 0xBB}, // CYRILLIC SMALL LETTER YERU
        {0x044C, 0xBC}, // CYRILLIC SMALL LETTER SOFT SIGN
        {0x044D, 0xBD}, // CYRILLIC SMALL LETTER E
        {0x044E, 0xBE}, // CYRILLIC SMALL LETTER YU
        {0x044F, 0xBF}, // CYRILLIC SMALL LETTER YA
        {0x2514, 0xC0}, // BOX DRAWINGS LIGHT UP AND RIGHT
        {0x2534, 0xC1}, // BOX DRAWINGS LIGHT UP AND HORIZONTAL
        {0x252C, 0xC2}, // BOX DRAWINGS LIGHT DOWN AND HORIZONTAL
        {0x251C, 0xC3}, // BOX DRAWINGS LIGHT VERTICAL AND RIGHT
        {0x2500, 0xC4}, // BOX DRAWINGS LIGHT HORIZONTAL
        {0x253C, 0xC5}, // BOX DRAWINGS LIGHT VERTICAL AND HORIZONTAL
        {0x2563, 0xC6}, // BOX DRAWINGS DOUBLE VERTICAL AND LEFT
        {0x2551, 0xC7}, // BOX DRAWINGS DOUBLE VERTICAL
        {0x255A, 0xC8}, // BOX DRAWINGS DOUBLE UP AND RIGHT
        {0x2554, 0xC9}, // BOX DRAWINGS DOUBLE DOWN AND RIGHT
        {0x2569, 0xCA}, // BOX DRAWINGS DOUBLE UP AND HORIZONTAL
        {0x2566, 0xCB}, // BOX DRAWINGS DOUBLE DOWN AND HORIZONTAL
        {0x2560, 0xCC}, // BOX DRAWINGS DOUBLE VERTICAL AND RIGHT
        {0x2550, 0xCD}, // BOX DRAWINGS DOUBLE HORIZONTAL
        {0x256C, 0xCE}, // BOX DRAWINGS DOUBLE VERTICAL AND HORIZONTAL
        {0x2510, 0xCF}, // BOX DRAWINGS LIGHT DOWN AND LEFT
        {0x2591, 0xD0}, // 	LIGHT SHADE
        {0x2592, 0xD1}, // 	MEDIUM SHADE
        {0x2593, 0xD2}, // 	DARK SHADE
        {0x2502, 0xD3}, // BOX DRAWINGS LIGHT VERTICAL
        {0x2524, 0xD4}, // BOX DRAWINGS LIGHT VERTICAL AND LEFT
        {0x2116, 0xD5}, // 	NUMERO SIGN
        {0x00A7, 0xD6}, // 	SECTION SIGN
        {0x2557, 0xD7}, // BOX DRAWINGS DOUBLE DOWN AND LEFT
        {0x255D, 0xD8}, // BOX DRAWINGS DOUBLE UP AND LEFT
        {0x2518, 0xD9}, // BOX DRAWINGS LIGHT UP AND LEFT
        {0x250C, 0xDA}, // BOX DRAWINGS LIGHT DOWN AND RIGHT
        {0x2588, 0xDB}, // 	FULL BLOCK
        {0x2584, 0xDC}, // 	LOWER HALF BLOCK
        {0x258C, 0xDD}, // 	LEFT HALF BLOCK
        {0x2590, 0xDE}, // 	RIGHT HALF BLOCK
        {0x2580, 0xDF}, // 	UPPER HALF BLOCK
        {0x03B1, 0xE0}, // GREEK SMALL LETTER ALPHA
        {0x03B2, 0xE1}, // GREEK SMALL LETTER BETA
        {0x0393, 0xE2}, // GREEK CAPITAL LETTER GAMMA
        {0x03C0, 0xE3}, // GREEK SMALL LETTER PI
        {0x03A3, 0xE4}, // GREEK CAPITAL LETTER SIGMA
        {0x03C3, 0xE5}, // GREEK SMALL LETTER SIGMA
        {0x03BC, 0xE6}, // GREEK SMALL LETTER MU
        {0x03C4, 0xE7}, // GREEK SMALL LETTER TAU
        {0x03A6, 0xE8}, // GREEK CAPITAL LETTER PHI
        {0x0398, 0xE9}, // GREEK CAPITAL LETTER THETA
        {0x03A9, 0xEA}, // GREEK CAPITAL LETTER OMEGA
        {0x03B4, 0xEB}, // GREEK SMALL LETTER DELTA
        {0x221E, 0xEC}, // 	INFINITY
        {0x2205, 0xED}, // 	EMPTY SET
        {0x2208, 0xEE}, // 	ELEMENT OF
        {0x2229, 0xEF}, // 	INTERSECTION
        {0x2261, 0xF0}, // 	IDENTICAL TO
        {0x00B1, 0xF1}, // 	PLUS-MINUS SIGN
        {0x2265, 0xF2}, // 	GREATER-THAN OR EQUAL TO
        {0x2264, 0xF3}, // 	LESS-THAN OR EQUAL TO
        {0x2320, 0xF4}, // 	TOP HALF INTEGRAL
        {0x2321, 0xF5}, // 	BOTTOM HALF INTEGRAL
        {0x00F7, 0xF6}, // 	DIVISION SIGN
        {0x2248, 0xF7}, // 	ALMOST EQUAL TO
        {0x00B0, 0xF8}, // 	DEGREE SIGN
        {0x2219, 0xF9}, // 	BULLET OPERATOR
        {0x00B7, 0xFA}, // 	MIDDLE DOT
        {0x221A, 0xFB}, // 	SQUARE ROOT
        {0x207F, 0xFC}, // 	SUPERSCRIPT LATIN SMALL LETTER N
        {0x00B2, 0xFD}, // 	SUPERSCRIPT TWO
        {0x25A0, 0xFE}, // 	BLACK SQUARE
        {0x00A0, 0xFF}, // 	NO-BREAK SPACE
    };

    static {
        init();
    }

    private static class Decoder extends SingleByteDecoder {

        private static String byteToCharTable;

        public Decoder(Charset charset) {
            super(charset, byteToCharTable);
        }
    }

    private static class Encoder extends SingleByteEncoder {

        private static String index2;
        private static short[] index1;

        public Encoder(Charset charset) {
            super(charset, index1, index2, mask1, mask2, shift);
        }
    }

    public BulgarianMikCharset() {
        super(CANONICAL_NAME, ALIASES);
    }

    @Override
    public boolean contains(Charset charset) {
        return charset instanceof BulgarianMikCharset;
    }

    @Override
    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    public String getDecoderSingleByteMappings() {
        return Decoder.byteToCharTable;
    }

    public short[] getEncoderIndex1() {
        return Encoder.index1;
    }

    public String getEncoderIndex2() {
        return Encoder.index2;
    }

    private static void init() {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        char[] charArray = new char[0x100];
        for(int i = 0; i < BulgarianMikCharset.byteToCharArray.length; i++) {
            char[] item = BulgarianMikCharset.byteToCharArray[i];
            int byteValue = item[1];
            int charValue = item[0];
            map.put(charValue, byteValue);
            if(i >= 0x80) {
                charArray[i - 0x80] = (char) charValue;
            } else {
                charArray[i + 0x80] = (char) charValue;
            }
        }
        Decoder.byteToCharTable = new String(charArray);

        TreeMap<Integer, Integer> pageMap = new TreeMap<Integer, Integer>();
        for(int ch : map.keySet()) {
            int page = (ch & mask1) >> shift;
            int remainder = ch & mask2;

            Integer maxIndex = pageMap.get(page);
            if((maxIndex = pageMap.get(page)) == null) {
                pageMap.put(page, remainder);
            } else {
                maxIndex = Math.max(maxIndex, remainder);
                pageMap.put(page, maxIndex);
            }
        }

        int pageSize = pageMap.lastKey() + 1;
        int charArraySize = 0;
        for(int i = 0; i < pageSize; i++) {
            Integer maxIndex;
            if((maxIndex = pageMap.get(i)) != null) {
                pageMap.put(i, charArraySize);
                charArraySize += maxIndex + 1;
            } else {
                pageMap.put(i, -1);
            }
        }
        TreeMap<Integer, Integer> charArrayMap = new TreeMap<Integer, Integer>();

        for(int ch : map.keySet()) {
            int page = (ch & mask1) >> shift;
            int remainder = ch & mask2;
            int index = pageMap.get(page) + remainder;
            charArrayMap.put(index, map.get(ch));
        }

        charArray = new char[charArraySize];
        for(int i = 0; i < charArraySize; i++) {
            Integer ch;
            if((ch = charArrayMap.get(i)) != null) {
                charArray[i] = (char) ((int) ch);
            } else {
                charArray[i] = 0;
            }
        }

        Encoder.index2 = new String(charArray);

        int size = pageMap.size();
        Encoder.index1 = new short[size];
        for(int i = 0; i < size; i++) {
            Encoder.index1[i] = (short) ((int) pageMap.get(i));
        }
    }
}
