package tmrapps.getinshapeapp.Exercise;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import tmrapps.getinshapeapp.Category.CategoryFragment;
import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

/**
 * This is the exercise activity - we can manage (add and watch) exercises here.
 */
public class ExerciseActivity extends AppCompatActivity implements ExerciseFragment.OnFragmentInteractionListener,
        CategoryFragment.OnFragmentInteractionListener, ExerciseDetailsFragment.OnFragmentInteractionListener,
        CreateExerciseFragment.OnFragmentInteractionListener {
    private RoleType role;

    private Fragment categoryFrag;
    private Fragment exerciseListFrag;
    private Fragment exerciseDetailsFrag;
    private Fragment createExerciseFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Get permissions from the main activity
        this.role = RoleType.valueOf(getIntent().getStringExtra("ROLE_TYPE"));

        // First show the category list
        this.onShowCategory();
    }

    /**
     * Opens the category fragment - it has a list of all the categories of exercises
     */
    public void onShowCategory(){
        this.categoryFrag = CategoryFragment.newInstance(this.role);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.categoryFrag);
        transaction.commit();
    }

    @Override
    public void onShowExercisesOfCategory(String categoryId, String categoryName) {
        this.exerciseListFrag = ExerciseFragment.newInstance(this.role.name(), categoryId, categoryName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.exerciseListFrag);
        transaction.addToBackStack(this.categoryFrag.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onShowExerciseDetails(String id) {
        this.exerciseDetailsFrag = ExerciseDetailsFragment.newInstance(id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.exerciseDetailsFrag);
        transaction.addToBackStack(this.exerciseListFrag.getClass().getName());
        transaction.commit();
    }

    /**
     * Show the exercises in category fragment
     * @param categoryId
     * @param categoryName
     */
    @Override
    public void onShowCreateExercise(String categoryId, String categoryName) {
        this.createExerciseFrag = CreateExerciseFragment.newInstance(categoryId, categoryName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.createExerciseFrag);
        transaction.addToBackStack(this.exerciseListFrag.getClass().getName());
        transaction.commit();
    }

    /**
     * This function is called after a new exercise was created
     * @param exercise
     * @param exerciseImage
     * @param categoryName
     */
    @Override
    public void onCreateExercise(Exercise exercise, Bitmap exerciseImage, String categoryName) {
        this.exerciseListFrag = ExerciseFragment.newInstance(this.role.name(), exercise.getCategoryId(), categoryName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.exerciseListFrag);
        getSupportFragmentManager().popBackStack();
        //transaction.remove(this.createExerciseFrag);
        //transaction.add(R.id.exerciseContent, this.exerciseListFrag);
        //transaction.addToBackStack(this.categoryFrag.getClass().getName());
        transaction.commit();
    }
}
