package com.hse.raven;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cat on 04.05.18.
 */

public class ScheduleLoader {
    String basicUri = "https://www.hse.ru/api/timetable/lessons";
    private Map<String, String> params;
    private Map<String,String> groupID;

    public int getDayOfWeek(String weekDay){
        switch(weekDay){
            case "Пн":
                return 1;
            case "Вт":
                return 2;
            case "Ср":
                return 3;
            case "Чт":
                return 4;
            case "Пт":
                return 5;
            case "Сб":
                return 6;
            case "Вс":
                return 7;
                default:
                    return 0;
        }
    }

    public ScheduleLoader() {
       groupID = new HashMap<>();
       groupID.put("17ПИ", "6929");
       groupID.put("16ПИ", "7290");
       groupID.put("15ПИ", "6371");
       groupID.put("14ПИ", "7237");
       groupID.put("17ФМ", "6933");
       groupID.put("16ФМ", "7249");
       groupID.put("15ФМ", "7241");
       groupID.put("17БИ1", "6911");
       groupID.put("17БИ2", "6913");
       groupID.put("17БИ3", "6912");
       groupID.put("16БИ1", "6584");
       groupID.put("16БИ2", "6583");
       groupID.put("15БИ1", "6374");
       groupID.put("15БИ2", "6370");
       groupID.put("14БИ1", "6219");
       groupID.put("14БИ2", "6218");
       groupID.put("17ПМИ1", "6927");
       groupID.put("17ПМИ2", "6928");
       groupID.put("16ПМИ1", "7247");
       groupID.put("16ПМИ2", "7248");
       groupID.put("15ПМИ", "7242");
       groupID.put("14ПМИ", "7238");
       groupID.put("17Э1", "6934");
       groupID.put("17Э2", "6930");
       groupID.put("17Э3", "6935");
       groupID.put("17Э4", "7605");
       groupID.put("16Э1", "6611");
       groupID.put("16Э2", "6604");
       groupID.put("16Э3", "6605");
       groupID.put("16Э4", "6606");
       groupID.put("15Э1", "6367");
       groupID.put("15Э2", "6379");
       groupID.put("15Э3", "6380");
       groupID.put("14Э1", "6229");
       groupID.put("14Э2", "6230");
       groupID.put("14Э3", "6231");
       groupID.put("17Ю1", "6938");
       groupID.put("17Ю2", "6939");
       groupID.put("17Ю3", "7620");
       groupID.put("16Ю1", "6607");
       groupID.put("16Ю2", "6559");
       groupID.put("16Ю3", "6608");
       groupID.put("15Ю1", "6368");
       groupID.put("15Ю2", "6369");
       groupID.put("14Ю1", "6217");
       groupID.put("14Ю2", "6232");
       groupID.put("17ФИЛ", "6931");
       groupID.put("17ФИЛ2", "7659");
       groupID.put("16ФИЛ", "7245");
       groupID.put("15ФИЛ", "6373");
       groupID.put("14ФИЛ", "7239");
       groupID.put("17ФПЛ", "6926");
       groupID.put("17ФПЛ2", "7660");
       groupID.put("16ФПЛ", "7246");
       groupID.put("15ФПЛ", "6372");
       groupID.put("14ФПЛ", "7240");
       groupID.put("17М1", "6924");
       groupID.put("17М2", "6932");
       groupID.put("17М3", "6925");
       groupID.put("17М4", "7606");
       groupID.put("16М1", "6581");
       groupID.put("16М2", "6580");
       groupID.put("16М3", "6579");
       groupID.put("16М4", "6609");
       groupID.put("15М1", "6376");
       groupID.put("15М2", "6378");
       groupID.put("15М3", "6375");
       groupID.put("15М4", "6377");
       params = new HashMap<>();
    }
    public Request<JSONObject> getScheduleRequest(String fromDate, String toDate, String group){
        //TODO: Prepare to neseccary formats
        params.put("fromdate", fromDate);
        params.put("todate", toDate);
        params.put("groupid", groupID.get(group));

        CustomRequest request = new CustomRequest(Request.Method.GET, basicUri, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //TODO:Parse args
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error

            }
        });
        return request;
    }

}
