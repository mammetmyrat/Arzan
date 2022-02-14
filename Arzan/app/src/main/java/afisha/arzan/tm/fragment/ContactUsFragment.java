package afisha.arzan.tm.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.yandex.metrica.impl.ob.I;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.LoginActivity;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.fragment.main.HomeFragment;
import afisha.arzan.tm.response.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactUsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactUsFragment newInstance(String param1, String param2) {
        ContactUsFragment fragment = new ContactUsFragment();
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

    private EditText phone,content;
    private String token;
    private MaterialButton send;
    private Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        dialog = new Dialog(getContext());
        token  = SPManager.getInstance(getContext()).getData(SPManager.TOKEN,"");
        phone = view.findViewById(R.id.phone);
        content = view.findViewById(R.id.content);
        send = view.findViewById(R.id.send);
        view.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                        .replace(R.id.frame,new HomeFragment())
                        .commit();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPManager.getInstance(requireContext()).getBoolean(SPManager.AUTH,false)){
                    SPManager.loading(dialog);
                    sendSuggest(content.getText().toString()+" tel: "+"+993"+phone.getText().toString());
                }else startActivity(new Intent(requireContext(), LoginActivity.class));
            }
        });
        return view;
    }
    private void sendSuggest(String message){
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postThanks(token,message,"suggest");
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful()){
                    Dialog dialog = new Dialog(requireContext());
                    SPManager.doneDialog(getContext(),getResources().getString(R.string.suggest_sended),dialog);
                    MaterialButton button = dialog.findViewById(R.id.ok);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }



}