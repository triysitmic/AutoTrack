package autotrack;

import java.util.List;

import autotrack.impl.Tagged;

/**
 * @author zhengyanda
 */
public class CommonTag implements Tagged {

    private List<String> mValues;

    public CommonTag(List<String> values) {
        mValues = values;
    }

    @Override
    public List<String> getTags() {
        return mValues;
    }
}
