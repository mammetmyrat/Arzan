package afisha.arzan.tm.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.RegionActivity;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.fragment.main.RulesFragment;
import afisha.arzan.tm.request.PayResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
    private LinearLayout tkm,rus;
    private RelativeLayout region,rules,download,loggOff,payment;
    private     String lang= "tm";
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        lang = SPManager.getInstance(getContext()).getData(SPManager.LANG,"rus");
        dialog = new Dialog(getContext());
        tkm = view.findViewById(R.id.tkm);
        rus = view.findViewById(R.id.rus);
        region = view.findViewById(R.id.choose_region);
        rules = view.findViewById(R.id.rules);
        download = view.findViewById(R.id.download);
        loggOff = view.findViewById(R.id.exit);
        payment = view.findViewById(R.id.payment);


        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegionActivity.class));
            }
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new RulesFragment());
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String packageName = requireContext().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        });

        loggOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dialog);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(requireContext());
                payDialog(dialog);
            }
        });

        switch (lang){
            case "rus":
                rus.setBackgroundResource(R.drawable.lang_bg);break;
            case "tk":
                tkm.setBackgroundResource(R.drawable.lang_bg);break;
        }
        tkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(getActivity(),"tk");
                SPManager.getInstance(getContext()).setData(SPManager.LANG,"tk");
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });
        rus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(getActivity(),"rus");
                SPManager.getInstance(getContext()).setData(SPManager.LANG,"rus");
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });



        return view;
    }
    public boolean loadFragment(Fragment fragment)
    {
        if (fragment!=null){
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                    .replace(R.id.frame,fragment)
                    .addToBackStack(null)
                    .commit();
        }
        return true;
    }

    private void showDialog(Dialog dialog) {
        dialog.setContentView(R.layout.exit_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(true);
        dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPManager.getInstance(getContext()).setBoolean(SPManager.AUTH,false);
                getActivity().finishAffinity();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void payDialog(Dialog dialog) {
        dialog.setContentView(R.layout.pay_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(true);
        EditText code = dialog.findViewById(R.id.code);
        dialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code!=null) sendCode(dialog,code.getText().toString());
            }
        });
    }
    public void setLanguage(Activity activity, String language){
        Locale locale = new Locale(language);
        Resources resources  =activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

    private void sendCode(Dialog dialog,String id){
        Call<PayResponse> call = ApiClient.getInstance().getApi().pay(id);
        call.enqueue(new Callback<PayResponse>() {
            @Override
            public void onResponse(Call<PayResponse> call, Response<PayResponse> response) {
                if (response.code()==200){
                    success(response.body().getUrl());
                    Log.e("TAG", "pay: "+response.code() );
                    dialog.dismiss();
                }else {
                    Log.e("TAG", "pay: "+response.code() );
                    Toast.makeText(getContext(), R.string.error_code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PayResponse> call, Throwable t) {

            }
        });
    }
    private void success(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }


}