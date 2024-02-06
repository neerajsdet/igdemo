package org.paytm.insurance.utils;

import io.cucumber.datatable.DataTable;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtils {




    public static String randomAlphanumericStringGenerator(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }


}
