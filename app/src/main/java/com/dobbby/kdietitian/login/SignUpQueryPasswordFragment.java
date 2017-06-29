package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.util.WbToastFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpQueryPasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpQueryPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpQueryPasswordFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;
    private TextInputEditText nicknameEditText;
    private TextInputEditText passwordEditText;
    private TextView termTextView;
    private AppCompatCheckBox termCheckBox;
    private Button signUpButton;

    public SignUpQueryPasswordFragment() {
        // Required empty public constructor
    }

    public static SignUpQueryPasswordFragment newInstance() {
        return new SignUpQueryPasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view) {
        termTextView = (TextView) view.findViewById(R.id.term_of_use);
        termCheckBox = (AppCompatCheckBox) view.findViewById(R.id.term_checkbox);
        signUpButton = (Button) view.findViewById(R.id.sign_up);
        nicknameEditText = (TextInputEditText) view.findViewById(R.id.nickname);
        passwordEditText = (TextInputEditText) view.findViewById(R.id.password);

        termTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_query_password, container, false);
        init(view);
        return view;
    }

    public void showNicknameUsedError() {
        nicknameEditText.setError(getResources().getString(R.string.nickname_used_error));
        nicknameEditText.requestFocus();
    }

    public void setTermCheckBoxChecked(final boolean checked) {
        termCheckBox.post(new Runnable() {
            @Override
            public void run() {
                termCheckBox.setChecked(checked);
            }
        });
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

    private boolean checkIfTextInvalid() {
        if (TextUtils.isEmpty(nicknameEditText.getText())) {
            nicknameEditText.setError(getResources().getString(R.string.empty_nickname_error));
            nicknameEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(passwordEditText.getText())) {
            passwordEditText.setError(getResources().getString(R.string.empty_password_error));
            passwordEditText.requestFocus();
            return false;
        }

        if (passwordEditText.getText().toString().length() < 6) {
            passwordEditText.setError(getResources().getString(R.string.short_password_error));
            passwordEditText.requestFocus();
            return false;
        }

        return true;
    }

    public String getNickname() {
        return nicknameEditText.getText().toString();
    }

    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.term_of_use:
                listener.showTermOfUseDetails();
                break;
            case R.id.sign_up:
                if (checkIfTextInvalid()) {
                    if (termCheckBox.isChecked()) {
                        listener.signUp();
                    } else {
                        WbToastFactory.create(getContext(), getResources().getString(R.string.term_not_checked_error)).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void showTermOfUseDetails();

        void signUp();
    }
}
