/*
 * Copyright 2015 AndroidPlot.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.pam.codenamehippie.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.androidplot.LineRegion;
import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.SeriesAndFormatter;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.TextOrientationType;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.ui.widget.TextLabelWidget;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYSeriesFormatter;
import com.pam.codenamehippie.HippieApplication;
import com.pam.codenamehippie.R;
import com.pam.codenamehippie.modele.TransactionModele;
import com.pam.codenamehippie.modele.depot.DepotManager;
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */

public class Stats_Activity extends HippieActivity
        implements ObservateurDeDepot<TransactionModele> {

    private enum SeriesSize {
        Dix,
        Vingt,
        Soixante
    }


    class MyBarFormatter extends BarFormatter {

        public MyBarFormatter(int fillColor, int borderColor) {
            super(fillColor, borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass() {
            return MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer getRendererInstance(XYPlot plot) {
            return new MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter> {

        public MyBarRenderer(XYPlot plot) {
            super(plot);
        }

        /**
         * Implementing this method to allow us to inject our
         * special selection getFormatter.
         *
         * @param index  index of the point being rendered.
         * @param series XYSeries to which the point being rendered belongs.
         * @return
         */
        @Override
        public MyBarFormatter getFormatter(int index, XYSeries series) {
            if (Stats_Activity.this.selection != null &&
                    Stats_Activity.this.selection.second == series &&
                    Stats_Activity.this.selection.first == index) {
                return Stats_Activity.this.selectionFormatter;
            } else {
                return this.getFormatter(series);
            }
        }
    }


    // Create a couple arrays of y-values to plot:
    Number[] series1Numbers10 = {2, null, 5, 2, 7, 4, 3, 7, 4, 5};

    Number[] series1Numbers = this.series1Numbers10;

    // private static final String NO_SELECTION_TXT = "Touch bar to select.";
    private XYPlot plot;
    private CheckBox series1CheckBox;

    private Spinner spRenderStyle, spWidthStyle, spSeriesSize;
    private SeekBar sbFixedWidth, sbVariableWidth;
    private XYSeries series1;

    private MyBarFormatter formatter1;

    private MyBarFormatter selectionFormatter;
    private TextLabelWidget selectionWidget;
    private Pair<Integer, XYSeries> selection;
    private Integer orgId;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TransactionModeleDepot transactionModeleDepot =
                DepotManager.getInstance().getTransactionModeleDepot();
//        transactionModeleDepot.ajouterUnObservateur(this);
        if (this.orgId != null && this.orgId != -1) {
            // TODO: Ajouter 2 DatePicker dans le layout list_statistique
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2016);
            Date dateDebut = calendar.getTime();
            calendar = Calendar.getInstance();
            Date dateFin = calendar.getTime();
            transactionModeleDepot.peuplerTransactions(this.orgId, dateDebut, dateFin);
        }
    }

    @Override
    public void surDebutDeRequete() {
        this.afficherLaProgressBar();
    }

    @Override
    public void surChangementDeDonnees(List<TransactionModele> modeles) {


    }

    @Override
    public void surFinDeRequete() {
        this.cacherLaProgressbar();
    }

    @Override
    public void surErreur(IOException e) {
        //todo: snackbar
        Log.e(this.getClass().getSimpleName(), e.getMessage(), e);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_stats);

        // initialize our XYPlot reference:
        this.plot = (XYPlot) this.findViewById(R.id.plot);

//        formatter1 = new MyBarFormatter(Color.argb(200, 88, 93, 89), Color.LTGRAY);
//        formatter2 = new MyBarFormatter(Color.argb(200, 145, 169, 145), Color.LTGRAY);
//        formatter3 = new MyBarFormatter(Color.argb(200, 148, 145, 169), Color.LTGRAY);
//        formatter4 = new MyBarFormatter(Color.argb(200, 106, 80, 87), Color.LTGRAY);
//        formatter5 = new MyBarFormatter(Color.argb(200, 169, 154, 128), Color.LTGRAY);
//        formatter6 = new MyBarFormatter(Color.argb(200, 70, 92, 70), Color.LTGRAY);
//        formatter7 = new MyBarFormatter(Color.argb(200, 74, 70, 92), Color.LTGRAY);
//        selectionFormatter = new MyBarFormatter(Color.argb(1000,224,222,84), Color.WHITE);

        this.formatter1 = new MyBarFormatter(Color.argb(200, 78, 130, 189), Color.LTGRAY);//Bleu

        this.selectionFormatter = new MyBarFormatter(Color.argb(1000, 224, 222, 84), Color.WHITE);

        /*formatter1 = new MyBarFormatter(Color.argb(200, 85, 87, 79), Color.LTGRAY);
        formatter2 = new MyBarFormatter(Color.argb(200, 166, 201, 85), Color.LTGRAY);
        formatter3 = new MyBarFormatter(Color.argb(200, 207, 215, 194), Color.LTGRAY);
        formatter4 = new MyBarFormatter(Color.argb(200, 76, 87, 53), Color.LTGRAY);
        formatter5 = new MyBarFormatter(Color.argb(200, 159, 164, 158), Color.LTGRAY);
        formatter6 = new MyBarFormatter(Color.argb(200, 93, 134, 59), Color.LTGRAY);
        formatter7 = new MyBarFormatter(Color.argb(200, 67, 101, 40), Color.LTGRAY);
        selectionFormatter = new MyBarFormatter(Color.argb(1000,224,222,84), Color.WHITE);*/

        this.selectionWidget = new TextLabelWidget(this.plot.getLayoutManager(),
                this.getString(R.string.stats_non_selectionne),

                new Size(PixelUtils.dpToPix(100),
                        SizeLayoutType.ABSOLUTE,
                        PixelUtils.dpToPix(100),
                        SizeLayoutType.ABSOLUTE),
                TextOrientationType.HORIZONTAL);

        this.selectionWidget.setPaddingLeft(10);

        this.selectionWidget.getLabelPaint().setTextSize(PixelUtils.dpToPix(20));

        // add a dark, semi-transparent background to the selection label widget:
        Paint p = new Paint();
        p.setARGB(100, 0, 0, 0);
        this.selectionWidget.setBackgroundPaint(p);

        this.selectionWidget.position(0, XLayoutStyle.RELATIVE_TO_CENTER, PixelUtils.dpToPix(60),
                YLayoutStyle.ABSOLUTE_FROM_TOP, AnchorPosition.TOP_MIDDLE);
        this.selectionWidget.pack();

        // reduce the number of range labels
        this.plot.setTicksPerRangeLabel(3);
        this.plot.setRangeLowerBoundary(0, BoundaryMode.FIXED);
        this.plot.getGraphWidget().getGridBox().setPadding(30, 10, 30, 0);

        this.plot.setTicksPerDomainLabel(2);

        // setup checkbox listers:
        this.series1CheckBox = (CheckBox) this.findViewById(R.id.tv_statistiques_valeur_total);
        this.series1CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS1CheckBoxClicked(b);
                    }
                });


        this.plot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Stats_Activity.this.onPlotClicked(
                            new PointF(motionEvent.getX(), motionEvent.getY()));
                }
                return true;
            }
        });

        this.spRenderStyle = (Spinner) this.findViewById(R.id.spRenderStyle);
        BarRenderer.BarRenderStyle arr[] =
                {BarRenderer.BarRenderStyle.SIDE_BY_SIDE, BarRenderer.BarRenderStyle.STACKED};
        ArrayAdapter<BarRenderer.BarRenderStyle> adapter =
                new ArrayAdapter<BarRenderer.BarRenderStyle>(this,
                        android.R.layout.simple_spinner_item,
                        arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spRenderStyle.setAdapter(adapter);
        this.spRenderStyle.setSelection(BarRenderer.BarRenderStyle.STACKED.ordinal());
        this.spRenderStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Stats_Activity.this.updatePlot();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        this.spWidthStyle = (Spinner) this.findViewById(R.id.spWidthStyle);
        ArrayAdapter<BarRenderer.BarWidthStyle> adapter1 =
                new ArrayAdapter<BarRenderer.BarWidthStyle>(this,
                        android.R.layout.simple_spinner_item,
                        BarRenderer.BarWidthStyle.values());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spWidthStyle.setAdapter(adapter1);
        this.spWidthStyle.setSelection(BarRenderer.BarWidthStyle.VARIABLE_WIDTH.ordinal());
        this.spWidthStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (BarRenderer.BarWidthStyle.FIXED_WIDTH.equals(
                        Stats_Activity.this.spWidthStyle.getSelectedItem())) {
                    Stats_Activity.this.sbFixedWidth.setVisibility(View.VISIBLE);
                    Stats_Activity.this.sbVariableWidth.setVisibility(View.INVISIBLE);
                } else {
                    Stats_Activity.this.sbFixedWidth.setVisibility(View.INVISIBLE);
                    Stats_Activity.this.sbVariableWidth.setVisibility(View.VISIBLE);
                }
                Stats_Activity.this.updatePlot();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        this.spSeriesSize = (Spinner) this.findViewById(R.id.spSeriesSize);
        ArrayAdapter<SeriesSize> adapter11 =
                new ArrayAdapter<SeriesSize>(this, android.R.layout.simple_spinner_item,
                        SeriesSize.values());
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spSeriesSize.setAdapter(adapter11);
        this.spSeriesSize.setSelection(SeriesSize.Dix.ordinal());
        this.spSeriesSize.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                switch ((SeriesSize) arg0.getSelectedItem()) {
                    case Dix:
                        Stats_Activity.this.series1Numbers = Stats_Activity.this.series1Numbers10;
                        break;
                    default:
                        break;
                }
                Stats_Activity.this.updatePlot();
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        this.sbFixedWidth = (SeekBar) this.findViewById(R.id.sbFixed);
        this.sbFixedWidth.setProgress(50);
        this.sbFixedWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int i,
                                          boolean b) {
                Stats_Activity.this.updatePlot();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        this.sbVariableWidth = (SeekBar) this.findViewById(R.id.sbVariable);
        this.sbVariableWidth.setProgress(1);
        this.sbVariableWidth.setVisibility(View.INVISIBLE);
        this.sbVariableWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int i,
                                          boolean b) {
                Stats_Activity.this.updatePlot();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        this.plot.setDomainValueFormat(new NumberFormat() {
            @Override
            public StringBuffer format(double value, StringBuffer buffer, FieldPosition field) {
                int year = (int) (value + 0.5d) / 12;
                int month = (int) ((value + 0.5d) % 12);
                return new StringBuffer(DateFormatSymbols.getInstance().getShortMonths()[month] +
                        " '0" +
                        year);
            }

            @Override
            public StringBuffer format(long value, StringBuffer buffer, FieldPosition field) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public Number parse(String string, ParsePosition position) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        });
        this.updatePlot();

    }

    private void updatePlot() {

        // Remove all current series from each plot
        this.plot.clear();

        // Setup our Series with the selected number of elements
        this.series1 = new SimpleXYSeries(Arrays.asList(this.series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Totale");


        // add a new series' to the xyplot:
        if (this.series1CheckBox.isChecked()) {
            this.plot.addSeries(this.series1, this.formatter1);
        }

        // Setup the BarRenderer with our selected options
        MyBarRenderer renderer = ((MyBarRenderer) this.plot.getRenderer(MyBarRenderer.class));
        renderer.setBarRenderStyle(
                (BarRenderer.BarRenderStyle) this.spRenderStyle.getSelectedItem());
        renderer.setBarWidthStyle((BarRenderer.BarWidthStyle) this.spWidthStyle.getSelectedItem());
        renderer.setBarWidth(this.sbFixedWidth.getProgress());
        renderer.setBarGap(this.sbVariableWidth.getProgress());

        if (BarRenderer.BarRenderStyle.STACKED.equals(this.spRenderStyle.getSelectedItem())) {
            this.plot.setRangeTopMin(60);
        } else {
            this.plot.setRangeTopMin(0);
        }

        this.plot.redraw();

    }

    private void onPlotClicked(PointF point) {

        // make sure the point lies within the graph area.  we use gridrect
        // because it accounts for margins and padding as well.
        if (this.plot.getGraphWidget()
                .getGridDimensions()
                .marginatedRect.contains(point.x, point.y)) {
            Number x = this.plot.getXVal(point);
            Number y = this.plot.getYVal(point);

            this.selection = null;
            double xDistance = 0;
            double yDistance = 0;

            // find the closest value to the selection:
            for (SeriesAndFormatter<XYSeries, ? extends XYSeriesFormatter> sfPair : this.plot
                    .getSeriesRegistry()) {
                XYSeries series = sfPair.getSeries();
                for (int i = 0; i < series.size(); i++) {
                    Number thisX = series.getX(i);
                    Number thisY = series.getY(i);
                    if (thisX != null && thisY != null) {
                        double thisXDistance = LineRegion.measure(x, thisX).doubleValue();
                        double thisYDistance = LineRegion.measure(y, thisY).doubleValue();
                        if (this.selection == null) {
                            this.selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance < xDistance) {
                            this.selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance == xDistance &&
                                thisYDistance < yDistance &&
                                thisY.doubleValue() >= y.doubleValue()) {
                            this.selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        }
                    }
                }
            }

        } else {
            // if the press was outside the graph area, deselect:
            this.selection = null;
        }

        if (this.selection == null) {
            this.selectionWidget.setText(this.getString(R.string.stats_non_selectionne));
            this.selectionWidget.setPaddingRight(35);
        } else {
            this.selectionWidget.setText(this.selection.second.getTitle() + " : " +
                    this.selection.second.getY(this.selection.first) + " $ ");
        }
        this.plot.redraw();
    }

    private void onS1CheckBoxClicked(boolean checked) {
        if (checked) {
            this.plot.addSeries(this.series1, this.formatter1);
        } else {
            this.plot.removeSeries(this.series1);
        }
        this.plot.redraw();
    }

}