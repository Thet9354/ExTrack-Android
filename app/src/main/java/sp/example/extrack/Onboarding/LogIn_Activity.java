package sp.example.extrack.Onboarding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.extrack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import sp.example.extrack.MainActivity;

public class LogIn_Activity extends AppCompatActivity {

    private EditText editText_email, editTxt_password;

    private androidx.appcompat.widget.AppCompatButton btn_logIn;

    private String mEmail, mPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        auth = FirebaseAuth.getInstance();

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmail = editText_email.getText().toString();
                mPassword = editTxt_password.getText().toString();

                emailValidation();
                passwordValidation();
                validateInput();
            }
        });
    }

    private void validateInput() {
        if (!emailValidation() | !passwordValidation()){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            //Authenticate here
            auth.signInWithEmailAndPassword(mEmail, mPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LogIn_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LogIn_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean passwordValidation() {
        if (mPassword.isEmpty())
        {
            editTxt_password.setError("Field cannot be empty to proceed");
            return false;
        }
        else
            return true;
    }

    private boolean emailValidation() {
        if (mEmail.isEmpty())
        {
            editText_email.setError("Field cannot be empty to proceed");
            return false;
        }
        else
            return true;
    }

    private void initWidget() {

        editText_email = findViewById(R.id.editText_email);
        editTxt_password = findViewById(R.id.editTxt_password);

        btn_logIn = findViewById(R.id.btn_logIn);

    }
}
