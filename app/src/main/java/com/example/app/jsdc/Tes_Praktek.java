package com.example.app.jsdc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.app.jsdc.Utils.SelectedFragment;
import com.example.app.jsdc.Utils.SessionManager;

public class Tes_Praktek extends Fragment implements SelectedFragment {
    // Store instance variables
    private int page;
    int jumlahSoal;
    View view;
    SessionManager sessionManager;
    // newInstance constructor for creating fragment with arguments
    public static Tes_Praktek newInstance(int page) {
        Tes_Praktek tesPraktek = new Tes_Praktek();
        Bundle args = new Bundle();
        args.putInt("0", page);
        tesPraktek.setArguments(args);
        return tesPraktek;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("0");
        sessionManager = new SessionManager(getContext());
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*jumlahSoal = sessionManager.getJumlahSoal();
        for (int i = 0; i<jumlahSoal; i++){
            String pertanyaan = sessionManager.getQuestion(sessionManager.getQuestionId(i));
        }*/
        view = inflater.inflate(R.layout.fragment_tes__praktek, container, false);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layoutSoal);
            int jumlahSoal = sessionManager.getJumlahSoal();
            for (int i = 0; i < jumlahSoal; i++) {
                Log.d("panjang", "cek" + jumlahSoal);
                Log.d("hasil", "adalah" + sessionManager.getQuestionId(i));
                String pertanyaan = sessionManager.getQuestion(sessionManager.getQuestionId(i));




                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
                );

                LinearLayout linearLayoutHorizontal = new LinearLayout(getActivity());
                linearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutHorizontal.setLayoutParams(lp);
                linearLayoutHorizontal.setGravity(17);

                LinearLayout.LayoutParams lpEt = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT
                );
                lpEt.gravity = Gravity.RIGHT;

                TextView tvSoal = new TextView(getActivity());
                tvSoal.setText(pertanyaan);
                tvSoal.setPadding(10, 10, 10, 10);
                tvSoal.setWidth(500);


                EditText etSoal = new EditText(getActivity());
                etSoal.setHint("0");
                etSoal.setId(i);
                etSoal.setLayoutParams(lpEt);
                etSoal.setInputType(InputType.TYPE_CLASS_NUMBER);
                etSoal.setWidth(200);
                etSoal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                linearLayoutHorizontal.addView(tvSoal);
                linearLayoutHorizontal.addView(etSoal);
                linearLayout.addView(linearLayoutHorizontal);

        }
        return view;
    }

    @Override
    public void OnSelectedView(int position) {
        Log.d("asd","asda");
    }

}
