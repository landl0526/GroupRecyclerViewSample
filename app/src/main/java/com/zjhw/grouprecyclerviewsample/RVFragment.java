package com.zjhw.grouprecyclerviewsample;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RVFragment extends Fragment {

    public RVFragment(int count) {
        // Required empty public constructor
        this.count = count;
    }

    int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_r_v, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(getContext(), count));

        return view;
    }

    class MyAdapter extends RecyclerView.Adapter {

        Context context;
        int items;
        public  MyAdapter(Context context, int itemsCount) {
            this.context = context;
            items = itemsCount;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_child, parent, false);
            return new BaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return items;
        }

        class BaseViewHolder extends RecyclerView.ViewHolder {

            public BaseViewHolder(@NonNull View itemView) {
                super(itemView);


            }
        }
    }
}