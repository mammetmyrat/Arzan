package afisha.arzan.tm.fragment.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import com.yandex.metrica.impl.ob.G;

import java.util.ArrayList;
import java.util.Timer;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.NotificationActvity;
import afisha.arzan.tm.activity.OfficalsActivity;
import afisha.arzan.tm.activity.RecommendedActivity;
import afisha.arzan.tm.activity.RegionActivity;
import afisha.arzan.tm.activity.StatisticActivity;
import afisha.arzan.tm.adapter.BannerAdapter;
import afisha.arzan.tm.adapter.PostAdapter;
import afisha.arzan.tm.adapter.VipAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.app.SliderTask;
import afisha.arzan.tm.model.GlobalVar;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.response.FollowingResponse;
import afisha.arzan.tm.response.MainBanner;
import afisha.arzan.tm.response.MainPost;
import afisha.arzan.tm.response.Statistic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerView, rvVIp;
    private String token;
    private int regionId;
    private TextView regionText, viewAll, badgeNotify,badgeOfficial,badgeStatistic;
    private SwipeRefreshLayout swipeRefresh;
    private WormDotsIndicator dotsIndicator;
    private ViewPager viewPager;
    private MaterialCardView viewType, region, statistic, officials,notify;
    private ImageView typeIcon;
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Post> vipPosts = new ArrayList<>();
    private RelativeLayout vipLine;
    private Timer timer;
    private PostAdapter adapter;
    private VipAdapter vipAdapter;
    public static NestedScrollView scrollView;
    private int limit = 15, offset = 0;
    private ProgressBar progressBar;
    private Dialog dialog;
    private Handler mHandler;
    private Runnable SCROLLING_RUNNABLE;
    private LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dialog = new Dialog(getContext());
        vipAdapter = new VipAdapter(getActivity());
        regionText = view.findViewById(R.id.region_text);
        viewPager = view.findViewById(R.id.viewPager);
        dotsIndicator = view.findViewById(R.id.dot);
        rvVIp = view.findViewById(R.id.rv_vip);
        viewType = view.findViewById(R.id.view_type);
        typeIcon = view.findViewById(R.id.type_icon);
        region = view.findViewById(R.id.region);
        progressBar = view.findViewById(R.id.progress);
        scrollView = view.findViewById(R.id.nested);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.rv_posts);
        officials = view.findViewById(R.id.official);
        viewAll = view.findViewById(R.id.view_all);
        vipLine = view.findViewById(R.id.vipline);
        statistic = view.findViewById(R.id.statistic);
        notify = view.findViewById(R.id.notification);
        badgeNotify = view.findViewById(R.id.badgeNotify);
        badgeOfficial = view.findViewById(R.id.badgeOfficial);
        badgeStatistic = view.findViewById(R.id.badgeStatistic);

        Log.e("TAG", "onStart: "+Global.NOTIFY_COUNT);
        if (Global.NOTIFY_COUNT>0) badgeNotify.setText(String.valueOf(Global.NOTIFY_COUNT));
        else  badgeNotify.setVisibility(View.GONE);
        if (Global.OFFICIAL_COUNT>0) badgeOfficial.setText(String.valueOf(Global.OFFICIAL_COUNT));
        else  badgeOfficial.setVisibility(View.GONE);
        adapter = new PostAdapter(getActivity());
        layoutManager = new LinearLayoutManager(getContext(),  RecyclerView.HORIZONTAL, false);
        rvVIp.setLayoutManager(layoutManager);

        SPManager.loading(dialog);
        token = SPManager.getInstance(getContext()).getData(SPManager.TOKEN, "");
        regionId = SPManager.getInstance(getContext()).getIntData(SPManager.REGION_ID, 6);

        regionText.setText(SPManager.getInstance(getContext()).getData(SPManager.REGION, "Ashgabat"));

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts(limit, offset, regionId);
                getBanners();
                getRecommend(regionId);
                getStatistic();
            }
        });
        getMoney();

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RecommendedActivity.class));
            }
        });


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    offset = offset + limit;
                    progressBar.setVisibility(View.VISIBLE);
                    getPosts(limit, offset, regionId);
                }
            }
        });
        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                startActivity(new Intent(getContext(), RegionActivity.class));
                getActivity().overridePendingTransition(R.anim.in_right, R.anim.out_left);
            }
        });

        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StatisticActivity.class));
                requireActivity().overridePendingTransition(R.anim.in_left, R.anim.out_right);
                badgeStatistic.setVisibility(View.GONE);
            }
        });

        officials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OfficalsActivity.class));
            }
        });
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotificationActvity.class));
                badgeNotify.setVisibility(View.GONE);

            }
        });
        getPosts(limit, offset, regionId);
        getBanners();
        getRecommend(regionId);
        getStatistic();
        getFollowings();

        return view;
    }

    private void getStatistic() {
        int regionId = SPManager.getInstance(requireContext()).getIntData(SPManager.REGION_ID, 6);
        Call<Statistic> call = ApiClient.getInstance().getApi().getStatistic();
        call.enqueue(new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (int i = 0; i < response.body().getStatRegions().size(); i++) {
                        if (regionId == response.body().getStatRegions().get(i).getId()) {
                            Global.statRegion = response.body().getStatRegions().get(i);
                            Log.e("TAG", "stat Region: " + Global.statRegion.getName());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Statistic> call, Throwable t) {

            }
        });
    }

    private void getPosts(int limit, int offset, int regionId) {
        Call<MainPost> call = ApiClient.getInstance().getApi().getAllPosts(token, regionId, 5, limit, offset);
        call.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful() && response.body().getPosts() != null) {
                    dialog.dismiss();
                    if (response.body().getPosts().size() != 0) {
                        progressBar.setVisibility(View.GONE);
                        posts.addAll(response.body().getPosts());
                        adapter.setType(adapter.getType());
                        adapter.setPosts(posts);
                        adapter.notifyDataSetChanged();
                        recyclerView.setSaveEnabled(true);
                        if (adapter.getType() == PostAdapter.SMALL) {
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

                        } else {
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                        recyclerView.setAdapter(adapter);
                        changeViewType(adapter);
                        swipeRefresh.setRefreshing(false);
                        Log.e("TAG", "onResponse: " + response.body().getPosts());
                    } else {
                        progressBar.setVisibility(View.GONE);
                        swipeRefresh.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                dialog.dismiss();
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), requireActivity().getResources().getString(R.string.network_error_toast), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFollowings() {
        Call<FollowingResponse> call = ApiClient.getInstance().getApi().getMainMobile(token, regionId);
        call.enqueue(new Callback<FollowingResponse>() {
            @Override
            public void onResponse(Call<FollowingResponse> call, Response<FollowingResponse> response) {
                if (response.isSuccessful()) {
                    Global.followings = response.body().getFollowings();
                    Log.e("TAG", "followers: " + response.body().getFollowings());
                }
            }

            @Override
            public void onFailure(Call<FollowingResponse> call, Throwable t) {
                Log.e("TAG", "onResponse: " + t.getLocalizedMessage());

            }
        });
    }

    private void getRecommend(int regionId) {
        Call<MainPost> call = ApiClient.getInstance().getApi().getRecommended(token, regionId, 1, 15, 0);
        call.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful() && response.body().getPosts() != null) {
                    if (response.body().getPosts().size() != 0) {
                        Log.e("TAG", "recommended: " + response.body().getPosts());
                        vipPosts.addAll(response.body().getPosts());
                        vipLine.setVisibility(View.VISIBLE);
                        if (vipPosts.size() != 0) {
                            viewAll.setVisibility(View.VISIBLE);
                            vipAdapter.setPosts(vipPosts);
                            rvVIp.setAdapter(vipAdapter);
                            autoScroll(rvVIp, vipAdapter);
                        }
                    }
                } else {
                    Log.e("TAG", "recommended: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), requireActivity().getResources().getString(R.string.network_error_toast), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBanners() {
        Call<MainBanner> call = ApiClient.getInstance().getApi().getBanners("app_banner_page_18", regionId);
        call.enqueue(new Callback<MainBanner>() {
            @Override
            public void onResponse(Call<MainBanner> call, Response<MainBanner> response) {
                if (response.isSuccessful() && response.body().getBanners() != null) {
                    BannerAdapter adapter = new BannerAdapter(getActivity());
                    adapter.setType(BannerAdapter.BANNER);
                    adapter.setBanners(response.body().getBanners());
                    viewPager.setAdapter(adapter);
                    dotsIndicator.setViewPager(viewPager);
                    if (timer == null) {
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new SliderTask(adapter, getActivity(), viewPager), 10, 5000);
                    }
                }
            }

            @Override
            public void onFailure(Call<MainBanner> call, Throwable t) {

            }
        });
    }
    private void getMoney(){
        Call<GlobalVar> call = ApiClient.getInstance().getApi().getServices();
        call.enqueue(new Callback<GlobalVar>() {
            @Override
            public void onResponse(Call<GlobalVar> call, Response<GlobalVar> response) {
                if (response.isSuccessful()){
                    Global.paymentServices = response.body().getPaymentServices();
                }
            }

            @Override
            public void onFailure(Call<GlobalVar> call, Throwable t) {

            }
        });
    }
    private void changeViewType(PostAdapter adapter) {
        viewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getType() == PostAdapter.STANDARD) {
                    adapter.setType(PostAdapter.SMALL);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    typeIcon.setImageResource(R.drawable.ic_view_big);
                } else if (adapter.getType() == PostAdapter.BIG) {
                    adapter.setType(PostAdapter.STANDARD);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    typeIcon.setImageResource(R.drawable.ic_view_small);
                } else {
                    adapter.setType(PostAdapter.BIG);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    typeIcon.setImageResource(R.drawable.ic_view_standart);
                }
            }
        });
    }

    private void autoScroll(RecyclerView recyclerView, VipAdapter adapter) {
        final int speedScroll = 2000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                if(count < adapter.getItemCount()){
                    if(count==adapter.getItemCount()-1){
                        flag = false;
                    }else if(count == 0){
                        flag = true;
                    }
                    if(flag) {
                        count++;
                    }
                    else {
                        count=0;
                    }
                    recyclerView.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };

        handler.postDelayed(runnable,speedScroll);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (Global.NOTIFY_COUNT!=0) badgeNotify.setText(String.valueOf(Global.NOTIFY_COUNT));
        else  badgeNotify.setVisibility(View.GONE);
        if (Global.OFFICIAL_COUNT!=0) badgeOfficial.setText(String.valueOf(Global.OFFICIAL_COUNT));
        else  badgeOfficial.setVisibility(View.GONE);
    }
}