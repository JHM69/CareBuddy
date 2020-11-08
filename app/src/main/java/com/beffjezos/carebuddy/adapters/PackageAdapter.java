package com.beffjezos.carebuddy.adapters;import android.content.Context;import android.content.Intent;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.TextView;import androidx.cardview.widget.CardView;import androidx.recyclerview.widget.RecyclerView;import com.beffjezos.carebuddy.R;import com.beffjezos.carebuddy.models.Package;import com.beffjezos.carebuddy.ui.activities.CharitiesList;import java.util.List;public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyViewHolder> {    private final List<Package> packageList;    private final Context context;    public PackageAdapter(List<Package> packageList, Context context) {        this.packageList = packageList;        this.context = context;    }    @Override    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_package, viewGroup, false);        return new MyViewHolder(view);    }    @Override    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {        viewHolder.nameTV.setText(packageList.get(i).getName());        viewHolder.desTv.setText(packageList.get(i).getShortDes());        viewHolder.priceTv.setText("৳" + packageList.get(i).getTotalPrice());        viewHolder.logo.setImageResource(packageList.get(i).getLogo());        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                v.getContext().startActivity(new Intent(v.getContext(), CharitiesList.class).putExtra("donS", packageList.get(i)));            }        });        viewHolder.donate.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                v.getContext().startActivity(new Intent(v.getContext(), CharitiesList.class).putExtra("donS", packageList.get(i)));            }        });    }    @Override    public int getItemCount() {        return packageList.size();    }    public class MyViewHolder extends RecyclerView.ViewHolder {        TextView nameTV, desTv, priceTv;        ImageView logo;        CardView donate;        public MyViewHolder(View itemView) {            super(itemView);            nameTV = itemView.findViewById(R.id.textView5);            desTv = itemView.findViewById(R.id.textView10);            priceTv = itemView.findViewById(R.id.textView11);            logo = itemView.findViewById(R.id.floatingActionButton3);            donate = itemView.findViewById(R.id.cardView3);        }    }}