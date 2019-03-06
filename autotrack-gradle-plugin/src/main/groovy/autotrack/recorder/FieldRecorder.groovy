package autotrack.recorder

import autotrack.constants.Constant

class FieldRecorder extends Recorder {

    private String mDesc

    FieldRecorder(String name, String desc) {
        mDesc = desc
        mName = name
    }

    @Override
    void addTypeByDesc(String desc) {
        switch (desc) {
            case Constant.desc.ANNOTATION_CLICK:
                addType(Constant.type.TYPE_TRACK_CLICK)
                break
            case Constant.desc.ANNOTATION_EXPOSURE:
                addType(Constant.type.TYPE_TRACK_EXPOSURE)
                break
            case Constant.desc.ANNOTATION_CLICK_EXPOSURE:
                addType(Constant.type.TYPE_TRACK_CLICK)
                addType(Constant.type.TYPE_TRACK_EXPOSURE)
                break
            default: break
        }
    }

    @Override
    boolean shouldGenerateCode() {
        return mType != 0
    }

    String getDesc() {
        return mDesc
    }

    boolean trackClick() {
        return (mType & Constant.type.TYPE_TRACK_CLICK) != 0
    }

    boolean trackExposure() {
        return (mType & Constant.type.TYPE_TRACK_EXPOSURE) != 0
    }

    @Override
    String toString() {
        return "FieldRecorder: \n" +
                "name = " + mName + "\n" +
                "desc = " + mDesc + "\n" +
                "value = " + getValues().toString() + "\n" +
                "trackClick? = " + trackClick() + "\n" +
                "trackExposure? = " + trackExposure() + "\n"
    }
}
