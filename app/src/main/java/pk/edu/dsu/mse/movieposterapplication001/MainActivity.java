package pk.edu.dsu.mse.movieposterapplication001;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    String movieName;
    String url;
    List<String> movie_names = new ArrayList<>();

    ListView lv_movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et_movie = findViewById(R.id.movieId);
        Button findBtn = findViewById(R.id.findId);
        lv_movies = findViewById(R.id.nameId);




        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie_names.clear();




                movieName = et_movie.getText().toString();
                Log.e("TAG",movieName);
                url = "https://omdbapi.com/?s="+movieName+"&apikey=6084c3f2";
                Log.e("TAG",url);
                parseJson(url);

            }
        });


    }
    private void parseJson(final String url){
        Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject data) {

                        String response = data.get("Response").getAsString();
                        Log.e("TAG",response);


                        JsonArray array = data.getAsJsonArray("Search");
                        if (response.equals("True")){




                            for (int i =0;i<array.size();i++) {

                                JsonObject jsonRes = (JsonObject) array.get(i);
                                String title = jsonRes.get("Title").getAsString();
                                String year = jsonRes.get("Year").getAsString();


                                Log.e("TAG", title);
                                movie_names.add(title +" -" +year);



                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,movie_names);
                            lv_movies.setAdapter(adapter);

                        }else{
                            Toast.makeText(MainActivity.this,"Not Found",Toast.LENGTH_SHORT).show();

                        }



                    }
                });



    }
}
