package hello.servlet.web.frontController.v4;

import java.util.Map;
import java.util.Objects;

public interface ControllerV4 {
    /**
     *
     * @param paraMap
     * @param model
     * @return  viewname
     */
    String process(Map<String,String>paraMap, Map<String, Object> model);
}
