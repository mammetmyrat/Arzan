package afisha.arzan.tm.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.SERVICE;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdPayment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThirdPayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdPayment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdPayment newInstance(String param1, String param2) {
        ThirdPayment fragment = new ThirdPayment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_payment, container, false);
        SERVICE ser = FirstFragment.service;
        String regionText = "";
        TextView name = view.findViewById(R.id.name);
        TextView service = view.findViewById(R.id.service);
        TextView regions = view.findViewById(R.id.regions);
        TextView moneyText = view.findViewById(R.id.moneyText);

        MaterialButton send = view.findViewById(R.id.confirm);
        moneyText.setText(String.valueOf(Global.money)+" TMT");

        name.setText(ser.getUsername());
        service.setText(ser.getServiceName());
        if (ser.getRegions().size()==6){
            regionText = "TURKMENISTAN";
        }else {
            for (int i=0;i<ser.getRegions().size();i++){
                if (i==0) regionText+=(ser.getRegions().get(i));
                else regionText+=(", "+ser.getRegions().get(i));
            }
        }
        regions.setText(regionText);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback(ser);
            }
        });


        return view;
    }
    private void sendFeedback(SERVICE service){
        String token = SPManager.getInstance(requireContext()).getData(SPManager.TOKEN,"");
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postThanks(token,service.getServiceName()+service.getRegions(),"service");
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()){
                    Dialog dialog = new Dialog(requireContext());
                    SPManager.doneDialog(getContext(),getResources().getString(R.string.service_sended)+ "\n+99364103444",dialog);
                    MaterialButton button = dialog.findViewById(R.id.ok);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(requireContext(), MainActivity.class));
                        }
                    });
                    Log.e("TAG", "sended: " );
                }else Log.e("TAG", "feedback gitmedi: " +response.code());

            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.e("TAG", "sended error: " );

            }
        });
    }

}