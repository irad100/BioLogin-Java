import auth.Authentication;
import auth.Face;
import auth.User;
import etc.FireBase;

public class Main {
    public static void main(String[] args) {
        FireBase fireBase = new FireBase();
        User user = fireBase.retrieve();
        Authentication a = new Face(user);
        a.enroll();
        fireBase.upload(user);
        //System.out.println(a.verify());

    }

}
