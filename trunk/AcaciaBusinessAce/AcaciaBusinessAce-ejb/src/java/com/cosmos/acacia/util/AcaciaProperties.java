/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import com.cosmos.acacia.security.AccessLevel;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Miro
 */
public interface AcaciaProperties
    extends Map<String, Serializable>
{
    AccessLevel getAccessLevel();
    List<AccessLevel> getAccessLevels();
    List<AcaciaProperties> getAcaciaProperties();
    List<AcaciaProperties> getPropertiesStartsWith(String keyPrefix);
    List<AcaciaProperties> getPropertiesEndsWith(String keySuffix);
    List<AcaciaProperties> getPropertiesContains(String keyValue);
    BigInteger getRelatedObjectId();
    AcaciaProperties getParentProperties();
    AcaciaProperties getProperties(AccessLevel accessLevel);
    AcaciaProperties getProperties(String key);
    Serializable getProperty(String key);
    Serializable setProperty(String key, Serializable value);
    Serializable removeProperty(String key);
}
