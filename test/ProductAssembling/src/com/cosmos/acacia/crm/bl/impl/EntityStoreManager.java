/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

/**
 *
 * @author Miro
 */
public class EntityStoreManager
{
/*
    private static EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityStoreManager> entityStoreManager = new ThreadLocal<EntityStoreManager>();

    private static EntityManagerFactory getEntityManagerFactory()
    {
        if(entityManagerFactory == null)
        {
            entityManagerFactory = Persistence.createEntityManagerFactory("LegislativeDocumentsPU");
        }

        return entityManagerFactory;
    }

    public static EntityStoreManager getEntityStoreManager()
    {
        EntityStoreManager esm = entityStoreManager.get();
        if(esm == null)
        {
            esm = new EntityStoreManager();
            entityStoreManager.set(esm);
        }

        return esm;
    }

    public static void closeEntityStoreManager()
    {
        EntityStoreManager esm = entityStoreManager.get();
        if(esm != null)
        {
            entityStoreManager.set(null);
        }
    }


    private User user;

    private EntityStoreManager()
    {
    }

    public EntityManager createEntityManager()
    {
        return getEntityManagerFactory().createEntityManager();
    }

    public void close()
    {
        closeEntityStoreManager();
    }

    public Object persist(Object entity) {
        EntityManager em = createEntityManager();
        return persist(entity, em, true);
    }

    public Object persist(Object entity, EntityManager em) {
        return persist(entity, em, true);
    }

    private Object persist(Object entity, EntityManager em, boolean checkForRegistration) {
        if(checkForRegistration)
            checkRegistration();

        boolean hasActiveTransation;
        EntityTransaction tx = em.getTransaction();
        if(!(hasActiveTransation = tx.isActive()))
            tx.begin();

        try
        {
            boolean mustMerge = true;

            if(entity instanceof DataObjectBean)
            {
                DataObjectBean doBean = (DataObjectBean)entity;
                BigInteger id = doBean.getId();
                DataObject dataObject = doBean.getDataObject();
                if(id == null)
                {
                    mustMerge = false;
                    if(dataObject == null || dataObject.getDataObjectId() == null)
                    {
                        DataObjectType dot = DataObjectTypeUtil.getDataObjectType(entity.getClass().getName());

                        if(dataObject == null)
                            dataObject = new DataObject();

                        User currentUser = getUser();
                        Date now = new Date();

                        if(dataObject.getCreatedOn() == null)
                            dataObject.setCreatedOn(now);
                        if(dataObject.getCreatedFrom() == null)
                            dataObject.setCreatedFrom(currentUser);

                        dataObject.setLastModifiedOn(now);
                        dataObject.setLastModifiedFrom(currentUser);

                        dataObject.setDataObjectType(dot);
                        em.persist(dataObject);
                    }

                    id = dataObject.getDataObjectId();
                    doBean.setId(id);
                    doBean.setDataObject(dataObject);
                }
                else
                {
                    if(dataObject == null)
                        dataObject = em.find(DataObject.class, id);
                    else
                        dataObject = em.merge(dataObject);
                    em.persist(dataObject);
                }
            }

            if(mustMerge)
                entity = em.merge(entity);
            em.persist(entity);

            if(!hasActiveTransation)
                tx.commit();

            return entity;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            tx.rollback();
            throw new RuntimeException(ex);
        }
        finally
        {
            if(!hasActiveTransation)
                em.close();
        }
    }

    public Object remove(Object entity)
    {
        checkRegistration();

        EntityManager em = createEntityManager();

        entity = em.merge(entity);
        em.remove(entity);
        return entity;
    }

    private void checkRegistration()
    {
        if(user == null)
            throw new RuntimeException("The user is not logged. First login.");
    }

    public User login(String username, String password)
    {
        EntityManager em = createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            Query q = em.createQuery("select u from User u where (u.username = :username or u.emailAddress = :username) and u.password = :password");
            q.setParameter("username", username);
            q.setParameter("password", password);
            try
            {
                user = (User)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                user = null;
            }
            tx.commit();
            return user;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            tx.rollback();
            throw new RuntimeException(ex);
        }
        finally
        {
            em.close();
        }
    }

    public void logout()
    {
        user = null;
        close();
    }

    public User save(User user)
    {
        EntityManager em = createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            User currentUser = getUser();
            if(currentUser == null)
                currentUser = user;

            Person person;
            if((person = user.getPerson()) != null && person.getPersonId() == null)
            {
                persist(person, em, false);
            }

            if(user.getUserId() != null)
            {
                user = em.merge(user);
            }
            else
            {
                user.setCreator(currentUser);
                user.setCreationTime(new Date());
                user.setActiveUser(true);
                user.setNewUser(true);
            }

            em.persist(user);
            tx.commit();
            return user;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            tx.rollback();
            throw new RuntimeException(ex);
        }
        finally
        {
            em.close();
        }
    }

    public void removeUser(User user)
    {
        EntityManager em = createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            user = em.merge(user);
            Person person;
            if((person = user.getPerson()) != null)
            {
                Query q = em.createQuery("update User u set u.person = null where u.userId = :userId");
                q.setParameter("userId", user.getUserId());
                q.executeUpdate();

                q = em.createQuery("delete from Person p where p.personId = :personId");
                q.setParameter("personId", person.getPersonId());
                q.executeUpdate();

                q = em.createQuery("delete from DataObject d where d.createdFrom = :user or d.lastModifiedFrom = :user");
                q.setParameter("user", user);
                q.executeUpdate();
            }

            em.remove(user);
            tx.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            tx.rollback();
            throw new RuntimeException(ex);
        }
        finally
        {
            em.close();
        }
    }

    public User getUser()
    {
        return user;
    }
*/

}
