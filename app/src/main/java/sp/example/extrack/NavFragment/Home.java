package sp.example.extrack.NavFragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.extrack.Model.Articles;
import com.example.extrack.R;

import java.util.ArrayList;

import sp.example.extrack.Adapter.ArticleAdapter;
import sp.example.extrack.SpaceItemDecoration;

public class Home extends Fragment {

    private Context mContext;

    private TextView txtView_welcomeMsg, txtView_cardName, txtView_cardBalance, txtView_cardLimit, txtView_allowanceType, txtView_noTransaction;

    private RecyclerView rv_articles;

    ArticleAdapter articleAdapter;

    private final ArrayList<Articles> articlesArrayList = new ArrayList<>();

    private androidx.cardview.widget.CardView cv_cardWallet;

    private ImageView btn_about, btn_setting;

    int[] articleImage = {R.drawable.article1, R.drawable.article2, R.drawable.article3, R.drawable.article4, R.drawable.article5, R.drawable.article2};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = getActivity();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View v) {

        //TextView
        txtView_welcomeMsg = v.findViewById(R.id.txtView_welcomeMsg);
        txtView_cardName = v.findViewById(R.id.txtView_cardName);
        txtView_cardBalance = v.findViewById(R.id.txtView_cardBalance);
        txtView_cardLimit = v.findViewById(R.id.txtView_cardLimit);
        txtView_allowanceType = v.findViewById(R.id.txtView_allowanceType);

        //Imageview
        btn_about = v.findViewById(R.id.btn_about);
        btn_setting = v.findViewById(R.id.btn_setting);

        //RecyclerView
        rv_articles = v.findViewById(R.id.rv_articles);

        //CardView
        cv_cardWallet = v.findViewById(R.id.cv_cardWallet);

        initUI();

        pageDirectories();
    }

    private void pageDirectories() {

    }

    private void initUI() {
        //for better performance of recyclerview.

        int spaceInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        rv_articles.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        rv_articles.setHasFixedSize(true);

        articleAdapter = new ArticleAdapter(getContext(), articlesArrayList);
        rv_articles.setAdapter(articleAdapter);

        //layout to contain recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setSmoothScrollbarEnabled(true);
        // orientation of linearlayoutmanager.
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setAutoMeasureEnabled(true);

        //set layoutmanager for recyclerview.
        rv_articles.setLayoutManager(llm);

        new loadArticle().execute();
    }

    Articles articles;

    class loadArticle extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            try {

                String[] articleTitle = getResources().getStringArray(R.array.article_title);
                String[] articleLink = getResources().getStringArray(R.array.article_link);


                for (int i = 0 ; i < articleTitle.length; i++)
                {
                    articles = new Articles();
                    articles.setArticleImage(articleImage[i]);
                    articles.setArticleTitle(articleTitle[i]);
                    articles.setArticleLink(articleLink[i]);
                    articlesArrayList.add(articles);
                    articles = null;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            if (articlesArrayList != null && articlesArrayList.size() > 0) {
                articleAdapter = new ArticleAdapter(mContext, articlesArrayList);
                rv_articles.setAdapter(articleAdapter);
                articleAdapter.notifyDataSetChanged();
            }
        }
    }

}