package com.francoisdexemple.materialpreference.model;


import java.util.ArrayList;
import java.util.Collections;

public class MaterialPreferenceList {

    private ArrayList<MaterialPreferenceCard> cards = new ArrayList<>();

    private MaterialPreferenceList(Builder builder) {
        this.cards = builder.cards;
    }

    public MaterialPreferenceList(MaterialPreferenceCard... materialPreferenceCards) {
        Collections.addAll(cards, materialPreferenceCards);
    }

    public MaterialPreferenceList addCard(MaterialPreferenceCard card) {
        cards.add(card);
        return this;
    }

    public MaterialPreferenceList clearCards(MaterialPreferenceCard card) {
        cards.clear();
        return this;
    }

    public ArrayList<MaterialPreferenceCard> getCards() {
        return cards;
    }

    public static class Builder {
        private ArrayList<MaterialPreferenceCard> cards = new ArrayList<>();

        public Builder addCard(MaterialPreferenceCard card) {
            this.cards.add(card);
            return this;
        }

        public MaterialPreferenceList build() {
            return new MaterialPreferenceList(this);
        }
    }
}
