package com.francoisdexemple.materialpreferencedemo;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.francoisdexemple.materialpreference.MaterialPreferenceFragment;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceActionItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceItemOnClickAction;
import com.francoisdexemple.materialpreference.model.MaterialPreferenceList;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import static com.francoisdexemple.materialpreferencedemo.ExampleMaterialPreferenceActivity.THEME_LIGHT_DARKBAR;


public class ExampleMaterialPreferenceFragment extends MaterialPreferenceFragment {

    private MaterialPreferenceActionItem createDynamicItem(String subText, final Context c) {
        final MaterialPreferenceActionItem item = new MaterialPreferenceActionItem.Builder()
                .text("Dynamic UI")
                .subText(subText)
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_refresh)
                        .color(ContextCompat.getColor(c, R.color.mp_color_icon_dark_theme)
                        ).sizeDp(18))
                .build();
        item.setOnClickAction(new MaterialPreferenceItemOnClickAction() {
            @Override
            public void onClick() {
                item.setSubText("Random number: " + ((int) (Math.random() * 10)));
                refreshMaterialPreferenceList();
            }
        });
        return item;

    }

    @Override
    protected MaterialPreferenceList getMaterialPreferenceList(final Context c) {
        MaterialPreferenceList list = Demo.createMaterialPreferenceList(c, R.color.mp_color_icon_dark_theme, THEME_LIGHT_DARKBAR);

        list.getCards().get(4).getItems().add(createDynamicItem("Tap for a random number", c));

        final MaterialPreferenceActionItem time = new MaterialPreferenceActionItem.Builder()
                .text("Unix Time In Millis")
                .subText("Time")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_clock)
                        .color(ContextCompat.getColor(c, R.color.mp_color_icon_dark_theme)
                        ).sizeDp(18))
                .build();
        list.getCards().get(4).getItems().add(time);

        return list;
    }

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("MaterialPrefFragment", "Updating with time");
            if (getList().getCards().size() > 0) {
                ((MaterialPreferenceActionItem) getList().getCards().get(4).getItems().get(7)).setSubText("" + System.currentTimeMillis());
                refreshMaterialPreferenceList();
            }
            handler.postDelayed(this, 1000);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        runnable.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected int getTheme() {
        return R.style.AppTheme_MaterialPreferenceActivity_Fragment;
    }
}
