package com.fit2081.fit2081_a1_parkdarin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.fit2081_a1_parkdarin.provider.EventCategoryViewModel;
import com.fit2081.fit2081_a1_parkdarin.provider.EventViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListEvent extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EventAdapter eventAdapter;
    RecyclerView eventRecyclerView;
    LinearLayoutManager layoutManager;
    private EventViewModel mEventViewModel;

    public FragmentListEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        layoutManager = new LinearLayoutManager(view.getContext());
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setHasFixedSize(true);
        eventAdapter = new EventAdapter(getContext(), new ArrayList<>());
        eventRecyclerView.setAdapter(eventAdapter);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        mEventViewModel.getAllCategories().observe(getViewLifecycleOwner(), newData -> {
            eventAdapter.setEvents(newData);
            eventAdapter.notifyDataSetChanged();
        });
    }
}