package com.zukuk.service;

import com.zukuk.model.QuizQuestion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizApiService {

    private static final String API_URL =
            "https://opentdb.com/api.php?amount=1&type=multiple&encode=url3986";
    private static final int MAX_TENTATIVES = 5;
    private static final int DELAI_MS = 5000;

    public QuizQuestion fetchQuestion() throws Exception {
        Exception dernierErreur = null;

        for (int tentative = 1; tentative <= MAX_TENTATIVES; tentative++) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .GET()
                        .build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                JSONObject json = new JSONObject(response.body());
                int responseCode = json.getInt("response_code");

                if (responseCode == 0) {
                    JSONObject q = json.getJSONArray("results").getJSONObject(0);
                    String question = decode(q.getString("question"));
                    String correct  = decode(q.getString("correct_answer"));

                    List<String> answers = new ArrayList<>();
                    answers.add(correct);
                    JSONArray wrongs = q.getJSONArray("incorrect_answers");
                    for (int i = 0; i < wrongs.length(); i++)
                        answers.add(decode(wrongs.getString(i)));

                    Collections.shuffle(answers);
                    return new QuizQuestion(question, correct, answers);
                }

                System.err.println("OpenTDB response_code=" + responseCode
                        + " — tentative " + tentative + "/" + MAX_TENTATIVES);

                if (tentative < MAX_TENTATIVES)
                    Thread.sleep(DELAI_MS);

            } catch (Exception e) {
                dernierErreur = e;
                System.err.println("Erreur réseau tentative " + tentative
                        + "/" + MAX_TENTATIVES + " : " + e.getMessage());
                if (tentative < MAX_TENTATIVES)
                    Thread.sleep(DELAI_MS);
            }
        }

        throw new Exception("OpenTDB indisponible après "
                + MAX_TENTATIVES + " tentatives", dernierErreur);
    }

    private String decode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }
}