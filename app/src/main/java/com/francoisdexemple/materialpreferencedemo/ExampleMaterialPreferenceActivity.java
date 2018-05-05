package com.francoisdexemple.materialpreferencedemo;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.francoisdexemple.materialpreference.ConvenienceBuilder;
import com.francoisdexemple.materialpreference.MaterialPreferenceActivity;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceActionItem;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceItemOnClickAction;
import com.francoisdexemple.materialpreference.items.MaterialPreferenceTitleItem;
import com.francoisdexemple.materialpreference.model.MaterialPreferenceCard;
import com.francoisdexemple.materialpreference.model.MaterialPreferenceList;
import com.francoisdexemple.materialpreference.util.ViewTypeManager;
import com.francoisdexemple.materialpreferencedemo.custom.MyCustomItem;
import com.francoisdexemple.materialpreferencedemo.custom.MyViewTypeManager;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

public class ExampleMaterialPreferenceActivity extends MaterialPreferenceActivity {

    public static final String THEME_EXTRA = "";
    public static final int THEME_LIGHT_LIGHTBAR = 0;
    public static final int THEME_LIGHT_DARKBAR = 1;
    public static final int THEME_DARK_LIGHTBAR = 2;
    public static final int THEME_DARK_DARKBAR = 3;
    public static final int THEME_CUSTOM_CARDVIEW = 4;

    protected int colorIcon = R.color.mp_color_icon_light_theme;

    @NonNull
    @Override
    protected MaterialPreferenceList getMaterialPreferenceList(@NonNull final Context c) {
        MaterialPreferenceCard.Builder advancedCardBuilder = new MaterialPreferenceCard.Builder();
        advancedCardBuilder.title("Advanced");

        advancedCardBuilder.addItem(new MaterialPreferenceTitleItem.Builder()
                .text("TitleItem OnClickAction")
                .icon(R.mipmap.ic_launcher)
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(c, Uri.parse("http://www.daniel-stone.uk")))
                .build());

        advancedCardBuilder.addItem(new MaterialPreferenceActionItem.Builder()
                .text("Snackbar demo")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_code_tags)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .setOnClickAction(new MaterialPreferenceItemOnClickAction() {
                    @Override
                    public void onClick() {
                        Snackbar.make(((ExampleMaterialPreferenceActivity) c).findViewById(R.id.mp_material_preference_activity_coordinator_layout), "Test", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .build());

        advancedCardBuilder.addItem(new MaterialPreferenceActionItem.Builder()
                .text("OnLongClickAction demo")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_hand_pointing_right)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .setOnLongClickAction(new MaterialPreferenceItemOnClickAction() {
                    @Override
                    public void onClick() {
                        Toast.makeText(c, "Long pressed", Toast.LENGTH_SHORT).show();
                    }
                })
                .build());

        advancedCardBuilder.addItem(new MyCustomItem.Builder()
                .text("Custom Item")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_code_braces)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .build());

        advancedCardBuilder.addItem(createDynamicItem("Tap for a random number & swap position", c));

        return Demo.createMaterialPreferenceList(c, colorIcon, getIntent().getIntExtra(THEME_EXTRA, THEME_LIGHT_DARKBAR)).addCard(advancedCardBuilder.build());
    }

    private MaterialPreferenceActionItem createDynamicItem(String subText, final Context c) {
        final MaterialPreferenceActionItem item = new MaterialPreferenceActionItem.Builder()
                .text("Dynamic UI")
                .subText(subText)
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_refresh)
                        .color(ContextCompat.getColor(c, colorIcon)
                        ).sizeDp(18))
                .build();
        item.setOnClickAction(new MaterialPreferenceItemOnClickAction() {
            @Override
            public void onClick() {
                getList().getCards().get(4).getItems().remove(getList().getCards().get(4).getItems().indexOf(item));
                int newIndex = ((int) (Math.random() * 5));
                getList().getCards().get(4).getItems().add(newIndex, item);
                item.setSubText("Random number: " + ((int) (Math.random() * 10)));
                setMaterialPreferenceList(getList());
            }
        });

        return item;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("test", "onCreate: " + getIntent().getIntExtra(THEME_EXTRA, THEME_LIGHT_DARKBAR));
        switch (getIntent().getIntExtra(THEME_EXTRA, THEME_LIGHT_DARKBAR)) {
            case THEME_LIGHT_LIGHTBAR:
                setTheme(R.style.AppTheme_MaterialPreferenceActivity_Light);
                colorIcon = R.color.mp_color_icon_light_theme;
                break;
            case THEME_DARK_LIGHTBAR:
                setTheme(R.style.AppTheme_MaterialPreferenceActivity_Dark_LightActionBar);
                colorIcon = R.color.mp_color_icon_dark_theme;
                break;
            case THEME_LIGHT_DARKBAR:
                setTheme(R.style.AppTheme_MaterialPreferenceActivity_Light_DarkActionBar);
                colorIcon = R.color.mp_color_icon_light_theme;
                break;
            case THEME_DARK_DARKBAR:
                setTheme(R.style.AppTheme_MaterialPreferenceActivity_Dark);
                colorIcon = R.color.mp_color_icon_dark_theme;
                break;
            case THEME_CUSTOM_CARDVIEW:
                setTheme(R.style.AppTheme_MaterialPreferenceActivity_Light_DarkActionBar_CustomCardView);
                colorIcon = R.color.mp_color_icon_dark_theme;
                break;
        }

        super.onCreate(savedInstanceState);

//        Call this method to let the scrollbar scroll off screen
//        setScrollToolbar(true);
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.mp_title_preference);
    }

    @NonNull
    @Override
    protected ViewTypeManager getViewTypeManager() {
        return new MyViewTypeManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_example_material_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refreshMaterialPreferenceList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
