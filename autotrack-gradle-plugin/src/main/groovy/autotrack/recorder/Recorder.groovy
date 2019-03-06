package autotrack.recorder
/**
 * Created by zhengyanda on 2019/3/6
 */
abstract class Recorder {
    protected String mName

    protected int mType = 0

    private List<String> mValues = new ArrayList<>()

    void addType(int flag) {
        mType |= flag
    }

    public void setName(String name) {
        mName = name
    }

    String getName() {
        return mName
    }

    void addValue(String value) {
        mValues.add(value)
    }

    List<String> getValues() {
        return mValues
    }

    abstract boolean shouldGenerateCode()

    abstract void addTypeByDesc(String desc)
}
