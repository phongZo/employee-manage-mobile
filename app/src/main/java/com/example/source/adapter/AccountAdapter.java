package com.example.source.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.source.R;
import com.example.source.constant.Constant;
import com.example.source.model.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private List<Account> accountList;
    private ItemClickListener itemClickListener;

    public AccountAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<Account> accountList) {
        this.accountList = accountList;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onDeleteBtn(Account account);

        void onItemClick(Account account);
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_manage_rows_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountList.get(position);
        if (account == null) {
            return;
        }
        if(account.getAvatarPath() != null){
            byte[] pic = account.getAvatarPath();
            Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
            holder.imgAvatar.setImageBitmap(bitmap);
        }
        holder.tvFullName.setText(account.getFullname());
        holder.tvEmail.setText(account.getEmail());
        holder.tvPhone.setText(account.getPhone());

        if (account.getKind().equals(Constant.KIND_ADMIN)) {
            holder.tvKind.setText("Admin");
        } else {
            holder.tvKind.setText("Employee");
        }

        holder.tvId.setText(account.getId().toString());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onDeleteBtn(account);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(account);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return accountList.get(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        if (accountList != null)
            return accountList.size();
        return 0;
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvFullName;
        private TextView tvEmail;
        private TextView tvPhone;
        private TextView tvKind;
        private TextView tvId;
        private Button btnDelete;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvFullName = itemView.findViewById(R.id.tv_fullname);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvKind = itemView.findViewById(R.id.tv_kind);
            tvId = itemView.findViewById(R.id.tv_id);
            btnDelete = itemView.findViewById(R.id.btn_delete_account);
        }
    }
}
