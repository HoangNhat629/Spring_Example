package vn.tayjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamEater extends Thread {
    BufferedReader br;

    /**
     * Construct a StreamEater on an InputStream.
     */
    public StreamEater(InputStream is) {
        this.br = new BufferedReader(new InputStreamReader(is));
    }

    public void run() {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("zsh: " + line);
            }
        } catch (IOException e) {
            // Do something to handle exception
            System.out.println(e.getMessage());

        } finally {
            try {
                br.close();
            } catch (Exception e) {
                System.out.printf(e.getMessage());
            }
        }
    }
}
