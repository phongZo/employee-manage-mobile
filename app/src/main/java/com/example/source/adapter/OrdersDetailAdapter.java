package com.example.source.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.source.R;
import com.example.source.database.Database;
import com.example.source.model.OrdersDetail;
import com.example.source.model.Product;

import java.util.List;

public class OrdersDetailAdapter extends RecyclerView.Adapter<OrdersDetailAdapter.OrdersDetailViewHolder>{
    private List<OrdersDetail> ordersDetailList;
    private Database database;

    public void setData(List<OrdersDetail> ordersDetailList, Database database){
        this.ordersDetailList = ordersDetailList;
        this.database = database;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrdersDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_detail_row_item,parent,false);
        return new OrdersDetailAdapter.OrdersDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersDetailViewHolder holder, int position) {
        OrdersDetail ordersDetail = ordersDetailList.get(position);
        if (ordersDetail == null){
            return;
        }
        Product product = database.findProductById(ordersDetail.getProductId());

        holder.productName.setText(product.getName());
        holder.price.setText(product.getPrice().toString() + "$");
        holder.quantity.setText("x" +ordersDetail.getQuantity().toString());
    }

    @Override
    public int getItemCount() {
        if(ordersDetailList != null){
            return ordersDetailList.size();
        }
        return 0;
    }

    public class OrdersDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView quantity;
        private TextView price;

        public OrdersDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.product_quantity);
            price = itemView.findViewById(R.id.price);
        }
    }
}
