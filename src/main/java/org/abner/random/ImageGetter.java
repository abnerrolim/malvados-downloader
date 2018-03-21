package org.abner.random;

import java.io.*;
import java.net.URL;
import java.util.logging.Logger;

public class ImageGetter {


    private final static Logger LOGGER = Logger.getLogger(ImageGetter.class.getName());

    public final static String[] EXT = {".jpg", ".gif", ".png"};

    public final static String HOST = "https://www.malvados.com.br/";


    public Image downloadImage(String imageName) throws IOException {
        Image response = null;
        for (String ext : EXT) {
            try {
                URL url = new URL(HOST + imageName + ext);
                LOGGER.info("Getting " + url);
                try (InputStream in = new BufferedInputStream(url.openStream())) {
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                        byte[] buf = new byte[1024];
                        int n = 0;
                        while (-1 != (n = in.read(buf))) {
                            out.write(buf, 0, n);
                        }
                        response = new Image(out.toByteArray(), imageName + ext);
                    }
                }
                break;
            } catch (FileNotFoundException e) {
                LOGGER.info("Unable to find " + imageName + " as " + ext);
            }
        }
        if (response == null)
            throw new FileNotFoundException(imageName + "Can't be found");
        return response;
    }


}
