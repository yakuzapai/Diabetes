package com.kandon.caramelwaffle.diabetes.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kandon.caramelwaffle.diabetes.Adapter.FoodAdapter;
import com.kandon.caramelwaffle.diabetes.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class DessertFragment extends Fragment {
    private Context context;
    private RecyclerView dessertrecyclerView;
    private FoodAdapter adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mFoodRef;


    public DessertFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static DessertFragment newInstance() {
        DessertFragment fragment = new DessertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        context = getContext();

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dessert, container, false);
        initInstances(rootView, savedInstanceState);
        setInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        dessertrecyclerView = (RecyclerView) rootView.findViewById(R.id.DessertRecyclerview);
    }

    private void setInstances(View rootView, Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFoodRef = mDatabase.child("dessert");
        adapter = new FoodAdapter(context,mFoodRef);
        dessertrecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        dessertrecyclerView.setLayoutManager(new LinearLayoutManager(context));
        dessertrecyclerView.setAdapter(adapter);

    }


    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
