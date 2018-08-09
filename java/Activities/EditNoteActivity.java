package com.example.a4id1.manageralbumow.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.a4id1.manageralbumow.Helpers.DatabaseManager;
import com.example.a4id1.manageralbumow.R;

public class EditNoteActivity extends AppCompatActivity {

    private DatabaseManager db;
    private EditText newTitle;
    private EditText newText;
    private LinearLayout newColors;
    private String tmpTitle;
    private String tmpText;
    private int tmpColor;
    private int[] colors;
    private Button saveEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Bundle bundle = getIntent().getExtras();
        final String noteId = bundle.getString("key");

        db = new DatabaseManager(
                EditNoteActivity.this,
                "NotatkiBaranMikolaj.db",
                null,
                3
        );

        newTitle = (EditText) findViewById(R.id.newTitle);
        newText = (EditText) findViewById(R.id.newText);
        newColors = (LinearLayout) findViewById(R.id.newColor);

        colors = new int[]{Color.rgb(255,205,255), Color.rgb(255,0,0), Color.rgb(13,54,99), Color.rgb(155,8,78)};

        for (int i=0; i<colors.length; i++){

            LinearLayout newLayout = new LinearLayout(EditNoteActivity.this);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    newColors.getLayoutParams().width/colors.length,
                    newColors.getLayoutParams().height,
                    1.0f
            );
            newLayout.setLayoutParams(param);
            newLayout.setBackgroundColor(colors[i]);
            newLayout.setOrientation(LinearLayout.VERTICAL);
            final int finalI = i;
            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colors[0] = colors[finalI];
                }
            });
            newColors.addView(newLayout);


            saveEdit = (Button) findViewById(R.id.saveEdit);
            saveEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tmpTitle = newTitle.getText().toString();
                    tmpText = newText.getText().toString();
                    db.edit(noteId, tmpTitle, tmpText, colors[0]);

                    finish();
                }
            });

        }
    }
}
