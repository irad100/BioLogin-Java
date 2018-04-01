package etc;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Test {
    public static void main (String[]args) throws IOException {
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
    }
}
