package com.francoisdexemple.materialpreference.adapters;

import android.content.Context;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.francoisdexemple.materialpreference.R;
import com.francoisdexemple.materialpreference.model.MaterialPreferenceCard;
import com.francoisdexemple.materialpreference.util.DefaultViewTypeManager;
import com.francoisdexemple.materialpreference.util.ViewTypeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MaterialPreferenceListAdapter extends RecyclerView.Adapter<MaterialPreferenceListAdapter.MaterialPreferenceListViewHolder> {


    private final AsyncListDiffer<MaterialPreferenceCard> differ = new AsyncListDiffer<MaterialPreferenceCard>(this, DIFF_CALLBACK);

    private Context context;

    private ViewTypeManager viewTypeManager;

    public MaterialPreferenceListAdapter() {
        setHasStableIds(true);
        this.viewTypeManager = new DefaultViewTypeManager();
    }

    public MaterialPreferenceListAdapter(ViewTypeManager customViewTypeManager) {
        setHasStableIds(true);
        this.viewTypeManager = customViewTypeManager;
    }

    @Override
    public MaterialPreferenceListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        if (viewGroup instanceof RecyclerView) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.material_preference_list_card, viewGroup, false);
            view.setFocusable(true);
            return new MaterialPreferenceListViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(MaterialPreferenceListViewHolder holder, int position) {
        MaterialPreferenceCard card = differ.getCurrentList().get(position);

        if (holder.cardView instanceof CardView) {
            CardView cardView = (CardView) holder.cardView;
            int cardColor = card.getCardColor();
            if (cardColor != 0) {
                cardView.setBackgroundColor(cardColor);
            } else {
                cardView.setBackgroundColor(cardView.getCardBackgroundColor().getDefaultColor());
            }
        }

        CharSequence title = card.getTitle();
        int titleRes = card.getTitleRes();

        holder.title.setVisibility(View.VISIBLE);
        if (title != null) {
            holder.title.setText(title);
        } else if (titleRes != 0) {
            holder.title.setText(titleRes);
        } else {
            holder.title.setVisibility(View.GONE);
        }

        int titleColor = card.getTitleColor();

        if (holder.title.getVisibility() == View.VISIBLE) {
            if (titleColor != 0) {
                holder.title.setTextColor(titleColor);
            } else {
                holder.title.setTextColor(holder.title.getTextColors().getDefaultColor());
            }
        }

        holder.adapter.setData(card.getItems());
    }

    @Override
    public long getItemId(int position) {
        return UUID.fromString(differ.getCurrentList().get(position).getId()).getMostSignificantBits() & Long.MAX_VALUE;
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(ArrayList<MaterialPreferenceCard> newData) {
        List<MaterialPreferenceCard> data = new ArrayList<>();
        for (MaterialPreferenceCard card : newData) {
            data.add(card.clone());
        }
        differ.submitList(data);
    }


    List<MaterialPreferenceCard> getData() {
        return differ.getCurrentList();
    }

    class MaterialPreferenceListViewHolder extends RecyclerView.ViewHolder {

        final View cardView;
        final TextView title;
        final RecyclerView recyclerView;
        MaterialPreferenceItemAdapter adapter;

        MaterialPreferenceListViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.mp_list_card);
            title = (TextView) view.findViewById(R.id.mp_list_card_title);
            recyclerView = (RecyclerView) view.findViewById(R.id.mp_card_recyclerview);
            adapter = new MaterialPreferenceItemAdapter(viewTypeManager);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }


    public static final DiffUtil.ItemCallback<MaterialPreferenceCard> DIFF_CALLBACK = new DiffUtil.ItemCallback<MaterialPreferenceCard>() {
        @Override
        public boolean areItemsTheSame(MaterialPreferenceCard oldItem, MaterialPreferenceCard newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(MaterialPreferenceCard oldItem, MaterialPreferenceCard newItem) {
            boolean result;
            result = oldItem.toString().equals(newItem.toString());
            if (oldItem.getItems().size() != newItem.getItems().size()) return false;
            for (int i = 0; i < oldItem.getItems().size(); i++) {
                if (!oldItem.getItems().get(i).getDetailString().equals(newItem.getItems().get(i).getDetailString())) return false;
            }
            return result;
        }
    };
}
