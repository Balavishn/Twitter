package com.example.balavishnu.twitterjson;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Json {
    @GET("posts")
    Call<List<Post>> getPosts();
}
