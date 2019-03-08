package autotrack.constants

class Constant {

    static HashSet<String> fieldDescs
    static HashSet<String> classDescs

    static {
        fieldDescs = new HashSet<>()
        fieldDescs.add(desc.ANNOTATION_CLICK)
        fieldDescs.add(desc.ANNOTATION_CLICK_EXPOSURE)
        fieldDescs.add(desc.ANNOTATION_EXPOSURE)
        fieldDescs.add(desc.ANNOTATION_PAGE)
        fieldDescs.add(desc.ANNOTATION_INTERCEPT)

        classDescs = new HashSet<>()
        classDescs.add(desc.ANNOTATION_PAGE)
    }

    //extension name
    public final static String EXTENSION_NAME = "autotrackExt"

    //默认的点击事件拦截周期
    public final static long DEFAUT_CLICK_CYCLE = 1500

    static class type {
        public final static int TYPE_TRACK_CLICK = 0x000001
        public final static int TYPE_TRACK_EXPOSURE = 0x000002

        public final static int TYPE_TRACK_PAGE = 0x000001
    }

    static class desc {
        private final static String ANNOTATION_PACKAGE = "autotrack/annotations/"

        public final static String ANNOTATION_CLICK = "L" + ANNOTATION_PACKAGE + "Click;"
        public final static String ANNOTATION_CLICK_EXPOSURE =
                "L" + ANNOTATION_PACKAGE + "ClickAndExposure;"
        public final static String ANNOTATION_EXPOSURE = "L" + ANNOTATION_PACKAGE + "" + "Exposure;"
        public final static String ANNOTATION_PAGE = "L" + ANNOTATION_PACKAGE + "Page;"
        public final static String ANNOTATION_INTERCEPT = "L" + ANNOTATION_PACKAGE + "Intercept;"
    }
}
