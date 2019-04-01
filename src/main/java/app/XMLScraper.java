package app;

import app.kafka.KafkaSender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author saikat
 * Created on 30/3/2019
 */
public class XMLScraper implements Runnable {

    private File fileToScrapForXml;

    public XMLScraper(File fileToScrapForXml) {
        this.fileToScrapForXml = fileToScrapForXml;
    }

    @Override
    public void run() {
        try {
            String content = FileUtils.readFileToString(fileToScrapForXml);
            System.out.println(content);
            KafkaSender.sendXml(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
