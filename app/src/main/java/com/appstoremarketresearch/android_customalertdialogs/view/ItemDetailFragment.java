package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.appstoremarketresearch.android_customalertdialogs.R;
import com.appstoremarketresearch.android_customalertdialogs.controller.ItemDetailActivity;
import com.appstoremarketresearch.android_customalertdialogs.controller.ItemListActivity;
import com.appstoremarketresearch.android_customalertdialogs.model.DummyContent;
import com.appstoremarketresearch.android_customalertdialogs.notification.AppEventNotifier;
import com.appstoremarketresearch.android_customalertdialogs.notification.AssetFileNameReceiver;

import java.io.FileNotFoundException;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment
    extends Fragment
    implements AssetFileNameReceiver {

    private View mRootView;

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

    /**
     * Does the array contain the target text?
     */
    private static boolean containsAsset(
        String[] array,
        String target) {

        if (array != null && target != null) {

            for (String s : array) {
                if (s.equals(target)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * handleException
     */
    private void handleException(Exception ex) {
        Activity callingActivity = getActivity();

        switch (Integer.parseInt(mItem.id)) {

            case 8:
                WebView webview = (WebView)mRootView.findViewById(R.id.webview);
                Object tag = webview.getTag();

                if (tag != null &&
                        tag.toString().startsWith("fileNotFoundActivity=")) {

                    int index = tag.toString().indexOf("=")+1;

                    String activityName =
                        "com.appstoremarketresearch.android_customalertdialogs.controller."
                        + tag.toString().substring(index);

                    try {
                        Class fileNotFoundActivity = Class.forName(activityName);

                        AlertDialogFactory.showFileNotFoundActivity(
                                callingActivity, fileNotFoundActivity, ex);
                    }
                    catch (ClassNotFoundException cnfe) {
                        AlertDialogFactory.showAlertDialog(
                                callingActivity, this, mItem, ex);
                    }
                }
                else {
                    AlertDialogFactory.showAlertDialog(
                            callingActivity, this, mItem, ex);
                }

                break;

            default:
                AlertDialogFactory.showAlertDialog(callingActivity, this, mItem, ex);
                break;
        }
    }

    /**
     * loadHtmlFile
     */
    private void loadHtmlFile(String htmlFileName) {

        try {
            // get list of HTML files
            String [] assets = getContext().getAssets().list("html");

            // confirm the request file exists
            if (containsAsset(assets, htmlFileName)) {
                String path = "file:///android_asset/html/" + htmlFileName;
                loadHtmlPath(path);
            }
            else {
                throw new FileNotFoundException(htmlFileName);
            }
        }
        catch (Exception ex) {
            handleException(ex);
        }
    }

    /**
     * loadHtmlPath
     */
    private void loadHtmlPath(String path) {
        WebView webview = (WebView)mRootView.findViewById(R.id.webview);
        webview.loadUrl(path);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            setUpAppBarLayout(mItem);
        }
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.item_detail, container, false);

        // show the content as HTML in a WebView
        if (mItem != null) {
            String filename = "helloworld_" + mItem.id + ".html";
            loadHtmlFile(filename);
        }

        AppEventNotifier.subscribe(this);

        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppEventNotifier.unsubscribe(this);
    }

    @Override
    public void receiveAssetFileName(String filename) {
        loadHtmlFile(filename);
    }

    /**
     * setUpAppBarLayout
     */
    private void setUpAppBarLayout(DummyContent.DummyItem item) {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout)activity.findViewById(R.id.toolbar_layout);

        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.content);
        }
    }
}
