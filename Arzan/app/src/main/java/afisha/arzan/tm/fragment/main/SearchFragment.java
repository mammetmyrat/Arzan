package afisha.arzan.tm.fragment.main;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.PostAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.response.MainPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment   {


    public SearchFragment() {
        // Required empty public constructor
    }


    private ImageView back,viewType;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ImageView searchBtn;
    private String  searchText;
    private TextView count;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;
    private int limit = 15,offset= 0;
    private List<Post> posts = new ArrayList<>();
    private PostAdapter adapter;
    private Dialog dialog;
    private RelativeLayout no_result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_dragment, container, false);
        back = view.findViewById(R.id.back_btn);
        dialog = new Dialog(getActivity());
        no_result = view.findViewById(R.id.no_result);
        searchView = view.findViewById(R.id.editText);
        searchBtn = view.findViewById(R.id.search);
        count = view.findViewById(R.id.count);
        progressBar = view.findViewById(R.id.progress);
        scrollView = view.findViewById(R.id.nested);
        viewType = view.findViewById(R.id.view_type);
        recyclerView = view.findViewById(R.id.rv_search);
        adapter = new PostAdapter(getActivity());
        count.setVisibility(View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                posts=  new ArrayList<>();
                searchText = searchView.getQuery().toString();
                getSearchPosts(limit,offset);
                progressBar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                no_result.setVisibility(View.GONE);
                SPManager.loading(dialog);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY==v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    offset = offset+limit;
                    progressBar.setVisibility(View.VISIBLE);
                    getSearchPosts(limit,offset);
                }
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posts = new ArrayList<>();
                searchText = searchView.getQuery().toString();
                getSearchPosts(limit,offset);
                progressBar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                no_result.setVisibility(View.GONE);
                SPManager.loading(dialog);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                        .replace(R.id.frame,new HomeFragment())
                        .commit();
            }
        });

        return view;
    }
    private void getSearchPosts(int limit,int offset) {
        Call<MainPost> callPost = ApiClient.getInstance().getApi().getSearchPost(searchText,limit,offset);
        callPost.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()) {
                        posts.addAll(response.body().getPosts());
                        adapter.setPosts(posts);
                        progressBar.setVisibility(View.GONE);
                        adapter.setType(adapter.getType());
                        recyclerView.setAdapter(adapter);
                        count.setText(response.body().getCount());
                        count.setVisibility(View.VISIBLE);
                        recyclerView.setSaveEnabled(true);
                        dialog.dismiss();
                        if (adapter.getType()==PostAdapter.SMALL){
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                        }else {
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                        recyclerView.setAdapter(adapter);
                        changeViewType(adapter);
                }
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void changeViewType(PostAdapter adapter){
        viewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getType()==PostAdapter.STANDARD){
                    adapter.setType(PostAdapter.SMALL);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                    viewType.setImageResource(R.drawable.ic_view_big);
                }else if (adapter.getType()==PostAdapter.BIG){
                    adapter.setType(PostAdapter.STANDARD);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    viewType.setImageResource(R.drawable.ic_view_standart);
                }else {
                    adapter.setType(PostAdapter.BIG);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    viewType.setImageResource(R.drawable.ic_view_small);
                }
            }
        });
    }


}