package org.abner.random;

public class NameUtils {

    private NameUtils(){}

    public static Long getNumberFromFileName(String fileName){
        String name = removeExtension(fileName);
        String number = name.replaceAll("[^0-9.]", "");
        if(number.isEmpty())
            return 0l;
        return Long.valueOf(number);
    }

    public static String removeExtension(String fileName){
        String name = fileName;
        if(fileName.contains("."))
            name = fileName.split("\\.")[0];
        return name;
    }
}
