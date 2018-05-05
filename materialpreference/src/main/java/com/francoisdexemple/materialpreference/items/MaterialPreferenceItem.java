package com.francoisdexemple.materialpreference.items;

import java.util.UUID;

public abstract class MaterialPreferenceItem {

    public String id = "NO-UUID";

    public MaterialPreferenceItem() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public abstract int getType();

    public abstract String getDetailString();

    public abstract MaterialPreferenceItem clone();

}
