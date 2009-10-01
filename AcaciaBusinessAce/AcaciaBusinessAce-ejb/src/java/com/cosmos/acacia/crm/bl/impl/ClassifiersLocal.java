/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Local
public interface ClassifiersLocal
    extends ClassifiersRemote
{
    ClassifierGroup saveClassifierGroupLocal(ClassifierGroup classifierGroup);
    Classifier saveClassifierLocal(Classifier classifier);
}
