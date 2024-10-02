package com.frunoman.load.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Endpoints {
    private static Config config = ConfigFactory.load();
    public static final String BASE_URL = config.getString("gatling.baseUrl");
    public static final String POSTS = "/posts";
    public static final String SINGLE_POST = "/posts/#{id}";
}
