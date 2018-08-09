package com.example.a4id1.manageralbumow.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a4id1.manageralbumow.Helpers.DatabaseManager;
import com.example.a4id1.manageralbumow.Adapters.MyArrayAdapter;
import com.example.a4id1.manageralbumow.R;

public class NotesActivity extends AppCompatActivity {

    private DatabaseManager db;
    private ListView listviewNotes;
    private String tmpId;
    private String tmpText;
    private String tmpTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new DatabaseManager(
                NotesActivity.this,
                "NotatkiBaranMikolaj.db",
                null,
                3
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        MyArrayAdapter adapter = new MyArrayAdapter(
                NotesActivity.this,
                R.layout.note_layout,
                db.getAll(),
                db
        );

        listviewNotes = (ListView) findViewById(R.id.listviewNotes);

        listviewNotes.setAdapter(adapter);

        listviewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                tmpId = db.getAll().get(position).getPhotoname();
                tmpTitle = db.getAll().get(position).getTitle();
                tmpText = db.getAll().get(position).getText();

                AlertDialog.Builder alert = new AlertDialog.Builder(NotesActivity.this);
                alert.setTitle("Wybierz, co chcesz zrobić");
//nie może mieć setMessage!!!
                final String[] opcje = {"Edytuj","Usuń","Sortuj"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // wyswietl opcje[which]);
                        switch (opcje[which]){
                            case "Edytuj":
                                Intent intent = new Intent(NotesActivity.this,EditNoteActivity.class);
                                String send = tmpId;
                                intent.putExtra("key", send);
                                startActivity(intent);
                                break;
                            case "Usuń":
                                db.delete(tmpId);
                                updateList(listviewNotes);
                                break;
                            case "Sortuj":
                                break;
                        }

                    }
                });
//
                alert.show();
                return false;
            }
        });
    }

    private void updateList(ListView list_view){

        MyArrayAdapter adapter = new MyArrayAdapter(
                NotesActivity.this,
                R.layout.note_layout,
                db.getAll(),
                db
        );
        adapter.notifyDataSetChanged();
        list_view.setAdapter(adapter);

    }
}
