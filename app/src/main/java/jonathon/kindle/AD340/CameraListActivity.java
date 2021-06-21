package jonathon.kindle.AD340;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CameraListActivity extends AppCompatActivity {

    private ArrayList<Camera> cameraArrayList;
    private RequestQueue requestQueue;

    Context context;
    RecyclerView cameraList;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_list);

        context = getApplicationContext();
        cameraArrayList = new ArrayList<>();

        cameraList = findViewById(R.id.cameraRecycler);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        cameraList.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new Adapter(CameraListActivity.this, cameraArrayList);
        cameraList.setAdapter(recyclerViewAdapter);

        requestQueue = Volley.newRequestQueue(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getTrafficCamData();
        } else {
            Toast toast = Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void getTrafficCamData() {
        String URL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        JSONArray features = response.getJSONArray("Features");
                        for (int i = 0; i < features.length(); i++) {
                            JSONObject featureItem = features.getJSONObject(i);
                            JSONArray cameras = featureItem.getJSONArray("Cameras");
                            for (int k = 0; k < cameras.length(); k++) {
                                JSONObject cameraItem = cameras.getJSONObject(k);
                                String address = cameraItem.getString("Description");
                                String imageUrl = cameraItem.getString("ImageUrl");
                                String type = cameraItem.getString("Type");
                                cameraArrayList.add(new Camera(type, address, imageUrl));
                            }
                        }
                        recyclerViewAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        requestQueue.add(request);
    }

    public static class Camera {
        private final String camDescription;
        private final String camImageUrl;

        public Camera(String source, String address, String url) {
            camDescription = address;
            camImageUrl = checkSource(source, url);
        }

        public String checkSource(String source, String url) {
            if (source.equals("sdot")) {
                return "https://www.seattle.gov/trafficcams/images/" + url;
            } else {
                return "https://images.wsdot.wa.gov/nw/" + url;
            }
        }

        public String getDescription() {
            return camDescription;
        }

        public String getUrl() {
            return camImageUrl;
        }
    }

    public static class Adapter extends RecyclerView.Adapter<CameraListActivity.Adapter.ViewHolder> {
        private final Context context;
        private final ArrayList<Camera> camList;

        public Adapter(Context context, ArrayList<Camera> cameras) {
            this.context = context;
            this.camList = cameras;
    }
        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView cameraDescription;
            public ImageView cameraImage;

            public ViewHolder(View itemView) {
                super(itemView);
                cameraDescription = itemView.findViewById(R.id.cameraDescription);
                cameraImage = itemView.findViewById(R.id.cameraImage);
            }
        }

        @NotNull
        @Override
        public CameraListActivity.Adapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.camera_item, viewGroup, false);
            return new CameraListActivity.Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CameraListActivity.Adapter.ViewHolder viewHolder, int position) {
            Camera cameras = camList.get(position);
            String URL = cameras.getUrl();
            String description = cameras.getDescription();
            viewHolder.cameraDescription.setText(description);
            Picasso.get().load(URL).fit().centerInside().into(viewHolder.cameraImage);
        }

        @Override
        public int getItemCount() {
            return camList.size();
        }
    }
}
