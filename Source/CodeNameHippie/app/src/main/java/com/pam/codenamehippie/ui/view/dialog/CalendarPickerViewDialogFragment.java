package com.pam.codenamehippie.ui.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pam.codenamehippie.R;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

import java.util.Calendar;
import java.util.Date;

public class CalendarPickerViewDialogFragment extends AppCompatDialogFragment
        implements OnDateSelectedListener {

    public interface OnDismissListener {

        void onDismiss(CalendarPickerViewDialogFragment fragment, DialogInterface dialog);
    }

    public interface OnCancelListener{
        void onCancel(CalendarPickerViewDialogFragment fragment, DialogInterface dialogInterface);
    }

    public static final class Builder {

        private Date minDate;
        private Date maxDate;
        private Date selectedDate;
        private SelectionMode selectionMode;

        private Builder() {
            this.minDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 10);
            this.maxDate = calendar.getTime();
            this.selectedDate = this.minDate;
            this.selectionMode = SelectionMode.SINGLE;
        }

        /**
         * Construit le fragment
         *
         * @return un nouveau {@link CalendarPickerViewDialogFragment}
         */
        public CalendarPickerViewDialogFragment pisCestTout() {
            Bundle args = new Bundle();
            CalendarPickerViewDialogFragment fragment = new CalendarPickerViewDialogFragment();
            args.putSerializable(MIN_DATE, this.minDate);
            args.putSerializable(MAX_DATE, this.maxDate);
            args.putSerializable(SELECTED_DATE, this.selectedDate);
            args.putSerializable(SELECTION_MODE, this.selectionMode);
            fragment.setArguments(args);
            return fragment;
        }

        public Builder avecCetteDateMaximale(@NonNull Date date) {
            if (date.after(this.minDate)) {
                this.maxDate = date;
            }
            return this;
        }

        public Date dateMaximale() {
            return this.maxDate;
        }

        public Builder avecCetteDateMinimale(@NonNull Date date) {
            if (date.before(this.maxDate)) {
                this.minDate = date;
            }
            return this;
        }

        public Date dateMinimale() {
            return this.minDate;
        }

        public Date selectDate() {
            return this.selectedDate;
        }

        public Builder avecCetteDateSelectionnee(@NonNull Date date) {
            if (((this.minDate.before(date) || this.minDate.equals(date)) &&
                 (this.maxDate.after(date)))) {
                this.selectedDate = date;
            }
            return this;
        }

        public Builder enModeDeSelection(SelectionMode mode) {
            this.selectionMode = mode;
            return this;
        }

        public SelectionMode modeDeSelection() {
            return this.selectionMode;
        }
    }

    private static final String MIN_DATE = "minDate";
    private static final String MAX_DATE = "maxDate";
    private static final String SELECTED_DATE = "selectedDate";
    private static final String SELECTION_MODE = "selectionMode";

    private Date minDate;
    private Date maxDate;
    private Date selectedDate;
    private SelectionMode selectionMode;
    private OnDateSelectedListener onDateSelectedListener;
    private OnDismissListener onDismissListener;
    private OnCancelListener onCancelListener;
    private CalendarPickerView view;

    public CalendarPickerViewDialogFragment() {
        this.minDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        this.maxDate = calendar.getTime();
        this.selectedDate = this.minDate;
        this.selectionMode = SelectionMode.SINGLE;
    }



    public static Builder assigneUnNouveauFragment() {
        return new Builder();
    }

    public CalendarPickerView getCalendar() {
        return this.view;
    }

    public SelectionMode modeDeSelection() {
        return this.selectionMode;
    }

    public Date dateSelectionee() {
        return this.selectedDate;
    }

    public CalendarPickerViewDialogFragment setOnDateSelectedListener(OnDateSelectedListener
                                                                              listener) {
        this.onDateSelectedListener = listener;
        return this;
    }

    public CalendarPickerViewDialogFragment setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = (savedInstanceState != null) ? savedInstanceState : this.getArguments();
        if (args != null) {
            if (args.containsKey(MIN_DATE)) {
                this.minDate = ((Date) args.getSerializable(MIN_DATE));
            }
            if (args.containsKey(MAX_DATE)) {
                this.maxDate = ((Date) args.getSerializable(MAX_DATE));
            }
            if (args.containsKey(SELECTED_DATE)) {
                this.selectedDate = ((Date) args.getSerializable(SELECTED_DATE));
            }
            if (args.containsKey(SELECTION_MODE)) {
                this.selectionMode = ((SelectionMode) args.getSerializable(SELECTION_MODE));
            }
        }
        this.setStyle(STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view =
                ((CalendarPickerView) inflater.inflate(R.layout.dialogfragment_calendarpickerview,
                                                       container, false));
        this.view.init(this.minDate, this.maxDate)
                 .withSelectedDate(this.selectedDate)
                 .inMode(this.selectionMode);
        this.view.setOnDateSelectedListener(this);
        return this.view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.view != null) {
            if (this.selectionMode == SelectionMode.SINGLE) {
                this.view.selectDate(this.selectedDate);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MIN_DATE, this.minDate);
        outState.putSerializable(MAX_DATE, this.maxDate);
        outState.putSerializable(SELECTED_DATE, this.selectedDate);
        outState.putSerializable(SELECTION_MODE, this.selectionMode);

    }

    @Override
    public void onDateSelected(Date date) {
        if (this.selectionMode == SelectionMode.SINGLE) {
            this.selectedDate = date;
            this.dismiss();
        }
        if (this.onDateSelectedListener != null) {
            this.onDateSelectedListener.onDateSelected(date);
        }
    }

    @Override
    public void onDateUnselected(Date date) {
        if (this.onDateSelectedListener != null) {
            this.onDateSelectedListener.onDateUnselected(date);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.onDismissListener != null) {
            this.onDismissListener.onDismiss(this, dialog);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
