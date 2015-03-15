Base Principals and Ideas for the next version after the first working release

# Introduction #

When this first release with limited functionality is in production, the next release will be started. This next release will be the first release with all planed functionality. It will be maven based. All functionality will be separated in small modules (OSGi Bundles) as follow:
  * API Modules - the API and the implementation will be in different bundles
  * Implementation Modules
  * Persistence (Data Access Services) Modules
  * Services (Business Logic) Modules
  * GUI Modules - Eclipse RCP (or/and SwingX).

The Web Services will be the communication protocol between the Server and Client.
BPEL will be the connection language between the services.


# Details #

1. The [Acacia Framework](http://kenai.com/projects/acacia) and [Acacia Client](http://kenai.com/projects/acacia-client) will be OSGi based using Enterprise OSGi and Distributed OSGi (RFC-119):
  * Client specification:
    * [Universal and Distributed JWS Eclipse RCP Client](https://bugs.eclipse.org/bugs/show_bug.cgi?id=272662)
    * [Universal and Distributed JWS OSGi Client based on NetBeans Platform](http://www.netbeans.org/issues/show_bug.cgi?id=162911)
> Helpful projects:
    * [Eclipse Riena Project](http://www.eclipse.org/proposals/riena/)
    * [Eclipse Communication Framework Project](http://www.eclipse.org/ecf/)
    * [Apache CXF-DOSGi](http://cxf.apache.org/dosgi-releases.html)
    * [Newton Framework](http://newton.codecauldron.org/site/index.html)
    * [UFace](http://code.google.com/p/uface/) implements the [JFace Data Binding](http://wiki.eclipse.org/index.php/JFace_Data_Binding) for GWT, gwt-ext, MyGWT, JFace, Swing and SwingX.
    * [WidgetServer](http://www.c1-setcon.de/widgetserver/) is a component based, server-side, Java/XML rich-client-framework which enables an application to run as either:
      * an application with a rich Web client based on AJAX (HTML, XML, CSS, Javascript);
      * an application with a simple Web client based on HTML and CSS;
      * a standalone application with a Swing GUI;
      * a client/server application with a thin Swing client;
      * or a mobile application on different devices like PDAs or Smartphones.
    * [CookXml](http://cookxml.yuanheng.org/) is a powerful general purpose dynamic XML data binding tool. It is designed to be easy to use and easily extensible. The current implementation only does the unmarshaling. The marshaling aspect will be added.
    * [SwiXml](http://www.swixml.org/), is a small GUI generating engine for Java applications and applets. Graphical User Interfaces are described in XML documents that are parsed at runtime and rendered into javax.swing objects.
    * The [Java-XML-GuiBuilder](http://sourceforge.net/projects/jxmlguibuilder/) creates on the basis of XML documents a runable Java swing surface. These tools are meant for the production by horizontal prototypes, the specification/documentation of the user's interface, the runtime to
    * [XUI](http://sourceforge.net/projects/xui/) is a Java and XML RIA platform for building smart app's. Swing, AWT and other widget sets can be used on a range of hardware. XUI's modular framework can help many aspects of application development. NetBeans and Eclipse are available.
    * [Spring Rich Client Project](http://spring-rich-c.sourceforge.net/1.0.0/index.html) (Spring-RCP) mission is to provide an elegant way to build highly-configurable, GUI-standards-following rich-client applications faster by leveraging the Spring Framework, and a rich library of UI factories and support classes.
    * [AjaxSwing](http://creamtec.com/products/ajaxswing/) automatically converts Java Swing and AWT applications into AJAX websites at runtime.
    * [JavaFX](http://www.javafx.com/)
    * [IT Mill](http://www.itmill.com/)/[Vaadin](http://vaadin.com/) Server-Driven RIA Framework (DHTML/JavaScript & Swing).
    * [ZK Direct RIA, Open Source Ajax](http://www.zkoss.org/)

  * Server specification
    * [FUSE Open Source Project](http://fusesource.com/)
    * [Apache ODE (Orchestration Director Engine)](http://ode.apache.org/)
    * [OSGi based OpenESB with BPEL integration](https://fuji.dev.java.net/) and GUI Design using [NetBeans](http://www.netbeans.org/)
    * [GlassFish v3 - OSGi based Java EE 6 implementation](https://glassfish.dev.java.net/)


2. Program model
  * Data Types definitions
  * Entity definitions
  * Forms: Entity, Entity List, Customization, Relations
  * Form modes: read only, update, create, delete
The reference implementation will be hosted in Kenai Project [Acacia Business Ace](http://kenai.com/projects/acacia-business-ace)

3. GUI based Rich Form Development. The forms will be dynamically generated from both Java annotations or Database. The customization of the system and standard/integrated forms also will be GUI based. The primary GUI will be Swing or/and SWT based. As alternative Ajax Web Client will be provided.

4. ERP module
> 4.1.

5. CRM module
> 5.1.

6. HR Management module
> 6.1.

7. e-Commerce module
> 7.1.

8. Useful links:
  * [OrangeHRM - Open Source HR Management](http://www.orangehrm.com/)
  * [xTuple ERP - open source ERP, accounting, CRM package for small to midsized businesses.](http://www.xtuple.org/)
  * [JFire... ...the new, powerful and free ERP, CRM, eBusiness and SCM/SRM solution for business enterprises. JFire is entirely free/open-source software, uses the latest technologies (EJB 3, JDO 2, Eclipse RCP 3.3) and is designed to be highly customizable.](https://www.jfire.org/modules/content/)
  * [Apache Aries Proposal](http://wiki.apache.org/incubator/AriesProposal) - The Aries project will deliver a set of pluggable Java components enabling an enterprise OSGi application programming model. This includes implementations and extensions of application-focused specifications defined by the OSGi Alliance Enterprise Expert Group (EEG) and an assembly format for multi-bundle applications, for deployment to a variety of OSGi based runtimes.
  * [Eclipse Gemini - Enterprise Modules Project Proposal](http://eclipse.org/proposals/gemini/)
  * [XWT is the XML Windowing Toolkit.](http://www.xwt.org/), [XWT stands for eclipse XML Windowing Toolkit](http://wiki.eclipse.org/E4/XWT) - It is a powerful and lightweight declarative UI framework designed for Eclipse, based on XML as markup language. XWT simplifies UI programming. You can create visible UI elements in the declarative XML markup with a physical separation of the UI definition from the run-time logic. An XML based declarative language is very intuitive for creating interfaces ranging from prototype to production, especially for people with a background in web design and technologies.
  * [Ample SDK](http://www.amplesdk.com/) is a standard-based cross-browser JavaScript GUI Framework for building Rich Internet Applications. It employs XML technologies (such as XUL, SVG or HTML5) for UI layout, CSS for UI style and JavaScript for application logic. It equalizes browsers and brings technologies support to those missing any.
  * [Metawidget](http://metawidget.sourceforge.net/index.html) is a 'smart User Interface widget' that populates itself, at runtime, with UI components to match the properties of your business objects.
  * [JIDE Common Layer](https://jide-oss.dev.java.net/) is Swing component library built on top of Java/Swing. It is also the foundation of other component products from JIDE. This project has over 30 Swing components and over 100k lines of code. It greatly enhanced the default component set provided by Swing and allow developers to focus on business logic layer instead of making components.
  * [XForms](http://www.w3.org/MarkUp/Forms/) projects:
    * [Mozilla XForms Project](http://www.mozilla.org/projects/xforms/)
    * [Novell XForms Explorer](http://developer.novell.com/wiki/index.php/Novell_XForms_Explorer/)
    * [X-Smiles](http://www.x-smiles.org/)
    * [IBM XML Forms](http://alphaworks.ibm.com/tech/xmlforms/)
    * [formsPlayer](http://www.formsplayer.com/)