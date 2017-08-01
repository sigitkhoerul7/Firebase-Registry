package sigit.firebaseregistry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by sigit on 26/07/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view
    private Button buttonSignIn;
    private EditText editTextEmai;
    private EditText editTextPassword;
    private TextView textViewSignup;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase object

        firebaseAuth = firebaseAuth.getInstance();
//        if object getcurrent methode is not nul
//        means user is alredy logged in
        if (firebaseAuth.getCurrentUser()!=null){
            //closing this activity
            finish();
            //opening profile activiy
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
//        initializing view
        editTextEmai = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button)findViewById(R.id.buttonSignin);
        textViewSignup = (TextView)findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

//        attaching click listener
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

//    methode userLogin
    private void userlogin(){
        String email = editTextEmai.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

//        checking if email and password empty

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Plase Enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Plase enter password", Toast.LENGTH_LONG).show();
            return;
        }
//        if email and password not empety
//        displaying progresdialog

        progressDialog.setMessage("Registring please wait...");
        progressDialog.show();

//        logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                //if the task is sucssesfull
                if(task.isSuccessful()){
                    //start the profile Activity
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                }
            }
        });
    }
    public void onClick(View view){
        if (view==buttonSignIn){
            userlogin();
        }
        if (view==textViewSignup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}

