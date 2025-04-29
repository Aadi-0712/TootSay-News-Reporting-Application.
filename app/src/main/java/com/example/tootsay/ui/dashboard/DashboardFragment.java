package com.example.tootsay.ui.dashboard;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tootsay.databinding.FragmentDashboardBinding;
import com.example.tootsay.rcadapter;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    SearchView seaview;
    RecyclerView recview;

    List<Article> articleList = new ArrayList<>();
    rcadapter rca ;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

DashboardViewModel dashboardViewModel=new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        seaview = binding.s1;
        recview = binding.rc2;

        seaview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getnews("GENERAL", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        seaview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seaview.setIconified(false);
            }
        });
        setupRecyclerView();
        return root;
    }



    void setupRecyclerView() {
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        rca = new rcadapter(articleList);
        recview.setAdapter(rca);
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