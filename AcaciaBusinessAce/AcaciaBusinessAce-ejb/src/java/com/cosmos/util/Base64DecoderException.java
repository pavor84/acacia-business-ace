package com.cosmos.util;

/**
 *
 * @author Miro
 * @version 1.0
 */
public class Base64DecoderException
    extends Exception
{

    public Base64DecoderException(Throwable cause)
    {
        super("Base64Decoder exception", cause);
    }
}
