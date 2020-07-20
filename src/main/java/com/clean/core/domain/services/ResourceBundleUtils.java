package com.clean.core.domain.services;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ResourceBundleUtils {

    /**
     * Useful constant for country.
     */
    public static Locale SPANISH = new Locale("es", "ES");

    /**
     * Useful constant for country. Fork from Locale.US
     */
    public static Locale ENGLISH = Locale.US;

    public static ResourceBundle fromExternalFile(String externalFile, Locale locale) throws MalformedURLException {
        return fromExternalFile(new File(externalFile), locale);
    }

    /**
     * Create a ResourceBundle from an external {@code file}, asume the folder
     * as the file.getParentFile().toString() and the baseName as the
     * externalFile.getName().<\br>
     * NOTE: The file name DON'T include the lenguaje or .properties
     * extension.<\br>
     * Example: new File("C:\\Users\\SOME_USER\\Documents\\resource_file")
     *
     * @param externalFile external resource file
     * @param locale Locale of file
     * @return the ResourceBundle corresponding to that file
     * @throws MalformedURLException if the external file is not valid
     */
    public static ResourceBundle fromExternalFile(File externalFile, Locale locale) throws MalformedURLException {
        externalFile = new File(externalFile.getAbsolutePath());
        String folder = externalFile.getParentFile().toString();
        String file = externalFile.getName();
        return fromExternalFile(folder, file, locale);
    }

    /**
     * Create a ResourceBundle from an {@code externalFolder} and
     * {@code externalFileName}.<\br>
     * NOTE: The {@code externalFileName} name DON'T include the lenguaje or
     * .properties extension.<\br>
     *
     * @param externalFolder External folder of file
     * @param externalFileName External baseName of file
     * @param locale Locale of file
     * @return the ResourceBundle corresponding to that file
     * @throws MalformedURLException if the external file is not valid
     */
    public static ResourceBundle fromExternalFile(String externalFolder, String externalFileName, Locale locale) throws MalformedURLException {
        URL[] urls = {new File(externalFolder).toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);
        return ResourceBundle.getBundle(externalFileName,
                locale, loader);
    }

    /**
     * Create a ResourceBundle from an {@code internalFileName}.<\br>
     * NOTE: The {@code internalFileName} name DON'T include the lenguaje or
     * .properties extension.<\br>
     *
     * @param internalFile internal file
     * @param locale Locale of file
     * @return the ResourceBundle corresponding to that file
     */
    public static ResourceBundle fromInternalFile(String internalFile, Locale locale) {
        return ResourceBundle.getBundle(internalFile, locale);
    }

}
