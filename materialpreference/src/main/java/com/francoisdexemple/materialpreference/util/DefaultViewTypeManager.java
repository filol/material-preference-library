package com.francoisdexemple.materialpreference.util;

import android.content.Context;
import android.view.View;

import com.francoisdexemple.materialpreference.holders.MaterialPreferenceItemViewHolder;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceActionItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceCheckBoxItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceSwitchItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceTitleItem;

import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemLayout.ACTION_LAYOUT;
import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemLayout.CHECKBOX_LAYOUT;
import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemLayout.SWITCH_LAYOUT;
import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemLayout.TITLE_LAYOUT;
import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemType.ACTION_ITEM;
import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemType.CHECKBOX_ITEM;
import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemType.SWITCH_ITEM;
import static com.francoisdexemple.materialpreference.util.ViewTypeManager.ItemType.TITLE_ITEM;


public class DefaultViewTypeManager extends ViewTypeManager {

    public static final class ItemType {
        public static final int ACTION_ITEM = ViewTypeManager.ItemType.ACTION_ITEM;
        public static final int SWITCH_ITEM = ViewTypeManager.ItemType.SWITCH_ITEM;
        public static final int TITLE_ITEM = ViewTypeManager.ItemType.TITLE_ITEM;
        public static final int CHECKBOX_ITEM = ViewTypeManager.ItemType.CHECKBOX_ITEM;
    }

    public static final class ItemLayout {
        public static final int ACTION_LAYOUT = ViewTypeManager.ItemLayout.ACTION_LAYOUT;
        public static final int TITLE_LAYOUT = ViewTypeManager.ItemLayout.TITLE_LAYOUT;
        public static final int SWITCH_LAYOUT = ViewTypeManager.ItemLayout.SWITCH_LAYOUT;
        public static final int CHECKBOX_LAYOUT = ViewTypeManager.ItemLayout.CHECKBOX_LAYOUT;
    }

    public int getLayout(int itemType) {
        switch (itemType) {
            case ACTION_ITEM:
                return ACTION_LAYOUT;
            case TITLE_ITEM:
                return TITLE_LAYOUT;
            case SWITCH_ITEM:
                return SWITCH_LAYOUT;
            case CHECKBOX_ITEM:
                return CHECKBOX_LAYOUT;
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
            case SWITCH_ITEM:
                return MaterialPreferenceSwitchItem.getViewHolder(view);
            case CHECKBOX_ITEM:
                return MaterialPreferenceCheckBoxItem.getViewHolder(view);
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
            case SWITCH_ITEM:
                MaterialPreferenceSwitchItem.setupItem((MaterialPreferenceSwitchItem.MaterialPreferenceSwitchItemViewHolder) holder,(MaterialPreferenceSwitchItem) item, context);
                break;
            case CHECKBOX_ITEM:
                MaterialPreferenceCheckBoxItem.setupItem((MaterialPreferenceCheckBoxItem.MaterialPreferenceCheckBoxItemViewHolder) holder, (MaterialPreferenceCheckBoxItem) item, context);
        }
    }
}
