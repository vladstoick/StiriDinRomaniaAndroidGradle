package com.vladstoick.Fragments.AddElement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.vladstoick.stiridinromania.R;

import butterknife.InjectView;
import butterknife.Views;


/**
 * Created by Vlad on 9/27/13.
 */
public class AddElementCategoryFragment extends SherlockFragment {
    @InjectView(R.id.categoriesListView) ListView mListView;
    public AddElementCategoryFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_add_elemens_category,container,false);
        Views.inject(this,mView);
        return mView;
    }
}