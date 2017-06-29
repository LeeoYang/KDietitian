package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.util.WbNumberPickerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InitGoalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class InitGoalFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = InitGoalFragment.class.getSimpleName();

    private OnFragmentInteractionListener listener;
    private WbNumberPickerView weightPicker1;
    private WbNumberPickerView weightPicker2;
    private WbNumberPickerView weightPicker3;
    private ImageView loseWeightImage;
    private ImageView keepShapeImage;
    private Button nextStep;

    public InitGoalFragment() {
        // Required empty public constructor
    }

    public static InitGoalFragment newInstance() {
        return new InitGoalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init_goal, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        nextStep = (Button) view.findViewById(R.id.next_step);
        loseWeightImage = (ImageView) view.findViewById(R.id.lose_weight_img);
        keepShapeImage = (ImageView) view.findViewById(R.id.keep_shape_img);

        nextStep.setOnClickListener(this);
        loseWeightImage.setOnClickListener(this);
        keepShapeImage.setOnClickListener(this);

        initWbNumberPickers(view);
    }

    private void initWbNumberPickers(View view) {
        weightPicker1 = (WbNumberPickerView) view.findViewById(R.id.weight_picker1);
        weightPicker2 = (WbNumberPickerView) view.findViewById(R.id.weight_picker2);
        weightPicker3 = (WbNumberPickerView) view.findViewById(R.id.weight_picker3);

        weightPicker1.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker1.setStartIndex(5);

        weightPicker2.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker2.setStartIndex(0);

        weightPicker3.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker3.setStartIndex(0);
    }

    private float parseWeightFromNumberPickers() {
        return weightPicker1.getCurrentIndex() * 10
                + weightPicker2.getCurrentIndex()
                + weightPicker3.getCurrentIndex() * 0.1f;
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

    private void highlightLoseWeight() {
        loseWeightImage.setImageResource(R.drawable.selected_circle);
        keepShapeImage.setImageResource(R.drawable.unselected_circle);
    }

    private void highlightKeepShape() {
        loseWeightImage.setImageResource(R.drawable.unselected_circle);
        keepShapeImage.setImageResource(R.drawable.selected_circle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_step:
                listener.initAimWeight(parseWeightFromNumberPickers());
                break;
            case R.id.lose_weight_img:
                highlightLoseWeight();
                break;
            case R.id.keep_shape_img:
                highlightKeepShape();
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void initAimWeight(float aimWeight);
    }
}
