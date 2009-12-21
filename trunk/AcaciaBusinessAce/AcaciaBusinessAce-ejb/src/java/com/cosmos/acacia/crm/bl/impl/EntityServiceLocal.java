/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectEntity;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface EntityServiceLocal extends EntityServiceRemote {

    Set<Package> getAllEntityPackages();

    Set<Package> getOwnPackages(Package entityPackage);

    Set<Package> getRelatedPackages(Package entityPackage);

    Set<Class<? extends DataObjectEntity>> getAllDataObjectEntities();

    Set<Class<? extends DataObjectEntity>> getDataObjectEntities(Package entityPackage);
}
