package etc;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Json {

    public void toJson(User user) throws IOException { new Gson().toJson(user, new FileWriter(user.getName() + ".json")); }

    public User fromJson() throws IOException { return new Gson().fromJson(new FileReader(System.getProperty("user.name")+ ".json"), User.class); }
}
