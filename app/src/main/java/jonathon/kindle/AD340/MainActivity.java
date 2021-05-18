package jonathon.kindle.AD340;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] btnNames = {"Cities", "Parks", "Traffic", "Music", "Movies", "Food"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Hello World");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, btnNames);

        GridView gridView = (GridView) findViewById(R.id.btnGridView);
        gridView.setAdapter(adapter);

        AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "you clicked " + btnNames[+position], Toast.LENGTH_SHORT).show();
            }
        };

        gridView.setOnItemClickListener(messageClickedHandler);
    }

}