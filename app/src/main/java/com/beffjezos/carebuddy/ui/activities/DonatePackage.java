package com.beffjezos.carebuddy.ui.activities;import androidx.appcompat.app.AppCompatActivity;import androidx.cardview.widget.CardView;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import android.annotation.SuppressLint;import android.app.ProgressDialog;import android.database.Cursor;import android.os.Bundle;import android.text.Editable;import android.text.TextWatcher;import android.view.View;import android.widget.EditText;import android.widget.ImageView;import android.widget.TextView;import android.widget.Toast;import com.beffjezos.carebuddy.R;import com.beffjezos.carebuddy.SqlDonor;import com.beffjezos.carebuddy.adapters.ProductAdapter;import com.beffjezos.carebuddy.models.Charity;import com.beffjezos.carebuddy.models.Donation;import com.beffjezos.carebuddy.models.Package;import com.beffjezos.carebuddy.models.Product;import com.bumptech.glide.Glide;import com.bumptech.glide.request.RequestOptions;import com.google.android.gms.tasks.OnSuccessListener;import com.google.firebase.database.FirebaseDatabase;import java.util.List;public class DonatePackage extends AppCompatActivity {    static TextView TotalPackageTV;    static TextView desTv;    static TextView priceTv;    static Package packages;    static int pacC = 0;    List<Product> productList;    TextView nameTV;    ImageView logo;    ImageView logo22;    CardView donate;    Charity charity;    EditText pacCount;    TextView nameTvh, addressTvh, numberTvh, summeryTvh, dateTvh;    ProgressDialog mDialog;    private RecyclerView recyclerView;    private RecyclerView.LayoutManager layoutManager;    private ProductAdapter productAdapter;    @SuppressLint("SetTextI18n")    public static void updatePackage(Product product, int position) {        packages.getProductList().set(position, product);        String des = "";        for (int i = 0; i < packages.getProductList().size(); i++) {            des += (packages.getProductList().get(i).getName()) + (" ") + (packages.getProductList().get(i).getQuantity()) + (" ") + (packages.getProductList().get(i).getUnit()) + (", ");        }        assert des != null;        packages.setShortDes(des.toString());        priceTv.setText("Total: ৳ " + packages.getTotalPrice());        desTv.setText(packages.getShortDes());        if (pacC != 0) {            TotalPackageTV.setText("Total Amount: ৳ " + String.valueOf(pacC * packages.getTotalPrice()));        }    }    @SuppressLint("SetTextI18n")    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_donate_package);        nameTV = findViewById(R.id.name);        desTv = findViewById(R.id.textView8);        priceTv = findViewById(R.id.money500);        logo = findViewById(R.id.textView6);        donate = findViewById(R.id.cardView3);        recyclerView = findViewById(R.id.recyclerView2);        mDialog = new ProgressDialog(this);        mDialog.setMessage("Please wait..");        mDialog.setIndeterminate(true);        mDialog.setCancelable(false);        mDialog.setCanceledOnTouchOutside(false);        logo22 = findViewById(R.id.textView19);        nameTvh = findViewById(R.id.name3);        addressTvh = findViewById(R.id.addressCha8);        dateTvh = findViewById(R.id.addressCha9);        layoutManager = new LinearLayoutManager(getApplicationContext());        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));        recyclerView.setLayoutManager(layoutManager);        recyclerView.setHasFixedSize(true);        packages = (Package) getIntent().getSerializableExtra("donateP");        TotalPackageTV = findViewById(R.id.money);        pacCount = findViewById(R.id.age_name);        charity = (Charity) getIntent().getSerializableExtra("1222");        assert packages != null;        try {            productList = packages.getProductList();        } catch (NullPointerException h) {        }        final SqlDonor sqlDonor = new SqlDonor(getApplicationContext());        donate.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                mDialog.show();                Cursor rs = null;                String donorName = "", donorNum = "", donorAddress = "";                try {                    rs = sqlDonor.getData(1);                    rs.moveToFirst();                    donorName = rs.getString(rs.getColumnIndex(SqlDonor.CONTACTS_COLUMN_NAME));                    donorNum = rs.getString(rs.getColumnIndex(SqlDonor.CONTACTS_COLUMN_NUMBER));                    donorAddress = rs.getString(rs.getColumnIndex(SqlDonor.CONTACTS_COLUMN_ADDRESS));                } catch (RuntimeException g) {                }                if (!(pacC == 0)) {                    startBkashPayment(donorNum, charity.getNumber(), 0, charity.getId(), donorName, donorAddress);                } else {                    Toast.makeText(DonatePackage.this, "Enter how many...", Toast.LENGTH_SHORT).show();                    mDialog.dismiss();                }            }        });        try {            nameTV.setText(packages.getName());            logo.setImageResource(packages.getLogo());            desTv.setText(packages.getShortDes());            priceTv.setText("Package Price: ৳ " + packages.getTotalPrice());            nameTvh.setText(charity.getName());            Glide.with(getApplicationContext())                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.ic_flat))                    .load(charity.getLogo())                    .into(logo22);            addressTvh.setText(charity.getLocation());            dateTvh.setText(charity.getStubDate());            summeryTvh.setText(charity.getSummery());        } catch (NullPointerException d) {        }        try {            for (int i = 0; i < productList.size(); i++) {                productAdapter = new ProductAdapter(productList, getApplicationContext());                recyclerView.setAdapter(productAdapter);                productAdapter.notifyDataSetChanged();            }        } catch (NullPointerException j) {        }        pacCount.addTextChangedListener(new TextWatcher() {            public void onTextChanged(CharSequence s, int start, int before,                                      int count) {                //do your work here                try {                    pacC = Integer.parseInt(s.toString());                    TotalPackageTV.setText("Total Amount: ৳ " + String.valueOf(pacC * packages.getTotalPrice()));                } catch (Exception uh) {                }            }            public void beforeTextChanged(CharSequence s, int start, int count,                                          int after) {            }            public void afterTextChanged(Editable s) {            }        });    }    private void startBkashPayment(String donorNum, String charityNum, long amount, String charityId, String donName, String donorAddress) {        String summery = "Donated " + pacC + " items " + packages.getName();        amount = pacC * packages.getTotalPrice();        Donation donation = new Donation(donName, summery, donorNum, charityId, "cksdfafoadawpdibfef", donorAddress, "package", amount);        FirebaseDatabase.getInstance().getReference().child("Charities").child(charityId).child("Donations").push().setValue(donation).addOnSuccessListener(new OnSuccessListener<Void>() {            @Override            public void onSuccess(Void aVoid) {                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();                mDialog.dismiss();                finish();            }        });    }}