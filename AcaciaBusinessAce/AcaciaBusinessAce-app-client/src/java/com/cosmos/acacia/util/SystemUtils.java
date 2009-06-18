/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.util;

import com.cosmos.acacia.crm.bl.currency.CurrencyRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.gui.entity.EntityPanelException;
import com.cosmos.acacia.service.ServiceManager;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.Date;
import org.jdesktop.beansbinding.DefaultELContext;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.el.ELContext;
import org.jdesktop.el.impl.lang.FunctionMapperImpl;

/**
 *
 * @author Miro
 */
public class SystemUtils {

    private static final InheritableThreadLocal<ELContext> elContext =
            new InheritableThreadLocal<ELContext>();

    public static ELContext getSystemELContext() {
        ELContext context;
        if ((context = elContext.get()) != null) {
            return context;
        }

        context = createELContext();
        elContext.set(context);

        return context;
    }

    public static void removeSystemELContext() {
        elContext.remove();
    }

    public static ELContext createELContext() {
        ELContext context = new DefaultELContext();
        FunctionMapperImpl functionMapper = (FunctionMapperImpl) context.getFunctionMapper();
        String prefix = "";
        String localName = "convertAmount";
        Method method;
        try {
            method = SystemUtils.class.getMethod(localName,
                    BigDecimal.class, Date.class, DbResource.class, DbResource.class);
        } catch (Exception ex) {
            throw new EntityPanelException(ex);
        }
        functionMapper.addFunction(prefix, localName, method);

        localName = "now";
        try {
            method = SystemUtils.class.getMethod(localName);
        } catch (Exception ex) {
            throw new EntityPanelException(ex);
        }
        functionMapper.addFunction(prefix, localName, method);

        return context;
    }

    public static BigDecimal convertAmount(BigDecimal amount, Date rateForDate,
            DbResource fromCurrency, DbResource toCurrency) {
        if (amount == null || rateForDate == null || fromCurrency == null || toCurrency == null) {
            return null;
        }

        if (fromCurrency.equals(toCurrency) || BigDecimal.ZERO.equals(amount)) {
            return amount;
        }

        CurrencyRemote currencyService = (CurrencyRemote) ServiceManager.getService("currency");
        CurrencyExchangeRate cer = currencyService.getCurrencyExchangeRate(rateForDate, fromCurrency, toCurrency);
        return amount.multiply(cer.getExchangeRate(), MathContext.DECIMAL64);
    }

    public static Date now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static ELProperty create(String expression) {
        return ELProperty.create(getSystemELContext(), expression);
    }
}
