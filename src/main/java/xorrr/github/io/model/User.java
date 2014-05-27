package xorrr.github.io.model;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {

    private String objectId;
    private String login;
    private Map<String, Range> ranges;

    @JsonProperty("_id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setLogin(String name) {
        this.login = name;
        ranges = new HashMap<>();
    }

    public String getLogin() {
        return this.login;
    }

    public void addRange(String key, Range value) {
        this.ranges.put(key, value);
    }

    public Map<String, Range> getRanges() {
        return this.ranges;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result
                + ((objectId == null) ? 0 : objectId.hashCode());
        result = prime * result + ((ranges == null) ? 0 : ranges.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (objectId == null) {
            if (other.objectId != null)
                return false;
        } else if (!objectId.equals(other.objectId))
            return false;
        if (ranges == null) {
            if (other.ranges != null)
                return false;
        } else if (!ranges.equals(other.ranges))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [objectId=" + objectId + ", login=" + login
                + ", amount of set ranges=" + ranges.size() + "]";
    }

}
