package com.vladstoick.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.squareup.otto.Subscribe;
import com.vladstoick.DataModel.NewsGroup;
import com.vladstoick.OttoBus.BusProvider;
import com.vladstoick.OttoBus.DataLoadedEvent;
import com.vladstoick.Utils.NewsGroupFragmentAdapter;
import com.vladstoick.Utils.Tags;
import com.vladstoick.stiridinromania.R;
import com.vladstoick.stiridinromania.StiriApp;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by vlad on 7/19/13.
 */
public class NewsGroupDetailFragment extends SherlockFragment {
    public interface NewsGroupDetailFragmentCommunicationInterface{
        public void selectedNewsSource(int id);
    }
    int newsGroupId;
    public static String TAG = "NEWSGROUPFRAGMENT";
    private NewsGroup newsGroup;
    private View mView;
    @InjectView(R.id.newsGroupListView) ListView mList;
    private NewsGroupDetailFragmentCommunicationInterface mListener;
    NewsGroupFragmentAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setHasOptionsMenu(true);
        if (savedInstanceState != null)
            newsGroup = savedInstanceState.getParcelable(Tags.NEWSGROUP_TAG);
        else if (getArguments() != null) {
            newsGroupId = getArguments().getInt(Tags.NEWSGROUP_TAG_ID);
            StiriApp app = (StiriApp)(getSherlockActivity().getApplication());
            newsGroup = app.newsDataSource.getNewsGroup(newsGroupId);
        }
    }
    public NewsGroupDetailFragment() {
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NewsGroupDetailFragmentCommunicationInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentCommunicationInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        BusProvider.getInstance().unregister(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Tags.NEWSGROUP_TAG, newsGroup);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_newsgroup, container, false);
        Views.inject(this,mView);
        adapter = new NewsGroupFragmentAdapter(newsGroup, getSherlockActivity());
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.selectedNewsSource(newsGroup.newsSources.get(position).getId());
            }
        });
        return mView;
    }
    @Subscribe
    public void onDataLoaded(DataLoadedEvent event)
    {
        newsGroup = ((StiriApp)(getSherlockActivity()).getApplication())
                .newsDataSource.getNewsGroup(newsGroupId);
        adapter.setData(newsGroup);
        adapter.notifyDataSetChanged();
    }
}
