import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Mikhail_Kaspiarovich on 9/27/2016.
 */
public class Query {

    public final static String correct = "correct";

    private String type;
    private String text;
    private String result;

    public Query() {

    }

    public Query(String type, String text) {

        this.type = type;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
