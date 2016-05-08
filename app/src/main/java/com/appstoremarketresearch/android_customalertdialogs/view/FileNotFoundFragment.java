package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.appstoremarketresearch.android_customalertdialogs.R;
import com.appstoremarketresearch.android_customalertdialogs.notification.AppEventType;
import com.appstoremarketresearch.android_customalertdialogs.notification.AssetFileNameReceiver;

import java.io.FileNotFoundException;

/**
 * FileNotFoundFragment
 */
public class FileNotFoundFragment
    extends Fragment
    implements AssetFileNameReceiver {

    private static String LOG_TAG = FileNotFoundFragment.class.getSimpleName();

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.file_not_found_dialog, container, false);

        Activity activity = getActivity();
        Intent intent = activity.getIntent();

        String key = FileNotFoundException.class.getSimpleName();
        Exception exception = (Exception)intent.getSerializableExtra(key);

        FileNotFoundUI.setUpMessageText(rootView, exception);
        FileNotFoundUI.setUpCancelButton(rootView);

        Spinner spinner = FileNotFoundUI.setUpFileSelector(rootView);
        FileNotFoundUI.setUpOkButton(rootView, spinner, this);

        return rootView;
    }

    @Override
    public void receiveAssetFileName(String filename) {
        Intent intent = new Intent();
        intent.setAction(AppEventType.ASSET_FILE_SELECTED.name());
        intent.putExtra("filename", filename);
        getActivity().sendBroadcast(intent);
    }
}
