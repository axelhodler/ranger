package xorrr.github.io.model;

public class Range {

    private int startTime;
    private int endTime;

    public Range(int startTime, int endTime) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + endTime;
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
        if (startTime != other.startTime)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Range [startTime=" + startTime + ", endTime=" + endTime + "]";
    }

}
