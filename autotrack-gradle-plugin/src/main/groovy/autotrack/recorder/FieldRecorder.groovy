package autotrack.recorder

import autotrack.constants.Constant

class FieldRecorder {

    private String mName
    private String mDesc

    private String mValue

    private int mTypeFlag = 0

    FieldRecorder(String name, String desc) {
        mName = name
        mDesc = desc
    }

    @Override
    String toString() {
        return "FieldRecorder: \n" +
                "name = " + mName + "\n" +
                "desc = " + mDesc + "\n" +
                "value = " + mValue + "\n" +
                "trackClick? = " + trackClick() + "\n" +
                "trackExposure? = " + trackExposure() + "\n"
    }

    void setValue(String value) {
        mValue = value
    }

    String getName() {
        return mName
    }

    String getDesc() {
        return mDesc
    }

    String getValue() {
        return mValue
    }

    void addFlagByDesc(String desc) {
        switch (desc) {
            case Constant.desc.ANNOTATION_CLICK:
                addFlag(Constant.type.TYPE_TRACK_CLICK)
                break
            case Constant.desc.ANNOTATION_EXPOSURE:
                addFlag(Constant.type.TYPE_TRACK_EXPOSURE)
                break
            case Constant.desc.ANNOTATION_CLICK_EXPOSURE:
                addFlag(Constant.type.TYPE_TRACK_CLICK)
                addFlag(Constant.type.TYPE_TRACK_EXPOSURE)
                break
            default: break
        }
    }

    void addFlag(int flag) {
        mTypeFlag |= flag
    }

    boolean trackClick() {
        return (mTypeFlag & Constant.type.TYPE_TRACK_CLICK) != 0
    }

    boolean trackExposure() {
        return (mTypeFlag & Constant.type.TYPE_TRACK_EXPOSURE) != 0
    }

    boolean shouldGenerateCode() {
        return mTypeFlag != 0
    }
}
