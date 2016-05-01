package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.appstoremarketresearch.android_customalertdialogs.controller.ItemDetailActivity;
import com.appstoremarketresearch.android_customalertdialogs.controller.ItemListActivity;
import com.appstoremarketresearch.android_customalertdialogs.R;
import com.appstoremarketresearch.android_customalertdialogs.model.DummyContent;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // show the content as HTML in a WebView
        if (mItem != null) {

            String htmlFileName = "file:///android_asset/helloworld_" + mItem.id + ".html";

            try {
                switch(Integer.parseInt(mItem.id)) {
                    case 1:
                        WebView webview = (WebView) rootView.findViewById(R.id.webview);
                        webview.loadUrl(htmlFileName);
                        break;

                    default:
                        throw new FileNotFoundException(htmlFileName);
                }
            }
            catch (Exception ex) {
                android.util.Log.e(this.getClass().getSimpleName(), ex.toString());
            }
        }

        return rootView;
    }
}
