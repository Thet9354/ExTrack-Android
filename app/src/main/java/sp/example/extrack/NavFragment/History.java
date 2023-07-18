package sp.example.extrack.NavFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.extrack.Adapter.RecentTransactionAdapter;
import com.example.extrack.Model.Transaction;
import com.example.extrack.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sp.example.extrack.SpaceItemDecoration;

public class History extends Fragment {

    private ImageView imgView_back;
    private androidx.recyclerview.widget.RecyclerView rv_transactionHistory;

    private Context mContext;

    private RecentTransactionAdapter recentTransactionAdapter;

    Transaction transaction;


    private final ArrayList<Transaction> transactionArrayList = new ArrayList<>();


    FirebaseDatabase database = FirebaseDatabase.getInstance("Add your own url here");
    DatabaseReference databaseReference  = database.getReference().child("Users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

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

        //RecyclerView
        rv_transactionHistory = v.findViewById(R.id.rv_transactionHistory);

        //ImageView
        imgView_back = v.findViewById(R.id.imgView_back);

        initUI();

        pageDirectories();
    }

    private void pageDirectories() {

        imgView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void initUI() {
        initRecView();
    }

    private void initRecView() {

        //for better performance of recyclerview.

        rv_transactionHistory.setHasFixedSize(true);

        recentTransactionAdapter = new RecentTransactionAdapter(getContext(), transactionArrayList);
        rv_transactionHistory.setAdapter(recentTransactionAdapter);

        int spaceInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        rv_transactionHistory.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        //layout to contain recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setSmoothScrollbarEnabled(true);
        // orientation of linearlayoutmanager.
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setAutoMeasureEnabled(true);

        //set layoutmanager for recyclerview.
        rv_transactionHistory.setLayoutManager(llm);

        rv_transactionHistory.setAdapter(recentTransactionAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {

            String userExpense = "User's Expenses";

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.child("Expenditure").child(userExpense).getChildren())
                {
                    transaction = dataSnapshot.getValue(Transaction.class);
                    transactionArrayList.add(transaction);
                }
                recentTransactionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}