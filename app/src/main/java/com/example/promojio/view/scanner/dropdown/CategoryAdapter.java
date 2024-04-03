package com.example.promojio.view.scanner.dropdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<String> {

    private final List<String> categories;
    private final int viewResourceId;

    public CategoryAdapter(Context context, int viewResourceId, List<String> categories) {
        super(context, viewResourceId, categories);
        this.viewResourceId = viewResourceId;
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, null);
        }
        String category = this.categories.get(position);
        if (category != null) {
            TextView categoryLabel = (TextView) view.findViewById(android.R.id.text1);
            if (categoryLabel != null) {
                categoryLabel.setText(category);
            }
        }
        return view;
    }
}
