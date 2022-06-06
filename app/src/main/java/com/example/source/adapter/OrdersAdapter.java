package com.example.source.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.source.R;
import com.example.source.model.Orders;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private List<Orders> ordersList;
    private ItemClickListener itemClickListener;

    public OrdersAdapter(OrdersAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<Orders> ordersList){
        this.ordersList = ordersList;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(Orders orders);
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_row_item,parent,false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Orders orders = ordersList.get(position);
        if (orders == null){
            return;
        }

        holder.tvCustomerName.setText("Tên khách hàng: " + orders.getCustomerName());
        holder.tvEmployeeId.setText("Mã nhân viên : " + orders.getEmployeeId().toString());
        holder.tvCustomerPhone.setText("Số điện thoại: " + orders.getCustomerPhone());
        holder.tvTimeOrdered.setText("Time: " + orders.getTimeOrder().trim());
        holder.tvTotalMoney.setText("Tổng tiền: " + orders.getTotalMoney().toString() + '$');
        holder.tvId.setText(orders.getId().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(orders);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(ordersList != null){
            return ordersList.size();
        }
        return 0;
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEmployeeId;
        private TextView tvCustomerName;
        private TextView tvCustomerPhone;
        private TextView tvTimeOrdered;
        private TextView tvId;
        private TextView tvTotalMoney;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmployeeId = itemView.findViewById(R.id.orders_employee_id);
            tvCustomerName = itemView.findViewById(R.id.orders_customer_name);
            tvCustomerPhone = itemView.findViewById(R.id.orders_customer_phone);
            tvTimeOrdered = itemView.findViewById(R.id.orders_time);
            tvTotalMoney = itemView.findViewById(R.id.orders_total_money);
            tvId = itemView.findViewById(R.id.orders_id);
        }
    }
}
