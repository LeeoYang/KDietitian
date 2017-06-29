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
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.bean.DietitianAuthQuery;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubmitDietitianAuthTextFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SubmitDietitianAuthTextFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;
    private TextInputEditText nameText;
    private TextInputEditText emailText;
    private TextInputEditText hospitalText;
    private TextInputEditText officeText;
    private TextInputEditText titleText;
    private Button nextStep;

    public SubmitDietitianAuthTextFragment() {
        // Required empty public constructor
    }

    public static SubmitDietitianAuthTextFragment newInstance() {
        return new SubmitDietitianAuthTextFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_dietitian_auth_text, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        nextStep = (Button) view.findViewById(R.id.next_step);
        nameText = (TextInputEditText) view.findViewById(R.id.name_text);
        emailText = (TextInputEditText) view.findViewById(R.id.email_text);
        hospitalText = (TextInputEditText) view.findViewById(R.id.hospital_text);
        officeText = (TextInputEditText) view.findViewById(R.id.office_text);
        titleText = (TextInputEditText) view.findViewById(R.id.title_text);

        nextStep.setOnClickListener(this);
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

    private DietitianAuthQuery readDietitianAuthQueryFormTexts() {
        if (TextUtils.isEmpty(nameText.getText())) {
            nameText.setError(getResources().getString(R.string.name_empty_error));
            nameText.requestFocus();
            return null;
        }

        if (TextUtils.isEmpty(emailText.getText())) {
            emailText.setError(getResources().getString(R.string.email_empty_error));
            emailText.requestFocus();
            return null;
        }

        if (TextUtils.isEmpty(hospitalText.getText())) {
            hospitalText.setError(getResources().getString(R.string.hospital_empty_error));
            hospitalText.requestFocus();
            return null;
        }

        if (TextUtils.isEmpty(officeText.getText())) {
            officeText.setError(getResources().getString(R.string.office_empty_error));
            officeText.requestFocus();
            return null;
        }

        if (TextUtils.isEmpty(titleText.getText())) {
            titleText.setError(getResources().getString(R.string.title_empty_error));
            titleText.requestFocus();
            return null;
        }

        return new DietitianAuthQuery()
                .setName(nameText.getText().toString())
                .setEmail(emailText.getText().toString())
                .setHospital(hospitalText.getText().toString())
                .setOffice(officeText.getText().toString())
                .setTitle(titleText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_step:
                DietitianAuthQuery dietitianAuthQuery = readDietitianAuthQueryFormTexts();

                if (dietitianAuthQuery != null) {
                    listener.submitAuthText(dietitianAuthQuery);
                }
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void submitAuthText(DietitianAuthQuery dietitianAuthQuery);
    }
}
