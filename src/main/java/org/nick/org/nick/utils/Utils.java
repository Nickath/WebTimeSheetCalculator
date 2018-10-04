package org.nick.org.nick.utils;

public class Utils {

    public static String convertToPathOS(String path){
        String fileSeparator = System.getProperty("file.separator");
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            return path.replaceAll("/",fileSeparator);
        }
        else{
            return path.replaceAll("\\\\", fileSeparator);
        }
    }
}
