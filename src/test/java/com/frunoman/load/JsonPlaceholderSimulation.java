package com.frunoman.load;

import com.frunoman.load.api.dto.PostBody;
import com.frunoman.load.utils.Endpoints;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class JsonPlaceholderSimulation extends Simulation {
    Config config = ConfigFactory.load();

    int getPostsUserCount = config.getInt("gatling.userCount.getPosts");
    int getSinglePostUserCount = config.getInt("gatling.userCount.getSinglePost");
    int createPostUserCount = config.getInt("gatling.userCount.createPost");

    // Define HTTP protocol with base URL
    HttpProtocolBuilder httpProtocol = http.baseUrl(Endpoints.BASE_URL)
            .header("Content-Type", "application/json");

    // Scenario 1: Fetch all posts (GET)
    ScenarioBuilder getPostsScenario = scenario("Fetch All Posts")
            .exec(http("Get All Posts")
                    .get(Endpoints.POSTS)
                    .check(status().is(200))
            );

    ScenarioBuilder getSinglePostScenario = scenario("Fetch Single Post")
            .exec(session -> {
                return session.set("id", (int) (Math.random() * 100) + 1);
            })
            .exec(http("Get Single Post")
                    .get(Endpoints.SINGLE_POST)
                    .check(status().is(200))
            );

    ScenarioBuilder createPostScenario = scenario("Create New Post")
            .exec(http("Create New Post")
                    .post(Endpoints.POSTS)  // Using relative path
                    .body(StringBody(PostBody.generatePostBody().objectToJson(true)))
                    .check(status().is(201))
            );

    {
        setUp(
                getPostsScenario.injectOpen(atOnceUsers(getPostsUserCount)),
                getSinglePostScenario.injectOpen(atOnceUsers(getSinglePostUserCount)),
                createPostScenario.injectOpen(atOnceUsers(createPostUserCount))
        ).protocols(httpProtocol);
    }
}
