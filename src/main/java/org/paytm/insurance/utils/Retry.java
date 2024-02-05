package org.paytm.insurance.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    final int maxRetry = 1;
    int count = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (count < maxRetry) {
                count++;
                return true;
            }
        }
        return false;
    }
}
