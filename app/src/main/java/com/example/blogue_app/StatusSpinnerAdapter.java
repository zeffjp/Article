package com.example.blogue_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import java.util.List;

public class StatusSpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<StatusItem> statusItems;

    public StatusSpinnerAdapter(Context context, List<StatusItem> statusItems) {
        this.context = context;
        this.statusItems = statusItems;
    }

    @Override
    public int getCount() {
        return statusItems.size();
    }

    @Override
    public Object getItem(int position) {
        return statusItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item_status, parent, false);
            holder = new ViewHolder();
            holder.colorView = convertView.findViewById(R.id.statusColorView);
            holder.statusTextView = convertView.findViewById(R.id.statusTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StatusItem currentItem = (StatusItem) getItem(position);

        holder.colorView.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_shape));
        holder.colorView.getBackground().setTint(currentItem.getColor()); // Assurez-vous que le fond est coloré
        holder.statusTextView.setText(currentItem.getStatus());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    static class ViewHolder {
        View colorView;
        TextView statusTextView;
    }
}
