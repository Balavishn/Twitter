package com.example.balavishnu.twitterjson;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String File_name="data.txt";
        final TextView text=(TextView)findViewById(R.id.t1);
        Retrofit retro=new Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        Json js=retro.create(Json.class);
        Call<List<Post>> call=js.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, final Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    text.setText("code:"+response.code());
                    return;
                }
                Thread t=new Thread(){
                    public void run(){
                        while(!isInterrupted()) try {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Post> posts=response.body();
                                    for(Post post:posts){
                                        String c="";
                                        c+="user id"+post.getId()+"\n";
                                        c+="id"+post.getUserid()+"\n";
                                        c+="title"+post.getTitle()+"\n";
                                        c+="text"+post.getText()+"\n";
                                        text.append(c);
                                        FileOutputStream fos=null;
                                        try {
                                            fos=openFileOutput(File_name,MODE_PRIVATE);
                                            fos.write(c.getBytes());
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(MainActivity.this,"Save to:"+getFilesDir()+"/"+File_name,Toast.LENGTH_LONG).show();
                                        NotificationCompat.Builder build=new NotificationCompat.Builder(MainActivity.this)
                                                .setAutoCancel(true)
                                                .setSmallIcon(R.drawable.ic_launcher_background)
                                                .setContentText(c)
                                                .setContentTitle(c);
                                        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                        manager.notify(0,build.build());

                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });

    }

}
