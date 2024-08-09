package vn.tayjava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class BackupJobScheduler {

    //@Scheduled(cron = "* * * * * *")
    @Scheduled(fixedDelay = 300000)
    public void backup() {
        log.info("Running backup job");

        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        log.info("Datetime: {}", datetime);

        String os = System.getProperty("os.name").toLowerCase();
        log.info("OS: {}", os);

        try {
//            Process process = Runtime.getRuntime().exec("./backup-schedule-job.sh");
            Process process = Runtime.getRuntime().exec("src/main/resources/backup-schedule-job.sh");
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("zsh: " + line);
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.exit(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
