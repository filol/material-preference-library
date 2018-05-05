package com.francoisdexemple.materialpreference.util;

import android.content.Context;
import android.view.View;

import com.francoisdexemple.materialpreference.R;
import com.francoisdexemple.materialpreference.holders.MaterialPreferenceItemViewHolder;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceItem;


public abstract class ViewTypeManager {

    public static final class ItemType {
        public static final int ACTION_ITEM = 0;
        public static final int TITLE_ITEM = 1;
        public static final int SWITCH_ITEM = 2;
        public static final int CHECKBOX_ITEM = 3;
    }

    public static final class ItemLayout {
        public static final int ACTION_LAYOUT = R.layout.material_preference_action_item;
        public static final int TITLE_LAYOUT = R.layout.material_preference_title_item;
        public static final int SWITCH_LAYOUT = R.layout.material_preference_switch_item;
        public static final int CHECKBOX_LAYOUT = R.layout.material_preference_checkbox_item;
    }

    public abstract int getLayout(int itemType);

    public abstract MaterialPreferenceItemViewHolder getViewHolder(int itemType, View view);

    public abstract void setupItem(int itemType, MaterialPreferenceItemViewHolder holder, MaterialPreferenceItem item, Context context);
}
