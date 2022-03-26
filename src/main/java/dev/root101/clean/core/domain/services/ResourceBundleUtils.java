/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.root101.clean.core.domain.services;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
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
