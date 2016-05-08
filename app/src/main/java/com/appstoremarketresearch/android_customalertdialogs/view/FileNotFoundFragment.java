package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstoremarketresearch.android_customalertdialogs.R;

/**
 * FileNotFoundFragment
 */
public class FileNotFoundFragment extends Fragment {

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {

        return inflater.inflate(R.layout.file_not_found_dialog, container, false);
    }
}
