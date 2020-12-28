package com.com.meyshop.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.meyshop.R;
import com.com.meyshop.model.MyOrders;
import com.com.meyshop.views.MyOrderDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrderRecyclerAdapter extends RecyclerView.Adapter<MyOrderRecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<MyOrders> myOrder;

    public MyOrderRecyclerAdapter(Context c, ArrayList<MyOrders> orders){
        context = c;
        myOrder = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myorders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.nama.setText(myOrder.get(position).getnama_Product());
        holder.harga.setText(myOrder.get(position).gettotal_harga()+"");
        holder.jumlah.setText(myOrder.get(position).getjumlah_product()+"");
        Picasso.with(context).load(myOrder.get(position).getUrl_thumbnail().toString())
                .centerCrop().fit().into(holder.gambar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyOrderDetailsActivity.class);
                intent.putExtra("nama_order", myOrder.get(position).getOrder_number());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama, harga, jumlah;
        ImageView gambar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama_order_product);
            gambar = itemView.findViewById(R.id.gambar_order_product);
            harga = itemView.findViewById(R.id.harga_order_product);
            jumlah = itemView.findViewById(R.id.jumlah_barang_order_product);
        }
    }
}
