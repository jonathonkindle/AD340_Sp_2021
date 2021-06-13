package jonathon.kindle.AD340;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "jonathon_kindle.AD340.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        TextView movieTitle = findViewById(R.id.movieTitle);
        TextView movieYear = findViewById(R.id.movieYear);
        TextView movieDirector = findViewById(R.id.movieDirector);
        TextView movieDescription = findViewById(R.id.movieDescription);
        ImageView movieImage = findViewById(R.id.movieImage);


        Intent intent = getIntent();
        String[] movieDetails = intent.getStringArrayExtra(EXTRA_MESSAGE);
        if (movieDetails != null) {
            movieTitle.setText(movieDetails[0]);
            movieYear.setText(String.format(" | %s", movieDetails[1]));
            movieDirector.setText(movieDetails[2]);
            movieDescription.setText(movieDetails[4]);
            Picasso.get().load(movieDetails[3]).into(movieImage);
        }
    }

}