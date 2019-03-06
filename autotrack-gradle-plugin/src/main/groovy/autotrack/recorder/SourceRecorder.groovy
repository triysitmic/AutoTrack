package autotrack.recorder

import autotrack.constants.Constant

class SourceRecorder extends Recorder {

    private ArrayList<FieldRecorder> fields = new ArrayList<>()

    SourceRecorder() {
    }

    @Override
    boolean shouldGenerateCode() {
        boolean flag = mType != 0
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
