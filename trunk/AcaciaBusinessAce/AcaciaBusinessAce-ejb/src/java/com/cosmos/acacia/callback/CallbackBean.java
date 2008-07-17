package com.cosmos.acacia.callback;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CallbackBean implements CallbackLocal {

    protected static Logger log = Logger.getLogger(CallbackBean.class);

    @Override
    public CallbackTransportObject handle(CallbackHandler handler,
            CallbackTransportObject request)
    {
        SecurityManager sm = System.getSecurityManager();
        log.info("SM: " + sm);
        
        Map<String, Object> params = request.getParams();
        
        for (String key : params.keySet()) {
            Object obj = params.get(key);
            try {
                if (obj.getClass().getPackage().getName().indexOf("com.cosmos.acacia") != -1
                        || obj instanceof Collection)
                {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(obj);
                    byte[] array = bos.toByteArray();
                    params.put(key, array);
                }
            } catch (Exception ex) {
                
            }
        }
        
        try {
            return handler.handle(request);
        } catch (Exception ex) {
            log.error("Error during callback", ex);
            return null;
        }

    }

}
