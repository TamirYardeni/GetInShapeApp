package tmrapps.getinshapeapp.Main.PersonalArea;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tmrapps.getinshapeapp.Main.MainActivity;
import tmrapps.getinshapeapp.Main.MultiSelectionSpinner;
import tmrapps.getinshapeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment_personal_area must implement the
 * {@link PersonalAreaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalAreaFragment#newInstance} factory method to
 * create an instance of this fragment_personal_area.
 */
public class PersonalAreaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment_personal_area initialization parameters, e.g. ARG_ITEM_NUMBER
    private OnFragmentInteractionListener mListener;
    DatePickerDialog datePickerDialog;

    public PersonalAreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment_personal_area using the provided parameters.
     *
     * @return A new instance of fragment_personal_area PersonalAreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalAreaFragment newInstance() {
        PersonalAreaFragment fragment = new PersonalAreaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment_personal_area
        View view = inflater.inflate(R.layout.fragment_personal_area, container, false);

        Button signBtn = (Button) view.findViewById(R.id.btnEndDate);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimePickerClick(v);
            }
        });

        MultiSelectionSpinner spinner=(MultiSelectionSpinner)view.findViewById(R.id.input1);

        List<String> list = new ArrayList<String>();
        list.add("ראשון");
        list.add("שני");
        list.add("שלישי");
        list.add("רביעי");
        list.add("חמישי");
        list.add("שישי");
        list.add("שבת");

        spinner.setItems(list);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onTimePickerClick(View v) {
            final TextView textView = (TextView) getView().findViewById(R.id.txtEndDate);

            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            // date picker dialog
            datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            textView.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);

                        }}
                    , mYear, mMonth, mDay);
            datePickerDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment_personal_area to allow an interaction in this fragment_personal_area to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}
