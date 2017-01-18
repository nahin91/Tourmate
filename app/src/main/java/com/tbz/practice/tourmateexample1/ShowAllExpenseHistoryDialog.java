package com.tbz.practice.tourmateexample1;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by rahma on 1/12/2017.
 */

public class ShowAllExpenseHistoryDialog {
    private Context context;
    private Dialog dialog;
    private ListView expenseListLv;
    private TextView okayTv;
    private ExpenseHistoryListViewAdapater adapater;

    public ShowAllExpenseHistoryDialog(Context context) {
        this.context=context;

    }

    public void showAllExpenseHistoryDialog(ArrayList<Expenditures> expenseList)
    {
        dialog=new Dialog(this.context);
        dialog.setContentView(R.layout.layout_list_expence);

        expenseListLv=(ListView) dialog.findViewById(R.id.dialogshowallevent_budget_history_list_lv);

        adapater=new ExpenseHistoryListViewAdapater(context,expenseList);
        //expenseListLv.setAdapter(null);
        expenseListLv.setAdapter(adapater);



        okayTv=(TextView) dialog.findViewById(R.id.dialogshowallevent_okay_tv);
        dialog.setTitle("Expense History");
        okayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // EventDetailFragment.expenseList.clear();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
