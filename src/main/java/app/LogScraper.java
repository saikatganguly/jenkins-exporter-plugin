package app;

import app.kafka.KafkaSender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author saikat
 * Created on 30/3/2019
 */
public class LogScraper implements Runnable {

    private File fileToScrapForLog;

    public LogScraper(File fileToScrapForLog) {
        this.fileToScrapForLog = fileToScrapForLog;
    }

    @Override
    public void run() {
        try{
            String content = FileUtils.readFileToString(fileToScrapForLog);
            System.out.println("Logs : "+ content);
            KafkaSender.sendlogs(content);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
