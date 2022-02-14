package afisha.arzan.tm.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.AddPostActivity;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.GlobalVar;
import afisha.arzan.tm.model.PaymentServices;
import afisha.arzan.tm.model.SERVICE;
import afisha.arzan.tm.model.Servi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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

    private LinearLayout banner,post,recommended,profile,notify;
    private ImageView icBanner,icPost,icRecommended,icProfile,icNotify;
    private TextView textPost,textRecommend,textBanner,textProfile,textNotify;
    private MaterialButton next;
    private String string;
    private int selectedId;
    private PaymentServices payServices;
    private List<Servi> servis = new ArrayList<>();
    public static SERVICE service = new SERVICE();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        banner = view.findViewById(R.id.banner);
        payServices = Global.paymentServices;
        post = view.findViewById(R.id.post);
        recommended = view.findViewById(R.id.recommended);
        icBanner = view.findViewById(R.id.ic_banner);
        icPost = view.findViewById(R.id.ic_post);
        icRecommended = view.findViewById(R.id.ic_vip);
        icProfile = view.findViewById(R.id.ic_profile);
        profile = view.findViewById(R.id.profile);
        next = view.findViewById(R.id.next);
        notify = view.findViewById(R.id.notification);
        icNotify = view.findViewById(R.id.ic_notify);
        textBanner = view.findViewById(R.id.textBanner);
        textPost = view.findViewById(R.id.textPost);
        textRecommend = view.findViewById(R.id.textRecommend);
        textProfile = view.findViewById(R.id.textProfile);
        textNotify = view.findViewById(R.id.textNotify);
        try {
            JSONArray jsonArray = payServices.getServices();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Servi servi = new Servi();
                servi.setId(jsonObject.getInt("id"));
                servi.setKey(jsonObject.getString("key"));
                servi.setValue(jsonObject.getInt("value"));
                servis.add(servi);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        string = getResources().getString(R.string.free);
        selectedId = 0;
        Global.money = 0;

        banner.setOnClickListener(this::onClick);
        post.setOnClickListener(this::onClick);
        recommended.setOnClickListener(this::onClick);
        profile.setOnClickListener(this::onClick);
        notify.setOnClickListener(this::onClick);
        next.setOnClickListener(this::onClick);


        return view;
    }

    private void onClick(View view){
        switch (view.getId()){
            case R.id.post:
                icPost.setImageResource(R.drawable.ic_radio_check);
                icRecommended.setImageResource(R.drawable.i_radio);
                icBanner.setImageResource(R.drawable.i_radio);
                icProfile.setImageResource(R.drawable.i_radio);
                icNotify.setImageResource(R.drawable.i_radio);
                string = getResources().getString(R.string.post);
                selectedId = 0;
                break;
            case R.id.recommended:
                icPost.setImageResource(R.drawable.i_radio);
                icRecommended.setImageResource(R.drawable.ic_radio_check);
                icBanner.setImageResource(R.drawable.i_radio);
                icProfile.setImageResource(R.drawable.i_radio);
                icNotify.setImageResource(R.drawable.i_radio);

                string = getResources().getString(R.string.recommend);
                selectedId = 1;

                break;

            case R.id.banner:
                icPost.setImageResource(R.drawable.i_radio);
                icRecommended.setImageResource(R.drawable.i_radio);
                icBanner.setImageResource(R.drawable.ic_radio_check);
                icProfile.setImageResource(R.drawable.i_radio);
                icNotify.setImageResource(R.drawable.i_radio);

                string = getResources().getString(R.string.banner);
                selectedId = 2;

                break;

            case R.id.profile:
                icPost.setImageResource(R.drawable.i_radio);
                icRecommended.setImageResource(R.drawable.i_radio);
                icBanner.setImageResource(R.drawable.i_radio);
                icProfile.setImageResource(R.drawable.ic_radio_check);
                icNotify.setImageResource(R.drawable.i_radio);
                string = getResources().getString(R.string.profile);
                selectedId = 3;

                break;
            case R.id.notification:
                icPost.setImageResource(R.drawable.i_radio);
                icRecommended.setImageResource(R.drawable.i_radio);
                icBanner.setImageResource(R.drawable.i_radio);
                icProfile.setImageResource(R.drawable.i_radio);
                icNotify.setImageResource(R.drawable.ic_radio_check);
                string = getResources().getString(R.string.notification);
                selectedId = 4;

                break;

            case R.id.next:
                switchNext(selectedId); break;


        }
    }

    private void switchNext(int selectedId){
        switch (selectedId){
            case 0:
                startActivity(new Intent(requireContext(), AddPostActivity.class));
                break;
            case 1:
                Global.money+=servis.get(1).getValue();
                loadFragment(new RegionFragment());
                break;
            case 2:
                Global.money+=servis.get(2).getValue();
                loadFragment(new RegionFragment());
                break;
            case 3:
                Global.money+=servis.get(3).getValue();
                loadFragment(new RegionFragment());
                break;
            case 4:
                Global.money+=servis.get(4).getValue();
                loadFragment(new RegionFragment());
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + selectedId);
        }

    }


    public boolean loadFragment(Fragment fragment)
    {
        Log.e("TAG", "money: "+Global.money );
        if (fragment!=null){
            service.setServiceName(string);
            service.setUsername(SPManager.getInstance(requireContext()).getData(SPManager.NAME,""));
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                    .replace(R.id.frame,fragment)
                    .addToBackStack(null)
                    .commit();
        }
        return true;
    }
}