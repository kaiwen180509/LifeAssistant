package com.lifeassistant.model;

import com.lifeassistant.retrofit.WeatherBean;

import java.util.Arrays;

public class WeatherDataParser {
    private WeatherBean.CwbopendataBean.DatasetBean dataset;

    public WeatherDataParser(WeatherBean weatherBean) {
        // 從 WeatherBean 取出資料集
        this.dataset = weatherBean.getCwbopendata().getDataset();
    }

    /**
     * 解析地點資料
     *
     * @param position 在資料集陣列的第幾個位置
     * @return 地點
     */
    public String parserLocationData(int position) {
        return dataset.getLocation().get(position).getLocationName();
    }

    /**
     * 解析更新時間資料
     *
     * @return 更新時間
     */
    public String parserTimeData() {
        // 取出時間
        String time = dataset.getDatasetInfo().getUpdate().substring(0, 19);
        // 轉換格式
        time = time.replace("-", "/").replace("T", " ");

        return time;
    }

    /**
     * 從 API 獲得3時段天氣描述資料中解析需要的資料
     *
     * @param position 在資料集陣列的第幾個位置
     * @return 天氣描述資料陣列 {描述資料, 描述層級}
     */
    public String[] parserDescriptionData(int position) {
        // 主要的描述資料
        String mainDescription;
        int mainDescriptionValue = 0;

        // 取得3個時段的天氣描述
        String[] descriptionName = new String[3];
        int[] descriptionValue = new int[3];
        for (int j = 0; j < 3; j++) {
            descriptionName[j] = dataset.getLocation().get(position).getWeatherElement().get(0).getTime().get(j).getParameter().getParameterName();
            String value = dataset.getLocation().get(position).getWeatherElement().get(0).getTime().get(j).getParameter().getParameterValue();
            // 檢查描述層級資料是否正確
            if (value.equals("") || value == null) {
                descriptionValue[j] = -1;
            } else {
                descriptionValue[j] = Integer.parseInt(value);
            }
        }

        // 判斷哪個描述層級較高
        mainDescription = descriptionName[0];
        mainDescriptionValue = descriptionValue[0];
        for (int k = 1; k < 3; k++) {
            if (descriptionValue[k - 1] < descriptionValue[k]) {
                mainDescription = descriptionName[k];
                mainDescriptionValue = descriptionValue[k];
            }
        }

        // 回傳描述陣列
        return new String[]{mainDescription, String.valueOf(mainDescriptionValue)};
    }

    /**
     * 從 API 獲得3時段溫度資料中解析需要的資料
     *
     * @param position 在資料集陣列的第幾個位置
     * @return 溫度資料陣列 {低溫, 高溫}
     */
    public int[] parserTemperatureData(int position) {
        // 取得3個時段的高低溫度
        int[] temperature = new int[6];
        for (int k = 0; k < 3; k++) {
            String valueH = dataset.getLocation().get(position).getWeatherElement().get(1).getTime().get(k).getParameter().getParameterName();
            String valueL = dataset.getLocation().get(position).getWeatherElement().get(2).getTime().get(k).getParameter().getParameterName();
            // 檢查高溫資料是否正確
            if (valueH.equals("") || valueH == null) {
                temperature[k] = -1;
            } else {
                temperature[k] = Integer.parseInt(valueH);
            }
            // 檢查低溫資料是否正確
            if (valueL.equals("") || valueL == null) {
                temperature[k + 3] = -1;
            } else {
                temperature[k + 3] = Integer.parseInt(valueL);
            }
        }
        // 進行溫度排序
        Arrays.sort(temperature);

        // 回傳溫度陣列
        return new int[]{temperature[0], temperature[5]};
    }

    /**
     * 解析體感描述資料
     *
     * @param position 在資料集陣列的第幾個位置
     * @return 體感描述
     */
    public String parserFeelData(int position) {
        // 主要體感描述
        String mainFeel;

        // 取得3個時段的體感描述
        String[] feel = new String[3];
        for (int k = 0; k < 3; k++) {
            feel[k] = dataset.getLocation().get(position).getWeatherElement().get(3).getTime().get(k).getParameter().getParameterName();
        }

        // 判斷哪個體感描述較完善
        mainFeel = feel[0];
        for (int k = 1; k < 3; k++) {
            if (mainFeel.length() < feel[k].length()) {
                mainFeel = feel[k];
            }
        }

        return mainFeel;
    }

    /**
     * 解析降雨機率資料
     *
     * @param position 在資料集陣列的第幾個位置
     * @return 降雨機率
     */
    public int parserRainData(int position) {
        // 取得3個時段的降雨機率
        int[] rain = new int[3];
        for (int j = 0; j < 3; j++) {
            String value = dataset.getLocation().get(position).getWeatherElement().get(4).getTime().get(j).getParameter().getParameterName();
            // 檢查降雨機率資料是否正確
            if (value.equals("") || value == null) {
                rain[j] = -1;
            } else {
                rain[j] = Integer.parseInt(value);
            }
        }

        // 進行降雨機率排序
        Arrays.sort(rain);

        // 取出最高的機率
        return rain[2];
    }
}
