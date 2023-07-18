package sp.example.extrack.Onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.extrack.R;

public class Login_Choice_Activity extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton btn_login, btn_signUp, btn_facebook, btn_gmail;

    private TextView txtView_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choice);

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {
        txtView_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), sp.example.extrack.Onboarding.Registration_Activity.class));
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), sp.example.extrack.Onboarding.Registration_Activity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
            }
        });
    }

    private void initWidget() {
        txtView_signUp = findViewById(R.id.txtView_signUp);

        btn_login = findViewById(R.id.btn_login);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_facebook = findViewById(R.id.btn_facebook);
        btn_gmail = findViewById(R.id.btn_gmail);
    }
}