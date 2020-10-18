package com.project.scambiolavoro.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;



import java.util.ArrayList;

public class DropDown extends AppCompatTextView implements View.OnClickListener {

    private ArrayList<String> options = new ArrayList<>();

    public DropDown(Context context) {
        super(context);
        initView();
    }

    public DropDown(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DropDown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setOnClickListener(this);
    }

    private PopupWindow popupWindowsort(Context context) {

        // initialize a pop up window type
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setWidth(this.getWidth());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line,
                options);
        // the drop down list is a list view
        ListView listViewSort = new ListView(context);

        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        // set on item selected
        listViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DropDown.this.setText(options.get(position));
                popupWindow.dismiss();
            }
        });

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        //popupWindow.setBackgroundDrawable(getResources().getColor());
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));

        // Working. Remember alpha is not in percentual like in the settings of layouts but in 255th
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(255, 245, 245, 245)));

        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            PopupWindow window = popupWindowsort(v.getContext());
            window.showAsDropDown(v, 0, 0);
        }
    }



    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }


   /* @Override
    public void setError(CharSequence error) {
        if (error == null) {
            setError(null, null);
        } else {
            Drawable dr = getContext().getDrawable(R.drawable.error);

            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
            setError(error, dr);
        }
    }*/
}
