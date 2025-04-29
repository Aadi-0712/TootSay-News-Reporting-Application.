package com.example.tootsay.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tootsay.databinding.FragmentHomeBinding;
import com.example.tootsay.rcadapter;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class
HomeFragment extends Fragment {
    List<Article> articleList = new ArrayList<>();
    rcadapter rca ;
    RecyclerView rcv ;
    Button b1 , b2 , b3 , b4 , b5 , b6 , b7 ;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        rcv = binding.rcv;

        b1 = binding.btn1;
        b2 = binding.btn2;
        b3 = binding.btn3;
        b4 = binding.btn4;
        b5 = binding.btn5;
        b6 = binding.btn6;
        b7 = binding.btn7;


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnews("GENERAL",null);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnews("BUSINESS",null);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnews("SPORTS",null);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnews("TECHNOLOGY",null);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnews("HEALTH",null);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnews("SCIENCE",null);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnews("ENTERTAINMENT",null);
            }
        });

        setupRecyclerView();
        getnews("GENERAL",null);
        return root;
    }

    void setupRecyclerView() {
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rca = new rcadapter(articleList);
        rcv.setAdapter(rca);
    }



    void getnews(String category, String query)
    {
        NewsApiClient newsApiClient = new NewsApiClient("93cacf66c7de4c10a0ec7dd65aefc581");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(category)
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        getActivity().runOnUiThread (() -> {
                            articleList = response.getArticles();
                            rca.updateData(articleList);
                            rca.notifyDataSetChanged();
                        });

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("", throwable.getMessage());
                    }
                }
        );
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}