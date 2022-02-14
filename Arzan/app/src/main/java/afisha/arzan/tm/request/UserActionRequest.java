package afisha.arzan.tm.request;

public class UserActionRequest {

    private String type;
    private String action;
    private int relId;
    private int count;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getRelId() {
        return relId;
    }

    public void setRelId(int relId) {
        this.relId = relId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
