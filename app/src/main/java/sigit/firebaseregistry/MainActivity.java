package sigit.firebaseregistry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //defaining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //defaining view object
    private EditText editTextEmail, editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private TextView textViewSignin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intializing firebase auth object

        firebaseAuth = firebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){
//            that means user alredy logged in
//            so close this activity
            finish();
//            and open profil activity
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
//        initializing view
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonSignup = (Button)findViewById(R.id.buttonSignup);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        progressDialog = new ProgressDialog(this);

        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }
    private void registerUser(){

//        Getting email and password from EditText
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().toString();

//        Checking if email and password are empty

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        //if the email and password are not empety
        //displaying a progress dialog

        progressDialog.setMessage("Registring Please Wait...");
        progressDialog.show();
        //Creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        check if succses
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else {
//                            display show message
                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }



//                      if (task.isSuccessful()) {
////                            display show messege
//                            Toast.makeText(MainActivity.this, "Successfully registed", Toast.LENGTH_LONG).show();
//                        } else {
////                        display show message
//                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
//
//                        }
                        progressDialog.dismiss();
                    }


                });
    }
    @Override
    public void onClick(View view) {
        //calling register method on click

        if (view==buttonSignup){
            registerUser();
        }if (view == textViewSignin){
//            open activity when user taps on the alredy registed textview
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
