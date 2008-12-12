/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

import java.util.List;

/**
 *
 * @author Miro
 */
public class Role
{
    private String roleName;
    private Object organization;
    private Object businessUnit;
    private Role parentRole;
    private List<Privilege> privileges;
}
