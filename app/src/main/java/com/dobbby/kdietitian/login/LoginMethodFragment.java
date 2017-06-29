package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dobbby.kdietitian.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginMethodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginMethodFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;

    public LoginMethodFragment() {
        // Required empty public constructor
    }

    public static LoginMethodFragment newInstance() {
        return new LoginMethodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view) {
        view.findViewById(R.id.login_by_tel).setOnClickListener(this);
        view.findViewById(R.id.sign_up_by_tel).setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_method, container, false);
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
            case R.id.login_by_tel:
                listener.goToLoginByTelView();
                break;
            case R.id.sign_up_by_tel:
                listener.goToSignUpByTelView();
                break;
            default:
                break;
        }
    }

    interface OnFragmentInteractionListener {
        void goToLoginByTelView();

        void goToSignUpByTelView();
    }
}
