package com.francoisdexemple.materialpreference.model;


import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;


import com.francoisdexemple.materialpreference.items.MaterialPreferenceItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class MaterialPreferenceCard {

    private String id = "NO-UUID";

    private CharSequence title = null;
    private int titleRes = 0;

    private int titleColor = 0;
    private int cardColor = 0;

    private ArrayList<MaterialPreferenceItem> items = new ArrayList<>();


    private MaterialPreferenceCard(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.title = builder.title;
        this.titleRes = builder.titleRes;
        this.titleColor = builder.titleColor;
        this.cardColor = builder.cardColor;
        this.items = builder.items;
    }

    public MaterialPreferenceCard(CharSequence title, MaterialPreferenceItem... materialPreferenceItems) {
        this.title = title;
        Collections.addAll(items, materialPreferenceItems);
    }

    public MaterialPreferenceCard(int titleRes, MaterialPreferenceItem... materialPreferenceItems) {
        this.titleRes = titleRes;
        Collections.addAll(items, materialPreferenceItems);
    }

    public CharSequence getTitle() {
        return title;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getCardColor() {
        return cardColor;
    }

    public ArrayList<MaterialPreferenceItem> getItems() {
        return items;
    }

    public static class Builder {
        private CharSequence title = null;
        @StringRes
        private int titleRes = 0;

        @ColorInt
        private int titleColor = 0;

        @ColorInt
        private int cardColor = 0;

        private ArrayList<MaterialPreferenceItem> items = new ArrayList<>();

        public Builder title(CharSequence title) {
            this.title = title;
            this.titleRes = 0;
            return this;
        }

        public Builder title(@StringRes int titleRes) {
            this.titleRes = titleRes;
            this.title = null;
            return this;
        }

        public Builder titleColor(@ColorInt int color) {
            this.titleColor = color;
            return this;
        }

        public Builder cardColor(@ColorInt int cardColor) {
            this.cardColor = cardColor;
            return this;
        }

        public Builder addItem(MaterialPreferenceItem item) {
            this.items.add(item);
            return this;
        }

        public MaterialPreferenceCard build() {
            return new MaterialPreferenceCard(this);
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        String result = "MaterialPreferenceCard{" +
                "id='" + id + '\'' +
                ", title=" + title +
                ", titleRes=" + titleRes +
                ", titleColor=" + titleColor +
                ", cardColor=" + cardColor + '}';
        return result;
    }

    public MaterialPreferenceCard(MaterialPreferenceCard card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.titleRes = card.getTitleRes();
        this.titleColor = card.getTitleColor();
        this.cardColor = card.getCardColor();
        this.items = new ArrayList<>();
        for (MaterialPreferenceItem item : card.items) {
            this.items.add(item.clone());
        }
    }

    public MaterialPreferenceCard clone() {
        return new MaterialPreferenceCard(this);
    }

}
