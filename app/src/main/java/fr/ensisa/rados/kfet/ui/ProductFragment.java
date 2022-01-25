package fr.ensisa.rados.kfet.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Calendar;

import fr.ensisa.rados.kfet.Permissions;
import fr.ensisa.rados.kfet.Picture;
import fr.ensisa.rados.kfet.R;
import fr.ensisa.rados.kfet.database.AppDatabase;
import fr.ensisa.rados.kfet.databinding.ProductFragmentBinding;

public class ProductFragment extends Fragment {

    static private final String TAG="ProductFragment";
    private ProductViewModel mViewModel;
    private ProductFragmentBinding binding;
    private Picture picture;

    private ActivityResultLauncher<Uri> capturePhoto = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (! result) return;
                    if (picture == null) picture = new Picture();
                    picture.addPictureToGallery(getContext());
                    mViewModel.setPicture (picture.getPhotoFile().getAbsolutePath());
                }
            }
    );
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false);
        binding.setLifecycleOwner(this);
        binding.setTakePicture(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picture == null) picture = new Picture();
                picture.createPhotoFile(getContext());
                capturePhoto.launch(picture.getPhotoURI(getContext()));
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Permissions.getInstance().requestPermissions(view,
                new String [] { Manifest.permission.WRITE_EXTERNAL_STORAGE }
        );
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mViewModel.setProductDao(AppDatabase.get().getProductDao());
        mViewModel.getProductValidator().observe(getViewLifecycleOwner(), v -> { getActivity().invalidateOptionsMenu(); });
        long id = ProductFragmentArgs.fromBundle(getArguments()).getId();
        if (id == 0) {
            mViewModel.createProduct();
        } else {
            mViewModel.setId(id);
        }
        binding.setVm(mViewModel);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.product_menu, menu);
    }
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.save).setEnabled(mViewModel.getProductValidator().getValue());
    }
    private boolean doSave() {
        mViewModel.save (mViewModel.getProduct());
        Navigation.findNavController(getView()).popBackStack();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: return doSave();
        }
        return super.onOptionsItemSelected(item);
    }

}

