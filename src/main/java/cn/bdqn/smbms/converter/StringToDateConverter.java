package cn.bdqn.smbms.converter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 日期类型转换器
 *
 * @author Matrix
 * @date 2020/4/27 12:41
 **/

public class StringToDateConverter implements Converter<String, Date> {

    private String datePattern;

    public StringToDateConverter(String datePattery) {
        this.datePattern = datePattery;
    }

    @Override
    public Date convert(String strDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat(datePattern).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
