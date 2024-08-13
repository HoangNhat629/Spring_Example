package vn.tayjava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
@Component
public class BackupJobScheduler {

    @Scheduled(cron = "* 8 * * * *")
    public void backup() {
        log.info("-----[Begin backup database]-----");

        String os = System.getProperty("os.name").toLowerCase();
        log.info("Operation System: {}", os);

        Process process = null;
        try {
            process = Runtime.getRuntime().exec("src/main/resources/backup-schedule-job.sh");
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                log.info("$ {}", line);
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.info("-----[Finish backup database]-----");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            assert process != null;
            process.destroy();
        }
    }


}
