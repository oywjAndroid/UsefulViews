package com.oywj.usefulviews.ui.activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.utils.JumpUtils;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeView();
        activeEvent();
    }

    private void activeView() {
        mListView = (ListView) findViewById(R.id.main_listView);
        String[] array = getResources().getStringArray(R.array.list_items);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, array);
        mListView.setAdapter(arrayAdapter);
    }

    private void activeEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        JumpUtils.jumpAnotherActivity(MainActivity.this,ShapeImageViewActivity.class);
                        break;
                }
            }
        });
    }
}
