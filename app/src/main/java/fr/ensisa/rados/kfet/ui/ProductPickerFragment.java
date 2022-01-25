package fr.ensisa.rados.kfet.ui;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.List;

import fr.ensisa.rados.kfet.R;
import fr.ensisa.rados.kfet.database.AppDatabase;
import fr.ensisa.rados.kfet.databinding.ProductItemBinding;
import fr.ensisa.rados.kfet.databinding.ProductPickerFragmentBinding;
import fr.ensisa.rados.kfet.model.Product;

public class PersonPickerFragment extends DialogFragment {

    private ProductPickerFragmentBinding binding;
    private PersonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProductPickerFragmentBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.list.getContext(), RecyclerView.VERTICAL, false));
        adapter = new PersonAdapter ();
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase.get().getProductDao().getAll().observe(getViewLifecycleOwner(), p -> adapter.setCollection(p));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point point = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(point);
        window.setLayout((int) (point.x * 0.75), (int) (point.y * 0.75));
        window.setGravity (Gravity.CENTER);
        super.onResume();
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

        private List<Product> collection;

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ProductItemBinding binding;
            public ViewHolder(@NonNull ProductItemBinding binding) {
                super(binding.getRoot());
                binding.getRoot().setOnClickListener(this);
                this.binding = binding;
            }

            @Override
            public void onClick(View v) {
                Product product = collection.get(getAdapterPosition());
                ProductPickerFragmentBinding arg = ProductPickerFragmentBinding.fromBundle(getArguments());
                String requestKey = arg.getRequestKey();
                Bundle result = new Bundle();
                result.putLong(requestKey, product.getPid());
                getParentFragmentManager().setFragmentResult(requestKey, result);
                dismiss();
            }
        }

        @NonNull
        @Override
        public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.product_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            PersonAdapter.ViewHolder vh = new PersonAdapter.ViewHolder(binding);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder holder, int position) {
            Person p = collection.get(position);
            holder.binding.setP(p);
        }

        @Override
        public int getItemCount() {
            return collection == null ? 0 : collection.size();
        }

        public void setCollection(List<Person> collection) {
            this.collection = collection;
            notifyDataSetChanged();
        }
    }

}