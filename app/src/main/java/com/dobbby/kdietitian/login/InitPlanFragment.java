package com.dobbby.kdietitian.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import com.dobbby.kdietitian.R;
import com.dobbby.kdietitian.util.WbNumberPickerView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InitPlanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class InitPlanFragment extends Fragment implements View.OnClickListener, WbNumberPickerView.OnPickListener {
    private static final String TAG = InitPlanFragment.class.getSimpleName();

    private static final String ARG_CURR_WEIGHT = "currentWeight";
    private static final String ARG_AIM_WEIGHT = "aimWeight";

    private OnFragmentInteractionListener listener;
    private View view;
    private WbNumberPickerView weightPicker2;
    private WbNumberPickerView weightPicker3;
    private TextView xWeeksText;
    private TextView xDaysText;
    private Button finish;

    private float currentWeight;
    private float aimWeight;

    public InitPlanFragment() {
        // Required empty public constructor
    }

    public static InitPlanFragment newInstance(float currentWeight, float aimWeight) {
        InitPlanFragment fragment = new InitPlanFragment();
        Bundle args = new Bundle();
        args.putFloat(ARG_CURR_WEIGHT, currentWeight);
        args.putFloat(ARG_AIM_WEIGHT, aimWeight);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            currentWeight = args.getFloat(ARG_CURR_WEIGHT);
            aimWeight = args.getFloat(ARG_AIM_WEIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_init_plan, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        xWeeksText = (TextView) view.findViewById(R.id.x_weeks);
        xDaysText = (TextView) view.findViewById(R.id.x_days);
        finish = (Button) view.findViewById(R.id.finish);

        finish.setOnClickListener(this);

        initWbNumberPickers(view);
        initXWeeksXDays();
    }

    private void initWbNumberPickers(View view) {
        weightPicker2 = (WbNumberPickerView) view.findViewById(R.id.weight_picker2);
        weightPicker3 = (WbNumberPickerView) view.findViewById(R.id.weight_picker3);

        weightPicker2.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker2.setStartIndex(0);

        weightPicker3.setDisplayValues(WbNumberPickerView.generateDisplayList(0, 9, null));
        weightPicker3.setStartIndex(0);

        weightPicker2.addOnPickListener(this);
        weightPicker3.addOnPickListener(this);
    }

    private float parseWeightFromNumberPickers() {
        return weightPicker2.getCurrentIndex() * 0.1f
                + weightPicker3.getCurrentIndex() * 0.01f;
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
            case R.id.finish:
                listener.initPlan(parseWeightFromNumberPickers());
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrollChanged(int curIndex) {
    }

    @Override
    public void onScrollFinished(int curIndex) {
        view.post(new Runnable() {
            @Override
            public void run() {
                float dietPerWeek = parseWeightFromNumberPickers();

                Log.i(TAG, "onScrollFinished: dietPerWeek" + dietPerWeek);

                if (dietPerWeek == 0f) {
                    initXWeeksXDays();
                    return;
                }

                // currentWeight > aimWeight
                float weeks = Math.abs(currentWeight - aimWeight) / dietPerWeek;
                float days = weeks * 7;

                Calendar calendar = new GregorianCalendar();
                calendar.add(Calendar.DATE, (int) days);

                String xWeeksStr = getResources().getString(R.string.x_weeks)
                        .replace("%d", String.valueOf((int) weeks));

                String xDaysStr = getResources().getString(R.string.x_days)
                        .replaceFirst("%d", String.valueOf(calendar.get(Calendar.YEAR)))
                        .replaceFirst("%d", String.valueOf(calendar.get(Calendar.MONTH)))
                        .replaceFirst("%d", String.valueOf(calendar.get(Calendar.DATE)))
                        .replaceFirst("%d", String.valueOf((int) days));

                xWeeksText.setText(xWeeksStr);
                xDaysText.setText(xDaysStr);
            }
        });
    }

    private void initXWeeksXDays() {
        final String infinite = "∞";

        String xWeeksStr = getResources().getString(R.string.x_weeks)
                .replace("%d", infinite);

        String xDaysStr = getResources().getString(R.string.x_days)
                .replace("%d", infinite);

        xWeeksText.setText(xWeeksStr);
        xDaysText.setText(xDaysStr);
    }

    public interface OnFragmentInteractionListener {
        void initPlan(float dietPerWeek);
    }
}
