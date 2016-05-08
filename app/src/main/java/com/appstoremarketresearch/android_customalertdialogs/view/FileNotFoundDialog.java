package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Spinner;

import com.appstoremarketresearch.android_customalertdialogs.R;

import java.io.FileNotFoundException;

/**
 * Created bon 5/6/2016.
 */
public class FileNotFoundDialog extends Dialog {

    private AssetFileNameReceiver   mReceiver;
    private FileNotFoundException   mException;

    /**
     * FileNotFoundDialog
     */
    public FileNotFoundDialog(
        Context context,
        FileNotFoundException exception,
        AssetFileNameReceiver receiver) {
        super(context);
        this.mException = exception;
        this.mReceiver = receiver;
    }

    /**
     * FileNotFoundDialog
     */
    public FileNotFoundDialog(
        Context context,
        int themeResId) {
        super(context, themeResId);
    }

    /**
     * FileNotFoundDialog
     */
    protected FileNotFoundDialog(
        Context context,
        boolean cancelable,
        OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.file_not_found_dialog);

        setUpTitleText();
        FileNotFoundUI.setUpMessageText(this, mException);
        FileNotFoundUI.setUpCancelButton(this);

        Spinner spinner = FileNotFoundUI.setUpFileSelector(this);
        FileNotFoundUI.setUpOkButton(this, spinner, mReceiver);
    }

    /**
     * setUpTitleText
     */
    private void setUpTitleText() {
        int resourceId = R.string.file_not_found_dialog_title;
        this.setTitle(resourceId);
    }
}
