package com.example.monitoringair;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.itangqi.waveloadingview.WaveLoadingView;

import static com.example.monitoringair.Support.NotifChannel.CHANNEL_1_ID;

public class HomeFragment extends Fragment {


    //inisialisasi
    WaveLoadingView warnaAir;
    TextView mhargaText, mpenggunaText;
    ImageView mlogutBTN;
    CardView mcardView;




    //FIREBASE auth & database
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference kekeruahDB = database.getReference();




    //GET THE LAYOUT FROM FRAGMENT_HOME
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        firebaseAuth=FirebaseAuth.getInstance();

        mhargaText = view.findViewById(R.id.textHarga);
        mpenggunaText=view.findViewById(R.id.pengguna);
        mcardView= view.findViewById(R.id.cardview1);
        warnaAir=view.findViewById(R.id.tingkatKeruh);
        warnaAir.setWaveColor(Color.rgb(135,206,250));


        mpenggunaText.setText(firebaseAuth.getCurrentUser().getEmail());


        RetriveData();


    }



    private void RetriveData() {
        kekeruahDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double harganya = dataSnapshot.child("harga").getValue(Double.class);
                int intPart = harganya.intValue();
                mhargaText.setText(String.valueOf(intPart));


                 Integer keruhnya = dataSnapshot.child("kekeruhan").getValue(Integer.class);

                warnaAir.setTopTitle("Kualitas Air");

                if(keruhnya==0){


                    int nilair= 135;
                    int nilaig = 206;
                    int nilaib = 250;

                    warnaAir.setWaveColor(Color.rgb(nilair,nilaig,nilaib));


                    warnaAir.setCenterTitle(String.valueOf("bersih"));
                }

                else if(keruhnya==1){


                    int nilair= 135+43;
                    int nilaig = 206-11;
                    int nilaib = 250-57;

                    warnaAir.setWaveColor(Color.rgb(nilair,nilaig,nilaib));


                    warnaAir.setCenterTitle(String.valueOf("keruh"));
                }
                else {

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }
    }
