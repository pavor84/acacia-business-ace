/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author miro
 */
public class ClassHelper {

    private static final char EXTENSION_SEPARATOR = '.';

    public static Class getClass(String className) {
        if ("boolean".equals(className)) {
            return boolean.class;
        } else if ("char".equals(className)) {
            return char.class;
        } else if ("byte".equals(className)) {
            return byte.class;
        } else if ("short".equals(className)) {
            return short.class;
        } else if ("int".equals(className)) {
            return int.class;
        } else if ("long".equals(className)) {
            return long.class;
        } else if ("float".equals(className)) {
            return float.class;
        } else if ("double".equals(className)) {
            return double.class;
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getClassName(Class cls) {
        if (boolean.class.equals(cls)) {
            return Boolean.class.getName();
        } else if (char.class.equals(cls)) {
            return Character.class.getName();
        } else if (byte.class.equals(cls)) {
            return Byte.class.getName();
        } else if (short.class.equals(cls)) {
            return Short.class.getName();
        } else if (int.class.equals(cls)) {
            return Integer.class.getName();
        } else if (long.class.equals(cls)) {
            return Long.class.getName();
        } else if (float.class.equals(cls)) {
            return Float.class.getName();
        } else if (double.class.equals(cls)) {
            return Double.class.getName();
        }

        return cls.getName();
    }

    public static String objectToString(Object object) {
        String result = object.getClass().getName() + " values {\n";
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                result += field.getName() + "=" + field.get(object) + "\n";
            } catch (Exception ex) {
            }
        }
        result += "\n}";

        return result;
    }

    public static Set<Class<?>> getClasses(String packageName)
            throws IOException, ClassNotFoundException {
        return getClasses(packageName, false, null);
    }

    public static Set<Class<?>> getClasses(String packageName, Class baseClass)
            throws IOException, ClassNotFoundException {
        return getClasses(packageName, false, baseClass);
    }

    public static Set<Class<?>> getClasses(String packageName, boolean includeSubDirs)
            throws IOException, ClassNotFoundException {
        return getClasses(packageName, includeSubDirs, null);
    }

    public static Set<Class<?>> getClasses(String packageName, boolean includeSubDirs, Class baseClass)
            throws IOException, ClassNotFoundException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return getClasses(loader, packageName, includeSubDirs, baseClass);
    }

    public static Set<Class<?>> getClasses(
            ClassLoader loader,
            String packageName,
            boolean includeSubDirs,
            Class baseClass)
            throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = loader.getResources(path);
        if (resources != null) {
            while (resources.hasMoreElements()) {
                String filePath = resources.nextElement().getFile();
                // WINDOWS HACK
                if (filePath.indexOf("%20") > 0) {
                    filePath = filePath.replaceAll("%20", " ");
                }
                if (filePath != null) {
                    if ((filePath.indexOf("!") > 0) & (filePath.indexOf(".jar") > 0)) {
                        String jarPath = filePath.substring(0, filePath.indexOf("!")).substring(filePath.indexOf(":") + 1);
                        // WINDOWS HACK
                        if (jarPath.indexOf(":") >= 0) {
                            jarPath = jarPath.substring(1);
                        }
                        classes.addAll(getFromJARFile(jarPath, path, includeSubDirs, baseClass));
                    } else {
                        classes.addAll(getFromDirectory(new File(filePath), packageName, includeSubDirs, baseClass));
                    }
                }
            }
        }
        return classes;
    }

    private static Set<Class<?>> getFromDirectory(
            File directory,
            String packageName,
            boolean includeSubDirs,
            Class baseClass)
            throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                String fileName = file.getName();
                if(file.isDirectory()) {
                    if(includeSubDirs) {
                        if(packageName != null && packageName.length() > 0) {
                            fileName = packageName + "." + fileName;
                        }
                        classes.addAll(getFromDirectory(
                                file, fileName, includeSubDirs, baseClass));
                    }
                } else if (fileName.endsWith(".class")) {
                    String name;
                    if(packageName != null && packageName.length() > 0) {
                        name = packageName + '.' + stripFilenameExtension(fileName);
                    } else {
                        name = stripFilenameExtension(fileName);
                    }
                    try {
                        Class<?> clazz = Class.forName(name);
                        if(baseClass == null || (baseClass != null && baseClass.isAssignableFrom(clazz))) {
                            classes.add(clazz);
                        }
                    } catch(Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }

    private static Set<Class<?>> getFromJARFile(
            String jar,
            String pathName,
            boolean includeSubDirs,
            Class baseClass)
            throws IOException, ClassNotFoundException {
        String packageName = pathName.replace('/', '.');
        Set<Class<?>> classes = new HashSet<Class<?>>();
        JarInputStream jarFile = new JarInputStream(new FileInputStream(jar));
        JarEntry jarEntry;
        do {
            jarEntry = jarFile.getNextJarEntry();
            if (jarEntry != null) {
                String className = jarEntry.getName();
                if(jarEntry.isDirectory()) {
                    if(includeSubDirs) {
                        //System.out.println("jarEntry: " + jarEntry);
                        //System.out.println("className: " + className);
                        //classes.addAll(getFromJARFile(file, packageName + "." + fileName, includeSubDirs));
                    }
                } else if (className.endsWith(".class")) {
                    className = stripFilenameExtension(className);
                    if (className.startsWith(pathName)) {
                        Class cls;
                        try {
                            cls = Class.forName(className.replace('/', '.'));
                            if(baseClass == null || (baseClass != null && baseClass.isAssignableFrom(cls))) {
                                if(includeSubDirs) {
                                    classes.add(cls);
                                } else if(packageName.equals(cls.getPackage().getName())) {
                                    classes.add(cls);
                                }
                            }
                        } catch(Throwable ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } while (jarEntry != null);
        return classes;
    }

    /**
     * Strip the filename extension from the given path, e.g. "mypath/myfile.txt" -> "mypath/myfile".
     * @param path - the file path (may be null)
     * @return the path with stripped filename extension, or null if none
     */
    public static String stripFilenameExtension(String path) {
        if(path == null) {
            return null;
        }

        int index;
        if((index = path.lastIndexOf(EXTENSION_SEPARATOR)) >= 0) {
            return path.substring(0, index);
        }

        return path;
    }
}
