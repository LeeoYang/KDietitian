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
import android.widget.ImageButton;
import android.widget.TextView;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.util.WbToastFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;
    private TextInputEditText telNoEditText;
    private TextInputEditText passwordEditText;
    private ImageButton clearTextButton;
    private TextView forgetPasswordTextView;
    private TextView signUpTextView;
    private Button loginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private void init(View view) {
        telNoEditText = (TextInputEditText) view.findViewById(R.id.tel_no);
        passwordEditText = (TextInputEditText) view.findViewById(R.id.password);
        clearTextButton = (ImageButton) view.findViewById(R.id.clear_text);
        forgetPasswordTextView = (TextView) view.findViewById(R.id.forget_password);
        signUpTextView = (TextView) view.findViewById(R.id.sign_up);
        loginButton = (Button) view.findViewById(R.id.login);

        clearTextButton.setOnClickListener(this);
        forgetPasswordTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
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

    public void showWrongInfoError() {
        telNoEditText.setError(getResources().getString(R.string.wrong_tel_or_password));
        telNoEditText.requestFocus();
    }

    private boolean checkIfTextInValid() {
        if (TextUtils.isEmpty(telNoEditText.getText())) {
            telNoEditText.setError(getResources().getString(R.string.empty_tel_error));
            telNoEditText.requestFocus();
            return true;
        }

        if (telNoEditText.getText().toString().length() != 11) {
            telNoEditText.setError(getResources().getString(R.string.short_tel_error));
            telNoEditText.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(passwordEditText.getText())) {
            passwordEditText.setError(getResources().getString(R.string.empty_password_error));
            passwordEditText.requestFocus();
            return true;
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (!checkIfTextInValid()) {
                    listener.performLoginByTel(
                            telNoEditText.getText().toString(),
                            passwordEditText.getText().toString()
                    );
                }
                break;
            case R.id.sign_up:
                listener.goToSignUpByTelView();
                break;
            case R.id.clear_text:
                passwordEditText.setText("");
                break;
            case R.id.forget_password:
                WbToastFactory.create(getContext(), "活该, 哈哈 ...").show();
                break;
            default:
                break;
        }
    }

    interface OnFragmentInteractionListener {
        void backToSelectMethodView();

        void goToSignUpByTelView();

        void performLoginByTel(String tel, String password);
    }
}
