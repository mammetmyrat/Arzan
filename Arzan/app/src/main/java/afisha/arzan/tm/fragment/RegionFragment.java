package afisha.arzan.tm.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.Region1Adapter;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.model.Region;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegionFragment newInstance(String param1, String param2) {
        RegionFragment fragment = new RegionFragment();
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

    public static List<String> selectedRegions = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_region, container, false);
        Region1Adapter adapter = new Region1Adapter(requireActivity(), Global.regions);
        RecyclerView recyclerView = view.findViewById(R.id.rv_regions);
        recyclerView.setAdapter(adapter);

        Global.selectedRegions = new ArrayList<>();
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.selectedRegions.size()!=0){
                    FirstFragment.service.setRegions(Global.selectedRegions);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                            .replace(R.id.frame,new ThirdPayment())
                            .addToBackStack(null)
                            .commit();
                }else {
                    Toast.makeText(requireContext(), R.string.choose_region, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}