package tmrapps.getinshapeapp.Category;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.Category.Model.AddCategoryDialog;
import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

public class CategoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private CategoryViewModel categoryViewModel;

    private CategoryFragment.CategoryAdapter categoriesAdapter;

    private ListView list;

    private static final String ARG_PARAM = "roleType";

    private RoleType role;

    private ProgressBar progressBar;

    private View categoryView;

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
        this.categoryView = inflater.inflate(R.layout.fragment_category, container, false);

        list = categoryView.findViewById(R.id.categoryList);
        categoriesAdapter = new CategoryFragment.CategoryAdapter(this);
        list.setAdapter(categoriesAdapter);

        progressBar = categoryView.findViewById(R.id.categoryProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        FloatingActionButton addCategoryBtn = categoryView.findViewById(R.id.addCategoryBtn);

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
        return categoryView;
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
            this.categoryViewModel.addCategory(categoryName);
        }
    }

    public void showCategory(String categoryId) {
        mListener.onShowExercisesOfCategory(categoryId);
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

        this.categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        this.categoryViewModel.getCategories().observe(this, (categories) -> {
            this.categoriesAdapter.data = categories;
            this.categoriesAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onShowExercisesOfCategory(String categoryId);
    }

    class CategoryAdapter extends BaseAdapter {

        CategoryFragment exerciseFrag;
        List<Category> data = new LinkedList<>();

        public CategoryAdapter(CategoryFragment fragment) {
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
