package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.util.WbNumberPickerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InitHeightWeightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class InitHeightWeightFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = InitHeightWeightFragment.class.getSimpleName();

    private OnFragmentInteractionListener listener;
    private WbNumberPickerView heightPicker1;
    private WbNumberPickerView heightPicker2;
    private WbNumberPickerView heightPicker3;
    private WbNumberPickerView weightPicker1;
    private WbNumberPickerView weightPicker2;
    private WbNumberPickerView weightPicker3;
    private Button nextStep;

    public InitHeightWeightFragment() {
        // Required empty public constructor
    }

    public static InitHeightWeightFragment newInstance() {
        return new InitHeightWeightFragment();
    }

    private void initWbNumberPickers(View view) {
        heightPicker1 = (WbNumberPickerView) view.findViewById(R.id.height_picker1);
        heightPicker2 = (WbNumberPickerView) view.findViewById(R.id.height_picker2);
        heightPicker3 = (WbNumberPickerView) view.findViewById(R.id.height_picker3);
        weightPicker1 = (WbNumberPickerView) view.findViewById(R.id.weight_picker1);
        weightPicker2 = (WbNumberPickerView) view.findViewById(R.id.weight_picker2);
        weightPicker3 = (WbNumberPickerView) view.findViewById(R.id.weight_picker3);

        heightPicker1.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 2, null));
        heightPicker1.setStartIndex(1);

        heightPicker2.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        heightPicker2.setStartIndex(5);

        heightPicker3.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        heightPicker3.setStartIndex(0);

        weightPicker1.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker1.setStartIndex(5);

        weightPicker2.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker2.setStartIndex(0);

        weightPicker3.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker3.setStartIndex(0);
    }

    private void init(View view) {
        nextStep = (Button) view.findViewById(R.id.next_step);

        nextStep.setOnClickListener(this);

        initWbNumberPickers(view);
    }

    private int parseHeightFromNumberPickers() {
        return heightPicker1.getCurrentIndex() * 100
                + heightPicker2.getCurrentIndex() * 10
                + heightPicker3.getCurrentIndex();
    }

    private float parseWeightFromNumberPickers() {
        return weightPicker1.getCurrentIndex() * 10
                + weightPicker2.getCurrentIndex()
                + weightPicker3.getCurrentIndex() * 0.1f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init_height_weight, container, false);
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
                listener.initHeightWeight(parseHeightFromNumberPickers(), parseWeightFromNumberPickers());
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void initHeightWeight(float height, float weight);
    }
}
