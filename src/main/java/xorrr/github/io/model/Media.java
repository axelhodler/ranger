package xorrr.github.io.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Media {

    private String objectId;
    private String url;
    private List<Range> ranges;

    public Media(String url) {
        super();
        this.url = url;
        this.ranges = new ArrayList<>();
    }

    @JsonProperty("_id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Range> getRanges() {
        return ranges;
    }

    public void addRange(Range range) {
        ranges.add(range);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((objectId == null) ? 0 : objectId.hashCode());
        result = prime * result + ((ranges == null) ? 0 : ranges.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
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
        Media other = (Media) obj;
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
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Media [objectId=" + objectId + ", url=" + url + ", ranges="
                + ranges + "]";
    }
}
