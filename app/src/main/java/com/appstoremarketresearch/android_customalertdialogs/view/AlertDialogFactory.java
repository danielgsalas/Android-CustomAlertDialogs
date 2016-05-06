package com.appstoremarketresearch.android_customalertdialogs.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.appstoremarketresearch.android_customalertdialogs.model.DummyContent;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created on 4/30/2016
 */
public class AlertDialogFactory {

    /**
     * showAlertDialog
     */
    public static void showAlertDialog(
        Activity activity,
        DummyContent.DummyItem mItem,
        Exception exception) {

        switch (Integer.parseInt(mItem.id)) {

            case 3:
                showAlertDialogErrorType(activity, exception);
                break;

            case 4:
                showAlertDialogErrorTypeAndMessage(activity, exception);
                break;

            case 5:
                showAlertDialogCancelToClose(activity, exception);
                break;

            case 6:
                showAlertDialogOptionalStackTrace(activity, exception);
                break;

            case 2:
            default:
                showAlertDialogGeneric(activity);
                break;
        }
    }

    /**
     * showAlertDialogCancelToClose
     */
    public static void showAlertDialogCancelToClose(
        final Activity activity,
        Exception exception) {

        String title = exception.getClass().getSimpleName();
        String message = exception.getMessage();

        new AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                activity.finish();
                }
            })
            .setPositiveButton("OK", null)
            .show();
    }

    /**
     * showAlertDialogOptionalStackTrace
     */
    public static void showAlertDialogOptionalStackTrace(
        final Activity activity,
        final Exception exception) {

        String title = exception.getClass().getSimpleName();
        String message = exception.getMessage();

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Show Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringWriter sw = new StringWriter();
                        exception.printStackTrace(new PrintWriter(sw));
                        String message = sw.toString();
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * showAlertDialogErrorTypeAndMessage
     */
    public static void showAlertDialogErrorTypeAndMessage(
        Activity activity,
        Exception exception) {

        String title = exception.getClass().getSimpleName();
        String message = exception.getMessage();

        new AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show();
    }

    /**
     * showAlertDialogErrorType
     */
    public static void showAlertDialogErrorType(
        Activity activity,
        Exception exception) {

        String title = "Error";
        String message = "An error occurred: ";
        message += exception.getClass().getSimpleName();

        new AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show();
    }

    /**
     * showAlertDialogGeneric
     */
    public static void showAlertDialogGeneric(Activity activity) {

        String title = "Error";
        String message = "An error occurred";

        new AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show();
    }
}
