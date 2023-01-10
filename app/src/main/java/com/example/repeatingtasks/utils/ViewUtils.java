package com.example.repeatingtasks.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.repeatingtasks.R;


public class ViewUtils {

    private ViewUtils() {
    }

    /**
     * Shows an AlertDialog with a share button with the given title and error message
     *
     * @param customError Custom error that contain a title resource id and the error
     */
    public static void showErrorDialog(CustomError customError, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(customError.getStringResourceId()))
                .setMessage(Log.getStackTraceString(customError.getThrowable()))
                .setPositiveButton(context.getResources().getText(R.string.share_text), (dialog, which) -> {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(customError.getStringResourceId()) + "\n\n" + Log.getStackTraceString(customError.getThrowable()));
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    context.startActivity(shareIntent);
                })
                .setNeutralButton(context.getResources().getText(R.string.close_text), null)
                .show();
    }

}
