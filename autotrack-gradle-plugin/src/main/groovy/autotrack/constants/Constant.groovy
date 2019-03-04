package autotrack.constants

class Constant {

    static HashSet<String> descs

    static {
        descs = new HashSet<>()
        descs.add(desc.ANNOTATION_CLICK)
        descs.add(desc.ANNOTATION_CLICK_EXPOSURE)
        descs.add(desc.ANNOTATION_EXPOSURE)
        descs.add(desc.ANNOTATION_PAGE)
        descs.add(desc.ANNOTATION_INTERCEPT)
    }

    public final static String POSTFIX = "_AutoTrack"

    static class type {
        public final static int TYPE_TRACK_CLICK = 0b000001
        public final static int TYPE_TRACK_EXPOSURE = 0b000010

        public final static int TYPE_TRACK_PAGE = 0b000001
    }

    static class desc {
        public final static String NULL = "()V"

        private final static String ANNOTATION_PACKAGE = "autotrack/annotations/"

        public final static String ANNOTATION_CLICK = "L" + ANNOTATION_PACKAGE + "Click;"
        public final static String ANNOTATION_CLICK_EXPOSURE =
                "L" + ANNOTATION_PACKAGE + "ClickAndExposure;"
        public final static String ANNOTATION_EXPOSURE = "L" + ANNOTATION_PACKAGE + "" + "Exposure;"
        public final static String ANNOTATION_PAGE = "L" + ANNOTATION_PACKAGE + "Page;"
        public final static String ANNOTATION_INTERCEPT = "L" + ANNOTATION_PACKAGE + "Intercept"
    }
}
