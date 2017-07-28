package com.example.sukesh.webmobitask;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int PICKFILE_REQUEST_CODE=2;
    Button selectFile;
    ListView listView;
    TextView textFile,textRead;
    GetSetter gs;
    ArrayList<GetSetter> arrayList= new ArrayList<GetSetter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dexter.initialize(getApplicationContext());

        selectFile = (Button)findViewById(R.id.select_file);
        textFile = (TextView)findViewById(R.id.text);
        textRead = (TextView)findViewById(R.id.textread);
        listView = (ListView)findViewById(R.id.listView);


        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.checkPermission(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        picFile();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(),"Denied",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                    }

                }, android.Manifest.permission.READ_EXTERNAL_STORAGE);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("file/*");
//                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });



    }

    public void picFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_REQUEST_CODE:
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    //textFile.setText(FilePath);
                    String fullPath = FilePath;
                    int dot = fullPath.lastIndexOf(".");
                    String ext = fullPath.substring(dot + 1);
                    if(ext.equalsIgnoreCase("txt")) // make sure case is irrelevant for your use case; otherwise use ext.equals("txt")
                    {
                        //do something with f here
                        readFile(FilePath);
                    }else{
                        Toast.makeText(this,"Please select dot txt file",Toast.LENGTH_SHORT).show();
                    }


                }
                break;

        }
    }

    private void readFile(String s) {
        StringBuilder text = new StringBuilder();

        try {
            //File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(s);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                Log.i("Test", "text : "+text+" : end");
                text.append('\n');

            }
            //textRead.setText(text.toString());
            readData(text.toString());

        }
        catch (IOException e) {
            e.printStackTrace();

        }
        finally{
            //br.close();
        }
    }

    private void readData(String sentence) {
        HashMap<String, Integer> map = new HashMap<>();
        sentence = sentence.toLowerCase();

        for(String word : sentence.split("\\W")) {
            if(word.isEmpty()) {
                continue;
            }
            if(map.containsKey(word)) {
                map.put(word, map.get(word)+1);
            }
            else {
                map.put(word, 1);
            }
        }
        int k=-1;
        // Step 10: Sorting logic by invoking sortByCountValue() method
        Map<String, Integer> wordLHMap = sortByCountValue(map);

        for(Map.Entry<String, Integer> entry : wordLHMap.entrySet()){
            //System.out.println(entry.getKey() + "\t" + entry.getValue());
           // if (entry.getValue())
            for (int i = 1; i <= entry.getValue(); i=i+3) {
                if ((entry.getValue() >= i && entry.getValue()<= (i+1)) && !(k >= i && k <= (i+2))) {
                    arrayList.add(new GetSetter("Range "+i + "-"+(i+2), 0));
                    //v.header.setText(i + "-" + (i + 1));
                    //Log.i("test:", "if");
                    k = i;
                }
//                }else {
//                    v.header.setVisibility(View.GONE);
//                    //Log.i("test:","else");
//                }
            }
            arrayList.add(new GetSetter(entry.getKey(),entry.getValue()));
        }
//        for(Map.Entry<String, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
////            gs.setWord(entry.getKey());
////            gs.setCount(entry.getValue()+"");
//            arrayList.add(new GetSetter(entry.getKey(),entry.getValue()+""));
//        }
        LAdapter adapter = new
        LAdapter(this,arrayList);
        //listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    public static Map<String, Integer> sortByCountValue(
            Map<String, Integer> mapOfRepeatedWord) {
        // get entrySet from HashMap object
        Set<Map.Entry<String, Integer>> setOfWordEntries = mapOfRepeatedWord.entrySet();
        // convert HashMap to List of Map entries
        List<Map.Entry<String, Integer>> listOfwordEntry =
                new ArrayList<Map.Entry<String, Integer>>(setOfWordEntries);
        // sort list of entries using Collections class utility method sort(ls, cmptr)
        Collections.sort(listOfwordEntry,
                new Comparator<Map.Entry<String, Integer>>() {

                    @Override
                    public int compare(Map.Entry<String, Integer> es1,
                                       Map.Entry<String, Integer> es2) {
                        return es1.getValue().compareTo(es2.getValue()); // NOTE
                    }
                });
        // store into LinkedHashMap for maintaining insertion order
        Map<String, Integer> wordLHMap =
                new LinkedHashMap<String, Integer>();
        // iterating list and storing in LinkedHahsMap
        for(Map.Entry<String, Integer> map : listOfwordEntry){
            wordLHMap.put(map.getKey(), map.getValue());
        }
        return wordLHMap;
    }
}
