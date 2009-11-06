package com.ijws.glassfishsvc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A command line utility to help Windows users create and remove a Windows 
 * service for Glassfish.  Glassfish's online documentation does not show an 
 * example for people who want to use a space in their path (such as 
 * C:\Program Files\). The complicated escape sequences are in-humane.
 *
 * @author Ryan de Laplante
 */
public class GlassfishSvc {
    private static int action = 0;
    private static String installDir = new File("").getAbsolutePath();
    private static String serviceName = "GlassfishAppServer";
    private static String domain = "domain1";
    private static String adminUser = "admin";
    private static String adminPass = "";
    
    private static final int ACTION_INSTALL = 1;
    private static final int ACTION_UNINSTALL = 2;
    
    
    public GlassfishSvc() {
    }
    
    /**
     * Main method where high level logic lives.  See the method body of
     * displayHelp() for command line information.
     */
    public static void main(String[] args) {
        System.out.println("");
        System.out.println("glassfishsvc v1.0 (Aug 19 2007)");
        System.out.println("");
        
        parseArguments(args);
        
        if (isValidInput()) {
            // display the settings that will be used
            System.out.println("Service name                : " + serviceName);
            System.out.println("Glassfish installation path : " + installDir);
            System.out.println("Glassfish domain            : " + domain);
            System.out.println("Glassfish admin username    : " + adminUser);
            
            if (adminPass.length() > 0) {
                System.out.println("Glassfish admin password    : " + 
                        adminPass);
            }
            
            System.out.println("");
            
            // perform the action
            if (action == ACTION_INSTALL) {
                installService();
            } else if (action == ACTION_UNINSTALL) {
                uninstallService();
            }
        } else {
            // invalid or missing input. Display help
            displayHelp();
        }

        return;
    }
    
    /**
     * Outputs instructions on how to use this program.
     */
    private static void displayHelp() {
        System.out.println("");
        System.out.println("DESCRIPTION:");
        System.out.println("    Installs and uninstalls a Windows service for Glassfish");
        System.out.println("");
        System.out.println("USAGE:");
        System.out.println("    java -jar glassfishsvc.jar [-i | -u] [OPTIONS]");
        System.out.println("");
        System.out.println("    -i        Installs a Windows service for Glassfish.");
        System.out.println("    -u        Uninstalls a Windows service for Glassfish.");
        System.out.println("    -n name   Name for the Windows service. Use double quotes around names");
        System.out.println("              that contain spaces.  Defaults to GlassfishAppServer.");
        System.out.println("    -d path   Directory where Glassfish is installed. Use double");
        System.out.println("              quotes around paths with spaces, and escape back slashes.");
        System.out.println("              Defaults to current directory.");
        System.out.println("    -m domain Name of the Glassfish domain to start and stop. Defaults to");
        System.out.println("              domain1.");
        System.out.println("    -a user   Glassfish admin user name.  Defaults to admin.");
        System.out.println("    -p pwd    Glassfish admin password.  A password.txt file will be created");
        System.out.println("              in the Glassfish install directory containing the password in");
        System.out.println("              plain text, and the Windows service will be configured to read");
        System.out.println("              from it. This is usually not necessary. If no password is passed");
        System.out.println("              in, the password.txt file will not be created.");
        System.out.println("");
        System.out.println("EXAMPLES:");
        System.out.println("    java -jar glassfishsvc.jar -i");
        System.out.println("    java -jar glassfishsvc.jar -i -p adminadmin");
        System.out.println("    java -jar glassfishsvc.jar -i -n MyServiceName -d \"C:\\\\Program Files\\\\Sun\\\\Glassfish\" -m myDomain -a admin5 -p secretpwd");
        System.out.println("    java -jar glassfishsvc.jar -u");
        System.out.println("    java -jar glassfishsvc.jar -u -n MyServiceName");
        System.out.println("");
        System.out.println("AUTHOR:");
        System.out.println("    Ryan de Laplante <ryan@ijws.com>");
        System.out.println("");
        
        return;
    }
    
    /**
     * Parses the command line arguments and fills in the static fields for use
     * by other parts of the program.  No validation is done in this method.
     *
     * @param args  Command line arguments from main()
     */
    private static void parseArguments(String args[]) {
        String value = null;
        String lastValue = null;
        
        for (int i = 0; i < args.length; i++) {
            lastValue = value;
            value = args[i].toLowerCase();
            
            if (value.equals("-i")) {
                // create a new windows service
                action = ACTION_INSTALL;
            } else if (value.equals("-u")) {
                // uninstall windows service
                action = ACTION_UNINSTALL;
            } else if (value.equals("-n")) {
                // service name
                if (i + 1 < args.length) {
                    serviceName = args[++i];
                }
            } else if (value.equals("-d")) {
                // path to glassfish installation
                if (i + 1 < args.length) {
                    installDir = args[++i];
                }
            } else if (value.equals("-m")) {
                // domain to start and stop
                if (i + 1 < args.length) {
                    domain = args[++i];
                }
            } else if (value.equals("-a")) {
                // glassfish admin user
                if (i + 1 < args.length) {
                    adminUser = args[++i];
                }
            } else if (value.equals("-p")) {
                // glassfish admin password
                if (i + 1 < args.length) {
                    adminPass = args[++i];
                }
            }
        }
        
        return;
    }
    
    /**
     * Validates the default settings or the overridden values from command line
     * arguments.  One of the checks looks for the presence of 
     * \lib\appservService.exe and \bin\asadmin.bat off of the Glassfish install
     * directory.
     *
     * @return true if the settings are valid
     */
    private static boolean isValidInput() {
        boolean result = true;
        File installFile;
        File testFile;
        
        // validate action
        if (action == 0) {
            System.out.println("* You need to specify -i or -u");
            result = false;
        }
        
        // validate the glassfish installation path
        if (action == ACTION_INSTALL) {
            // load the possibly escaped path string into a File, then remember
            // the un-escaped path with no trailing slash.
            installFile = new File(installDir);
            installDir = installFile.getPath();
            
            if (installFile.exists()) {
                if (installFile.isDirectory() == false) {
                    // installDir's path is not a directory
                    System.out.println("* " + installDir + " is not a " +
                            "directory");
                    result = false;
                } else {
                    // make sure <installPath>\lib\appservService.exe exists
                    testFile = new File(installFile.getPath() + 
                            "\\lib\\appservService.exe");

                    if (testFile.exists() == false) {
                        // appservService.exe not found
                        System.out.println("* " + testFile.getPath() + 
                                " not found");
                        result = false;
                    }

                    // make sure <installPath>\bin\asadmin.bat exists
                    testFile = new File(installFile.getPath() + 
                            "\\bin\\asadmin.bat");

                    if (testFile.exists() == false) {
                        // asadmin.bat not found
                        System.out.println("* " + testFile.getPath() + 
                                " not found");
                        result = false;
                    }
                    
                    // make sure the domain exists
                    testFile = new File(installFile.getPath() + "\\domains\\" + 
                            domain);
                    
                    if (testFile.exists() == false) {
                        // domain not found
                        System.out.println("* The directory for '" + domain +
                                "' does not exist : " + testFile.getPath());
                        result = false;
                    }
                }
            } else {
                // installDir's path does not exist
                System.out.println("* " + installDir + " does not exist");
                result = false;
            }
        }
        
        if (serviceName == null || serviceName.length() == 0) {
            // service name cannot be empty
            System.out.println("* Windows service name cannot be empty");
            result = false;
        }
        
        if (action == ACTION_INSTALL) {
            if (adminUser == null || adminUser.length() == 0) {
                // admin user cannot be empty
                System.out.println("* Glassfish admin username cannot be " +
                        "empty");
                result = false;
            }
        }
        
        return result;
    }
    
    /**
     * Builds the proper String for appservService.exe and sc.exe, then installs
     * the service. It will output "Done." if successful, or "Error: <msg>" if 
     * there was an exception.  This method uses code from Sun Java Application
     * Platform SDK installer to build the Strings.
     */
    private static void installService() {
        String[] scCommandArray;
        StringBuilder servicePath = new StringBuilder();
        
        System.out.println("Installing service...");
        
        // build the complicated String used to start/stop Glassfish
        servicePath.append(installDir + "\\lib\\appservService.exe " +
                "\\\"\\\\\\\"" + installDir + "\\bin\\asadmin.bat" + 
                "\\\\\\\"" + " start-domain --user " + adminUser);
        
        if (adminPass.length() > 0) {
            servicePath.append(" --passwordfile \\\\\\\"" + installDir + 
                    "\\password.txt\\\\\\\"");
        }

        servicePath.append(" " + domain + "\\\" \\\"\\\\\\\"" + installDir + 
                "\\bin\\asadmin.bat" + "\\\\\\\"" + " stop-domain " +
                domain + "\\\\\\\"");
        
        // build a String[] containing the Windows sc.exe command information
        // to be executed
        scCommandArray = new String[] { "sc.exe", "create", serviceName,
                "binPath=", servicePath.toString(),
                "start=", "auto",
                "type=", "own",
                "type=", "interact",
                "DisplayName=", serviceName };

        try {
            if (adminPass.length() > 0) {
                // create a password.txt file to help Glassfish start unattended
                File passwdFile = new File(installDir + "\\password.txt");
                FileWriter fw = new FileWriter(passwdFile);
                fw.write("AS_ADMIN_PASSWORD=" + adminPass + "\n");
                fw.close();
            }
            
            // install the service
            Runtime.getRuntime().exec(scCommandArray);
            System.out.println("Done.\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    
        return;
    }
    
    /**
     * Uses sc.exe to delete the Windows service who's name matches the value 
     * of serviceName. It will output "Done." if successful, or "Error: <msg>" 
     * if there was an exception.
     */
    private static void uninstallService() {
        String[] scCommandArray;
                
        System.out.println("Uninstalling service...");
        
        scCommandArray = new String[] { "sc.exe", "delete", serviceName};
            
        try {
            Runtime.getRuntime().exec(scCommandArray);
            System.out.println("Done.\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return;
    }
}
