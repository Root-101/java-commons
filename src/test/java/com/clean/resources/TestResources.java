package com.clean.resources;

import com.clean.core.domain.Resource;
import com.clean.core.domain.ResourceBundleUtils;
import com.clean.core.domain.ResourceServiceImpl;
import java.io.File;
import java.net.MalformedURLException;
import org.junit.Test;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class TestResources {

    @Test
    public void testExternalResource() throws MalformedURLException {
        Resource.registerResourceService(
                new ResourceServiceImpl(ResourceBundleUtils.fromExternalFile(new File("C:\\Users\\Yo\\Documents\\NetBeansProjects\\TestNB\\res\\abc"), ResourceBundleUtils.SPANISH))
        );
        Resource.getString("perro");
    }

    @Test
    public void testExternalResourceApart() throws MalformedURLException {
        Resource.registerResourceService(
                new ResourceServiceImpl(ResourceBundleUtils.fromExternalFile("C:\\Users\\Yo\\Documents\\NetBeansProjects\\TestNB\\res", "abc", ResourceBundleUtils.SPANISH))
        );
        Resource.getString("perro");
    }
}
