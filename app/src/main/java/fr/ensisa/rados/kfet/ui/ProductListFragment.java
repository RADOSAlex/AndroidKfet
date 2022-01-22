package fr.ensisa.rados.kfet.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fr.ensisa.rados.kfet.R;
import fr.ensisa.rados.kfet.model.Product;

public class ProductListFragment extends Fragment {

    private ProductListViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.product_list_fragment, container, false);
        FloatingActionButton fab = root.findViewById(R.id.add);
        fab.setOnClickListener(
                    view -> NavHostFragment.findNavController(this).navigate(R.id.action_productListFragment_to_productFragment)
        );
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        // TODO: Use the ViewModel
    }

}