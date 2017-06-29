package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.bean.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpQueryRoleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpQueryRoleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpQueryRoleFragment extends Fragment implements View.OnClickListener {
    private Button nextStep;
    private ImageView roleNormalImage;
    private ImageView roleDietitianImage;
    private TextView roleNormalText;
    private TextView roleDietitianText;
    private OnFragmentInteractionListener listener;
    private User.Role selectedRole;

    public SignUpQueryRoleFragment() {
        // Required empty public constructor
    }

    public static SignUpQueryRoleFragment newInstance() {
        return new SignUpQueryRoleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view) {
        nextStep = (Button) view.findViewById(R.id.next_step);
        roleNormalImage = (ImageView) view.findViewById(R.id.role_normal_img);
        roleDietitianImage = (ImageView) view.findViewById(R.id.role_dietitian_img);
        roleNormalText = (TextView) view.findViewById(R.id.role_normal_txt);
        roleDietitianText = (TextView) view.findViewById(R.id.role_dietitian_txt);

        nextStep.setOnClickListener(this);
        roleNormalImage.setOnClickListener(this);
        roleDietitianImage.setOnClickListener(this);
        roleNormalText.setOnClickListener(this);
        roleDietitianText.setOnClickListener(this);

        highlightRoleNormal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_query_role, container, false);
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
            case R.id.next_step:
                listener.goToQueryPasswordView(selectedRole);
                break;
            case R.id.role_normal_img:
                // fall through
            case R.id.role_normal_txt:
                highlightRoleNormal();
                break;
            case R.id.role_dietitian_img:
                // fall through
            case R.id.role_dietitian_txt:
                highlightRoleDietitian();
                break;
            default:
                break;
        }
    }

    private void highlightRoleNormal() {
        roleNormalImage.setImageResource(R.mipmap.role_normal_selected);
        roleDietitianImage.setImageResource(R.mipmap.role_dietitian);
        roleNormalText.setTextColor(getResources().getColor(R.color.colorSelected));
        roleDietitianText.setTextColor(getResources().getColor(R.color.colorNotSelected));

        selectedRole = User.Role.normal;
    }

    private void highlightRoleDietitian() {
        roleNormalImage.setImageResource(R.mipmap.role_normal);
        roleDietitianImage.setImageResource(R.mipmap.role_dietitian_selected);
        roleNormalText.setTextColor(getResources().getColor(R.color.colorNotSelected));
        roleDietitianText.setTextColor(getResources().getColor(R.color.colorSelected));

        selectedRole = User.Role.dietitian;
    }

    public interface OnFragmentInteractionListener {
        void goToQueryPasswordView(User.Role role);
    }
}
