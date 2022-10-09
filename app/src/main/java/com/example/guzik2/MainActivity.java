package com.example.guzik2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private  Button btnZielony, btnCzerwony, btnWynik;
    private TextView txtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnZielony = findViewById(R.id.buttonZielony);
        btnCzerwony = findViewById(R.id.buttonCzerwony);
        btnWynik = findViewById(R.id.buttonWynik);
        txtV = findViewById(R.id.textView);

        FirebaseAuth.getInstance().signInWithEmailAndPassword("zdzisiek@o2.pl","123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    txtV.setText("Zalogowany");
                } else {
                    txtV.setText("Niezalogowany");
                }
            }
        });

        txtV.setText("sdasdasdas");

        btnZielony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnWynik.setBackgroundResource(R.color.zielony);

                if (FirebaseAuth.getInstance().getCurrentUser() != null)
                    txtV.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("kolor/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Kolor(2000));



            }
        });
        btnCzerwony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnWynik.setBackgroundResource(R.color.czerwony);
            }
        });

    }
}