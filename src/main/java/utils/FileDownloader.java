package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Deprecated //We MIGHT need to use this in the future, but we currently do not download anything
public class FileDownloader {

    //This method downloads a file and returns a string containing the file's contents
    //It is used to download and pass json files to the Gson parser
    public String fileToString(String toConvert) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(toConvert);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    //TODO add methods to grab photos 'n stuff

}
