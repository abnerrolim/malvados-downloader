package org.abner.random;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileHandler {

    private Path directory;
    private Path configFile;
    private static final String CONTROL = ".last";

    private final static Logger LOGGER = Logger.getLogger(ImageGetter.class.getName());

    public FileHandler(String path){
        Path parsedPath = Paths.get(path);
        if(Files.notExists(parsedPath)){
            try {
                directory = Files.createDirectories(parsedPath);
            }catch (IOException e){
                directory = Paths.get("");
            }
        }else{
            directory = Paths.get(path);
        }
        configFile = directory.resolve(CONTROL);
    }

    public boolean write(Image image) throws IOException{
        Path file = directory.resolve(image.getName());
        if(Files.exists(file)){
            return true;
        }
        Files.write(file, image.getData());
        return false;
    }

    public String getLastImageName() throws IOException{
        List<Path> files = Files.list(directory).sorted((path1, path2) -> {
                        Long imgNumber1 = NameUtils.getNumberFromFileName(path1.getFileName().toString());
                        Long imgNumber2 = NameUtils.getNumberFromFileName(path2.getFileName().toString());
                        return imgNumber1.compareTo(imgNumber2);
            }).collect(Collectors.toList());
            if(files!= null && !files.isEmpty()){
                String lstName = files.get(files.size()-1).getFileName().toString();
                return NameUtils.removeExtension(lstName);
            }
        return  null;
    }
}
