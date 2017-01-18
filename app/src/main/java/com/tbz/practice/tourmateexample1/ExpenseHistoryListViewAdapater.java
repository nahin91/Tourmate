package com.tbz.practice.tourmateexample1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by rahma on 1/12/2017.
 */

public class ExpenseHistoryListViewAdapater extends ArrayAdapter<Expenditures>{
    private Context context;
    private ArrayList<Expenditures> expenseList;
    private TextView expenseNameTv;
    private TextView expenseTv;
    private TextView dateTv;
    private Expenditures expense;

    public ExpenseHistoryListViewAdapater(Context context, ArrayList<Expenditures> expenseList) {
        super(context, R.layout.layout_list_expence_style,expenseList);
        this.context=context;
        this.expenseList=expenseList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_list_expence_style,parent,false);
        expenseNameTv = (TextView)convertView.findViewById(R.id.showExpTitleTv);
        expenseTv = (TextView)convertView.findViewById(R.id.showExpCostTv);
        dateTv = (TextView)convertView.findViewById(R.id.showExpTimeTv);

        expense=expenseList.get(position);

        expenseNameTv.setText(expense.getTitle());
        expenseTv.setText(String.valueOf(expense.getCost()));
        dateTv.setText(String.valueOf(expense.getDate()));
        return convertView;
    }
}
