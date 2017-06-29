package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.dobbby.kdietitian.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpTermFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpTermFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpTermFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;
    private Button agreeButton;
    private Button disagreeButton;

    public SignUpTermFragment() {
        // Required empty public constructor
    }

    public static SignUpTermFragment newInstance() {
        return new SignUpTermFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view) {
        agreeButton = (Button) view.findViewById(R.id.agree_to_term);
        disagreeButton = (Button) view.findViewById(R.id.disagree_to_term);

        agreeButton.setOnClickListener(this);
        disagreeButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_term, container, false);
        init(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnFragmentInteractionListener"
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agree_to_term:
                listener.agreeToTerm();
                break;
            case R.id.disagree_to_term:
                listener.disagreeToTerm();
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void agreeToTerm();

        void disagreeToTerm();
    }
}
