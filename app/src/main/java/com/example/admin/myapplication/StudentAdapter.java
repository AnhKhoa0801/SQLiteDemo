package com.example.admin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 04/15.
 */

public class StudentAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Student> studentArrayList ;

    public StudentAdapter(Context context, int layout, ArrayList<Student> studentArrayList) {
        this.context = context;
        this.layout = layout;
        this.studentArrayList = studentArrayList;
    }

    @Override
    public int getCount() {
        return studentArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_student,null);
            viewHolder.txtName = view.findViewById(R.id.txtName);
            viewHolder.txtCode = view.findViewById(R.id.txtCode);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();

        }
        Student student = studentArrayList.get(i);
        viewHolder.txtName.setText(student.getName());
        viewHolder.txtCode.setText(student.getCode());
        return view;
    }
    private class ViewHolder
    {
        TextView txtName;
        TextView txtCode;
    }
}
