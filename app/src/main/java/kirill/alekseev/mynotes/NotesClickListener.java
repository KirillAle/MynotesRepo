package kirill.alekseev.mynotes;

import androidx.cardview.widget.CardView;
import kirill.alekseev.mynotes.Models.Notes;

public interface NotesClickListener {

    void onClick (Notes notes);
    void onLongClick (Notes notes, CardView cardView);




}
