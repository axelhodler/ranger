package xorrr.github.io.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Media {

    private String objectId;
    private String url;
    private int choicesByUsers;
    private double avgStartTime;
    private double avgEndTime;

    public Media() {

    }

    public Media(String url) {
        super();
        this.url = url;
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

    public int getChoicesByUsers() {
        return choicesByUsers;
    }

    public void setChoicesByUsers(int choicesByUsers) {
        this.choicesByUsers = choicesByUsers;
    }

    public double getAvgStartTime() {
        return avgStartTime;
    }

    public void setAvgStartTime(double avgStartTime) {
        this.avgStartTime = avgStartTime;
    }

    public double getAvgEndTime() {
        return avgEndTime;
    }

    public void setAvgEndTime(double avgEndTime) {
        this.avgEndTime = avgEndTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(avgEndTime);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(avgStartTime);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + choicesByUsers;
        result = prime * result
                + ((objectId == null) ? 0 : objectId.hashCode());
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
        if (Double.doubleToLongBits(avgEndTime) != Double
                .doubleToLongBits(other.avgEndTime))
            return false;
        if (Double.doubleToLongBits(avgStartTime) != Double
                .doubleToLongBits(other.avgStartTime))
            return false;
        if (choicesByUsers != other.choicesByUsers)
            return false;
        if (objectId == null) {
            if (other.objectId != null)
                return false;
        } else if (!objectId.equals(other.objectId))
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
        return "Media [objectId=" + objectId + ", url=" + url
                + ", choicesByUsers=" + choicesByUsers + ", avgStartTime="
                + avgStartTime + ", avgEndTime=" + avgEndTime + "]";
    }
}
