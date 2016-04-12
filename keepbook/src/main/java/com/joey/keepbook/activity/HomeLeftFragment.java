package com.joey.keepbook.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joey.keepbook.R;
import com.joey.keepbook.cache.CacheManager;
import com.joey.keepbook.cache.ChartCache;
import com.joey.keepbook.utils.ColorUtils;
import com.joey.keepbook.utils.DateManger;
import com.joey.keepbook.utils.TraversalUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Joey on 2016/3/13.
 */
public class HomeLeftFragment extends Fragment {
    private static final String TAG = "调试HomeLeftFragment";

    public final String[] strMonths = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    public final String[] strWeeks = new String[]{"第一周", "第二周", "第三周", "第四周", "第五周"};

    private final int monthCount = strMonths.length;
    private final int weekCount = strWeeks.length;


    private LineChartView chartTop;
    private ColumnChartView chartBottom;
    private ColumnChartData columnData;


    //坐标轴
    private Axis axisLineX;
    private Axis axisLineY;

    //数据
    private float[] weeksIn;
    private float[] weeksOut;
    private float[] monthsIn;
    private float[] monthsOut;
    private List<Line> linesIn;
    private List<Line> linesOut;

    //    private float[] maxValues;
    private float[] maxValues;
    private float maxMonthsValues;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_left, container, false);
        chartTop = (LineChartView) rootView.findViewById(R.id.chart_home_left_top);
        chartBottom = (ColumnChartView) rootView.findViewById(R.id.chart_home_left_bottom);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ChartCache chartCache = (ChartCache) CacheManager.getInstance().getCache(CacheManager.CHART_CACHE);
        weeksIn = chartCache.getWeeksIn();
        weeksOut = chartCache.getWeeksOut();
        monthsIn = chartCache.getMonthsIn();
        monthsOut = chartCache.getMonthsOut();
        //遍历数组
        TraversalUtils.array("home left fragment 中weeksIn", weeksIn);
        TraversalUtils.array("home left fragment 中weeksOut", weeksOut);
        TraversalUtils.array("home left fragment 中monthsIn", monthsIn);
        TraversalUtils.array("home left fragment 中monthsOut", monthsOut);
        maxValues = new float[12];
        //获取每个月的最大值 以及 一年中 最大值
        for (int i = 0; i < 12; i++) {//12个月
            maxMonthsValues = (maxMonthsValues > monthsIn[i]) ? maxMonthsValues : monthsIn[i];
            maxMonthsValues = (maxMonthsValues > monthsOut[i]) ? maxMonthsValues : monthsOut[i];
            for (int j = 0; j < 5; j++) {//每个月5周
                maxValues[i] = (maxValues[i] > weeksIn[i * 5 + j]) ? maxValues[i] : weeksIn[i * 5 + j];
                maxValues[i] = (maxValues[i] > weeksOut[i * 5 + j]) ? maxValues[i] : weeksOut[i * 5 + j];
            }
        }
        TraversalUtils.array("home left fragment 中 maxValues", maxValues);

        initDataLineChart();//初始化线表数据
        attributesSetLineChart();//初始化线表属性
        updateChart(DateManger.getInstance().getMonth());//初始化线表
        initDataColumnChart();//初始化图表数据
        generateColumnData();
    }

    private void initDataColumnChart() {

    }

    private void initDataLineChart() {
        linesIn = new ArrayList<Line>();
        linesOut = new ArrayList<Line>();
        for (int i = 0; i < 12; i++) {//12条线 代表12个月
            List<PointValue> pointIn = new ArrayList<PointValue>();
            List<PointValue> pointOut = new ArrayList<PointValue>();
            for (int j = 0; j < 5; j++) {//5个点，代表5周
                pointIn.add(new PointValue(j, weeksIn[i * 5 + j]));
                pointOut.add(new PointValue(j, weeksOut[i * 5 + j]));
            }
            Line lineIn = new Line(pointIn);
            Line lineOut = new Line(pointOut);
            lineIn.setColor(ColorUtils.COLOR_GREEN).setCubic(true);
            lineOut.setColor(ColorUtils.COLOR_ORANGE).setCubic(true);
            lineIn.setHasLabels(true);
            lineOut.setHasLabels(true);
            linesIn.add(lineIn);
            linesOut.add(lineOut);
        }
    }

    //属性设置
    private void attributesSetLineChart() {
        chartTop.setValueSelectionEnabled(false);
        //坐标轴名设置
        List<AxisValue> lineAxisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < strWeeks.length; i++) {
            lineAxisValues.add(new AxisValue(i).setLabel(strWeeks[i]));
        }
        axisLineX = new Axis(lineAxisValues).setHasLines(true);
        axisLineY = new Axis().setHasLines(true).setMaxLabelChars(4);
    }

    private void generateColumnData() {
        //轴名称 12个月
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < monthCount; i++) {
            axisValues.add(new AxisValue(i).setLabel(strMonths[i]));
        }

        List<Column> columns = new ArrayList<Column>();
        for (int i = 0; i < monthCount; ++i) {//12个月 每个月两根
            List<SubcolumnValue> values = new ArrayList<SubcolumnValue>();
            SubcolumnValue subValueIn = new SubcolumnValue(monthsIn[i], ColorUtils.COLOR_GREEN);
            SubcolumnValue subValueOut = new SubcolumnValue(monthsOut[i], ColorUtils.COLOR_ORANGE);
            values.add(subValueIn);
            values.add(subValueOut);
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }
        columnData = new ColumnChartData(columns);
        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));
        chartBottom.setColumnChartData(columnData);
        chartBottom.setOnValueTouchListener(new ValueTouchListener());
        chartBottom.setValueSelectionEnabled(true);
        Viewport v = new Viewport(-0.5f, maxMonthsValues*1.3f, 12, 0);
        chartBottom.setMaximumViewport(v);
        chartBottom.setCurrentViewport(v);
        chartBottom.setZoomType(ZoomType.HORIZONTAL);
        chartBottom.setZoomType(ZoomType.HORIZONTAL);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {
        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            updateChart(columnIndex);
        }

        @Override
        public void onValueDeselected() {
        }
    }

    private void updateChart(int month) {
        List<Line> lines = new ArrayList<Line>();
        lines.add(linesIn.get(month));
        lines.add(linesOut.get(month));
        chartTop.resetViewports();
        LineChartData lineData = new LineChartData(lines);
        lineData.setAxisXBottom(axisLineX);
        lineData.setAxisYLeft(axisLineY);
        chartTop.setLineChartData(lineData);
        chartTop.setViewportCalculationEnabled(false);
        Viewport v = new Viewport(-0.2f, maxValues[month]*1.3f, 4, 0);
        chartTop.setMaximumViewport(v);
        chartTop.setCurrentViewport(v);
        chartTop.setZoomType(ZoomType.HORIZONTAL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
