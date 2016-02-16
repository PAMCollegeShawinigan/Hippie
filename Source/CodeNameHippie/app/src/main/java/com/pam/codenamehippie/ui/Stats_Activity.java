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
import com.pam.codenamehippie.modele.depot.ObservateurDeDepot;
import com.pam.codenamehippie.modele.depot.TransactionModeleDepot;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;
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
         * @param index
         *         index of the point being rendered.
         * @param series
         *         XYSeries to which the point being rendered belongs.
         *
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
    Number[] series2Numbers10 = {4, 6, 3, null, 2, 0, 7, 4, 5, 4};
    Number[] series3Numbers10 = {1, 2, 4, 0, 1, 3, 5, 7, 9, null};
    Number[] series4Numbers10 = {5, 6, 7, null, 0, 9, 8, 7, 6, 5};
    Number[] series5Numbers10 = {9, 0, 1, 5, 2, 4, 6, 8, 3, null};
    Number[] series6Numbers10 = {2, 5, 1, 6, null, 7, 9, 1, 0, 9};
    Number[] series7Numbers10 = {3, 2, 6, 9, 2, 4, null, 2, 9, 8};
    Number[] series1Numbers20 = {2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3};
    Number[] series2Numbers20 = {4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 7, 9};
    Number[] series3Numbers20 = {8, 8, 9, null, 1, 3, 9, 2, 7, 2, 11, 3, 1, 2, 7, 5, 0, 1, 4, 10};
    Number[] series4Numbers20 = {2, 6, 7, 8, 9, 10, 1, null, 5, 7, 8, 9, 1, 2, 3, 5, 4, 3, 21, 1};
    Number[] series5Numbers20 = {1, 4, null, 8, 9, 2, 3, 0, 9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 9};
    Number[] series6Numbers20 = {12, 13, 4, 8, 9, null, 9, 3, 5, 6, 7, 1, 4, 7, 8, 8, 1, 2, 19, 1};
    Number[] series7Numbers20 =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    Number[] series1Numbers60 = {
            2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3, 2, null, 5, 2, 7, 4, 3,
            7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3, 2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5,
            3, 6, 3, 9, 3
    };
    Number[] series2Numbers60 = {
            4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7,
            4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4,
            0, 7, 4, 7, 9
    };
    Number[] series3Numbers60 = {
            1, 5, 2, null, 0, 4, 5, 6, 1, 2, 3, 4, 9, 8, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3,
            7, 6, 5, 4, 3, 2, 9, 1, 3, 8, 1, 2, 3, 5, 8, 9, null, 1, 0, 7, 6, 5, 8, 8, 1, 3, 4, 2,
            3, 0, 2, 3, 5
    };
    Number[] series4Numbers60 = {
            7, 4, 5, 4, 9, 6, null, 8, 4, 0, 7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2,
            8, 4, 0, 7, 4, 2, 3, 4, 9, 8, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3,
            2, 9, 1, 7, 7
    };
    Number[] series5Numbers60 = {
            7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 2, 3, 4, 9, 8, 7,
            6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3, 2, 9, 1, 7, 7, 4, 5, 4, 9, 6,
            null, 7, 9, 3, 5
    };
    Number[] series6Numbers60 = {
            0, 4, 5, 6, 1, 2, 3, 4, 9, 8, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3,
            2, 9, 1, 3, 8, 1, 2, 3, 5, 8, 9, null, 1, 0, 7, 6, 5, 8, 8, 1, 3, 4, 2, 3, 0, 2, 3, 5,
            7, 4, 8, 1, 8
    };
    Number[] series7Numbers60 = {
            2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3, 2, null, 5, 2, 7, 4, 3,
            7, 4, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3, 2, 9, 1, 3, 8, 1, 2, 3,
            5, 8, 9, 9, 9
    };
    Number[] series1Numbers = this.series1Numbers10;
    Number[] series2Numbers = this.series2Numbers10;
    Number[] series3Numbers = this.series3Numbers10;
    Number[] series4Numbers = this.series4Numbers10;
    Number[] series5Numbers = this.series5Numbers10;
    Number[] series6Numbers = this.series6Numbers10;
    Number[] series7Numbers = this.series7Numbers10;
    // private static final String NO_SELECTION_TXT = "Touch bar to select.";
    private XYPlot plot;
    private CheckBox series1CheckBox;
    private CheckBox series2CheckBox;
    private CheckBox series3CheckBox;
    private CheckBox series4CheckBox;
    private CheckBox series5CheckBox;
    private CheckBox series6CheckBox;
    private CheckBox series7CheckBox;
    private Spinner spRenderStyle, spWidthStyle, spSeriesSize;
    private SeekBar sbFixedWidth, sbVariableWidth;
    private XYSeries series1;
    private XYSeries series2;
    private XYSeries series3;
    private XYSeries series4;
    private XYSeries series5;
    private XYSeries series6;
    private XYSeries series7;
    private MyBarFormatter formatter1;
    private MyBarFormatter formatter2;
    private MyBarFormatter formatter3;
    private MyBarFormatter formatter4;
    private MyBarFormatter formatter5;
    private MyBarFormatter formatter6;
    private MyBarFormatter formatter7;
    private MyBarFormatter selectionFormatter;
    private TextLabelWidget selectionWidget;
    private Pair<Integer, XYSeries> selection;

    @Override
    protected void onPause() {
        super.onPause();
        TransactionModeleDepot depot =
                ((HippieApplication) this.getApplication()).getTransactionModeleDepot();
        depot.setFiltreDeListe(null);
        depot.supprimerTousLesObservateurs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        TransactionModeleDepot depot =
                ((HippieApplication) this.getApplication()).getTransactionModeleDepot();
        depot.ajouterUnObservateur(this);
        //On va recevoir les donnees de TransactionModeleDepot
        //   depot.peuplerListeOrganisme();

    }

    @Override
    public void surDebutDeRequete() {

    }

    @Override
    public void surChangementDeDonnees(List<TransactionModele> modeles) {

    }

    @Override
    public void surFinDeRequete() {

    }

    @Override
    public void surErreur(IOException e) {

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
        this.formatter2 = new MyBarFormatter(Color.argb(200, 194, 80, 79), Color.LTGRAY);//Rouge
        this.formatter3 = new MyBarFormatter(Color.argb(200, 156, 187, 88), Color.LTGRAY);//vert
        this.formatter4 = new MyBarFormatter(Color.argb(200, 46, 181, 164), Color.LTGRAY);//lime
        this.formatter5 = new MyBarFormatter(Color.argb(200, 128, 100, 162), Color.LTGRAY);//Violet
        this.formatter6 =
                new MyBarFormatter(Color.argb(200, 67, 172, 197), Color.LTGRAY);//Bleu pâle
        this.formatter7 =
                new MyBarFormatter(Color.argb(200, 50, 86, 140), Color.LTGRAY);//Bleu marin
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
        this.series1CheckBox = (CheckBox) this.findViewById(R.id.cat_boulangerie);
        this.series1CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS1CheckBoxClicked(b);
                    }
                });

        this.series2CheckBox = (CheckBox) this.findViewById(R.id.cat_fruits_et_legumes);
        this.series2CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS2CheckBoxClicked(b);
                    }
                });

        this.series3CheckBox = (CheckBox) this.findViewById(R.id.cat_Produits_laitiers);
        this.series3CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS3CheckBoxClicked(b);
                    }
                });

        this.series4CheckBox = (CheckBox) this.findViewById(R.id.cat_surgelés);
        this.series4CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS4CheckBoxClicked(b);
                    }
                });

        this.series5CheckBox = (CheckBox) this.findViewById(R.id.cat_viandes);
        this.series5CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS5CheckBoxClicked(b);
                    }
                });

        this.series6CheckBox = (CheckBox) this.findViewById(R.id.cat_non_périssable);
        this.series6CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS6CheckBoxClicked(b);
                    }
                });

        this.series7CheckBox = (CheckBox) this.findViewById(R.id.cat_non_comestible);
        this.series7CheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Stats_Activity.this.onS7CheckBoxClicked(b);
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
        this.spWidthStyle.setSelection(BarRenderer.BarWidthStyle.FIXED_WIDTH.ordinal());
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
                        Stats_Activity.this.series2Numbers = Stats_Activity.this.series2Numbers10;
                        Stats_Activity.this.series3Numbers = Stats_Activity.this.series3Numbers10;
                        Stats_Activity.this.series4Numbers = Stats_Activity.this.series4Numbers10;
                        Stats_Activity.this.series5Numbers = Stats_Activity.this.series5Numbers10;
                        Stats_Activity.this.series6Numbers = Stats_Activity.this.series6Numbers10;
                        Stats_Activity.this.series7Numbers = Stats_Activity.this.series7Numbers10;

                        break;
                    case Vingt:
                        Stats_Activity.this.series1Numbers = Stats_Activity.this.series1Numbers20;
                        Stats_Activity.this.series2Numbers = Stats_Activity.this.series2Numbers20;
                        Stats_Activity.this.series3Numbers = Stats_Activity.this.series3Numbers20;
                        Stats_Activity.this.series4Numbers = Stats_Activity.this.series4Numbers20;
                        Stats_Activity.this.series5Numbers = Stats_Activity.this.series5Numbers20;
                        Stats_Activity.this.series6Numbers = Stats_Activity.this.series6Numbers20;
                        Stats_Activity.this.series7Numbers = Stats_Activity.this.series7Numbers20;

                        break;
                    case Soixante:
                        Stats_Activity.this.series1Numbers = Stats_Activity.this.series1Numbers60;
                        Stats_Activity.this.series2Numbers = Stats_Activity.this.series2Numbers60;
                        Stats_Activity.this.series3Numbers = Stats_Activity.this.series3Numbers60;
                        Stats_Activity.this.series4Numbers = Stats_Activity.this.series4Numbers60;
                        Stats_Activity.this.series5Numbers = Stats_Activity.this.series5Numbers60;
                        Stats_Activity.this.series6Numbers = Stats_Activity.this.series6Numbers60;
                        Stats_Activity.this.series7Numbers = Stats_Activity.this.series7Numbers60;

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
                                          boolean b) {Stats_Activity.this.updatePlot();}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        this.sbVariableWidth = (SeekBar) this.findViewById(R.id.sbVariable);
        this.sbVariableWidth.setProgress(1);
        this.sbVariableWidth.setVisibility(View.INVISIBLE);
        this.sbVariableWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int i,
                                          boolean b) {Stats_Activity.this.updatePlot();}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
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
                                          SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "1");
        this.series2 = new SimpleXYSeries(Arrays.asList(this.series2Numbers),
                                          SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "2");
        this.series3 = new SimpleXYSeries(Arrays.asList(this.series3Numbers),
                                          SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "3");
        this.series4 = new SimpleXYSeries(Arrays.asList(this.series4Numbers),
                                          SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "4");
        this.series5 = new SimpleXYSeries(Arrays.asList(this.series5Numbers),
                                          SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "5");
        this.series6 = new SimpleXYSeries(Arrays.asList(this.series6Numbers),
                                          SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "6");
        this.series7 = new SimpleXYSeries(Arrays.asList(this.series7Numbers),
                                          SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "7");

        // add a new series' to the xyplot:
        if (this.series1CheckBox.isChecked()) {
            this.plot.addSeries(this.series1, this.formatter1);
        }
        if (this.series2CheckBox.isChecked()) {
            this.plot.addSeries(this.series2, this.formatter2);
        }
        if (this.series3CheckBox.isChecked()) {
            this.plot.addSeries(this.series3, this.formatter3);
        }
        if (this.series4CheckBox.isChecked()) {
            this.plot.addSeries(this.series4, this.formatter4);
        }
        if (this.series5CheckBox.isChecked()) {
            this.plot.addSeries(this.series5, this.formatter5);
        }
        if (this.series6CheckBox.isChecked()) {
            this.plot.addSeries(this.series6, this.formatter6);
        }
        if (this.series7CheckBox.isChecked()) {
            this.plot.addSeries(this.series7, this.formatter7);
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

    private void onS2CheckBoxClicked(boolean checked) {
        if (checked) {
            this.plot.addSeries(this.series2, this.formatter2);
        } else {
            this.plot.removeSeries(this.series2);
        }
        this.plot.redraw();
    }

    private void onS3CheckBoxClicked(boolean checked) {
        if (checked) {
            this.plot.addSeries(this.series3, this.formatter3);
        } else {
            this.plot.removeSeries(this.series3);
        }
        this.plot.redraw();
    }

    private void onS4CheckBoxClicked(boolean checked) {
        if (checked) {
            this.plot.addSeries(this.series4, this.formatter4);
        } else {
            this.plot.removeSeries(this.series4);
        }
        this.plot.redraw();
    }

    private void onS5CheckBoxClicked(boolean checked) {
        if (checked) {
            this.plot.addSeries(this.series5, this.formatter5);
        } else {
            this.plot.removeSeries(this.series5);
        }
        this.plot.redraw();
    }

    private void onS6CheckBoxClicked(boolean checked) {
        if (checked) {
            this.plot.addSeries(this.series6, this.formatter6);
        } else {
            this.plot.removeSeries(this.series6);
        }
        this.plot.redraw();
    }

    private void onS7CheckBoxClicked(boolean checked) {
        if (checked) {
            this.plot.addSeries(this.series7, this.formatter7);
        } else {
            this.plot.removeSeries(this.series7);
        }
        this.plot.redraw();
    }
}
