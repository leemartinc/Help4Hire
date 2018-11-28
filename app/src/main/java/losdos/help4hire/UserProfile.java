package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class UserProfile extends Fragment {

    View myView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("users");
    String RegisteredUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.current_user_profile, container, false);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String RegisteredUserEmail = currentUser.getEmail();

        RegisteredUserID = currentUser.getUid();

        final TextView fNameField = myView.findViewById(R.id.userProfileFirstName);
        final TextView lNameField = myView.findViewById(R.id.userProfileLastName);
        final TextView addressField = myView.findViewById(R.id.userProfileAddress);
        final TextView emailField = myView.findViewById(R.id.currentUserProfileEmail);
        final TextView roleField = myView.findViewById(R.id.current_role);
        final TextView uidField = myView.findViewById(R.id.current_user_id);


        reference.document(RegisteredUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    fNameField.setText(documentSnapshot.getString("firstName"));

                    lNameField.setText(documentSnapshot.getString("lastName"));

                    addressField.setText(documentSnapshot.getString("address"));

                    String DBrole = documentSnapshot.getString("role");

                    if(DBrole.equals("user")){
                        roleField.setText("You currently do not provide any services");
                    }else if(DBrole.equals("provider")){
                        roleField.setText("You are currently a service Provider");
                    }

                    emailField.setText(RegisteredUserEmail);

                    uidField.setText("User ID: " +RegisteredUserID);

                }else{
                    fNameField.setText("Error");
                    lNameField.setText("Error");
                    addressField.setText("Error");
                    roleField.setText("Error");
                    emailField.setText("Error");
                    uidField.setText("Error");
                }
            }
        });









        return myView;
    }
}
