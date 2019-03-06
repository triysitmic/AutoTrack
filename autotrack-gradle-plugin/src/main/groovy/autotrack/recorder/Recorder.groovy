package autotrack.recorder
/**
 * Created by zhengyanda on 2019/3/6
 */
abstract class Recorder {
    protected String mName

    protected int mType = 0

    void addType(int flag) {
        mType |= flag
    }

    void setName(String name) {
        mName = name
    }

    String getName() {
        return mName
    }

    abstract boolean shouldGenerateCode()

    abstract void addTypeByDesc(String desc)
}
