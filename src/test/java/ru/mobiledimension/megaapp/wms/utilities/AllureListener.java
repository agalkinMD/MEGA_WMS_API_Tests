package ru.mobiledimension.megaapp.wms.utilities;

import org.testng.*;

import java.io.File;

public class AllureListener implements ISuiteListener {

    public void onStart(ISuite suite) {
        System.out.println("Before executing suite: " + suite.getName());
    }

    public void onFinish(ISuite suite) {
        System.out.println("After executing Suite: " + suite.getName());
        deleteAllureHistoryTrend();
    }

    private void deleteAllureHistoryTrend() {
        File trendReport = new File("allure-report/history/history-trend.json");
        File historyReport = new File("allure-report/history/history.json");
        File trendResults = new File("allure-results/history/history-trend.json");
        File historyResults = new File("allure-results/history/history.json");
        if (trendReport.exists())
            trendReport.delete();
        if (historyReport.exists())
            historyReport.delete();
        if (trendResults.delete())
            trendResults.delete();
        if (historyResults.exists())
            historyResults.delete();
    }
}
