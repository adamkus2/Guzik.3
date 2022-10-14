package com.example.guzik2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private  Button btnZielony, btnCzerwony, btnWynik, btnNiebieski;
    private TextView txtV;
    private FirebaseDatabase database;
    private MediaPlayer music;
    private MediaPlayer musik;
    private MediaPlayer music2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnZielony = findViewById(R.id.buttonZielony);
        btnCzerwony = findViewById(R.id.buttonCzerwony);
        btnWynik = findViewById(R.id.buttonWynik);
        btnNiebieski = findViewById(R.id.buttonNiebieski);
        txtV = findViewById(R.id.textView);

        music = MediaPlayer.create(MainActivity.this,R.raw.amisietunic);
        musik = MediaPlayer.create(MainActivity.this,R.raw.wyjdzmido);
        music2 = MediaPlayer.create(MainActivity.this,R.raw.zarcik);

        database = FirebaseDatabase.getInstance("https://guzik-2-default-rtdb.europe-west1.firebasedatabase.app/");


        FirebaseAuth.getInstance().signInWithEmailAndPassword("zdzisiek@o2.pl","123456").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    txtV.setText("Zalogowany");
                } else {
                    txtV.setText("Niezalogowany");
                }
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (currentUser != null) {
            txtV.setText("Zalogowany jako " + database.getReference("kolor/"+FirebaseAuth.getInstance().getCurrentUser().getUid()));


        database.getReference("kolor/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    btnWynik.setBackgroundResource(snapshot.getValue(Kolor.class).getWartoscHEX());
                    if ( snapshot.getValue(Kolor.class).getWartoscHEX() == R.color.czerwony)
                        music.start();
                    if ( snapshot.getValue(Kolor.class).getWartoscHEX() == R.color.zielony)
                        musik.start();
                    if ( snapshot.getValue(Kolor.class).getWartoscHEX() == R.color.niebieski)
                        music2.start();
                }
                else
                    btnWynik.setBackgroundResource(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );



        btnZielony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnWynik.setBackgroundResource(R.color.zielony);

                database.getReference("kolor/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Kolor(R.color.zielony));


            }
        });
        btnCzerwony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnWynik.setBackgroundResource(R.color.czerwony);

                database.getReference("kolor/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Kolor(R.color.czerwony));

            }
        });
            btnNiebieski.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //btnWynik.setBackgroundResource(R.color.niebieski);

                    database.getReference("kolor/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Kolor(R.color.niebieski));

                }
            });

    } else {
        txtV.setText("Niezalogowany");
            FirebaseAuth.getInstance().signOut();

    }
    }
}