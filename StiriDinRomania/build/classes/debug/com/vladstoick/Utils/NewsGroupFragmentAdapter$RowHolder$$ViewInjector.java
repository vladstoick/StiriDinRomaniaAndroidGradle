// Generated code from Butter Knife. Do not modify!
package com.vladstoick.Utils;

import android.view.View;
import butterknife.Views.Finder;

public class NewsGroupFragmentAdapter$RowHolder$$ViewInjector {
  public static void inject(Finder finder, final com.vladstoick.Utils.NewsGroupFragmentAdapter.RowHolder target, Object source) {
    View view;
    view = finder.findById(source, 2131099740);
    target.mTitle = (android.widget.TextView) view;
    view = finder.findById(source, 2131099741);
    target.mDescription = (android.widget.TextView) view;
    view = finder.findById(source, 2131099739);
    target.mNumberOfNews = (android.widget.TextView) view;
  }

  public static void reset(com.vladstoick.Utils.NewsGroupFragmentAdapter.RowHolder target) {
    target.mTitle = null;
    target.mDescription = null;
    target.mNumberOfNews = null;
  }
}
