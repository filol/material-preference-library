package com.francoisdexemple.materialpreference;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.francoisdexemple.materialpreference.adapters.MaterialPreferenceListAdapter;
import com.francoisdexemple.materialpreference.model.MaterialPreferenceList;
import com.francoisdexemple.materialpreference.util.DefaultViewTypeManager;
import com.francoisdexemple.materialpreference.util.ViewTypeManager;


public abstract class MaterialPreferenceFragment extends Fragment {

    private MaterialPreferenceList list = new MaterialPreferenceList.Builder().build();
    private RecyclerView recyclerView;
    private MaterialPreferenceListAdapter adapter;

    public static com.francoisdexemple.materialpreference.MaterialPreferenceFragment newInstance(com.francoisdexemple.materialpreference.MaterialPreferenceFragment fragment) {
        return fragment;
    }

    protected abstract MaterialPreferenceList getMaterialPreferenceList(Context activityContext);

    protected int getTheme() {
        return R.style.Theme_AppCompat;
    }

    protected boolean shouldAnimate() {
        return true;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int style = getTheme();

        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), style);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View rootView = localInflater.inflate(R.layout.material_preference_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.mp_recyclerview);
        adapter = new MaterialPreferenceListAdapter(getViewTypeManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(20);

        ListTask task = new ListTask(getActivity());
        task.execute();

        return rootView;
    }

    protected ViewTypeManager getViewTypeManager() {
        return new DefaultViewTypeManager();
    }

    protected MaterialPreferenceList getList() {
        return list;
    }

    protected void setMaterialPreferenceList(MaterialPreferenceList materialPreferenceList) {
        list = materialPreferenceList;
        adapter.setData(list.getCards());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void refreshMaterialPreferenceList() {
        setMaterialPreferenceList(list);
    }

    private class ListTask extends AsyncTask<String, String, String> {

        Context fragmentContext;

        public ListTask(Context activityContext) {
            this.fragmentContext = activityContext;
        }

        @Override
        protected String doInBackground(String... params) {
            list = getMaterialPreferenceList(fragmentContext);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.setData(list.getCards());

            if (shouldAnimate()) {
                recyclerView.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(600)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .start();
            } else {
                recyclerView.setAlpha(1f);
                recyclerView.setTranslationY(0f);
            }

            super.onPostExecute(s);
            fragmentContext = null;
        }
    }
}
