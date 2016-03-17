package com.joey.keepbook.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joey.keepbook.R;
import com.joey.keepbook.activity.HomeActivity;
import com.joey.keepbook.bean.ChartInitData;
import com.joey.keepbook.engine.BillData;
import com.joey.keepbook.manager.MyActivityManager;
import com.joey.keepbook.utils.ColorUtils;
import com.joey.keepbook.utils.DateUtils;
import com.joey.keepbook.utils.LogUtils;
import com.joey.keepbook.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Joey on 2016/3/13.
 */
public class HomeLeftFragment extends Fragment {
    private static final String TAG = "调试HomeLeftFragment";
    private final int NOTIFY_CHART_DATA_CHANGED_OK = 1;
    private final int NOTIFY_CHART_DATA_CHANGED_ERROR = 2;

    public final static String[] months = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
            "九月", "十月", "十一月", "十二月",};

//    public final static String[] days = new String[]{"第一周", "第二周", "第三周", "第四周", "第五周",};

    private LineChartView chartTop;
    private ColumnChartView chartBottom;
    private ColumnChartData columnData;
    private HomeActivity mActivity;

    //bill数据
    private float[][] monthTotals;//[支出/收入 0-1] [月0-11 ]
    private float[][][] weekTotals;//[支出/收入 0-1] [月0-11 ] [周0-4 ]
    private Map<Integer, List<Line>> map;

    //坐标轴
    private Axis axisLineX;
    private Axis axisLineY;
    private LineChartData lineData;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HomeActivity) activity;
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
        //获取数据
        BillData mBillData = BillData.getInstance(mActivity);
        ChartInitData chartInitData = mBillData.getChartInitData();
        monthTotals = chartInitData.getMonthTotals();
        weekTotals = chartInitData.getWeekTotals();

        //初始化表
        initDataLineChart();//初始化线表数据
        initDataColumnChart();//初始化图表数据
        attributesSetLineChart();//初始化线表属性
        updateChart(DateUtils.getMonth());//初始化线表
        generateColumnData();
    }

    private void initDataColumnChart() {

    }
    private void initDataLineChart() {
        int numWeeks = weekTotals[0][0].length;
        int numMonths = monthTotals[0].length;//x总数 12
        int numClassify = monthTotals.length;//每个x 含y数目 2
        map = new HashMap<Integer, List<Line>>();
        //坐标点设置 默认两根线
        for (int i = 0; i < numClassify; i++) {//收入和支出
            List<Line> lines = new ArrayList<Line>();//一个月4根线
            for (int j = 0; j < numMonths; j++) {//月
                List<PointValue> values = new ArrayList<PointValue>();
                for (int k = 0; k < numWeeks; k++) {//周
                    values.add(new PointValue(k, weekTotals[i][j][k]));
                }
                Line line = new Line(values);
                line.setColor(ColorUtils.COLORS[i]).setCubic(true);
                line.setHasLabels(true);
                lines.add(line);
            }
            map.put(i, lines);
        }
    }
    //属性设置
    private void attributesSetLineChart() {
        chartTop.setValueSelectionEnabled(false);
        final String[] days = new String[]{"第一周", "第二周", "第三周", "第四周", "第五周",};
        //坐标轴名设置
        List<AxisValue> lineAxisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < days.length; i++) {
            lineAxisValues.add(new AxisValue(i).setLabel(days[i]));
        }
        axisLineX = (new Axis(lineAxisValues).setHasLines(true));
        axisLineY = new Axis().setHasLines(true).setMaxLabelChars(4);
    }

    private void generateColumnData() {
        //最大y坐标
        float maxY = 0;
        int numMonth = monthTotals[0].length;
        int numClassify = monthTotals.length;
        //轴数据
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < numMonth; i++) {
            axisValues.add(new AxisValue(i).setLabel(months[i]));
        }

        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numMonth; ++i) {
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numClassify; ++j) {
                SubcolumnValue subcolumnValue = new SubcolumnValue(monthTotals[j][i], ColorUtils.COLORS[j]);
                maxY = (monthTotals[j][i] > maxY) ? monthTotals[j][i] : maxY;
                values.add(subcolumnValue);
            }
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);
        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));
        chartBottom.setColumnChartData(columnData);
        chartBottom.setOnValueTouchListener(new ValueTouchListener());
        chartBottom.setValueSelectionEnabled(true);
        Viewport v = new Viewport(0, maxY + maxY / 5, 12, 0);
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
        float maxValues = 0;
        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < map.size(); i++) {
            List<Line> lineList = map.get(i);
            Line line = lineList.get(month);
            List<PointValue> values = line.getValues();
            for (PointValue value : values) {
                maxValues = (value.getY() > maxValues) ? value.getY() : maxValues;
            }
            line.setHasLabelsOnlyForSelected(true);
            lines.add(line);
        }
        chartTop.resetViewports();
        LineChartData lineData = new LineChartData(lines);
        lineData.setAxisXBottom(axisLineX);
        lineData.setAxisYLeft(axisLineY);
        chartTop.setLineChartData(lineData);
        chartTop.setViewportCalculationEnabled(false);

        Viewport v = new Viewport(0, maxValues + maxValues / 5, 4, 0);
        chartTop.setMaximumViewport(v);
        chartTop.setCurrentViewport(v);
        chartTop.setZoomType(ZoomType.HORIZONTAL);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
