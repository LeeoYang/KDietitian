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
 * {@link SubmitDietitianAuthPhotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SubmitDietitianAuthPhotoFragment extends Fragment implements View.OnClickListener{
    private OnFragmentInteractionListener listener;
    private Button sendButton;

    public SubmitDietitianAuthPhotoFragment() {
        // Required empty public constructor
    }

    public static SubmitDietitianAuthPhotoFragment newInstance() {
        return new SubmitDietitianAuthPhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_dietitian_auth_photo, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        sendButton = (Button) view.findViewById(R.id.send_dietitian_auth_query);

        sendButton.setOnClickListener(this);
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
            case R.id.send_dietitian_auth_query:
                listener.sendDietitianAuthQuery();
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void sendDietitianAuthQuery();
    }
}
