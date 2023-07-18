package sp.example.extrack.Onboarding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.extrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration_Activity extends AppCompatActivity {

    private EditText editTxt_username, editText_emailAddress, editTxt_password, editTxt_confirmPassword, editTxt_phoneNumber;

    private androidx.appcompat.widget.AppCompatButton btn_createAccount;

    private String userName, emailAddress, password, confirmPassword, phoneNumber;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {

        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = editTxt_username.getText().toString();
                emailAddress = editText_emailAddress.getText().toString();
                password = editTxt_password.getText().toString();
                confirmPassword = editTxt_confirmPassword.getText().toString();
                phoneNumber = editTxt_phoneNumber.getText().toString();

                userNameValidation();
                emailAddressValidation();
                passwordValidation();
                confirmPasswordValidation();
                phoneNumberValidation();
                validateInput();
            }
        });
    }

    private void validateInput() {
        if (userNameValidation() | emailAddressValidation() | passwordValidation() | confirmPasswordValidation() | phoneNumberValidation())
        {
            //Authenticate here
            auth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), sp.example.extrack.Onboarding.Registration_Finish_Activity.class));
                    } else {
                        Toast.makeText(Registration_Activity.this, "Sign up failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean phoneNumberValidation() {
        if (phoneNumber.isEmpty()){
            editTxt_phoneNumber.setError("Field cannot be empty to proceed");
            return false;
        } else
            return true;
    }

    private boolean confirmPasswordValidation() {
        if (confirmPassword.equals(password)){
            return true;
        } else {
            editTxt_confirmPassword.setError("Confirm Password does not match password entered");
            return false;
        }

    }

    private boolean emailAddressValidation() {
        if (emailAddress.isEmpty()){
            editText_emailAddress.setError("Field cannot be empty to proceed");
            return false;
        } else
            return true;
    }

    private boolean userNameValidation() {
        if (userName.isEmpty()){
            editTxt_username.setError("Field cannot be empty to proceed");
            return false;
        } else
            return true;
    }

    private boolean passwordValidation() {
        if (password.isEmpty()){
            editTxt_password.setError("Field cannot be empty to proceed");
            return false;
        } else
            return true;
    }

    private void initWidget() {

        editTxt_username = findViewById(R.id.editTxt_username);
        editText_emailAddress = findViewById(R.id.editText_emailAddress);
        editTxt_password = findViewById(R.id.editTxt_password);
        editTxt_confirmPassword = findViewById(R.id.editTxt_confirmPassword);
        editTxt_phoneNumber = findViewById(R.id.editTxt_phoneNumber);

        btn_createAccount = findViewById(R.id.btn_createAccount);
    }
}