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
 * {@link ThankForSupportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ThankForSupportFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener listener;

    public ThankForSupportFragment() {
        // Required empty public constructor
    }

    public static ThankForSupportFragment newInstance() {
        return new ThankForSupportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thank_for_support, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        Button continueToLogin = (Button) view.findViewById(R.id.continue_to_login);
        continueToLogin.setOnClickListener(this);
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
            case R.id.continue_to_login:
                listener.continueToLogin();
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void continueToLogin();
    }
}
