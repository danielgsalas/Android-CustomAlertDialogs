package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.appstoremarketresearch.android_customalertdialogs.R;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created bon 5/6/2016.
 */
public class FileNotFoundDialog extends Dialog {

    private AssetFileNameReceiver   mReceiver;
    private FileNotFoundException   mException;

    private static final String LOG_TAG = FileNotFoundDialog.class.getSimpleName();

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
        setUpMessageText();
        setUpCancelButton();

        Spinner spinner = setUpFileSelector();
        setUpOkButton(spinner);
    }

    /**
     * setUpCancelButton
     */
    private void setUpCancelButton() {

        Button button = (Button)findViewById(R.id.cancel_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close this dialog
                FileNotFoundDialog.this.dismiss();
            }
        });
    }

    /**
     * setUpFileSelector
     */
    private Spinner setUpFileSelector() {
        Spinner spinner = (Spinner)findViewById(R.id.file_spinner);

        try {
            String [] assets = getContext().getAssets().list("html");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, assets);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);
        }
        catch (IOException ex) {
            Log.e(LOG_TAG, ex.toString());
        }

        return spinner;
    }

    /**
     * setUpMessageText
     */
    private void setUpMessageText() {

        // extract the file name from the exception message
        String filename = mException.getMessage();
        filename = filename.substring(filename.lastIndexOf("/")+1);

        // prepare the message
        int resourceId = R.string.file_not_found_dialog_message;
        String message = getContext().getResources().getString(resourceId);
        message = String.format(message, filename);

        // show the message
        resourceId = R.id.message;
        TextView textView = (TextView)findViewById(resourceId);
        textView.setText(message);
    }

    /**
     * setUpOkButton
     */
    private void setUpOkButton(final Spinner spinner) {

        Button button = (Button)findViewById(R.id.ok_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner != null && mReceiver != null) {

                    // load the selected HTML file
                    String text = spinner.getSelectedItem().toString();
                    mReceiver.receiveAssetFileName(text);

                    // close this dialog
                    FileNotFoundDialog.this.dismiss();
                }
            }
        });
    }

    /**
     * setUpTitleText
     */
    private void setUpTitleText() {
        int resourceId = R.string.file_not_found_dialog_title;
        this.setTitle(resourceId);
    }
}
