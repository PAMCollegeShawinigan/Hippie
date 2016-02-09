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

import android.app.Activity;
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
import com.pam.codenamehippie.R;

import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;

/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class Stats_Activity extends HippieActivity
{

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
    private enum SeriesSize {
        Dix,
        Vingt,
        Soixante
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
    Number[] series6Numbers20 = {12, 13, 4, 8, 9, null, 9, 3, 5, 6, 7, 1, 4 , 7, 8, 8, 1, 2, 19, 1};
    Number[] series7Numbers20 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    Number[] series1Numbers60 = {2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3, 2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3, 2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3};
    Number[] series2Numbers60 = {4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 7, 9};
    Number[] series3Numbers60 = {1, 5, 2, null, 0, 4, 5, 6, 1, 2, 3, 4, 9, 8, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3, 2, 9, 1, 3, 8, 1, 2, 3, 5, 8, 9, null, 1, 0, 7, 6, 5, 8, 8, 1, 3, 4, 2, 3, 0, 2, 3, 5};
    Number[] series4Numbers60 = {7, 4, 5, 4, 9, 6, null, 8, 4, 0, 7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 2, 3, 4, 9, 8, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3, 2, 9, 1, 7, 7};
    Number[] series5Numbers60 = {7, 4, 7, 9, 4, 6, 3, null, 2, 0, 7, 4, 5, 4, 9, 6, 2, 8, 4, 0, 7, 4, 2, 3, 4, 9, 8, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3, 2, 9, 1, 7, 7, 4, 5, 4, 9, 6, null, 7, 9, 3, 5};
    Number[] series6Numbers60 = {0, 4, 5, 6, 1, 2, 3, 4, 9, 8, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3, 2, 9, 1, 3, 8, 1, 2, 3, 5, 8, 9, null, 1, 0, 7, 6, 5, 8, 8, 1, 3, 4, 2, 3, 0, 2, 3, 5, 7, 4, 8, 1, 8};
    Number[] series7Numbers60 = {2, null, 5, 2, 7, 4, 3, 7, 4, 5, 7, 4, 5, 8, 5, 3, 6, 3, 9, 3, 2, null, 5, 2, 7, 4, 3, 7, 4, 7, 6, 5, 3, 2, 1, 0, 9, 3, null, 1, 2, 3, 7, 6, 5, 4, 3, 2, 9, 1, 3, 8, 1, 2, 3, 5, 8, 9, 9 ,9};


    Number[] series1Numbers = series1Numbers10;
    Number[] series2Numbers = series2Numbers10;
    Number[] series3Numbers = series3Numbers10;
    Number[] series4Numbers = series4Numbers10;
    Number[] series5Numbers = series5Numbers10;
    Number[] series6Numbers = series6Numbers10;
    Number[] series7Numbers = series7Numbers10;



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
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);


//        formatter1 = new MyBarFormatter(Color.argb(200, 88, 93, 89), Color.LTGRAY);
//        formatter2 = new MyBarFormatter(Color.argb(200, 145, 169, 145), Color.LTGRAY);
//        formatter3 = new MyBarFormatter(Color.argb(200, 148, 145, 169), Color.LTGRAY);
//        formatter4 = new MyBarFormatter(Color.argb(200, 106, 80, 87), Color.LTGRAY);
//        formatter5 = new MyBarFormatter(Color.argb(200, 169, 154, 128), Color.LTGRAY);
//        formatter6 = new MyBarFormatter(Color.argb(200, 70, 92, 70), Color.LTGRAY);
//        formatter7 = new MyBarFormatter(Color.argb(200, 74, 70, 92), Color.LTGRAY);
//        selectionFormatter = new MyBarFormatter(Color.argb(1000,224,222,84), Color.WHITE);

        formatter1 = new MyBarFormatter(Color.argb(200, 78, 130, 189), Color.LTGRAY);//Bleu
        formatter2 = new MyBarFormatter(Color.argb(200, 194, 80, 79), Color.LTGRAY);//Rouge
        formatter3 = new MyBarFormatter(Color.argb(200, 156, 187, 88), Color.LTGRAY);//vert
        formatter4 = new MyBarFormatter(Color.argb(200, 46, 181, 164), Color.LTGRAY);//lime
        formatter5 = new MyBarFormatter(Color.argb(200, 128, 100, 162), Color.LTGRAY);//Violet
        formatter6 = new MyBarFormatter(Color.argb(200, 67, 172, 197), Color.LTGRAY);//Bleu pâle
        formatter7 = new MyBarFormatter(Color.argb(200, 50, 86, 140), Color.LTGRAY);//Bleu marin
        selectionFormatter = new MyBarFormatter(Color.argb(1000,224,222,84), Color.WHITE);

        /*formatter1 = new MyBarFormatter(Color.argb(200, 85, 87, 79), Color.LTGRAY);
        formatter2 = new MyBarFormatter(Color.argb(200, 166, 201, 85), Color.LTGRAY);
        formatter3 = new MyBarFormatter(Color.argb(200, 207, 215, 194), Color.LTGRAY);
        formatter4 = new MyBarFormatter(Color.argb(200, 76, 87, 53), Color.LTGRAY);
        formatter5 = new MyBarFormatter(Color.argb(200, 159, 164, 158), Color.LTGRAY);
        formatter6 = new MyBarFormatter(Color.argb(200, 93, 134, 59), Color.LTGRAY);
        formatter7 = new MyBarFormatter(Color.argb(200, 67, 101, 40), Color.LTGRAY);
        selectionFormatter = new MyBarFormatter(Color.argb(1000,224,222,84), Color.WHITE);*/

        selectionWidget = new TextLabelWidget(plot.getLayoutManager(),this.getString(R.string.stats_non_selectionne),

                new Size(
                        PixelUtils.dpToPix(100), SizeLayoutType.ABSOLUTE,
                        PixelUtils.dpToPix(100), SizeLayoutType.ABSOLUTE),
                TextOrientationType.HORIZONTAL);

        selectionWidget.setPaddingLeft(10);

        selectionWidget.getLabelPaint().setTextSize(PixelUtils.dpToPix(20));

        // add a dark, semi-transparent background to the selection label widget:
        Paint p = new Paint();
        p.setARGB(100, 0, 0, 0);
        selectionWidget.setBackgroundPaint(p);

        selectionWidget.position(
                0, XLayoutStyle.RELATIVE_TO_CENTER,
                PixelUtils.dpToPix(60), YLayoutStyle.ABSOLUTE_FROM_TOP,
                AnchorPosition.TOP_MIDDLE);
        selectionWidget.pack();


        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.setRangeLowerBoundary(0, BoundaryMode.FIXED);
        plot.getGraphWidget().getGridBox().setPadding(30, 10, 30, 0);

        plot.setTicksPerDomainLabel(2);


        // setup checkbox listers:
        series1CheckBox = (CheckBox) findViewById(R.id.cat_boulangerie);
        series1CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onS1CheckBoxClicked(b);
            }
        });

        series2CheckBox = (CheckBox) findViewById(R.id.cat_fruits_et_legumes);
        series2CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {onS2CheckBoxClicked(b);
            }
        });

        series3CheckBox = (CheckBox) findViewById(R.id.cat_Produits_laitiers);
        series3CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {onS3CheckBoxClicked(b);
            }
        });

        series4CheckBox = (CheckBox) findViewById(R.id.cat_surgelés);
        series4CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {onS4CheckBoxClicked(b);
            }
        });

        series5CheckBox = (CheckBox) findViewById(R.id.cat_viandes);
        series5CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {onS5CheckBoxClicked(b);
            }
        });

        series6CheckBox = (CheckBox) findViewById(R.id.cat_non_périssable);
        series6CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {onS6CheckBoxClicked(b);
            }
        });

        series7CheckBox = (CheckBox) findViewById(R.id.cat_non_comestible);
        series7CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {onS7CheckBoxClicked(b);
            }
        });




        plot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    onPlotClicked(new PointF(motionEvent.getX(), motionEvent.getY()));
                }
                return true;
            }
        });

        spRenderStyle = (Spinner) findViewById(R.id.spRenderStyle);
        ArrayAdapter <BarRenderer.BarRenderStyle> adapter = new ArrayAdapter <BarRenderer.BarRenderStyle> (this, android.R.layout.simple_spinner_item, BarRenderer.BarRenderStyle.values() );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRenderStyle.setAdapter(adapter);
        spRenderStyle.setSelection(BarRenderer.BarRenderStyle.OVERLAID.ordinal());
        spRenderStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                updatePlot();
            }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });

        spWidthStyle = (Spinner) findViewById(R.id.spWidthStyle);
        ArrayAdapter <BarRenderer.BarWidthStyle> adapter1 = new ArrayAdapter <BarRenderer.BarWidthStyle> (this, android.R.layout.simple_spinner_item, BarRenderer.BarWidthStyle.values() );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWidthStyle.setAdapter(adapter1);
        spWidthStyle.setSelection(BarRenderer.BarWidthStyle.FIXED_WIDTH.ordinal());
        spWidthStyle.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
            	if (BarRenderer.BarWidthStyle.FIXED_WIDTH.equals(spWidthStyle.getSelectedItem())) {
            		sbFixedWidth.setVisibility(View.VISIBLE);
            		sbVariableWidth.setVisibility(View.INVISIBLE);
            	} else {
            		sbFixedWidth.setVisibility(View.INVISIBLE);
            		sbVariableWidth.setVisibility(View.VISIBLE);
            	}
                updatePlot();
            }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });

        spSeriesSize = (Spinner) findViewById(R.id.spSeriesSize);
        ArrayAdapter <SeriesSize> adapter11 = new ArrayAdapter <SeriesSize> (this, android.R.layout.simple_spinner_item, SeriesSize.values() );
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSeriesSize.setAdapter(adapter11);
        spSeriesSize.setSelection(SeriesSize.Dix.ordinal());
        spSeriesSize.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                switch ((SeriesSize)arg0.getSelectedItem()) {
				case Dix:
					series1Numbers = series1Numbers10;
					series2Numbers = series2Numbers10;
                    series3Numbers = series3Numbers10;
                    series4Numbers = series4Numbers10;
                    series5Numbers = series5Numbers10;
                    series6Numbers = series6Numbers10;
                    series7Numbers = series7Numbers10;

					break;
				case Vingt:
					series1Numbers = series1Numbers20;
					series2Numbers = series2Numbers20;
                    series3Numbers = series3Numbers20;
                    series4Numbers = series4Numbers20;
                    series5Numbers = series5Numbers20;
                    series6Numbers = series6Numbers20;
                    series7Numbers = series7Numbers20;

                    break;
				case Soixante:
					series1Numbers = series1Numbers60;
					series2Numbers = series2Numbers60;
                    series3Numbers = series3Numbers60;
                    series4Numbers = series4Numbers60;
                    series5Numbers = series5Numbers60;
                    series6Numbers = series6Numbers60;
                    series7Numbers = series7Numbers60;

                    break;
				default:
					break;
                }
                updatePlot();
            }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });


        sbFixedWidth = (SeekBar) findViewById(R.id.sbFixed);
        sbFixedWidth.setProgress(50);
        sbFixedWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {updatePlot();}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        sbVariableWidth = (SeekBar) findViewById(R.id.sbVariable);
        sbVariableWidth.setProgress(1);
        sbVariableWidth.setVisibility(View.INVISIBLE);
        sbVariableWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {updatePlot();}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        plot.setDomainValueFormat(new NumberFormat() {
            @Override
            public StringBuffer format(double value, StringBuffer buffer, FieldPosition field) {
                int year = (int) (value + 0.5d) / 12;
                int month = (int) ((value + 0.5d) % 12);
                return new StringBuffer(DateFormatSymbols.getInstance().getShortMonths()[month] + " '0" + year);
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
        updatePlot();

    }

    private void updatePlot() {

    	// Remove all current series from each plot
        plot.clear();

        // Setup our Series with the selected number of elements
        series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "1");
        series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "2");
        series3 = new SimpleXYSeries(Arrays.asList(series3Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "3");
        series4 = new SimpleXYSeries(Arrays.asList(series4Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "4");
        series5 = new SimpleXYSeries(Arrays.asList(series5Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "5");
        series6 = new SimpleXYSeries(Arrays.asList(series6Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "6");
        series7 = new SimpleXYSeries(Arrays.asList(series7Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "7");

        // add a new series' to the xyplot:
        if (series1CheckBox.isChecked()) plot.addSeries(series1, formatter1);
        if (series2CheckBox.isChecked()) plot.addSeries(series2, formatter2);
        if (series3CheckBox.isChecked()) plot.addSeries(series3, formatter3);
        if (series4CheckBox.isChecked()) plot.addSeries(series4, formatter4);
        if (series5CheckBox.isChecked()) plot.addSeries(series5, formatter5);
        if (series6CheckBox.isChecked()) plot.addSeries(series6, formatter6);
        if (series7CheckBox.isChecked()) plot.addSeries(series7, formatter7);


        // Setup the BarRenderer with our selected options
        MyBarRenderer renderer = ((MyBarRenderer)plot.getRenderer(MyBarRenderer.class));
        renderer.setBarRenderStyle((BarRenderer.BarRenderStyle)spRenderStyle.getSelectedItem());
        renderer.setBarWidthStyle((BarRenderer.BarWidthStyle)spWidthStyle.getSelectedItem());
        renderer.setBarWidth(sbFixedWidth.getProgress());
        renderer.setBarGap(sbVariableWidth.getProgress());

        if (BarRenderer.BarRenderStyle.STACKED.equals(spRenderStyle.getSelectedItem())) {
        	plot.setRangeTopMin(15);
        } else {
        	plot.setRangeTopMin(0);
        }

        plot.redraw();

    }

    private void onPlotClicked(PointF point) {

        // make sure the point lies within the graph area.  we use gridrect
        // because it accounts for margins and padding as well. 
        if (plot.getGraphWidget().getGridDimensions().marginatedRect.contains(point.x, point.y)) {
            Number x = plot.getXVal(point);
            Number y = plot.getYVal(point);


            selection = null;
            double xDistance = 0;
            double yDistance = 0;


            // find the closest value to the selection:
            for (SeriesAndFormatter<XYSeries, ? extends XYSeriesFormatter> sfPair : plot.getSeriesRegistry()) {
                XYSeries series = sfPair.getSeries();
                for (int i = 0; i < series.size(); i++) {
                    Number thisX = series.getX(i);
                    Number thisY = series.getY(i);
                    if (thisX != null && thisY != null) {
                        double thisXDistance =
                                LineRegion.measure(x, thisX).doubleValue();
                        double thisYDistance =
                                LineRegion.measure(y, thisY).doubleValue();
                        if (selection == null) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance < xDistance) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance == xDistance &&
                                thisYDistance < yDistance &&
                                thisY.doubleValue() >= y.doubleValue()) {selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        }
                    }
                }
            }

        } else {
            // if the press was outside the graph area, deselect:
            selection = null;
        }

        if(selection == null) {
            selectionWidget.setText(this.getString(R.string.stats_non_selectionne));
            selectionWidget.setPaddingRight(35);
        } else {
            selectionWidget.setText(selection.second.getTitle() + " : " + selection.second.getY(selection.first) + " $ ");
        }
        plot.redraw();
    }

    private void onS1CheckBoxClicked(boolean checked) {
        if (checked) {
            plot.addSeries(series1, formatter1);
        } else {
            plot.removeSeries(series1);
        }
        plot.redraw();
    }

    private void onS2CheckBoxClicked(boolean checked) {
        if (checked) {
            plot.addSeries(series2, formatter2);
        } else {
            plot.removeSeries(series2);
        }
        plot.redraw();
    }

    private void onS3CheckBoxClicked(boolean checked) {
        if (checked) {
            plot.addSeries(series3, formatter3);
        } else {
            plot.removeSeries(series3);
        }
        plot.redraw();
    }

    private void onS4CheckBoxClicked(boolean checked) {
        if (checked) {
            plot.addSeries(series4, formatter4);
        } else {
            plot.removeSeries(series4);
        }
        plot.redraw();
    }

    private void onS5CheckBoxClicked(boolean checked) {
        if (checked) {
            plot.addSeries(series5, formatter5);
        } else {
            plot.removeSeries(series5);
        }
        plot.redraw();
    }

    private void onS6CheckBoxClicked(boolean checked) {
        if (checked) {
            plot.addSeries(series6, formatter6);
        } else {
            plot.removeSeries(series6);
        }
        plot.redraw();
    }

    private void onS7CheckBoxClicked(boolean checked) {
        if (checked) {
            plot.addSeries(series7, formatter7);
        } else {
            plot.removeSeries(series7);
        }
        plot.redraw();
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
         * @param index index of the point being rendered.
         * @param series XYSeries to which the point being rendered belongs.
         * @return
         */
        @Override
        public MyBarFormatter getFormatter(int index, XYSeries series) {
            if(selection != null &&
                    selection.second == series &&
                    selection.first == index) {
                return selectionFormatter;
            } else {
                return getFormatter(series);
            }
        }
    }
}
