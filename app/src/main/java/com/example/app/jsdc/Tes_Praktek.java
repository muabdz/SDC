package com.example.app.jsdc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.widget.Button;

import com.example.app.jsdc.R;
import com.example.app.jsdc.Utils.SelectedFragment;
import com.example.app.jsdc.Utils.SessionManager;
>>>>>>> f58a6028c08a874005f4241fc8a50bf07c7777d4


public class Tes_Praktek extends Fragment implements SelectedFragment {
    // Store instance variables
    private int page;
    int jumlahSoal;
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
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*jumlahSoal = sessionManager.getJumlahSoal();
        for (int i = 0; i<jumlahSoal; i++){
            String pertanyaan = sessionManager.getQuestion(sessionManager.getQuestionId(i));
        }*/
        View view = inflater.inflate(R.layout.fragment_tes__praktek, container, false);
        return view;
    }

    @Override
    public void OnSelectedView(int position) {
        Log.d("asd","asda");
    }
}
