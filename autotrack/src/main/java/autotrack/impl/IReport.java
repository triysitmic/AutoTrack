package autotrack.impl;

import java.util.List;

/**
 * @author zhengyanda
 */
public interface IReport {
    void report(Object values);

    void reportExposure(List<String> values);

    void reportPage(List<String> values);

    void reportClick(List<String> values);
}
