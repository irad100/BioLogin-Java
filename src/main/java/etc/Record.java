package etc;
import javax.sound.sampled.*;
import java.io.*;

public class Record {
    // the line from which audio data is captured
    private TargetDataLine line;

    public InputStream run() {
        try {
            // record duration, in milliseconds
            final long RECORD_TIME = 7000;
            // format of audio file
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

            // wav file
            File wavFile = new File(System.getProperty("user.home")+"/BioLoginTmp/tmp.wav");
            File tmp = new File(System.getProperty("user.home")+"/BioLoginTmp");
            if(!tmp.exists()) {
                boolean created = tmp.mkdir();
                if (!created) {
                    throw new FileNotFoundException("Couldn't create directory");
                }

            }
            // creates a new thread that waits for a specified
            // of time before stopping
            Thread stopper = new Thread(() -> {
                try { Thread.sleep(RECORD_TIME); }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
            });

            stopper.start();
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            AudioSystem.write(ais, fileType, wavFile);


            InputStream result;
            result = new FileInputStream(wavFile);
            while(!wavFile.delete())
                assert true;

            return result;
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }

}