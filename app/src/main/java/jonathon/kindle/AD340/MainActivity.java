package jonathon.kindle.AD340;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    String[] btnNames = {"Movies 1", "Movies 2", "Traffic Cams", "location", "Map"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.btnGridView);
        gridView.setAdapter(new ButtonAdapter(this));
    }

    public class ButtonAdapter extends BaseAdapter {
        private Context mainContext;

        public ButtonAdapter(Context context) {
            mainContext = context;
        }

        public int getCount() {
            return btnNames.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Button button;
            if (convertView == null) {
                button = new Button(mainContext);
            } else {
                button = (Button) convertView;
            }

            button.setText(btnNames[position]);
            button.setId(position);
            button.setOnClickListener(new BtnOnClickListener());

            return button;
        }

    }

    class BtnOnClickListener implements View.OnClickListener
    {

        public void onClick(View v)
        {
            int id = v.getId();

            Intent intent;
            switch (id) {
                case 0:
                    intent = new Intent(getBaseContext(), MovieListActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getBaseContext(), CameraListActivity.class);
                    startActivity(intent);
                    break;
                default:
                    Button b = (Button) v;
                    String label = b.getText().toString();
                    Toast.makeText(MainActivity.this, label,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


}