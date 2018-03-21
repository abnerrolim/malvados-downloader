package org.abner.random;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MalvadosDownloader {

    private final static Logger LOGGER = Logger.getLogger(MalvadosDownloader.class.getName());

    private final FileHandler fileHandler;
    private final ImageGetter imageGetter;

    private List<String> tirinhaNames = Arrays.asList("tirinha", "tirinhar", "tiramalvados", "tirainicial");
    private Iterator<String> it = tirinhaNames.iterator();

    private long imgNumber;

    public MalvadosDownloader(String path){
        fileHandler = new FileHandler(path);
        imageGetter = new ImageGetter();
        initializeImageNumber();
    }

    public void download(){
        FlowController flowControl = new FlowController();
        Image image = null;
        while (flowControl.hasNext()){
            try {
                String imgName = imgName(flowControl);
                image = imageGetter.downloadImage(imgName);
                flowControl.setEndReached(fileHandler.write(image));
                flowControl.success();
            }catch (Exception e){
                LOGGER.log(Level.WARNING, "Unable to retrieve image number " + imgNumber + ". Ignoring", e);
                flowControl.markException();
            }
        }
    }

    private String imgName(FlowController flowController){
        if(flowController.isLastSuccess()){
            it = tirinhaNames.listIterator();
            imgNumber++;
        }
        if(flowController.first && imgNumber < 1){
            return "tirainicial";
        }else if(it.hasNext())
            return it.next() +  imgNumber;l
        imgNumber++;
        it = tirinhaNames.listIterator();
        throw new RuntimeException("Tried every file combination, without success");
    }

    private void initializeImageNumber(){
        try {
            String fileName = fileHandler.getLastImageName();
            if(Objects.nonNull(fileName)){
                imgNumber = NameUtils.getNumberFromFileName(fileName);
            }
        }catch (IOException e){

        }
    }

    private class FlowController {

        private boolean endReached;
        private int exceptionCount;
        private boolean lastSuccess;
        private boolean first = true;

        public FlowController(){
            this.endReached = false;
            this.exceptionCount = 0;
            this.lastSuccess = true;
        }

        public boolean isLastSuccess(){
            return first ||
                    lastSuccess;
        }

        public void success(){
            this.exceptionCount = 0;
            this.lastSuccess = true;
            this.first = false;
        }
        public void markException(){
            exceptionCount++;
            this.first = false;
            this.lastSuccess = false;
        }
        public void setEndReached(boolean finish){
            this.endReached = finish;
            this.first = false;
        }

        public boolean hasNext(){
            return !endReached
                    && exceptionCount < 15;
        }
    }
}
