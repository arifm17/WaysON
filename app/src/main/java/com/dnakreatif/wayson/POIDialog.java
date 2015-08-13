package com.dnakreatif.wayson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.dnakreatif.wayson.R;

/**
 * Created by arifraqilla on 7/22/2015.
 */
public class POIDialog extends DialogFragment {

    public static String poiTag;
    private static String[] items;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        items = getResources().getStringArray(R.array.poi_tags);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.selectPOI)
                .setItems(R.array.poi_tags, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPoiTag(which, items);
                        //poiTag = items[which];
                    }
                });
        return builder.create();
    }

    public String getPoiTag(int which, String[] items) {
        poiTag = items[which];
        return poiTag;
    }
}
