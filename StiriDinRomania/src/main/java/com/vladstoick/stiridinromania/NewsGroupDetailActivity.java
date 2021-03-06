package com.vladstoick.stiridinromania;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.vladstoick.DataModel.NewsDataSource;
import com.vladstoick.DataModel.NewsGroup;
import com.vladstoick.Fragments.NewsGroupDetailFragment;
import com.vladstoick.Utils.Tags;

/**
 * An activity representing a single NewsGroup detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link NewsGroupListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link com.vladstoick.Fragments.NewsGroupDetailFragment}.
 */
public class NewsGroupDetailActivity extends SherlockFragmentActivity implements
        NewsGroupDetailFragment.NewsGroupDetailFragmentCommunicationInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsgroup_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            int newsGroupId = getIntent().getIntExtra(Tags.NEWSGROUP_TAG_ID, -1);
            NewsGroup newsGroup = NewsDataSource.getInstance().getNewsGroup(newsGroupId);
            setTitle(getString(R.string.app_name) + " " + newsGroup.getTitle());
            arguments.putInt(Tags.NEWSGROUP_TAG_ID, newsGroupId);
            NewsGroupDetailFragment fragment = new NewsGroupDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.newsgroup_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, NewsGroupListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectedNewsSource(int id) {
        Intent intent = new Intent(this, NewsItemListActivity.class);
        intent.putExtra(Tags.NEWSOURCE_TAG_ID, id);
        startActivity(intent);
    }
}
