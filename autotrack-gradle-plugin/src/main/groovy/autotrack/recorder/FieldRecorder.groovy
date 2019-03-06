package autotrack.recorder

import autotrack.constants.Constant

class FieldRecorder extends Recorder {

    private String mDesc

    private HashMap<String, List<String>> mValues = new HashMap<>()

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

    void addValues(String desc, List<String> values) {
        if (desc == Constant.desc.ANNOTATION_CLICK_EXPOSURE) {
            mValues.put(Constant.desc.ANNOTATION_CLICK, values)
            mValues.put(Constant.desc.ANNOTATION_EXPOSURE, values)
        }
        mValues.put(desc, values)
    }

    List<String> getClickValues() {
        return mValues.get(Constant.desc.ANNOTATION_CLICK)
    }

    List<String> getExposureValues() {
        return mValues.get(Constant.desc.ANNOTATION_EXPOSURE)
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
                "value = " + mValues.toString() + "\n" +
                "trackClick? = " + trackClick() + "\n" +
                "trackExposure? = " + trackExposure() + "\n"
    }
}
