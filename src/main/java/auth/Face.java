package auth;
import com.microsoft.cognitive.speakerrecognition.contract.*;
import com.microsoft.projectoxford.face.rest.*;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import etc.FireBase;
import etc.Snap;

public class Face extends Authentication{
    private User user;
    private FaceServiceClient client;

    public Face(User user) {
        this.user = user;
        client = new FaceServiceRestClient("https://westeurope.api.cognitive.microsoft.com/face/v1.0", "64da8bdc219544b9b8c07bf72c776542");
    }
    public boolean enroll() {
        try {
            InputStream inputSrc = new Snap().run();
            UUID profileID = client.detect(inputSrc, true, false, null)[0].faceId;
            user.setFace(profileID);
            setEnabled(true);
            System.out.println("Face Found!");
            System.out.println("Enrolled Successfully!");
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Face not Found! try again");
        }
        catch (ClientException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean verify(User user) {
        try {
            UUID profileIDSrc = user.getFace();
            if (profileIDSrc == null)
                throw new EnrollmentException(null);

            UUID profileIDTrg;

            InputStream inputTrg = new Snap().run();
            profileIDTrg = client.detect(inputTrg, true, false, null)[0].faceId;
            System.out.println("Face Found!");

            return client.verify(profileIDSrc, profileIDTrg).isIdentical;

        } catch (EnrollmentException e) {
            System.err.println("Finish Enrollment First");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Face not Found!");
        } catch (NullPointerException | ClientException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}