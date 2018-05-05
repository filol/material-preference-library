package com.francoisdexemple.materialpreferencedemo;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.francoisdexemple.materialpreference.model.MaterialPreferenceList;


public class ExampleMaterialPreferenceLicenseActivity extends ExampleMaterialPreferenceActivity {

    @NonNull @Override
    protected MaterialPreferenceList getMaterialPreferenceList(@NonNull final Context c) {
        return Demo.createMaterialPreferenceLicenseList(c, colorIcon);
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.mp_title_licenses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
}
