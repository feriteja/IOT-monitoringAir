package com.example.monitoringair;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monitoringair.Support.NotifArrayKualitas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.itangqi.waveloadingview.WaveLoadingView;

import static com.example.monitoringair.Support.NotifChannel.CHANNEL_1_ID;

public class HomeActivity extends AppCompatActivity {



    //FIREBASE auth & database
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference kekeruahDB = database.getReference();

    BottomNavigationView bottomNav;



    boolean bersihBol;
    boolean kotorBol;
    boolean burukBol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {

            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        bottomNav =findViewById(R.id.bottom_nav);

        bottomNav.setOnNavigationItemSelectedListener(navListener);


        RetriveDataforNotif();
        bersihBol=true;
        kotorBol=true;
        burukBol=true;

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerUser,
                new HomeFragment()).commit();


    }


    // BOTTOM NAVIGATION CONFIG
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment =null;
                    firebaseAuth=FirebaseAuth.getInstance();


                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerUser,
                                    new HomeFragment()).commit();
                            break;
                        case R.id.nav_chart:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerUser,
                                    new ChartFragment()).commit();
                            break;
                        case R.id.nav_logoutUser:
                            alertLogOut(firebaseAuth.getCurrentUser().getEmail());
                            break;
                    }

                    return true;
                }
            };



    //LOGOUT CONFIRMATION
    public void alertLogOut(String nama) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
        builder1.setTitle(nama);
        builder1.setMessage("Logout ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }




    //NOTIFICATION LOGIC
    private void RetriveDataforNotif() {


      kekeruahDB.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Integer keruhnya = dataSnapshot.child("kekeruhan").getValue(Integer.class);

              if (keruhnya == 0){

                  if(bersihBol) {
                      shownotification("Kualitas Air", "Bagus", 0);

                      bersihBol=false;
                      kotorBol=true;
                  }

              }
              else if(keruhnya==1){

                  if (kotorBol) {
                      shownotification("Kualitas Air", "Kurang baik", 0);

                      bersihBol=true;
                      kotorBol=false;
                  }
              }
              else {


              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });





    }

    //NOTIFICATION CODE
    public void shownotification(String title, String content, int id) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentTitle(title)
                        .setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, builder.build());
        Intent notificationIntent = new Intent(this, LoginActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, id, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());

    }



}
