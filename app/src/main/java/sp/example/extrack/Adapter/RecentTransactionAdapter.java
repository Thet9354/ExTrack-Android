package com.example.extrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extrack.Model.Transaction;
import com.example.extrack.R;

import java.util.ArrayList;

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecentTransactionAdapter.CardViewHolder>{

    private ArrayList<Transaction> transactionArrayList;
    private Context mContext;

    public RecentTransactionAdapter(Context mContext, ArrayList<Transaction> transactionArrayList) {
        this.transactionArrayList = transactionArrayList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private RecentTransactionAdapter.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(RecentTransactionAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public RecentTransactionAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(context).
                inflate(R.layout.row_transaction, parent, false);

        return new RecentTransactionAdapter.CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTransactionAdapter.CardViewHolder holder, int position) {

        holder.txtView_title.setText(transactionArrayList.get(position).getTitle());
        holder.txtView_purpose.setText(transactionArrayList.get(position).getPurpose());
        holder.txtView_payment.setText("-$" + transactionArrayList.get(position).getAmount());

        switch (transactionArrayList.get(position).getPurpose())
        {
            case "Food and Drinks":
                holder.img_transaction.setImageResource(R.drawable.foodndrink);
                break;
            case "Entertainment":
                holder.img_transaction.setImageResource(R.drawable.entertainment);
                break;
            case "Transport":
                holder.img_transaction.setImageResource(R.drawable.transport);
                break;
            case "Shopping":
                holder.img_transaction.setImageResource(R.drawable.shopping);
                break;
            case "Grocery":
                holder.img_transaction.setImageResource(R.drawable.grocery);
                break;
            case "Subscription":
                holder.img_transaction.setImageResource(R.drawable.subscription);
                break;
            case "Investment":
                holder.img_transaction.setImageResource(R.drawable.investment);
                break;
            case "Others":
                holder.img_transaction.setImageResource(R.drawable.others);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private CardView cv_transaction;
        private ImageView img_transaction;
        private TextView txtView_title, txtView_purpose, txtView_payment;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            //CardView
            cv_transaction = itemView.findViewById(R.id.cv_transaction);

            //ImageView
            img_transaction = itemView.findViewById(R.id.img_transaction);

            //TextView
            txtView_title = itemView.findViewById(R.id.txtView_title);
            txtView_purpose = itemView.findViewById(R.id.txtView_purpose);
            txtView_payment = itemView.findViewById(R.id.txtView_payment);
        }
    }
}
