package tmrapps.getinshapeapp.Category;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.Category.Model.AddCategoryDialog;
import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

public class CategoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private CategoryFragment.CategoryAdapter categoriesAdapter;

    private ListView list;

    private static final String ARG_PARAM = "roleType";

    private RoleType role;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(RoleType roleType) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, roleType.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.role = RoleType.valueOf(getArguments().getString(ARG_PARAM));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View exerciseView = inflater.inflate(R.layout.fragment_exercise_admin, container, false);

        list = exerciseView.findViewById(R.id.categoryListAdmin);
        categoriesAdapter = new CategoryFragment.CategoryAdapter(this);
        list.setAdapter(categoriesAdapter);

        FloatingActionButton addCategoryBtn = exerciseView.findViewById(R.id.addCategoryBtn);

        // If the user is admin - allow him to add categories.
        // In case he is a regular user - hide the "add category" button.
        if (this.role == RoleType.ADMIN) {
            addCategoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddCategoryDialog newFragment = new
                            AddCategoryDialog();
                    newFragment.setHandlerListener(new AddCategoryDialog.OnCategoryDialogInteractionListener() {
                        @Override
                        public void onCategoryAdded(String categoryName) {
                            addCategory(categoryName);
                        }
                    });

                    newFragment.show(CategoryFragment.this.getActivity().getSupportFragmentManager(),
                            "TAG");
                }
            });
        } else {
            addCategoryBtn.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return exerciseView;
    }

    private void addCategory(String categoryName) {
        boolean isExist = false;
        for (int counter = 0; counter < this.categoriesAdapter.data.size(); counter++) {
            if (this.categoriesAdapter.data.get(counter).getName().equals(categoryName)) {
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            this.categoriesAdapter.data.add(new Category(categoryName));
            this.list.setAdapter(categoriesAdapter);
            //TODO: notifyItemInserted
            //this.categoriesAdapter.notifyItemInserted(this.categoriesAdapter.data.size()-1);
        }
    }

    public void showCategory(String categoryId) {
        mListener.onShowCategory(categoryId);
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

    public interface OnFragmentInteractionListener {
        void onShowCategory(String categoryId);
    }

    class CategoryAdapter extends BaseAdapter {

        CategoryFragment exerciseFrag;
        List<Category> data = new LinkedList<>();

        public CategoryAdapter(CategoryFragment fragment) {
            data.add(new Category("ידיים"));
            data.add(new Category("בטן"));
            data.add(new Category("גב"));
            data.add(new Category("ישבן"));
            data.add(new Category("חזה"));

            /*// Write to local db
            Category catForDB = new Category("ידיים");
            catForDB.setId("0");
            DBexercise.getInstance().addCategory(catForDB);*/

            // write to firebase
            Category catForDB = new Category("ידיים");
            //FirebaseExercise.getInstance().addCategory(catForDB);
            /*List<Category> categoriesFromDB = DBexercise.getInstance().getAllCategories();

            for (Category cat :categoriesFromDB) {
                Log.d("TAG", "read category from db" + cat.getName());
            }*/

            this.exerciseFrag = fragment;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Category getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Category cat = data.get(i);

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.category_row, null);

                TextView categoryName = view.findViewById(R.id.categoryTxt);
                categoryName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index =(int)view.getTag();
                        exerciseFrag.showCategory(data.get(index).getId());
                    }
                });
            }

            TextView categoryName = view.findViewById(R.id.categoryTxt);
            categoryName.setText(cat.getName());
            categoryName.setTag(i);

            return view;
        }
    }
}
