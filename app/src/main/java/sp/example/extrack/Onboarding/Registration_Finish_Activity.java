package sp.example.extrack.Onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.extrack.R;

import sp.example.extrack.MainActivity;

public class Registration_Finish_Activity extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_finish);

        initWidget();

        pageDirectories();
    }

    private void pageDirectories() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void initWidget() {

        btn_next = findViewById(R.id.btn_next);
    }
}