package com.cosmos.acacia.crm.utils.numbers;

import java.math.BigInteger;

public abstract class BaseBulgarianNumberNamesAlgorithm implements NumberNamesAlgorithm {

    private static final String[] ONE_TO_NINE = new String[]{"един", "два",
        "три", "четири", "пет", "шест", "седем", "осем", "девет"};

    private static final String[] HUNDREDS = new String[] {"сто", "двеста", "триста",
            "четиристотин", "петстотин", "шестотин", "седемстотин", "осемстотин", "деветстотин"};

    protected static final String AND = " и ";

    @Override
    public String getNumberName(BigInteger integer) {
        String str = integer.toString();
        int strlen = str.length();
        int unit = 0;
        int dec = 0;
        int hun = 0;
        int tho = 0;
        int mil = 0;

        if (strlen > 0) {
            unit = Integer.parseInt(str.substring(strlen - 1));

            if (strlen > 1) {
                dec = Integer.parseInt(str.substring(strlen - 2, strlen - 1));

                if (strlen > 2) {
                    hun = Integer.parseInt(str.substring(strlen - 3, strlen - 2));

                    if (strlen > 3) {
                        tho = Integer.parseInt(
                                str.substring(strlen > 6 ? strlen - 6 : 0, strlen - 3));

                        if (str.length() > 6) {
                            mil = Integer.parseInt(
                                    str.substring(strlen > 9 ? strlen - 9 : 0, strlen - 6));
                        }
                    }
                }
            }
        }
        String number = "";

        number = getStringToHundeds(unit, dec, hun, getNumberGender());

        boolean thousandsAndCondition = hun == 0 || (dec == 0 && unit == 0);
        if (tho == 1) {
            number = "хиляда" + (thousandsAndCondition ? AND : " ") + number;
        } else if (tho > 1) {
            String prefixNumber = getStringToHundeds(tho);
            number =  prefixNumber + " хиляди" + (thousandsAndCondition ? AND : " ") + number;
        }

        boolean millionsAndCondition = tho == 0 || (hun == 0 && dec == 0 && unit == 0);
        if (mil == 1) {
            number = "един милион" + (millionsAndCondition ? AND : " ") + number;
        } else if (mil > 1){
            number = getStringToHundeds(mil) + " милиона" + (millionsAndCondition ? AND : " ") + number;
        }


        return number;
    }

    private String getStringToHundeds(int number) {
        int a1 = 0, a2 = 0, a3 = 0;
        String nmb = "" + number;
        int nmblen = nmb.length();
        if (nmblen > 0) {
            a1 = Integer.parseInt(nmb.substring(nmblen - 1));

            if (nmblen > 1) {
                a2 = Integer.parseInt(nmb.substring(nmblen - 2, nmblen - 1));

                if (nmblen > 2) {
                    a3 = Integer.parseInt(nmb.substring(nmblen - 3, nmblen - 2));
                }
            }
        }
        return getStringToHundeds(a1, a2, a3, GramaticalGender.FEMININE);
    }

    private String getStringToHundeds(int unit, int dec, int hun, GramaticalGender gender) {
        String number = "";
        if (unit > 0) {
            number = ONE_TO_NINE[unit - 1];

            if (gender == GramaticalGender.FEMININE && dec != 1) {
                number = number.replace(ONE_TO_NINE[0], "една");
                number = number.replace(ONE_TO_NINE[1], "две");
            }
            if (gender == GramaticalGender.NEUTER && dec != 1) {
                number = number.replace(ONE_TO_NINE[0], "едно");
                number = number.replace(ONE_TO_NINE[1], "две");
            }
        }

        if (unit == 0 && dec == 1) {
            number = "десет";
        } else if (dec == 1) {
            number = (ONE_TO_NINE[unit - 1] + "надесет").replace("нн", "н");
        } else if (dec > 0) {
            number = ONE_TO_NINE[dec - 1] + "десет" + (unit > 0 ? AND : "") + number;
        }

        if (hun > 0) {
            String hn = HUNDREDS[hun - 1];
            if (unit == 0 || dec == 0 || dec == 1)
                hn += AND;
            else
                hn += " ";

            number = hn + number;
        }

        return number;
    }

    protected abstract GramaticalGender getNumberGender();
}

enum GramaticalGender {
    MASCULINE, FEMININE, NEUTER
}
