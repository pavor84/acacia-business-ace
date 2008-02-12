/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.processing;

import com.cosmos.acacia.annotation.Property;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

/**
 *
 * @author miro
 */
// An annotation processor that processes all classes
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AcaciaAnnotationProcessor
    extends AbstractProcessor
{

    /*@Override
    public Set<String> getSupportedAnnotationTypes()
    {
        return supportedAnnotationTypes;
    }*/

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        /*String msg = "BeanInfoAnnotationProcessor.process(annotations:" + annotations + ", roundEnv: " + roundEnv + ")";
        note(msg);

        msg = "roundEnv.processingOver(): " + roundEnv.processingOver();
        note(msg);*/

        if(!roundEnv.processingOver())
        {
            /*note("getElementsAnnotatedWith: " + Property.class.getName());
            for(Element element : roundEnv.getElementsAnnotatedWith(Property.class))
            {
                note("element: " + element);
                note("element.getAnnotationMirrors(): " + element.getAnnotationMirrors());
                note("element.getEnclosedElements(): " + element.getEnclosedElements());
                note("element.getEnclosingElement(): " + element.getEnclosingElement());
                note("element.getKind(): " + element.getKind());
                note("element.getModifiers(): " + element.getModifiers());
                note("element.getSimpleName(): " + element.getSimpleName());
            }*/

            /*EntityBeanProcessor entityBeanProcessor = new EntityBeanProcessor(processingEnv);
            note("getElementsAnnotatedWith: " + Property.class.getName());
            for(Element element : roundEnv.getElementsAnnotatedWith(EntityBean.class))
            {
                note("element: " + element);
                note("element.asType(): " + element.asType());
                note("element.getClass(): " + element.getClass());
                note("element.getAnnotationMirrors(): " + element.getAnnotationMirrors());
                note("element.getEnclosedElements(): " + element.getEnclosedElements());
                note("element.getEnclosingElement(): " + element.getEnclosingElement());
                note("element.getKind(): " + element.getKind());
                note("element.getModifiers(): " + element.getModifiers());
                note("element.getSimpleName(): " + element.getSimpleName());
                entityBeanProcessor.process(element);
            }*/
        }
        /*roundEnv.
        for(TypeDeclaration t : roundEnv.getSpecifiedTypeDeclarations())
        {
            if (t.getModifiers().contains(Modifier.PUBLIC))
            {
                System.out.println(t);
                Map<String, Property> props = new TreeMap<String, Property>();
                for (MethodDeclaration m : t.getMethods())
                {
                    Property p = m.getAnnotation(Property.class);
                    if (p != null)
                    {
                        String mname = m.getSimpleName();
                        String[] prefixes = {"get", "set", "is"};
                        boolean found = false;
                        for (int i = 0; !found && i < prefixes.length; i++)
                        {
                            if (mname.startsWith(prefixes[i]))
                            {
                                found = true;
                                int start = prefixes[i].length();
                                String name = Introspector.decapitalize(mname.substring(start));
                                props.put(name, p);
                            }
                        }

                        if (!found)
                        {
                            env.getMessager().printError(m.getPosition(),
                                    "@Property must be applied to getXxx, setXxx, or isXxx method");
                        }
                    }
                }

                try
                {
                    if (props.size() > 0)
                    {
                        writeBeanInfoFile(t.getQualifiedName(), props);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        */
        return false;
    }

    private void note(String msg)
    {
        processingEnv.getMessager().printMessage(Kind.NOTE, msg);
    }

    /**
    Writes the source file for the BeanInfo class.
    @param beanClassName the name of the bean class
    @param props a map of property names and their annotations
     */
    private void writeBeanInfoFile(String beanClassName, Map<String, Property> props)
            throws IOException
    {
        /*System.out.println("writeBeanInfoFile()");
        //processingEnv.getFiler().createSourceFile(beanClassName, null);
        JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(beanClassName + "BeanInfo");
        PrintWriter out = new PrintWriter(javaFileObject.openWriter());
        //PrintWriter out = processingEnv.getFiler().createSourceFile(beanClassName + "BeanInfo", null);
        int i = beanClassName.lastIndexOf(".");
        if (i > 0)
        {
            out.print("package ");
            out.println(beanClassName.substring(0, i));
        }
        out.print("public class ");
        out.print(beanClassName.substring(i + 1));
        out.println("BeanInfo extends java.beans.SimpleBeanInfo");
        out.println("{");
        out.println("   public java.beans.PropertyDescriptor[] getPropertyDescriptors()");
        out.println("   {");
        out.println("      try");
        out.println("      {");
        for (Map.Entry<String, Property> e : props.entrySet())
        {
            out.print("         java.beans.PropertyDescriptor ");
            out.print(e.getKey());
            out.println("Descriptor");
            out.print("            = new java.beans.PropertyDescriptor(\"");
            out.print(e.getKey());
            out.print("\", ");
            out.print(beanClassName);
            out.println(".class);");
            String ed = e.getValue().editor().toString();
            if (!ed.equals(""))
            {
                out.print("         ");
                out.print(e.getKey());
                out.print("Descriptor.setPropertyEditorClass(");
                out.print(ed);
                out.println(".class);");
            }
        }
        out.println("         return new java.beans.PropertyDescriptor[]");
        out.print("         {");
        boolean first = true;
        for (String p : props.keySet())
        {
            if (first)
            {
                first = false;
            }
            else
            {
                out.print(",");
            }
            out.println();
            out.print("            ");
            out.print(p);
            out.print("Descriptor");
        }
        out.println();
        out.println("         };");
        out.println("      }");
        out.println("      catch (java.beans.IntrospectionException e)");
        out.println("      {");
        out.println("         e.printStackTrace();");
        out.println("         return null;");
        out.println("      }");
        out.println("   }");
        out.println("}");
        out.close();*/
    }
}
