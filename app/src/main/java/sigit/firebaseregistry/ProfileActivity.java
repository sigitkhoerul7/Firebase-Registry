package sigit.firebaseregistry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by sigit on 26/07/17.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
//    firebase auth object

    private FirebaseAuth firebaseAuth;

//    View Object
    private TextView textViewUserEmail;
    private Button buttonLogout;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        initializing firebase autentications object
        firebaseAuth = firebaseAuth.getInstance();
//        if the user is not logged in
//        that mean current user will return null

        if (firebaseAuth.getCurrentUser()==null){
//            closing this activity
            finish();
//            starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
//        Getting curent user
        FirebaseUser user = firebaseAuth.getCurrentUser();
//        initializing View
        textViewUserEmail = (TextView)findViewById(R.id.textViewEmail);
        buttonLogout = (Button)findViewById(R.id.buttonlogout);

//        displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

//        adding listener to button

        buttonLogout.setOnClickListener(this);
    }
    public void onClick(View v){
//        if logout is pressed
        if (v == buttonLogout){
//            looging out the user
            firebaseAuth.signOut();
//            closing Activity
            finish();
//            Starting login activity
            startActivity(new Intent(this,LoginActivity.class));

        }

    }




}
