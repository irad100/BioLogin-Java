package etc;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;

public class Snap{

    public InputStream run() {
        try {
            /*
            //out.setPlainText(null);
            TimeUnit.SECONDS.sleep(1);
            //out.appendPlainText;
            System.out.println("Taking a picture in:");
            TimeUnit.SECONDS.sleep(1);
            //out.appendPlainText;
            System.out.println("3...");
            TimeUnit.SECONDS.sleep(1);
            //out.appendPlainText;
            System.out.println("2...");
            TimeUnit.SECONDS.sleep(1);
            //out.appendPlainText;
            System.out.println("1... ");
            TimeUnit.SECONDS.sleep(1);
            //out.appendPlainText;
            System.out.println("Cheese!");
            TimeUnit.SECONDS.sleep(1);
            */
            Webcam webcam = Webcam.getDefault();
            Dimension[] d = { WebcamResolution.HD.getSize() };
            webcam.setCustomViewSizes(d);
            webcam.setViewSize(d[0]);
            webcam.open();
            ByteArrayOutputStream outputStream;
            InputStream result;
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(webcam.getImage(), "PNG", outputStream);
            outputStream.flush();
            result = new ByteArrayInputStream(outputStream.toByteArray());
            outputStream.close();
            webcam.close();
            return result;

        } catch (/*InterruptedException |*/ IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    public void platformSleep(int sec) {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000*sec);
                } catch (java.lang.InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    } */

}
