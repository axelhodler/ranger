package xorrr.github.io.model.contracts;

public abstract class RangeContract {
    public final void create(int startTime, int endTime) throws Exception {
        if (validate(startTime, endTime))
            setRange(startTime, endTime);
        else 
            throw new Exception("startTime has to be less than endTime");
    }

    protected abstract void setRange(int startTime, int endTime); 

    private boolean validate(int startTime, int endTime) {
        return startTime <= endTime;
    }
}
