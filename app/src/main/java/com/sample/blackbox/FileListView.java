package com.sample.blackbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListView extends AppCompatActivity {
    ListView fileView;
    EditText editSearch;
    SearchAdapter adapter;
    List<String> list;
    ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelistview);

        fileView = (ListView) findViewById(R.id.fileView);
        editSearch = (EditText) findViewById(R.id.editSearch);

        Intent inIntent = getIntent();
        String sysDir = (inIntent.getStringExtra("Name"));
        if( sysDir.length()==0) {
            Log.d("test", "Empty");
            finish();
        } else Log.d("test", sysDir);

        list = new ArrayList<String>();
        File[] sysFiles = (new File(sysDir).listFiles());
        String strFname;

        for (int i = 0; i < sysFiles.length; i++) {
            if (sysFiles[i].isFile() == true) {
                strFname = sysFiles[i].toString();
                Log.d("test", strFname);
                list.add(strFname.toString());
            }
        }

        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        adapter = new SearchAdapter(list, this);
        fileView.setAdapter(adapter);

        fileView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test", "결과 : " + list.get(position));
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("Name", list.get(position));
                startActivity(intent);
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });
    }

    public void search(String charText) {
        list.clear();
        if (charText.length() == 0) {
            list.addAll(arraylist);
        } else {
            for(int i = 0;i < arraylist.size(); i++)
            {
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    list.add(arraylist.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}