package com.android.saadtechn.cabtutorial;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> listItems;
    private ArrayAdapter<String> listAdapter;
    private ListView listViewItems;
    private String list_item;
    private Object mActionMode;
    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(list_item);
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete_menu:
                    confirmDelete();

                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewItems = (ListView) findViewById(R.id.list_view);

        listItems = new ArrayList<>();

        listItems.add("Saturday");
        listItems.add("Sunday");
        listItems.add("Monday");
        listItems.add("Thursday");
        listItems.add("Wednesday");
        listItems.add("Tuesday");
        listItems.add("Friday");


        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);//(this, android.R.layout.simple_list_item_2,listItems);

        listViewItems.setAdapter(listAdapter);

        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                list_item = listItems.get(position);
                if (mActionMode != null) {
                    return false;
                }
                mActionMode = MainActivity.this.startActionMode(mActionModeCallBack);
                return true;
            }
        });
    }

    private void confirmDelete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to delete record");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                listAdapter.remove(list_item);
                listAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }

}
