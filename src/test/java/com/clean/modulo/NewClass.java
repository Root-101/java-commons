package com.clean.modulo;

import com.clean.core.app.modules.DefaultAbstractModule;
import org.junit.Test;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NewClass {

    @Test
    public void abc() {
        String a = new DefaultAbstractModule() {
            @Override
            protected <T> T getOwnImplementation(Class<T> classType) {
                if (classType == String.class) {
                    return (T) "123";
                }
                return null;
            }

            @Override
            public String getModuleName() {
                return "achi";
            }
        }.getImplementation(String.class);
        System.out.println(a);
    }
}
