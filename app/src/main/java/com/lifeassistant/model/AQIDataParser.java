package com.lifeassistant.model;

import android.content.Context;

import com.lifeassistant.R;
import com.lifeassistant.retrofit.AQIBean;

import java.util.ArrayList;

public class AQIDataParser {
    private Context context;
    private ArrayList<AQIBean> list;

    private final static int COLOR_LEVEL_GOOD = 1;
    private final static int COLOR_LEVEL_NORMAL = 2;
    private final static int COLOR_LEVEL_MAYBE_BAD = 3;
    private final static int COLOR_LEVEL_BAD = 4;
    private final static int COLOR_LEVEL_VERY_BAD = 5;
    private final static int COLOR_LEVEL_DANGER = 6;

    public AQIDataParser(Context context, ArrayList<AQIBean> list) {
        this.list = list;
        this.context = context;
    }

    /**
     * 判斷等級的顏色
     *
     * @param position 在資料陣列的第幾個位置
     * @return res/color 的顏色
     */
    public int parserColorData(int position) {
        // 取得 AQI
        String aqi = parserAQIData(position);

        // 設備維護中的情況，使用例外色
        if (aqi.equals("E")) {
            return context.getResources().getColor(R.color.colorLevelException);
        }

        // 取得 AQI 的顏色層級
        int level = checkAQILevel(Integer.parseInt(aqi));

        if (level == COLOR_LEVEL_GOOD) {
            return context.getResources().getColor(R.color.colorLevelGood);
        } else if (level == COLOR_LEVEL_NORMAL) {
            return context.getResources().getColor(R.color.colorLevelNormal);
        } else if (level == COLOR_LEVEL_MAYBE_BAD) {
            return context.getResources().getColor(R.color.colorLevelMaybeBad);
        } else if (level == COLOR_LEVEL_BAD) {
            return context.getResources().getColor(R.color.colorLevelBad);
        } else if (level == COLOR_LEVEL_VERY_BAD) {
            return context.getResources().getColor(R.color.colorLevelVeryBad);
        }
        return context.getResources().getColor(R.color.colorLevelDanger);
    }

    /**
     * 解析資料總筆數
     *
     * @return 資料總筆數，無資料則回傳 0
     */
    public int parserDataSize() {
        // 檢查資料
        if (list != null) {
            return list.size();
        }
        // 無資料
        return 0;
    }

    /**
     * 使用觀測站名稱解析資料位置
     *
     * @param site 觀測站名稱
     * @return 資料位置，查無資料則回傳 -1
     */
    public int parserPositionData(String site) {
        // 遍歷找到資料
        for (int i = 0; i < list.size(); i++) {
            // 判斷資料位置
            if (list.get(i).getSiteName().equals(site)) {
                return i;
            }
        }
        // 無資料
        return -1;
    }

    /**
     * 解析建議資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return 建議
     */
    public String parserSuggestionData(int position) {
        // 取得 AQI
        String aqi = parserAQIData(position);

        // 設備維護中的情況，回傳當前狀態
        if (aqi.equals("E")) {
            return context.getString(R.string.data_null);
        }

        // 判斷 AQI 等級
        int level = checkAQILevel(Integer.parseInt(aqi));

        // 根據等級來取得影響
        String suggestion = "";
        switch (level) {
            case COLOR_LEVEL_GOOD:
                suggestion = context.getString(R.string.aqi_sensitive_suggestion_good);
                break;
            case COLOR_LEVEL_NORMAL:
                suggestion = context.getString(R.string.aqi_sensitive_suggestion_normal);
                break;
            case COLOR_LEVEL_MAYBE_BAD:
                suggestion = context.getString(R.string.aqi_sensitive_suggestion_maybe_bad);
                break;
            case COLOR_LEVEL_BAD:
                suggestion = context.getString(R.string.aqi_sensitive_suggestion_bad);
                break;
            case COLOR_LEVEL_VERY_BAD:
                suggestion = context.getString(R.string.aqi_sensitive_suggestion_very_bad);
                break;
            case COLOR_LEVEL_DANGER:
                suggestion = context.getString(R.string.aqi_sensitive_suggestion_danger);
                break;
            default:
                break;
        }
        return suggestion;
    }

    /**
     * 解析影響資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return 影響
     */
    public String parserInfluenceData(int position) {
        // 取得 AQI
        String aqi = parserAQIData(position);

        // 設備維護中的情況，回傳當前狀態
        if (aqi.equals("E")) {
            return context.getString(R.string.data_null);
        }

        // 判斷 AQI 等級
        int level = checkAQILevel(Integer.parseInt(aqi));

        // 根據等級來取得影響
        String influence = "";
        switch (level) {
            case COLOR_LEVEL_GOOD:
                influence = context.getString(R.string.aqi_influence_good);
                break;
            case COLOR_LEVEL_NORMAL:
                influence = context.getString(R.string.aqi_influence_normal);
                break;
            case COLOR_LEVEL_MAYBE_BAD:
                influence = context.getString(R.string.aqi_influence_maybe_bad);
                break;
            case COLOR_LEVEL_BAD:
                influence = context.getString(R.string.aqi_influence_bad);
                break;
            case COLOR_LEVEL_VERY_BAD:
                influence = context.getString(R.string.aqi_influence_very_bad);
                break;
            case COLOR_LEVEL_DANGER:
                influence = context.getString(R.string.aqi_influence_danger);
                break;
            default:
                break;
        }
        return influence;
    }

    /**
     * 判斷 AQI 的指標等級
     *
     * @param aqi AQI 指標
     * @return AQI 指標等級
     */
    private int checkAQILevel(int aqi) {
        if (aqi < 51) {
            return COLOR_LEVEL_GOOD;
        } else if (aqi < 101) {
            return COLOR_LEVEL_NORMAL;
        } else if (aqi < 151) {
            return COLOR_LEVEL_MAYBE_BAD;
        } else if (aqi < 201) {
            return COLOR_LEVEL_BAD;
        } else if (aqi < 301) {
            return COLOR_LEVEL_VERY_BAD;
        }
        return COLOR_LEVEL_DANGER;
    }

    /**
     * 解析 PM 2.5 資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return PM 2.5
     */
    public String parserPM25Data(int position) {
        return list.get(position).get_$PM25_AVG3();
    }

    /**
     * 解析時間資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return 時間
     */
    public String parserTimeData(int position) {
        return list.get(position).getPublishTime();
    }

    /**
     * 解析縣市地點資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return 縣市地點
     */
    public String parserCountryData(int position) {
        return list.get(position).getCounty();
    }

    /**
     * 解析 AQI 資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return AQI，設備維護或其他狀況則回傳 "E"
     */
    public String parserAQIData(int position) {
        String aqi = list.get(position).getAQI();
        if (aqi.equals("") || aqi == null) {
            aqi = "E";
        }
        return aqi;
    }

    /**
     * 解析汙染指標物資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return 汙染指標物
     */
    public String parserPollutantData(int position) {
        String pollutant = list.get(position).getPollutant();
        if (pollutant.equals("") || pollutant == null) {
            pollutant = context.getString(R.string.data_null);
        }
        return pollutant;
    }

    /**
     * 解析狀態資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return 狀態
     */
    public String parserStatusData(int position) {
        return list.get(position).getStatus();
    }

    /**
     * 解析觀測站資料
     *
     * @param position 在資料陣列的第幾個位置
     * @return 觀測站
     */
    public String parserSiteNameData(int position) {
        return list.get(position).getSiteName();
    }
}
