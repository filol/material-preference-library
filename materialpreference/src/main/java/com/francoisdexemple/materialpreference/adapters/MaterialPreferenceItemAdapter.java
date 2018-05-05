package com.francoisdexemple.materialpreference.adapters;

import android.content.Context;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.francoisdexemple.materialpreference.holders.MaterialPreferenceItemViewHolder;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceItem;
import com.francoisdexemple.materialpreference.util.DefaultViewTypeManager;
import com.francoisdexemple.materialpreference.util.ViewTypeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class MaterialPreferenceItemAdapter extends RecyclerView.Adapter<MaterialPreferenceItemViewHolder> {

    private final AsyncListDiffer<MaterialPreferenceItem> differ = new AsyncListDiffer<MaterialPreferenceItem>(this, DIFF_CALLBACK);

    private ViewTypeManager viewTypeManager;

    private Context context;

    MaterialPreferenceItemAdapter() {
        setHasStableIds(true);
        this.viewTypeManager = new DefaultViewTypeManager();
    }

    MaterialPreferenceItemAdapter(ViewTypeManager customViewTypeManager) {
        setHasStableIds(true);
        this.viewTypeManager = customViewTypeManager;
    }

    @Override
    public MaterialPreferenceItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        if (!(viewGroup instanceof RecyclerView)) {
            throw new RuntimeException("Not bound to RecyclerView");
        }

        int layoutId = viewTypeManager.getLayout(viewType);

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);

        return viewTypeManager.getViewHolder(viewType, view);
    }

    @Override
    public void onBindViewHolder(MaterialPreferenceItemViewHolder holder, int position) {
        viewTypeManager.setupItem(getItemViewType(position), holder, differ.getCurrentList().get(position), context);
    }


    @Override
    public long getItemId(int position) {
        return UUID.fromString(differ.getCurrentList().get(position).getId()).getMostSignificantBits() & Long.MAX_VALUE;
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return differ.getCurrentList().get(position).getType();
    }

    public void setData(ArrayList<MaterialPreferenceItem> newData) {
        List<MaterialPreferenceItem> data = new ArrayList<>();
        for (MaterialPreferenceItem item : newData) {
            data.add(item.clone());
        }
        differ.submitList(data);
    }

    public List<MaterialPreferenceItem> getData() {
        return differ.getCurrentList();
    }


    public static final DiffUtil.ItemCallback<MaterialPreferenceItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<MaterialPreferenceItem>() {
        @Override
        public boolean areItemsTheSame(MaterialPreferenceItem oldItem, MaterialPreferenceItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(MaterialPreferenceItem oldItem, MaterialPreferenceItem newItem) {
            return oldItem.getDetailString().equals(newItem.getDetailString());
        }
    };

}
