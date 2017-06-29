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
import com.dobbby.kdietitian.util.WbNumberPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InitGenderBirthdayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InitGenderBirthdayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InitGenderBirthdayFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;

    private WbNumberPickerView yearPicker;
    private WbNumberPickerView monthPicker;
    private WbNumberPickerView dayPicker;
    private ImageView maleImageButton;
    private ImageView femaleImageButton;
    private TextView maleText;
    private TextView femaleText;
    private Button nextStep;
    private String year;
    private String month;
    private String day;

    private User.Gender selectedGender;

    private final int startYear = 1920;
    private final int displayYear = 1999;

    public InitGenderBirthdayFragment() {
        // Required empty public constructor
    }

    public static InitGenderBirthdayFragment newInstance() {
        return new InitGenderBirthdayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        year = getResources().getString(R.string.year);
        month = getResources().getString(R.string.month);
        day = getResources().getString(R.string.day);
    }

    private void init(View view) {
        maleImageButton = (ImageView) view.findViewById(R.id.male_img);
        maleText = (TextView) view.findViewById(R.id.male_txt);
        femaleImageButton = (ImageView) view.findViewById(R.id.female_img);
        femaleText = (TextView) view.findViewById(R.id.female_txt);
        nextStep = (Button) view.findViewById(R.id.next_step);

        maleImageButton.setOnClickListener(this);
        maleText.setOnClickListener(this);
        femaleImageButton.setOnClickListener(this);
        femaleText.setOnClickListener(this);
        nextStep.setOnClickListener(this);

        highlightMale();

        initWbNumberPickers(view);
    }

    private void initWbNumberPickers(View view) {
        yearPicker = (WbNumberPickerView) view.findViewById(R.id.year_picker);
        monthPicker = (WbNumberPickerView) view.findViewById(R.id.month_picker);
        dayPicker = (WbNumberPickerView) view.findViewById(R.id.day_picker);

        yearPicker.setDisplayValues(getDisplayYears());
        monthPicker.setDisplayValues(getDisplayMonths());
        dayPicker.setDisplayValues(getDisplayDays());

        yearPicker.setStartIndex(displayYear - startYear);

        addListenerToYearPicker();
        addListenerToMonthPicker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init_gender_birth, container, false);
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

    private void addListenerToMonthPicker() {
        final int[] dayCount = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        final int February = 1;

        monthPicker.addOnPickListener(new WbNumberPickerView.OnPickListener() {
            @Override
            public void onScrollChanged(int curIndex) {
            }

            @Override
            public void onScrollFinished(int curIndex) {
                if (curIndex == February) {
                    int selectedYear = startYear + yearPicker.getCurrentIndex();

                    if (isLeapYear(selectedYear)) {
                        dayCount[February] = 29;
                    } else {
                        dayCount[February] = 28;
                    }
                }

                dayPicker.setDisplayValues(WbNumberPickerView.generateDisplayList(1, dayCount[curIndex], day));
                dayPicker.setStartIndex(0);
            }
        });
    }

    private boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        }
        return year % 4 == 0;
    }

    private void addListenerToYearPicker() {
        yearPicker.addOnPickListener(new WbNumberPickerView.OnPickListener() {
            @Override
            public void onScrollChanged(int curIndex) {
            }

            @Override
            public void onScrollFinished(int curIndex) {
                monthPicker.scrollTo(0);
                dayPicker.scrollTo(0);
            }
        });
    }

    private List<String> getDisplayYears() {
        return WbNumberPickerView.generateDisplayList(
                startYear,
                Calendar.getInstance().get(Calendar.YEAR),
                year
        );
    }

    private List<String> getDisplayMonths() {
        return WbNumberPickerView.generateDisplayList(1, 12, month);
    }

    private List<String> getDisplayDays() {
        return WbNumberPickerView.generateDisplayList(1, 31, day);
    }

    private void highlightMale() {
        maleImageButton.setImageResource(R.mipmap.male_selected);
        maleText.setTextColor(getResources().getColor(R.color.colorSelected));
//        maleText.setVisibility(View.VISIBLE);
        femaleImageButton.setImageResource(R.mipmap.female);
        femaleText.setTextColor(getResources().getColor(R.color.colorNotSelected));
//        femaleText.setVisibility(View.GONE);

        selectedGender = User.Gender.male;
    }

    private void highlightFemale() {
        maleImageButton.setImageResource(R.mipmap.male);
        maleText.setTextColor(getResources().getColor(R.color.colorNotSelected));
//        maleText.setVisibility(View.GONE);
        femaleImageButton.setImageResource(R.mipmap.female_selected);
        femaleText.setTextColor(getResources().getColor(R.color.colorSelected));
//        femaleText.setVisibility(View.VISIBLE);

        selectedGender = User.Gender.female;
    }

    private Date parseBirthdayFromNumberPickers() {
        Calendar calendar = new GregorianCalendar();

        calendar.set(
                startYear + yearPicker.getCurrentIndex(),
                monthPicker.getCurrentIndex(),
                dayPicker.getCurrentIndex() + 1
        );

        return calendar.getTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.male_img:
                // fall through
            case R.id.male_txt:
                highlightMale();
                break;
            case R.id.female_img:
                // fall through
            case R.id.female_txt:
                highlightFemale();
                break;
            case R.id.next_step:
                listener.initGenderBirthday(selectedGender, parseBirthdayFromNumberPickers());
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void initGenderBirthday(User.Gender gender, Date birthday);
    }
}
