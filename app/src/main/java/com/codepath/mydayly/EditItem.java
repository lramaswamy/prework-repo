package com.codepath.mydayly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditItem extends AppCompatActivity {

    String action = "";
    String oldName = "";
    CalendarView calendarView;
    DateChangeListener dcl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        TodoItems itemEdited = getIntent().getParcelableExtra("editItem");

        if (itemEdited == null) {
            action = "add";

        } else {
            action = "edit";
            oldName = itemEdited.getItem_name();
        }
        EditText editItem = (EditText) findViewById(R.id.editItem);
        Spinner categorySpinner = (Spinner) findViewById(R.id.spinner2);
        Spinner prioritySpinner = (Spinner) findViewById(R.id.spinner);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dcl = new DateChangeListener(calendarView);
        calendarView.setOnDateChangeListener(dcl);

        if(action.equals("edit")) {
            editItem.setText(itemEdited.getItem_name());
            categorySpinner.setSelection(getIndex(categorySpinner, itemEdited.getCategory()));
            prioritySpinner.setSelection(getIndex(prioritySpinner, itemEdited.getPriority()));
            calendarView.setDate(getDate(itemEdited.getDue_by()));
        }

    }

    long getDate(String date) {

        String parts[] = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

       return calendar.getTimeInMillis();

    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }


    public void saveTask(View view) {
        EditText editItem = (EditText) findViewById(R.id.editItem);

        Spinner categorySpinner = (Spinner) findViewById(R.id.spinner2);
        String category = categorySpinner.getItemAtPosition(categorySpinner.getSelectedItemPosition()).toString();

        Spinner prioritySpinner = (Spinner) findViewById(R.id.spinner);
        String priority = prioritySpinner.getItemAtPosition(prioritySpinner.getSelectedItemPosition()).toString();

        TodoItems newItem = new TodoItems(category, editItem.getText().toString(), priority, dcl.returnNewDate());
        if(action.equals("add"))
            TodoItemsHelper.getInstance().addItemtoDb(newItem);
        else
            TodoItemsHelper.getInstance().upgradeItem(newItem, oldName);
        updateItems(newItem);
    }

    public void updateItems(TodoItems newItem) {
        Intent data = new Intent();
        data.putExtra("newItem", newItem);
        setResult(RESULT_OK, data);
        finish();
    }
    class DateChangeListener implements CalendarView.OnDateChangeListener {
        public String newDate;

        DateChangeListener(CalendarView view) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            newDate = sdf.format(new Date(view.getDate()));
        }
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month,
                                        int dayOfMonth) {

            newDate = dayOfMonth + "/" + month + "/" + year;
        }

        String returnNewDate() {
            return newDate;
        }
    }
}
