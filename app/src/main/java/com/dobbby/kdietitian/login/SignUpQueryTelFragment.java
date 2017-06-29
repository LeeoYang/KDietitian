package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.dobbby.kdietitian.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpQueryTelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpQueryTelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpQueryTelFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;
    private TextInputEditText telNoText;
    private TextView queryPin;
    private Button nextStep;

    public SignUpQueryTelFragment() {
        // Required empty public constructor
    }

    public static SignUpQueryTelFragment newInstance() {
        return new SignUpQueryTelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view) {
        nextStep = (Button) view.findViewById(R.id.next_step);
        queryPin = (TextView) view.findViewById(R.id.query_pin);
        telNoText = (TextInputEditText) view.findViewById(R.id.tel_no);

        nextStep.setOnClickListener(this);
        queryPin.setOnClickListener(this);
    }

    public String getTelNo() {
        return telNoText.getText().toString();
    }

    public void showTelUsedError() {
        telNoText.setError(getResources().getString(R.string.tel_used_error));
        telNoText.requestFocus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_query_tel, container, false);
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

    private boolean checkIfTelNoValid() {
        if (TextUtils.isEmpty(telNoText.getText())) {
            telNoText.setError(getResources().getString(R.string.empty_tel_error));
            telNoText.requestFocus();
            return false;
        }

        if (telNoText.getText().toString().length() != 11) {
            telNoText.setError(getResources().getString(R.string.short_tel_error));
            telNoText.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_step:
                listener.goToSelectRoleView();
                break;
            case R.id.query_pin:
                if (checkIfTelNoValid()) {
                    listener.queryPin(telNoText.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void goToSelectRoleView();

        void queryPin(String telNo);
    }
}
