package com.francoisdexemple.materialpreference;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;


import com.francoisdexemple.materialpreference.adapters.MaterialPreferenceListAdapter;
import com.francoisdexemple.materialpreference.model.MaterialPreferenceList;
import com.francoisdexemple.materialpreference.util.DefaultViewTypeManager;
import com.francoisdexemple.materialpreference.util.ViewTypeManager;

import java.lang.ref.WeakReference;

public abstract class MaterialPreferenceActivity extends AppCompatActivity {

    private MaterialPreferenceList list = new MaterialPreferenceList.Builder().build();
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MaterialPreferenceListAdapter adapter;

    @NonNull
    protected abstract MaterialPreferenceList getMaterialPreferenceList(@NonNull Context context);

    @Nullable
    protected abstract CharSequence getActivityTitle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyAttributesExist();

        setContentView(R.layout.material_preference_activity);

        CharSequence title = getActivityTitle();
        if (title == null)
            setTitle(R.string.mp_title_preference);
        else
            setTitle(title);


        assignViews();
        initViews();

        ListTask task = new ListTask(this);
        task.execute();
    }

    private void verifyAttributesExist() {
        TypedValue typedValue = new TypedValue();
        boolean mpColorPrimaryExists =
                getTheme().resolveAttribute(R.attr.mp_color_primary, typedValue, true);
        boolean mpColorSecondaryExists =
                getTheme().resolveAttribute(R.attr.mp_color_secondary, typedValue, true);
        if (!mpColorPrimaryExists || !mpColorSecondaryExists) {
            throw new IllegalStateException(String.format("The current theme doesn't provide %s " +
                            "and/or %s. Please use a theme provided by the library or an extension.",
                    getResources().getResourceEntryName(R.attr.mp_color_primary),
                    getResources().getResourceEntryName(R.attr.mp_color_secondary)));
        }
    }

    private void assignViews() {
        toolbar = (Toolbar) findViewById(R.id.mp_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.mp_recyclerview);
        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(20);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        adapter = new MaterialPreferenceListAdapter(getViewTypeManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    @NonNull
    protected ViewTypeManager getViewTypeManager() {
        return new DefaultViewTypeManager();
    }

    @NonNull
    protected MaterialPreferenceList getList() {
        return list;
    }

    protected boolean shouldAnimate() {
        return true;
    }

    protected void refreshMaterialPreferenceList() {
        setMaterialPreferenceList(list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onTaskFinished(@Nullable MaterialPreferenceList materialPreferenceList) {
        if (materialPreferenceList != null) {
            list = materialPreferenceList;
            adapter.setData(list.getCards());

            if (shouldAnimate()) {
                recyclerView.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(600)
                        .setInterpolator(new FastOutSlowInInterpolator()).start();
            } else {
                recyclerView.setAlpha(1f);
                recyclerView.setTranslationY(0f);
            }
        } else {
            finish();//?? why we remain here anyway?
        }
    }

    protected void setMaterialPreferenceList(MaterialPreferenceList materialPreferenceList) {
        list = materialPreferenceList;
        adapter.setData(list.getCards());
    }

    protected void setScrollToolbar(boolean scrollToolbar) {
        if (toolbar != null) {
            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            if (scrollToolbar) {
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            } else {
                params.setScrollFlags(0);
            }
        }
    }

    private static class ListTask extends AsyncTask<String, String, MaterialPreferenceList> {

        private WeakReference<com.francoisdexemple.materialpreference.MaterialPreferenceActivity> context;

        ListTask(com.francoisdexemple.materialpreference.MaterialPreferenceActivity context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        protected MaterialPreferenceList doInBackground(String... params) {
            return isCancelled() || context.get() == null ? null : context.get().getMaterialPreferenceList(context.get());
        }

        @Override
        protected void onPostExecute(MaterialPreferenceList materialPreferenceList) {
            super.onPostExecute(materialPreferenceList);
            if (context.get() != null) {
                if (!context.get().isFinishing()) {
                    context.get().onTaskFinished(materialPreferenceList);
                }
            }
            context = null;
        }
    }
}
