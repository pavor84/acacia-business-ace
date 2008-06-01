/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallback;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
public class ProductAssembler
{
    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private EntityManagerFactory emf;
    private EntityManager entityManager;

    private ArrayDeque<DataHolder> stack = new ArrayDeque<DataHolder>();
    private DataHolder dataHolder;

    private boolean initialized;
    private Map parameters;


    public ProductAssembler(AssemblingSchema assemblingSchema)
    {
        dataHolder = new DataHolder();
        stack.push(dataHolder);
        dataHolder.assemblingSchema = assemblingSchema;
    }

    public AssembleResult assemble(AssembleResult assembleResult)
    {
        AssembleResult.Type assembleResultType = assembleResult.getType();
        if(!initialized && !AssembleResult.Type.Initialization.equals(assembleResultType))
            return new ExceptionAssembleResult(new IllegalArgumentException("The Product Assembler is not initialized."));
        if(initialized && AssembleResult.Type.Initialization.equals(assembleResultType))
            return new ExceptionAssembleResult(new IllegalArgumentException("The Product Assembler is already initialized."));

        switch(assembleResultType)
        {
            case Initialization:
                assembleResult = assemble(assembleResult.getParameters());
                break;

            case Intermediate:
                assembleResult = assemble(assembleResult.getComplexProductItems());
                break;

            case Callback:
                assembleResult = assemble(assembleResult.getApplicationCallbacks());
                break;

            default:
                return new ExceptionAssembleResult(new IllegalArgumentException("Unsupported AssembleResult.Type parameter: " + assembleResultType));
        }

        if(AssembleResult.Type.Final.equals(assembleResult.getType()))
        {
            EntityManager em = getEntityManager();
            try
            {
                em.getTransaction().begin();
                persist();
                em.getTransaction().commit();
            }
            catch(Exception ex)
            {
                em.getTransaction().rollback();
                return new ExceptionAssembleResult(ex);
            }
        }

        return assembleResult;
    }

    protected AssembleResult assemble(Map parameters)
    {
        initialized = true;
        this.parameters = parameters;
        dataHolder.assemblyProduct = new AssemblyProduct();

        return null;
    }

    protected AssembleResult assemble(List<ComplexProductItem> complexProductItems)
    {
        return null;
    }

    protected AssembleResult assemble(ApplicationCallback[] callbacks)
    {
        return null;
    }

    protected AssembleResult assemble()
    {
        if(hasNextAssemblingSchemaItem())
        {
            AssemblingSchemaItem asi = nextAssemblingSchemaItem();

        }

        return new FinalAssembleResult(dataHolder.assemblyProduct);
    }


    protected boolean hasNextAssemblingSchemaItem()
    {
        return dataHolder.assemblingSchemaItemNumber < getAssemblingSchemaItems().size();
    }

    protected AssemblingSchemaItem nextAssemblingSchemaItem()
    {
        return getAssemblingSchemaItems().get(dataHolder.assemblingSchemaItemNumber++);
    }

    protected EntityManagerFactory getEntityManagerFactory()
    {
        if(emf == null)
        {
            emf = Persistence.createEntityManagerFactory("ProductAssemblingPU");
        }

        return emf;
    }

    protected void persist()
    {
        persist(dataHolder.assemblyProduct);
    }

    protected void persist(AssemblyProduct assemblyProduct)
    {
        EntityManager em = getEntityManager();
        em.persist(assemblyProduct);
        List<ComplexProductItem> productItems = assemblyProduct.getProductItems();
        if(productItems != null && productItems.size() > 0)
        {
            for(ComplexProductItem productItem : productItems)
            {
                em.persist(productItem);
                Product product = productItem.getProduct();
                if(product instanceof AssemblyProduct)
                    persist((AssemblyProduct)product);
            }
        }
    }

    protected EntityManager getEntityManager()
    {
        if(entityManager == null)
        {
            entityManager = getEntityManagerFactory().createEntityManager();
        }

        return entityManager;
    }

    protected List<AssemblingSchemaItem> getAssemblingSchemaItems()
    {
        if(dataHolder.assemblingSchemaItems == null)
        {
            EntityManager em = getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Query q = em.createNamedQuery("AssemblingSchemaItem.findByAssemblingSchema");
            q.setParameter("assemblingSchema", dataHolder.assemblingSchema);
            dataHolder.assemblingSchemaItems = q.getResultList();
            em.getTransaction().commit();
        }

        return dataHolder.assemblingSchemaItems;
    }

    private class DataHolder
    {
        private AssemblyProduct assemblyProduct;

        private AssemblingSchema assemblingSchema;
        private List<AssemblingSchemaItem> assemblingSchemaItems;
        private int assemblingSchemaItemNumber;
    }
}
