package com.example.paul.walkdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Paul on 21/11/2017.
 */

public class AddWalk extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private Button mFirebaseBtn, submitWalk;
    private EditText addWalk, walkFormat, walkDiff, walkLength;

    private ProgressBar progressBar;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.add_walk);

        //create instance that points to database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Roscommon");

        mFirebaseBtn = (Button) findViewById(R.id.firebase_btn);

        addWalk = (EditText) findViewById(R.id.add_walk);
        walkDiff = (EditText) findViewById(R.id.walk_diffuctly);
        walkFormat = (EditText) findViewById(R.id.walk_format);
        walkLength = (EditText) findViewById(R.id.walk_length);

        addWalk.setVisibility(View.GONE);
        walkDiff.setVisibility(View.GONE);
        walkFormat.setVisibility(View.GONE);
        walkLength.setVisibility(View.GONE);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }



        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create child in root object
                //assign values in that child
                addWalk.setVisibility(View.VISIBLE);
                walkDiff.setVisibility(View.VISIBLE);
                walkFormat.setVisibility(View.VISIBLE);
                walkLength.setVisibility(View.VISIBLE);




                if (!addWalk.getText().toString().trim().equals("")) {



                            String walkName = addWalk.getText().toString().trim();
                            String difficultly = walkDiff.getText().toString().trim();
                            String format = walkFormat.getText().toString().trim();

                            HashMap<String, String> dataMap = new HashMap<String, String>();
                            dataMap.put("Format", format);
                            dataMap.put("Difficultly", difficultly);




                    mDatabase.child(walkName).setValue(dataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddWalk.this, "Walk added!", Toast.LENGTH_SHORT).show();
                                        addWalk.setText(null);
                                        walkDiff.setText(null);
                                        walkFormat.setText(null);


                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(AddWalk.this, "Failed to add walk!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });



                    //Toast.makeText(AddWalk.this, "It works", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(AddWalk.this, "Please enter  some data", Toast.LENGTH_LONG).show();


                }


                }
        });
    }
}