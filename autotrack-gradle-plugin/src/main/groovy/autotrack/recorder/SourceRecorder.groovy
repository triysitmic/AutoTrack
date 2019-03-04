package autotrack.recorder

import autotrack.constants.Constant

class SourceRecorder {

    private String mClassName

    private ArrayList<FieldRecorder> fields = new ArrayList<>()

    private int mTypeFlag = 0

    private String mValue

    SourceRecorder() {
    }

    boolean shouldGenerateCode() {
        boolean b = mTypeFlag != 0
        if (fields == null || fields.size() == 0) {
            return b
        }
        for (FieldRecorder fr : fields) {
            b = b || fr.shouldGenerateCode()
        }
        return b
    }

    void addFlagByDesc(String desc) {
        switch (desc) {
            case Constant.desc.ANNOTATION_PAGE:
                addFlag(Constant.type.TYPE_TRACK_PAGE)
                break
            default: break
        }
    }

    boolean trackPage() {
        return (mTypeFlag & Constant.type.TYPE_TRACK_PAGE) != 0
    }

    void addFlag(int flag) {
        mTypeFlag |= flag
    }

    void addField(FieldRecorder recorder) {
        fields.add(recorder)
    }

    List<FieldRecorder> getFields() {
        return fields
    }

    void setClassName(String className) {
        mClassName = className
    }

    String getClassName() {
        return mClassName
    }

    void setValue(String value) {
        mValue = value
    }

    String getValue() {
        return mValue
    }

    @Override
    String toString() {
        StringBuilder sb = new StringBuilder(
                "SourceRecorder : " + "\n" +
                        "className : " + mClassName + "\n"
        )
        for (FieldRecorder fr : fields) {
            sb.append(fr.toString())
        }
        return sb.toString()
    }

}
