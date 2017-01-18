package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by USER on 17-Jan-17.
 */

public class ExpenseAdapter extends ArrayAdapter{
    private Expenditures expenditures;
    private ArrayList<Expenditures> expenceList;
    private Context context;



    public ExpenseAdapter(Context context, ArrayList<Expenditures> expenceList) {
        super(context,R.layout.layout_list_expence_style,expenceList);
        this.expenceList=expenceList;
        this.context=context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_list_expence_style,parent,false);

            viewHolder=new ViewHolder();

            viewHolder.titleTv=(TextView)view.findViewById(R.id.showExpTitleTv);
            viewHolder.dateTimeTv=(TextView)view.findViewById(R.id.showExpTimeTv);
            viewHolder.costTv=(TextView)view.findViewById(R.id.showExpCostTv);

            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();

        }

        expenditures =expenceList.get(position);
        viewHolder.titleTv.setText(expenditures.getTitle());
        viewHolder.dateTimeTv.setText(expenditures.getDate());
        viewHolder.costTv.setText(expenditures.getCost());


        return view;
    }

    private static class ViewHolder{
        TextView titleTv;
        TextView dateTimeTv;
        TextView costTv;

    }
}
