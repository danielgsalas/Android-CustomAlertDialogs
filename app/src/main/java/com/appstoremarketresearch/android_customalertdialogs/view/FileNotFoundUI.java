package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.appstoremarketresearch.android_customalertdialogs.R;

import java.io.IOException;

/**
 * Created on 5/7/2016.
 */
public class FileNotFoundUI {

    private static final String LOG_TAG = FileNotFoundUI.class.getSimpleName();

    /**
     * setUpCancelButton
     */
    public static void setUpCancelButton(final Dialog dialog) {

        Button button = (Button)dialog.findViewById(R.id.cancel_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * setUpCancelButton
     */
    public static void setUpCancelButton(View view) {

        final Activity activity = (Activity)view.getContext();
        Button button = (Button)view.findViewById(R.id.cancel_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    /**
     * setUpFileSelector
     */
    public static Spinner setUpFileSelector(Object uiContainer) {

        Context context = null;
        Spinner spinner = null;

        if (uiContainer instanceof Dialog) {
            Dialog dialog = (Dialog)uiContainer;
            context = dialog.getContext();
            spinner = (Spinner)dialog.findViewById(R.id.file_spinner);
        }
        else if (uiContainer instanceof View) {
            View view = (View)uiContainer;
            context = view.getContext();
            spinner = (Spinner)view.findViewById(R.id.file_spinner);
        }

        try {
            String [] assets = context.getAssets().list("html");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, assets);
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
    public static void setUpMessageText(
        Dialog dialog,
        Exception exception) {

        Context context = dialog.getContext();

        int resourceId = R.id.message;
        TextView textView = (TextView)dialog.findViewById(resourceId);

        setUpMessageText(context, textView, exception);
    }

    /**
     * setUpMessageText
     */
    public static void setUpMessageText(
        View view,
        Exception exception) {

        Context context = view.getContext();

        int resourceId = R.id.message;
        TextView textView = (TextView)view.findViewById(resourceId);

        setUpMessageText(context, textView, exception);
    }

    /**
     * setUpMessageText
     */
    private static void setUpMessageText(
        Context context,
        TextView textView,
        Exception exception) {

        // extract the file name from the exception message
        String filename = exception.getMessage();
        filename = filename.substring(filename.lastIndexOf("/")+1);

        // show the message
        int resourceId = R.string.file_not_found_dialog_message;
        String message = context.getResources().getString(resourceId);
        message = String.format(message, filename);
        textView.setText(message);
    }

    /**
     * setUpOkButton
     */
    public static void setUpOkButton(
        final Dialog dialog,
        final Spinner spinner,
        final AssetFileNameReceiver receiver) {

        Button button = (Button)dialog.findViewById(R.id.ok_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner != null && receiver != null) {

                    // load the selected HTML file
                    String text = spinner.getSelectedItem().toString();
                    receiver.receiveAssetFileName(text);

                    // close this dialog
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * setUpOkButton
     */
    public static void setUpOkButton(
        final View view,
        final Spinner spinner,
        final AssetFileNameReceiver receiver) {

        Button button = (Button)view.findViewById(R.id.ok_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner != null && receiver != null) {

                    // load the selected HTML file
                    String text = spinner.getSelectedItem().toString();
                    receiver.receiveAssetFileName(text);

                    // finish the activity
                    Activity activity = (Activity)view.getContext();
                    activity.finish();
                }
            }
        });
    }
}
