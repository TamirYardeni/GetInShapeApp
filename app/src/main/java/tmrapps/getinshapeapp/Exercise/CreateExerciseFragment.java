package tmrapps.getinshapeapp.Exercise;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.R;

@RuntimePermissions
public class CreateExerciseFragment extends Fragment {

    private static final String ARG_PARAM_CATEGORY = "categoryId";

    public final static int PICK_PHOTO_CODE = 1046;

    private String categoryId;

    private String categoryName;


    private OnFragmentInteractionListener mListener;

    private ImageView imageExample;

    private Context context;

    private TextView textCategoryName;

    private EditText textExerciseName;

    private EditText textExerciseData;

    private EditText textExerciseNote;

    private Bitmap selectedImage;

    private ExerciseViewModel exerciseViewModel;

    public CreateExerciseFragment() {
        // Required empty public constructor
    }

    public static CreateExerciseFragment newInstance(String param1, String param2) {
        CreateExerciseFragment fragment = new CreateExerciseFragment();

        // Insert category id to bundle when creating new instance of this fragment
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_CATEGORY, param1);
        args.putString("categoryName", param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString(ARG_PARAM_CATEGORY);
            categoryName = getArguments().getString("categoryName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.craeteExerciseHeader);
        this.textCategoryName = view.findViewById(R.id.textCategoryName);
        this.textCategoryName.setText(this.categoryName);

        this.textExerciseName = view.findViewById(R.id.textNewExerciseName);
        this.textExerciseData = view.findViewById(R.id.textNewExerciseData);
        this.textExerciseNote = view.findViewById(R.id.textNewExerciseNotes);

        Button imageBtn = view.findViewById(R.id.chooseImgBtn);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromGallery();
            }
        });

        // Add event listener to "Add exercise" button
        Button createNewExercise = view.findViewById(R.id.createNewExerciseBtn);
        createNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExercise();
            }
        });

        // Get the image view - (Used for upload image from th e gallery)
        this.imageExample = view.findViewById(R.id.imgExmpl);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        this.exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
    }

    /**
     * Called when an image is selected from the phone gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data != null) {
            Uri photoUri = data.getData();
            // Do something with the photo based on Uri
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                //then create a copy of bitmap bmp1 into bmp2
                Bitmap bmpForResize = selectedImage.copy(selectedImage.getConfig(), true);

                Bitmap resizedBitmap = getResizedBitmap(bmpForResize, 200,200);
                // Load the selected image into a preview
                this.imageExample.setImageBitmap(resizedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        /*bm.recycle();*/
        return resizedBitmap;
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void getFileFromGallery() {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        CreateExerciseFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void createExercise() {
        Exercise exercise = new Exercise();

        // Insert data to the exercise


        if(!this.textExerciseName.getText().toString().equals("") &&
                !this.textExerciseData.getText().toString().equals("") &&
                !this.textExerciseNote.getText().toString().equals("") &&
                this.selectedImage != null

                ) {
            exercise.setName(this.textExerciseName.getText().toString());
            exercise.setData(this.textExerciseData.getText().toString());
            exercise.setNote(this.textExerciseNote.getText().toString());
            exercise.setCategoryId(this.categoryId);

            this.exerciseViewModel.addExercise(exercise, this.selectedImage);

            // Fire the event to the exercise activity
            mListener.onCreateExercise(exercise, this.selectedImage, this.categoryName);

            Toast.makeText(getActivity(), (String)"התרגיל נשמר בהצלחה",
                    Toast.LENGTH_LONG).show();
        }
        else {
            // Notify the user that he need to enter all the fields
            Toast.makeText(getActivity(), (String)"נא למאלות את כל השדות",
                    Toast.LENGTH_LONG).show();
        }
    }

    public interface OnFragmentInteractionListener {
        void onCreateExercise(Exercise exercise, Bitmap exerciseImage, String categoryName);
    }
}
