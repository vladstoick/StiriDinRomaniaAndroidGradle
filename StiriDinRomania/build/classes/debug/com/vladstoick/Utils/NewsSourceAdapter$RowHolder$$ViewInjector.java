// Generated code from Butter Knife. Do not modify!
package com.vladstoick.Utils;

import android.view.View;
import butterknife.Views.Finder;

public class NewsSourceAdapter$RowHolder$$ViewInjector {
  public static void inject(Finder finder, final com.vladstoick.Utils.NewsSourceAdapter.RowHolder target, Object source) {
    View view;
    view = finder.findById(source, 2131165281);
    target.mTitle = (android.widget.TextView) view;
    view = finder.findById(source, 2131165282);
    target.mDescription = (android.widget.TextView) view;
    view = finder.findById(source, 2131165280);
    target.mNumberOfNews = (android.widget.TextView) view;
    view = finder.findById(source, 2131165276);
    target.mButton = (android.widget.ImageButton) view;
  }

  public static void reset(com.vladstoick.Utils.NewsSourceAdapter.RowHolder target) {
    target.mTitle = null;
    target.mDescription = null;
    target.mNumberOfNews = null;
    target.mButton = null;
  }
}