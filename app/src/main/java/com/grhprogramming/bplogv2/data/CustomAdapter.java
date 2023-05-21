package com.grhprogramming.bplogv2.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grhprogramming.bplogv2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<BP> {

    private ArrayList<BP> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtTime;
        TextView txtDate;
        TextView txtBloodpressure;
        TextView txtHeartrate;
    }

    public CustomAdapter(ArrayList<BP> data, Context context) {
        super(context, R.layout.list_row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BP dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.txtDate = convertView.findViewById(R.id.txt_date);
            viewHolder.txtTime = convertView.findViewById(R.id.txt_time);
            viewHolder.txtBloodpressure = convertView.findViewById(R.id.txt_bloodpressure);
            viewHolder.txtHeartrate = convertView.findViewById(R.id.txt_heartrate);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        String s_Time = String.format("%02d:%02d", dataModel.getHour(), dataModel.getMinute());
        String s_Date = String.format("%d/%d/%d", dataModel.getMonth(), dataModel.getDay(), dataModel.getYear());
        String s_BP = String.format("%d/%d", dataModel.getSystolic(), dataModel.getDystolic());
        String s_HR = String.format("%d bpm", dataModel.getHeartrate());

        viewHolder.txtDate.setText(s_Date);
        viewHolder.txtTime.setText(s_Time);
        viewHolder.txtHeartrate.setText(s_HR);
        viewHolder.txtBloodpressure.setText(s_BP);

        return convertView;
    }
}
