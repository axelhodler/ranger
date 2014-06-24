package xorrr.github.io.model;

import org.codehaus.jackson.annotate.JsonProperty;

import xorrr.github.io.model.contracts.RangeContract;

public class Range extends RangeContract{

    private String objectId;
    private int startTime;
    private int endTime;

    public Range() {

    }

    public Range(int startTime, int endTime) {
        try {
            create(startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JsonProperty("_id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    @Override
    protected void setRange(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + endTime;
        result = prime * result
                + ((objectId == null) ? 0 : objectId.hashCode());
        result = prime * result + startTime;
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
        Range other = (Range) obj;
        if (endTime != other.endTime)
            return false;
        if (objectId == null) {
            if (other.objectId != null)
                return false;
        } else if (!objectId.equals(other.objectId))
            return false;
        if (startTime != other.startTime)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Range [objectId=" + objectId + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }

}
