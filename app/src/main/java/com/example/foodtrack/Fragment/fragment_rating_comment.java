package com.example.foodtrack.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_rating_comment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_rating_comment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView btn_back;
    private TextView btn_GuiCamNhan;
    private EditText textArea_Comment;


    public fragment_rating_comment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_rating_comment.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_rating_comment newInstance(String param1, String param2) {
        fragment_rating_comment fragment = new fragment_rating_comment();
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
        View view = inflater.inflate(R.layout.fragment_rating_comment, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    private void Mapping(View view){
        btn_back = (ImageView) view.findViewById(R.id.btn_back_rating_comment);
        btn_GuiCamNhan = (TextView) view.findViewById(R.id.btn_GuiCamNhan);
        textArea_Comment = (EditText) view.findViewById(R.id.textArea_Comment);
    }

    private void ControlButton(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_GuiCamNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textArea_Comment.setText("");
                CreatePopup(view);


            }
        });
    }

    private void CreatePopup(View view){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        View popupView = inflater.inflate(R.layout.popup_submit_cmt, null) ;

        PopupWindow popupWindow = new PopupWindow(popupView ,width, height, focusable);

        view.post(new Runnable(){
            @Override
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
        TextView okBtn;
        okBtn = (TextView) popupView.findViewById(R.id.btn_ok_popup_submit_cmt);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                if(mainActivity!=null){
                    mainActivity.ReplaceFragment(new fragment_myorders_history());
                }
            }
        });
    }
}