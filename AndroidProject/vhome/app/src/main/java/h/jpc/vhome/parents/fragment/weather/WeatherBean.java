package h.jpc.vhome.parents.fragment.weather;

public class WeatherBean {
    private String temp;//当前温度
    private String temperature;//今天温度范围
    private String city;//城市
    private String week;//星期几
    private String weather;//今日天气
    private String wind;//风向和风力
    private String dressing_index;//穿衣指数
    private String dressing_advice;//穿衣建议
    private String date;//20191202

    public WeatherBean() {
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDressing_index() {
        return dressing_index;
    }

    public void setDressing_index(String dressing_index) {
        this.dressing_index = dressing_index;
    }

    public String getDressing_advice() {
        return dressing_advice;
    }

    public void setDressing_advice(String dressing_advice) {
        this.dressing_advice = dressing_advice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
