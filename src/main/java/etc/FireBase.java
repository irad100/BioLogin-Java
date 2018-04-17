package etc;
import auth.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FireBase {
    private CollectionReference users;

    public FireBase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/files/FireBase.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setProjectId("bio-login")
                    .build();
            FirebaseApp.initializeApp(options);
            Firestore db = FirestoreClient.getFirestore();
            users = db.collection("users");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void upload(User user) {
        users.document(user.getName()).set(user);
    }

    public User retrieve() {
        try {
            String name = System.getProperty("user.name");
            DocumentSnapshot docUser = users.document(name).get().get();
            User user;
            if (docUser.exists()) {
                user = docUser.toObject(User.class);
            } else {
                user = new User();
            }
            return user;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
