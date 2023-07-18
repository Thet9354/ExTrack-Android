package sp.example.extrack.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extrack.Model.Articles;
import com.example.extrack.R;

import java.util.ArrayList;

import sp.example.extrack.WebView_Activity;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.CardViewHolder>{

    private ArrayList<Articles> articlesArrayList;
    private Context mContext;

    public ArticleAdapter(Context mContext, ArrayList<Articles> articlesArrayList) {
        this.articlesArrayList = articlesArrayList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private ArticleAdapter.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(ArticleAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public ArticleAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(context).
                inflate(R.layout.row_article, parent, false);

        return new ArticleAdapter.CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.CardViewHolder holder, int position) {

        holder.txtView_article.setText(articlesArrayList.get(position).getArticleTitle());
        holder.imgView_article.setImageResource(articlesArrayList.get(position).getArticleImage());

        holder.ll_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(mContext, WebView_Activity.class);
                intent.putExtra("Link", articlesArrayList.get(pos).getArticleLink());
                mContext.startActivity(intent);
            }
        });

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool food");
                intent.putExtra(Intent.EXTRA_TEXT, articlesArrayList.get(pos).getArticleLink());
                mContext.startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_article;
        private ImageView imgView_article, btn_share;
        private TextView txtView_article;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_article = itemView.findViewById(R.id.ll_article);
            imgView_article = itemView.findViewById(R.id.imgView_article);
            btn_share = itemView.findViewById(R.id.btn_share);
            txtView_article = itemView.findViewById(R.id.txtView_article);

        }
    }
}
