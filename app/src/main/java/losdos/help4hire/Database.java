package losdos.help4hire;


import com.google.firebase.firestore.FirebaseFirestore;

public class Database extends android.app.Application{

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }
}
