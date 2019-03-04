package autotrack.recorder

class SourceRecorder {

    private String mClassName

    private ArrayList<FieldRecorder> fields = new ArrayList<>()

    private int mTypeFlag = 0

    SourceRecorder() {
    }

    boolean shouldGenerateCode() {
        boolean b = mTypeFlag != 0
        if (fields == null || fields.size() == 0) {
            return b
        }
        for (FieldRecorder fr : fields) {
            b = b || fr.shouldGenertateCode()
        }
        return b
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

    void setDesc(String desc) {
        mDesc = desc
    }

    String getDesc() {
        return mDesc
    }

}
