package com.beffjezos.carebuddy.ui.activities;import android.os.Bundle;import android.widget.TextView;import android.widget.Toast;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.widget.Toolbar;import androidx.cardview.widget.CardView;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import com.beffjezos.carebuddy.R;import com.beffjezos.carebuddy.adapters.DonationAdaptetr;import com.beffjezos.carebuddy.models.Charity;import com.beffjezos.carebuddy.models.Donation;import com.bumptech.glide.Glide;import com.bumptech.glide.request.RequestOptions;import com.google.firebase.auth.FirebaseAuth;import com.google.firebase.database.ChildEventListener;import com.google.firebase.database.DataSnapshot;import com.google.firebase.database.DatabaseError;import com.google.firebase.database.FirebaseDatabase;import java.util.ArrayList;import java.util.List;import java.util.Objects;import de.hdodenhof.circleimageview.CircleImageView;public class CharityActivity extends AppCompatActivity {    Charity charity;    Donation donation;    List<Donation> donationList = new ArrayList<>();    TextView nameTv, addressTv, numberTv, summeryTv, dateTv;    private RecyclerView recyclerView;    private RecyclerView.LayoutManager layoutManager;    private DonationAdaptetr donationAdaptetr;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_charity);        Toolbar toolbar = findViewById(R.id.toolbar);        setSupportActionBar(toolbar);        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);        getSupportActionBar().setDisplayShowHomeEnabled(true);        nameTv = findViewById(R.id.name);        addressTv = findViewById(R.id.addressCha2);        summeryTv = findViewById(R.id.textView8);        dateTv = findViewById(R.id.addressCha3);        numberTv = findViewById(R.id.addressCha4);        final CircleImageView logo = findViewById(R.id.textView6);        CardView donate = findViewById(R.id.cardView3);        FirebaseDatabase.getInstance().getReference().child("Charities").orderByKey().equalTo(FirebaseAuth.getInstance().getUid()).addChildEventListener(new ChildEventListener() {            @Override            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {                charity = snapshot.getValue(Charity.class);                try {                    nameTv.setText(charity.getName());                    addressTv.setText(charity.getLocation());                    summeryTv.setText(charity.getSummery());                    dateTv.setText(charity.getStubDate());                    Glide.with(getApplicationContext())                            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_flat))                            .load(charity.getLogo())                            .into(logo);                } catch (NullPointerException j) {                    Toast.makeText(CharityActivity.this, "Null", Toast.LENGTH_SHORT).show();                }            }            @Override            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }            @Override            public void onChildRemoved(@NonNull DataSnapshot snapshot) {            }            @Override            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }            @Override            public void onCancelled(@NonNull DatabaseError error) {            }        });        recyclerView = findViewById(R.id.recyclerView2);        layoutManager = new LinearLayoutManager(getApplicationContext());        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));        recyclerView.setLayoutManager(layoutManager);        recyclerView.setHasFixedSize(true);        FirebaseDatabase.getInstance().getReference().child("Charities").child(FirebaseAuth.getInstance().getUid()).child("Donations").addChildEventListener(new ChildEventListener() {            @Override            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {                Donation don = snapshot.getValue(Donation.class);                donationList.add(don);                donationAdaptetr = new DonationAdaptetr(donationList, getApplicationContext());                recyclerView.setAdapter(donationAdaptetr);                donationAdaptetr.notifyDataSetChanged();            }            @Override            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }            @Override            public void onChildRemoved(@NonNull DataSnapshot snapshot) {            }            @Override            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {            }            @Override            public void onCancelled(@NonNull DatabaseError error) {            }        });    }}