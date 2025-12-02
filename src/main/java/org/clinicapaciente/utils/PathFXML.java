package org.clinicapaciente.utils;


import java.nio.file.Paths;

public class PathFXML {
    public static String pathbase() {
        return Paths.get("src/main/java/org/provapoo/view").toAbsolutePath().toString();
    }
}
