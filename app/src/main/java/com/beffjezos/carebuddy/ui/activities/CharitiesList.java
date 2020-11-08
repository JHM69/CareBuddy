package com.beffjezos.carebuddy.ui.activities;import android.os.Bundle;import android.view.LayoutInflater;import android.view.ViewGroup;import androidx.annotation.NonNull;import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.widget.Toolbar;import androidx.paging.PagedList;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;import com.beffjezos.carebuddy.R;import com.beffjezos.carebuddy.adapters.CharityViewHolder;import com.beffjezos.carebuddy.models.Charity;import com.beffjezos.carebuddy.models.Package;import com.firebase.ui.database.paging.DatabasePagingOptions;import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;import com.firebase.ui.database.paging.LoadingState;import com.google.firebase.database.DatabaseError;import com.google.firebase.database.DatabaseReference;import com.google.firebase.database.FirebaseDatabase;import java.util.Objects;public class CharitiesList extends AppCompatActivity {    SwipeRefreshLayout mSwipeRefreshLayout;    RecyclerView mRecyclerView;    FirebaseRecyclerPagingAdapter<Charity, CharityViewHolder> mAdapter;    Package dd;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_charities_list);        Toolbar toolbar = findViewById(R.id.toolbar);        setSupportActionBar(toolbar);        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);        getSupportActionBar().setDisplayShowHomeEnabled(true);        DatabaseReference mDb;        mSwipeRefreshLayout = findViewById(R.id.fdgdrg);        dd = (Package) getIntent().getSerializableExtra("donS");        // Package packages = (Package) getIntent().getSerializableExtra("donateP");        //Initialize RecyclerView        mRecyclerView = findViewById(R.id.recyclerView);        mRecyclerView.setHasFixedSize(true);        LinearLayoutManager mManager = new LinearLayoutManager(this);        mRecyclerView.setLayoutManager(mManager);        //Initialize Database        mDb = FirebaseDatabase.getInstance().getReference().child("Charities");        PagedList.Config config = new PagedList.Config.Builder()                .setEnablePlaceholders(false)                .setPrefetchDistance(5)                .setPageSize(10)                .build();        DatabasePagingOptions<Charity> options = new DatabasePagingOptions.Builder<Charity>()                .setLifecycleOwner(this)                .setQuery(mDb, config, Charity.class)                .build();        mAdapter = new FirebaseRecyclerPagingAdapter<Charity, CharityViewHolder>(options) {            @NonNull            @Override            public CharityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {                return new CharityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_charity, parent, false));            }            @Override            protected void onBindViewHolder(@NonNull CharityViewHolder holder,                                            int position,                                            @NonNull Charity model) {                holder.setItem(model, getApplicationContext(), dd);            }            @Override            protected void onLoadingStateChanged(@NonNull LoadingState state) {                switch (state) {                    case LOADING_INITIAL:                    case LOADING_MORE:                        // Do your loading animation                        mSwipeRefreshLayout.setRefreshing(true);                        break;                    case LOADED:                        // Stop Animation                        mSwipeRefreshLayout.setRefreshing(false);                        break;                    case FINISHED:                        //Reached end of Data set                        mSwipeRefreshLayout.setRefreshing(false);                        break;                    case ERROR:                        retry();                        break;                }            }            @Override            protected void onError(@NonNull DatabaseError databaseError) {                super.onError(databaseError);                mSwipeRefreshLayout.setRefreshing(false);                databaseError.toException().printStackTrace();            }        };        //Set Adapter to RecyclerView        mRecyclerView.setAdapter(mAdapter);        //Set listener to SwipeRefreshLayout for refresh action        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {            @Override            public void onRefresh() {                mAdapter.refresh();            }        });    }    //Start Listening Adapter    @Override    protected void onStart() {        super.onStart();        mAdapter.startListening();    }    //Stop Listening Adapter    @Override    protected void onStop() {        super.onStop();        mAdapter.stopListening();    }}