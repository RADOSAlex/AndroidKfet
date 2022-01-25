package fr.ensisa.rados.kfet.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fr.ensisa.rados.kfet.R;
import fr.ensisa.rados.kfet.database.AppDatabase;
import fr.ensisa.rados.kfet.database.FeedDatabase;
import fr.ensisa.rados.kfet.databinding.ProductItemBinding;
import fr.ensisa.rados.kfet.model.Product;

public class ProductListFragment extends Fragment {

    private ProductListViewModel mViewModel;
    private ProductAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.product_list_fragment, container, false);
        FloatingActionButton fab = root.findViewById(R.id.add);
        fab.setOnClickListener(
                    view -> NavHostFragment.findNavController(this).navigate(R.id.action_productListFragment_to_productFragment)
        );
        RecyclerView list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
//        ItemTouchHelper touchehelper = new ItemTouchHelper(
//                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//                    @Override
//                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                        return false;
//                    }
//
////                    @Override
////                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
////                        int position = viewHolder.getLayoutPosition();
////                        Product product = adapter.collection.get(position);
////                        switch (direction) {
////                            case ItemTouchHelper.LEFT:
////                                mViewModel.deleteProduct (product);
////                                break;
////                            case ItemTouchHelper.RIGHT:
////                                ProductListFragmentDirections.ActionProductListFragmentToProductFragment action = ProductListFragmentDirections.actionProductListFragmentToProductFragment();
////                                action.setId(product.getPid());
////                                NavHostFragment.findNavController(ProductListFragment.this).navigate(action);
////                                break;
////                        }
////                    }
//                }
//        );
//        touchehelper.attachToRecyclerView(list);
        adapter=new ProductAdapter();
        list.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        mViewModel.setProductDao(AppDatabase.get().getProductDao());
        mViewModel.getProducts().observe(getViewLifecycleOwner(), products -> adapter.setCollection(products));
    }
    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

        private List<Product> collection;

        private class ViewHolder extends RecyclerView.ViewHolder {
            ProductItemBinding binding;
            public ViewHolder(@NonNull ProductItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        @NonNull
        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.product_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            ViewHolder vh = new ViewHolder(binding);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
            Product p = collection.get(position);
            holder.binding.setP(p);
        }

        @Override
        public int getItemCount() {
            return collection == null ? 0 : collection.size();
        }

        public void setCollection(List<Product> collection) {
            this.collection = collection;
            notifyDataSetChanged();
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.products_menu, menu);
    }

    private boolean doPopulate (){
        FeedDatabase feeder = new FeedDatabase();
        feeder.feed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.populate: return doPopulate();
        }
        return super.onOptionsItemSelected(item);
    }

}