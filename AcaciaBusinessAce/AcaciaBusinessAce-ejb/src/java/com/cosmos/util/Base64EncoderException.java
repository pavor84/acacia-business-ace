package com.cosmos.util;

/**
 *
 * @author Miro
 * @version 1.0
 */
public class Base64EncoderException
    extends Exception
{

    public Base64EncoderException(Throwable cause)
    {
        super("Base64Encoder exception", cause);
    }
}
