package tmrapps.getinshapeapp.Category.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import tmrapps.getinshapeapp.R;

/**
 * Created by tamir on 1/20/2018.
 */

public class AddCategoryDialog extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.add_category_layout, null);
        final EditText categoryTxt = v.findViewById(R.id.categoryTxt);
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String msg = getString(R.string.add_category_err);
                if (!categoryTxt.getText().toString().equals("")) {
                    msg = getString(R.string.add_category_ok);
                }
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "CANCEL", Toast.LENGTH_LONG).show();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface OnCategoryDialogInteractionListener {
        // TODO: Update argument type and name
        void onCategoryAdded(String categoryName);
    }
}
