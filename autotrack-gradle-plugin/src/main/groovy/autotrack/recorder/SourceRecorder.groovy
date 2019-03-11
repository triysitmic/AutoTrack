package autotrack.recorder

import autotrack.constants.Constant

class SourceRecorder extends Recorder {

    private ArrayList<FieldRecorder> fields = new ArrayList<>()

    private List<String> mValues = new ArrayList<>()

    private boolean hasInjectMethod

    SourceRecorder() {
    }

    void addValue(String value) {
        mValues.add(value)
    }

    List<String> getValues() {
        return mValues
    }

    @Override
    boolean shouldGenerateCode() {
        boolean flag = (mType != 0) || hasInjectMethod
        if (fields == null || fields.size() == 0) {
            return flag
        }
        for (FieldRecorder fr : fields) {
            flag = flag || fr.shouldGenerateCode()
        }
        return flag
    }

    @Override
    void addTypeByDesc(String desc) {
        switch (desc) {
            case Constant.desc.ANNOTATION_PAGE:
                addType(Constant.type.TYPE_TRACK_PAGE)
                break
            default: break
        }
    }

    void setInjectMethod() {
        if (hasInjectMethod) {
            return
        }
        hasInjectMethod = true
    }

    boolean trackPage() {
        return (mType & Constant.type.TYPE_TRACK_PAGE) != 0
    }

    void addField(FieldRecorder recorder) {
        fields.add(recorder)
    }

    List<FieldRecorder> getFields() {
        return fields
    }

    @Override
    String toString() {
        StringBuilder sb = new StringBuilder(
                "SourceRecorder : " + "\n" +
                        "className : " + mName + "\n" +
                        "mValues : " + getValues().toString() + "\n"
        )
        for (FieldRecorder fr : fields) {
            sb.append(fr.toString())
        }
        return sb.toString()
    }

}
