package tmrapps.getinshapeapp.Category.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
 * This is a dialog fragment for adding a new category
 */
public class AddCategoryDialog extends DialogFragment{
    private OnCategoryDialogInteractionListener mListener;

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
                String catName = categoryTxt.getText().toString();
                // Validation check
                if (!catName.equals("")) {
                    msg = getString(R.string.add_category_ok);
                }

                // Show result to the user
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                if(mListener!=null)
                    mListener.onCategoryAdded(catName);
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

    public void setHandlerListener(OnCategoryDialogInteractionListener listener)
    {
        mListener = listener;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCategoryDialogInteractionListener {
        void onCategoryAdded(String categoryName);
    }
}
