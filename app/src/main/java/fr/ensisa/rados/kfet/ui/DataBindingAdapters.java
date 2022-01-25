package fr.ensisa.rados.kfet.ui;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ensisa.rados.kfet.Picture;
import fr.ensisa.rados.kfet.R;
import fr.ensisa.rados.kfet.model.ProductType;

public class DataBindingAdapters {

    @BindingAdapter("android:background")
    public static void setBackground (View view, ProductType productType) {
        int colorId = R.color.color_no;
        if (productType != null) {
            switch (productType) {
                case food: colorId= R.color.color_food;break;
                case drinks: colorId=R.color.color_drinks;break;



            }
        }
        int color = view.getResources().getColor(colorId, null);
        view.setBackgroundColor(color);
    }

    @BindingAdapter("level")
    public static void setLevel (ImageView view, ProductType productType) {
        if (productType == null) {
            view.setImageLevel(0);
        } else {
            view.setImageLevel(productType.ordinal());
        }
    }

    private static SimpleDateFormat output = null;

    @BindingAdapter("android:text")
    public static void setDate (TextView view, Date date) {
        String text = null;
        if (date == null) {
            text = view.getResources().getString(R.string.nodate);
        } else {
            if (output == null) {
                output = new SimpleDateFormat ("dd MMMM yyyy");
            }
            text = output.format(date);
        }
        view.setText(text);
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, int currentValue) {
        boolean todo = false;
        if (! todo && view.getText() == null) todo = true;
        if (! todo && view.getText().toString().isEmpty()) todo = true;
        if (! todo) {
            try {
                int inView = Integer.parseInt(view.getText().toString());
                if (inView != currentValue) todo = true;
            } catch (NumberFormatException e) {
                todo = true;
            }
        }
        if (todo) view.setText(Integer.toString(currentValue));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(EditText view) {
        if (view.getText() == null) return 0;
        if (view.getText().toString().isEmpty()) return 0;
        try {
            int inView = Integer.parseInt(view.getText().toString());
            return inView;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @BindingAdapter(value={"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void setSpinnerBinding(Spinner view, int newSelectedValue, final InverseBindingListener attrChange) {
        view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attrChange.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != -1) {
            String nextValue = Integer.toString(newSelectedValue);
            int index = ((ArrayAdapter<String>) view.getAdapter()).getPosition(nextValue);
            view.setSelection(index, true);
        }
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static int setSpinnerInverseBinding(Spinner view) {
        String selectedItem = (String) view.getSelectedItem();
        try {
            int inView = Integer.parseInt(selectedItem);
            return inView;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @BindingAdapter("bitmap")
    public static void setPicture (ImageView view, String picture) {
        if (picture == null) return;
        int w = view.getWidth();
        int h = view.getHeight();
        Bitmap bitmap = Picture.getBitmapFromUri(view.getContext(), picture, w, h);
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("error")
    public static void setErrorMessage (TextInputLayout view, int messageId) {
        // messageID can be
        // 0 -> no error && nothing to say
        // >0 -> error && a message negative to display
        // <0 -> no error && a message positive to display
        String message = null;
        if (messageId > 0) {
            message = view.getResources().getString(messageId);
        } else if (messageId < 0) {
            message = view.getResources().getString(-messageId);
        }
        view.setError(message);
        view.setErrorEnabled(message != null);
    }

}