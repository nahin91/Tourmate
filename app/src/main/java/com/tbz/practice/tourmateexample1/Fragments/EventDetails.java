package com.tbz.practice.tourmateexample1.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbz.practice.tourmateexample1.EventAdaptor;
import com.tbz.practice.tourmateexample1.EventManager;
import com.tbz.practice.tourmateexample1.Events;
import com.tbz.practice.tourmateexample1.ExpenditureManager;
import com.tbz.practice.tourmateexample1.Expenditures;
import com.tbz.practice.tourmateexample1.ExpenseAdapter;
import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.ShowAllExpenseHistoryDialog;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by USER on 30-Dec-16.
 */

public class EventDetails extends Fragment {
    ListView showExpListview;
    TextView showEventNameTv;
    TextView showPercentTv;
    TextView showBudgetTv;
    TextView showDateTv;
    TextView showStartPLacetTv;
    TextView showDestinationTv;
    Button btnExpenditure;
    Button btnDeleteEvent;
    Button btnEditEvent;

    ProgressBar progressBar;
    EditText expTitleEt;
    EditText expAmmountEt;
    EditText newBudgetEt;
    EditText editDateTv;

    String eventName;
    String startPlace;
    String destination;
    String date;
    int eventId;

    String st;

    String budgett;
    int budget ;
    int newBudget;
    int reducedBudget;
    int expenditure;
    int expTemp;
    int consumed;

    Events events;
    EventManager manager;

    Expenditures expenditures;
    ExpenditureManager expManager;
    String dateTime;
    ArrayList<Expenditures> expList;
    ArrayList<Expenditures> expendituresArrayList;
    ExpenseAdapter expenseAdapter;

    private java.util.Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener datelistener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details,container,false);

        expendituresArrayList=new ArrayList<>();
        showEventNameTv= (TextView) view.findViewById(R.id.detailsEventNameTv);
        showPercentTv= (TextView) view.findViewById(R.id.detailsPercentageTv);
        showBudgetTv= (TextView) view.findViewById(R.id.detailsBudgetTv);
        showDateTv= (TextView) view.findViewById(R.id.showDetailsDepartdate);
        showStartPLacetTv= (TextView) view.findViewById(R.id.showDetailsStartPlace);
        showDestinationTv= (TextView) view.findViewById(R.id.showDetailsDestination);
        btnExpenditure= (Button) view.findViewById(R.id.expenditureBt);
        btnDeleteEvent = (Button) view.findViewById(R.id.deleteEvent);
        btnEditEvent = (Button) view.findViewById(R.id.editEvent);

        myCalendar = java.util.Calendar.getInstance();
        myCalendar.add(java.util.Calendar.DATE, -1);



        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        manager=new EventManager(getContext().getApplicationContext());
        expManager=new ExpenditureManager(getContext());
        expList = new ArrayList<>();

        eventName = getArguments().getString("EVENT_NAME");
        showEventNameTv.setText(eventName);
        startPlace = getArguments().getString("STARTING_PLACE");
        showStartPLacetTv.setText(startPlace);
        destination = getArguments().getString("DESTINATION");
        showDestinationTv.setText(destination);
        date = getArguments().getString("DATE");
        showDateTv.setText(date);
        eventId = getArguments().getInt("EVENT_ID");

        budgett = getArguments().getString("BUDGET");
        budget = Integer.parseInt(budgett);
        showBudgetTv.setText(budget+"");

        expList = expManager.getExpenditures(eventId);

        newBudget=0;
        expenditure = 0;

        expTemp=0;
        consumed=0;

        for (int i=0; i<expList.size(); i++){
            expenditures =expList.get(i);
            if (expList!=null){
                //Toast.makeText(getContext(), "expList not null", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), expenditures.getTitle() + " : " + expenditures.getCost(), Toast.LENGTH_SHORT).show();
                expenditure += Integer.valueOf(expenditures.getCost().toString());
                //Toast.makeText(getContext(), "first expenditure loaded value = " + expenditure, Toast.LENGTH_SHORT).show();
            }
        }
        if(budget >= expenditure){
            reducedBudget = budget-expenditure;
            Toast.makeText(getContext(), "reduce = budget - exp = "+reducedBudget, Toast.LENGTH_SHORT).show();
        }else reducedBudget=0;
        calculateProgress();

        btnExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenditureAction();
            }
        });
        btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deletedEvent = manager.deleteEvent(eventId);
                boolean deletedExpenditure = expManager.deleteExpenditures(eventId);
                if(deletedEvent)
                    Toast.makeText(getContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getContext(), "event not deleted", Toast.LENGTH_SHORT).show();
                if(deletedExpenditure)
                    Toast.makeText(getContext(), "Expenditure deleted", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getContext(), "Expenditure not deleted", Toast.LENGTH_SHORT).show();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                EventListFragment eventListFragment = new EventListFragment();
                ft.replace(R.id.eventFragmentContainer,eventListFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                ft.commit();
            }
        });
        btnEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_edit_event);
                dialog.setTitle("Edit Event");
                //TODO: action of EXIT Button
                Button btnEditCancelt = (Button) dialog.findViewById(R.id.btn_EditCancleEvent);
                btnEditCancelt.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btnEditSubmit = (Button) dialog.findViewById(R.id.btn_EditEvent);
                btnEditSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editNameTv= (EditText) dialog.findViewById(R.id.input_EditEventName);
                        EditText editStartTv= (EditText) dialog.findViewById(R.id.input_EditStartPlace);
                        EditText editDestinationTv= (EditText) dialog.findViewById(R.id.input_EditDestination);
                        editDateTv= (EditText) dialog.findViewById(R.id.input_EditDate);
                        EditText editBudgetTv= (EditText) dialog.findViewById(R.id.input_EditBudget);

                        editNameTv.setText(getArguments().getString("EVENT_NAME"));
                        editStartTv.setText(getArguments().getString("STARTING_PLACE"));
                        editDestinationTv.setText(getArguments().getString("DESTINATION"));
                        editDateTv.setText(getArguments().getString("DATE"));
                        editBudgetTv.setText(getArguments().getString("BUDGET"));

                        datelistener = new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                myCalendar.set(java.util.Calendar.YEAR, year);
                                myCalendar.set(java.util.Calendar.MONTH, monthOfYear);
                                myCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);

                                updateLabel();
                            }

                        };
                        editDateTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "editDate is pressd", Toast.LENGTH_SHORT).show();
                                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), datelistener,
                                        myCalendar.get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                                        myCalendar.get(java.util.Calendar.DAY_OF_MONTH));
                                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                                datePickerDialog.show();
                            }
                        });


                        String editName = editNameTv.getText().toString();
                        String editStart = editStartTv.getText().toString();
                        String editDestination = editDestinationTv.getText().toString();
                        String editDate = editDateTv.getText().toString();
                        String editBudget = editBudgetTv.getText().toString();

                        Events eventss= new Events(editName,editStart,editDestination,editDate,editBudget);
                        boolean updated=manager.updateEvent(eventId,eventss);
                        if (updated) Toast.makeText(getContext(), "event update", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Edit is not successfull", Toast.LENGTH_SHORT).show();


                    }
                });
                dialog.show();
            }
        });

        return view;
    }

    private void updateLabel() {
        String myFormat = "EEE, d MMM yyyy"; //In which you need put here
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, Locale.US);

        editDateTv.setText(sdf.format(myCalendar.getTime()));
    }

    private void ExpenditureAction() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_expenditure);
        dialog.setTitle("Expenditure");
        //TODO: action of EXIT Button
        Button btnExit = (Button) dialog.findViewById(R.id.btnExitExpTv);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //TODO: done
//______________________________________________________________________________________
        //TODO: action of ADD Expenditure
        Button btnAddExp = (Button) dialog.findViewById(R.id.btnAddExpTv);
        btnAddExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                final Dialog dlg = new Dialog(getContext());
                dlg.setTitle("Add Expense");
                dlg.setContentView(R.layout.layout_add_expence);
                expTitleEt = (EditText) dlg.findViewById(R.id.expTitleEt);
                expAmmountEt = (EditText) dlg.findViewById(R.id.expAmmountEt);
                dlg.show();
                Button addExp = (Button) dlg.findViewById(R.id.btnAddExpence);
                addExp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateProgressBar();

                        ShowProgressBar();
                        dlg.dismiss();
                    }
                });


            }
        });
        //TODO: done
//______________________________________________________________________________________
        //TODO: action of ADD New Budget
        Button btnAddNewBudget = (Button) dialog.findViewById(R.id.btnAddBudgetTv);
        btnAddNewBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Dialog dlg = new Dialog(getContext());
                dlg.setTitle("Add Budget");
                dlg.setContentView(R.layout.layout_add_budget);
                newBudgetEt= (EditText) dlg.findViewById(R.id.newBudgetEt);

                Button addBudget = (Button) dlg.findViewById(R.id.btnAddBudget);
                addBudget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newBudget = Integer.valueOf(newBudgetEt.getText().toString());
                        budget = budget+newBudget;
                        Toast.makeText(getContext(), "addNewBudget is "+budget, Toast.LENGTH_SHORT).show();

                        events= new Events(String.valueOf(budget));
                        boolean updated=manager.updateBudget(eventId,events);
                        if(updated){
                            Toast.makeText(getContext(),"updated & new budget is "+events.getBudget(), Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getContext(), "successfully failed !", Toast.LENGTH_SHORT).show();

                        //budget=Integer.parseInt(budgett);
                        //showBudgetTv.setText(String.valueOf(budget));
                        Toast.makeText(getContext(), reducedBudget + " + " + newBudget + " = ", Toast.LENGTH_SHORT).show();

                        reducedBudget=reducedBudget+newBudget;
                        Toast.makeText(getContext(), reducedBudget + "", Toast.LENGTH_SHORT).show();
                        //tempReducedBudget=reducedBudget;
                        consumed = (expenditure * 100) / budget;

                        progressBar.setProgress(consumed);
                        showPercentTv.setText(consumed + "%");
                        showBudgetTv.setText("("+expenditure+"/"+budget+")");
                        calculateProgress();
                    }
                });
                dlg.show();
            }
        });
        //TODO: done
//__________________________________________________________________________________________________
        //TODO: action of SHOW Expenditure
        Button btnShowExpence = (Button) dialog.findViewById(R.id.btnShowExpTv);
        btnShowExpence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Dialog dlg = new Dialog(getContext());
                dlg.setContentView(R.layout.layout_list_expence);
                dlg.setTitle("Expenditure History");


               /* expenseAdapter=new ExpenseAdapter(getContext(),expList);
                showExpListview.setAdapter(expenseAdapter);*/
                ShowAllExpenseHistoryDialog showAllExpenseHistoryDialog=new ShowAllExpenseHistoryDialog(getContext());
                showAllExpenseHistoryDialog.showAllExpenseHistoryDialog(expList);


                dlg.show();

            }
        });


        //TODO: done

        dialog.show();
    }


    private void ShowProgressBar() {

        if(reducedBudget !=0 && reducedBudget > 0) {


            st=expAmmountEt.getText().toString();

            String expTitle = expTitleEt.getText().toString();

            DateFormat df = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                dateTime = df.format(Calendar.getInstance().getTime());
            }

            expenditures=new Expenditures(expTitle,st,dateTime,eventId);
            boolean inserted = expManager.addExpenditure(expenditures);
            if(inserted)
                Toast.makeText(getContext(), "expenditure saved", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "expenditure not saved", Toast.LENGTH_SHORT).show();


            expenditure += Integer.valueOf(st);

            calculateProgress();

        }else Toast.makeText(getContext(), "Sorry! No Ammount is remainnig.", Toast.LENGTH_SHORT).show();
    }

    private void calculateProgress() {
        if(expenditure >= 0) {
            Toast.makeText(getContext(), "before calculation expenditure = "+expenditure, Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), "the value of  reducedbudget before calculation = "+reducedBudget, Toast.LENGTH_LONG).show();
            int lastExp = 0;
            if(st != null ){
                lastExp=Integer.valueOf(st);
                if(reducedBudget > lastExp){
                    reducedBudget = reducedBudget - lastExp;
                }else reducedBudget=0;

            }/*else
                reducedBudget = reducedBudget - lastExp;*/
            st=null;

            Toast.makeText(getContext(), "after reduce - lastExp = "+reducedBudget, Toast.LENGTH_LONG).show();


            //tempReducedBudget = reducedBudget;
            //expTemp = expTemp + expenditure;


            consumed = (expenditure * 100) / budget;
            progressBar.setProgress(consumed);

            if(consumed > 100){
                int extraExpence = expenditure-budget;

                showPercentTv.setText("100%");

                Toast.makeText(getContext(), "Your Expence has exceeded your Budget!! Your Extra expence is "+(expenditure-budget), Toast.LENGTH_LONG).show();
            }else{
                showPercentTv.setText(consumed + "%");
                showBudgetTv.setText("("+expenditure+"/"+budget+")");
            }
            Toast.makeText(getContext(), "the value of  reducedbudget AFTER CALCULATION = "+reducedBudget, Toast.LENGTH_LONG).show();

        }else Toast.makeText(getContext(), "please enter some ammount", Toast.LENGTH_SHORT).show();
    }

    private void CreateProgressBar() {
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.showContextMenu();
        progressBar.setScaleY(5f);
    }
}
