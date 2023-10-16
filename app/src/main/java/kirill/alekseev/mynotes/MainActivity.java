package kirill.alekseev.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import kirill.alekseev.mynotes.Adapter.NotesListAdapter;
import kirill.alekseev.mynotes.DataBase.RoomDB;
import kirill.alekseev.mynotes.Models.Notes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab_add;
    NotesListAdapter notesListAdapter;
    RoomDB dataBase;
    List<Notes> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);

        dataBase = RoomDB.getInstance(this);
        notes = dataBase.mainDao().getAll();

        updateRecycler(notes);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent,101);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                dataBase.mainDao().insert(new_notes);

                notes.clear();
                notes.addAll(dataBase.mainDao().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }

    }

    private void updateRecycler(List<Notes> notes) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

        }
    };
}