package auth;
import com.microsoft.cognitive.speakerrecognition.SpeakerVerificationClient;
import com.microsoft.cognitive.speakerrecognition.SpeakerVerificationRestClient;
import com.microsoft.cognitive.speakerrecognition.contract.*;
import com.microsoft.cognitive.speakerrecognition.contract.verification.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.microsoft.cognitive.speakerrecognition.contract.EnrollmentException;

import etc.Json;
import etc.Record;

public class Voice extends Authentication{
    private User user;
    private SpeakerVerificationClient client;


    static private Scanner input = new Scanner(System.in);
    public Voice(User user) {
        this.user = user;
        client = new SpeakerVerificationRestClient("416a01ef921b468db0664b854300d326");
    }
    private String setSentence() {
        String sentence = "none";
        try {
            List<VerificationPhrase> phrases = client.getPhrases("en-us");
            for (int i=0; i < phrases.size(); i++) {
                System.out.println(i+1 + ": " + phrases.get(i).phrase);
            }
            System.out.print("Choose a Sentence: ");
            sentence = phrases.get(input.nextInt()-1).phrase;
            System.out.println("You chose: " + sentence);

        } catch (PhrasesException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return sentence;
    }
    public boolean enroll() {
        try {
            UUID profileID = user.getVoice();
            if(profileID == null) {
                profileID = client.createProfile("en-us").verificationProfileId;
                user.setVoice(profileID);
            }

            String sentence = setSentence();
            if(client.getProfile(profileID).enrollmentStatus.equals(EnrollmentStatus.ENROLLED))
                client.resetEnrollments(profileID);
            int i = 1;
            Enrollment voiceSrc;
            do {
                System.out.println(i + ": Please say '" + sentence + "' into the microphone");
                InputStream inputSrc = new Record().run();
                voiceSrc = client.enroll(inputSrc, profileID);
                i++;
            } while(voiceSrc.remainingEnrollments > 0);
            System.out.println("Enrolled Successfully!");
            return true;
        } catch (CreateProfileException | GetProfileException | ResetEnrollmentsException | IOException | EnrollmentException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean verify() {
        try {
            User user = new Json().fromJson();
            UUID profileID = user.getVoice();
            if (!client.getProfile(profileID).enrollmentStatus.equals(EnrollmentStatus.ENROLLED))
                throw new EnrollmentException("Not Enrolled");
            System.out.println("To Login say the Sentence into the microphone");
            InputStream inputTrg = new Record().run();
            return client.verify(inputTrg, profileID).result == Result.ACCEPT;

        } catch (GetProfileException | IOException | EnrollmentException | VerificationException e) {
            e.printStackTrace();
            return false;
        }
    }
}