package afisha.arzan.tm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import afisha.arzan.tm.R;
import afisha.arzan.tm.app.Global;

public class DayFragment extends Fragment {


    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        TextView allPosts = view.findViewById(R.id.all_post_count);
        TextView viewCount = view.findViewById(R.id.view_count);
        TextView likeCount = view.findViewById(R.id.like_count);
        TextView shareCount = view.findViewById(R.id.share_count);


        allPosts.setText(String.valueOf(Global.statRegion.getDay().getPostCount()));
        viewCount.setText(String.valueOf(Global.statRegion.getDay().getViewCount()));
        likeCount.setText(String.valueOf(Global.statRegion.getDay().getLikeCount()));
        shareCount.setText(String.valueOf(Global.statRegion.getDay().getShareCount()));

        return view;
    }
}