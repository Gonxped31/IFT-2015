import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTools {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    public static boolean isValid(String date){
        simpleDateFormat.applyPattern("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e){
            return false;
        }
        return true;
    }
}
