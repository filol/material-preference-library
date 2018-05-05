package com.francoisdexemple.materialpreferencedemo.custom;

import android.content.Context;
import android.view.View;


import com.francoisdexemple.materialpreference.holders.MaterialPreferenceItemViewHolder;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceActionItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceTitleItem;
import com.francoisdexemple.materialpreference.util.ViewTypeManager;
import com.francoisdexemple.materialpreferencedemo.R;

import static com.francoisdexemple.materialpreferencedemo.custom.MyViewTypeManager.ItemLayout.ACTION_LAYOUT;
import static com.francoisdexemple.materialpreferencedemo.custom.MyViewTypeManager.ItemLayout.CUSTOM_LAYOUT;
import static com.francoisdexemple.materialpreferencedemo.custom.MyViewTypeManager.ItemLayout.TITLE_LAYOUT;
import static com.francoisdexemple.materialpreferencedemo.custom.MyViewTypeManager.ItemType.ACTION_ITEM;
import static com.francoisdexemple.materialpreferencedemo.custom.MyViewTypeManager.ItemType.CUSTOM_ITEM;
import static com.francoisdexemple.materialpreferencedemo.custom.MyViewTypeManager.ItemType.TITLE_ITEM;

public class MyViewTypeManager extends ViewTypeManager {

    public static final class ItemType {
        public static final int ACTION_ITEM = ViewTypeManager.ItemType.ACTION_ITEM;
        public static final int TITLE_ITEM = ViewTypeManager.ItemType.TITLE_ITEM;
        public static final int CUSTOM_ITEM = 10;
    }

    public static final class ItemLayout {
        public static final int ACTION_LAYOUT = ViewTypeManager.ItemLayout.ACTION_LAYOUT;
        public static final int TITLE_LAYOUT = ViewTypeManager.ItemLayout.TITLE_LAYOUT;
        public static final int CUSTOM_LAYOUT = R.layout.custom_item;
    }


    public int getLayout(int itemType) {
        switch (itemType) {
            case ACTION_ITEM:
                return ACTION_LAYOUT;
            case TITLE_ITEM:
                return TITLE_LAYOUT;
            case CUSTOM_ITEM:
                return CUSTOM_LAYOUT;
            default:
                return -1;
        }
    }

    public MaterialPreferenceItemViewHolder getViewHolder(int itemType, View view) {
        switch (itemType) {
            case ACTION_ITEM:
                return MaterialPreferenceActionItem.getViewHolder(view);
            case TITLE_ITEM:
                return MaterialPreferenceTitleItem.getViewHolder(view);
            case CUSTOM_ITEM:
                return MyCustomItem.getViewHolder(view);
            default:
                return null;
        }
    }

    public void setupItem(int itemType, MaterialPreferenceItemViewHolder holder, MaterialPreferenceItem item, Context context) {
        switch (itemType) {
            case ACTION_ITEM:
                MaterialPreferenceActionItem.setupItem((MaterialPreferenceActionItem.MaterialPreferenceActionItemViewHolder) holder, (MaterialPreferenceActionItem) item, context);
                break;
            case TITLE_ITEM:
                MaterialPreferenceTitleItem.setupItem((MaterialPreferenceTitleItem.MaterialPreferenceTitleItemViewHolder) holder, (MaterialPreferenceTitleItem) item, context);
                break;
            case CUSTOM_ITEM:
                MyCustomItem.setupItem((MyCustomItem.MyCustomItemViewHolder) holder, (MyCustomItem) item, context);
                break;
        }
    }
}
