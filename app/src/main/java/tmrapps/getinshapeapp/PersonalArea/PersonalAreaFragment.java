package tmrapps.getinshapeapp.PersonalArea;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import tmrapps.getinshapeapp.Main.MultiSelectionSpinner;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalAreaRepository;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformation;
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
    boolean isDateChanged;

    TimePickerDialog timePickerDialog;
    String userId;
    PersonalInformation personalInformation = new PersonalInformation();
    PersonalAreaViewModel personalAreaViewModel;
    private ProgressBar progressBar;


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
        if (getArguments() != null) {
            this.userId = getArguments().getString("userId");
        }
    }

    private void changeToReadOnlyMode() {
        ((Button) getView().findViewById(R.id.btnSave)).setText("שינוי");
        getView().findViewById(R.id.btnEndDate).setEnabled(false);
        getView().findViewById(R.id.btnTime).setEnabled(false);
        getView().findViewById(R.id.edCurWeight).setEnabled(false);
        getView().findViewById(R.id.edWeightAchieved).setEnabled(false);
        getView().findViewById(R.id.multiSelectDay).setEnabled(false);
        setBtnClick(getView().findViewById(R.id.btnSave), this::onEditBtnClick);

    }

    private void changeToEditMode() {
        ((Button) getView().findViewById(R.id.btnSave)).setText("שמור");
        getView().findViewById(R.id.btnEndDate).setEnabled(true);
        getView().findViewById(R.id.btnTime).setEnabled(true);
        getView().findViewById(R.id.edCurWeight).setEnabled(true);
        getView().findViewById(R.id.multiSelectDay).setEnabled(true);
        getView().findViewById(R.id.edWeightAchieved).setEnabled(true);
        setBtnClick(getView().findViewById(R.id.btnSave), this::onSaveBtnClick);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isDateChanged = false;
        // Inflate the layout for this fragment_personal_area
        View view = inflater.inflate(R.layout.fragment_personal_area, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.personalAreaAppBarHeader);
        this.hideAllUIElements(view);
        progressBar = view.findViewById(R.id.personalAreaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        personalAreaViewModel.getPersonalInformation(this.userId).observe(this, info -> {
            progressBar.setVisibility(View.GONE);
            showAllUIElements(view);
            if (info != null) {
                changeToReadOnlyMode();
                personalInformation = info;
                updateViewWithData(view, info);
            }
            else {
                changeToEditMode();
                personalInformation = new PersonalInformation();
            }
        });

        setBtnClick(view.findViewById(R.id.btnTime), this::onTimePickerClick);
        setBtnClick(view.findViewById(R.id.btnEndDate), this::onDatePickerClick);

        MultiSelectionSpinner spinner = view.findViewById(R.id.multiSelectDay);
        spinner.setItems(getAllDayOfWeek());

        return view;
    }


    private List<String> getAllDayOfWeek() {
        List<String> list = new ArrayList<String>();
        list.add("ראשון");
        list.add("שני");
        list.add("שלישי");
        list.add("רביעי");
        list.add("חמישי");
        list.add("שישי");
        list.add("שבת");

        return list;
    }

    private void updateViewWithData(View v, PersonalInformation info) {
        ((EditText) v.findViewById(R.id.edWeightAchieved)).setText(Long.toString(info.getWeightToAchieve()));
        ((EditText) v.findViewById(R.id.edCurWeight)).setText(Long.toString(info.getCurrentWeight()));
        ((TextView) v.findViewById(R.id.txtEndDate)).setText(info.getDateEndOfTrain());
        ((TextView) v.findViewById(R.id.txtTime)).setText(info.getTime());
        ((MultiSelectionSpinner)getView().findViewById(R.id.multiSelectDay)).setSelection(info.dayOfWeek);
    }

    private void hideAllUIElements(View v) {
        LinearLayout layout = v.findViewById(R.id.llPersonalArea);

        layout.setVisibility(View.GONE);
    }

    private void showAllUIElements(View v) {
        LinearLayout layout = v.findViewById(R.id.llPersonalArea);

        layout.setVisibility(View.VISIBLE);
    }

    private void setBtnClick(Button btn, final Consumer<View> func){
        btn.setOnClickListener(view -> func.accept(view));
    }

    private void onSaveBtnClick(View v) {
        progressBar.setVisibility(View.VISIBLE);

        final EditText textWeightAchieve = getView().findViewById(R.id.edWeightAchieved);
        final EditText textCurWeight = getView().findViewById(R.id.edCurWeight);
        final MultiSelectionSpinner daysOfWeek = getView().findViewById(R.id.multiSelectDay);

        personalInformation.setUserId(this.userId);
        personalInformation.setWeightToAchieve(Long.parseLong( "" + textWeightAchieve.getText()));
        personalInformation.setCurrentWeight(Long.parseLong( "" + textCurWeight.getText()));
        personalInformation.setDayOfWeek(daysOfWeek.getSelectedStrings());

        PersonalAreaRepository.instance.savePersonalInformation(personalInformation);

        progressBar.setVisibility(View.GONE);

        if (isDateChanged)
            mListener.makeCalendarEvent(personalInformation.dateEndOfTrain);

        isDateChanged = false;
        this.changeToReadOnlyMode();
    }

    private void onEditBtnClick(View v) {
        this.changeToEditMode();
    }

    // TODO: Rename method, update argument and hook method into UI event
    private void onTimePickerClick(View v) {
            final TextView textView = getView().findViewById(R.id.txtTime);

            final Calendar c = Calendar.getInstance();

            int mHour = c.get(Calendar.HOUR); // current hour
            int mMinute = c.get(Calendar.MINUTE); // current minute

            // date picker dialog
            timePickerDialog = new TimePickerDialog(getActivity(),
                    (view, hour, minute) -> {
                        // set day of month , month and year value in the edit text
                        textView.setText(hour + ":" +
                        minute);
                        personalInformation.setHourTrain(hour);
                        personalInformation.setMinuteTrain(minute);

                    }
                    , mHour, mMinute, true);

        timePickerDialog.show();
    }

    private void onDatePickerClick(View v) {
        final TextView textView = getView().findViewById(R.id.txtEndDate);
        Calendar c = Calendar.getInstance();

        if (personalInformation.dateEndOfTrain != null){
            c.setTime(personalInformation.dateEndOfTrain);
        }

        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // set day of month , month and year value in the edit text
                    textView.setText(dayOfMonth + "/"
                            + (monthOfYear + 1) + "/" + year);
                    Calendar cal = Calendar.getInstance();
                    cal.set(year,monthOfYear,dayOfMonth);

                    personalInformation.setDateEndOfTrain(cal.getTime());
                    isDateChanged = true;
                }
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

        personalAreaViewModel = ViewModelProviders.of(this).get(PersonalAreaViewModel.class);
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
        void makeCalendarEvent(Date d);
    }
}
