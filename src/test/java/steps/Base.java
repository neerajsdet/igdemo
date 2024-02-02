package steps;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import rest.utils.ReqResUtils;

@Slf4j
public class Base {

  protected static final HashMap<Long, ReqResUtils> reqResUtilMap = new HashMap<>();
  public static Map<Long, HashMap<String, String>> globalDataMap = new HashMap<>();
  Long threadId;

  public Base() {
    threadId = Thread.currentThread().getId();
//    log.info("Current Thread ID: " + threadId);
    if (!reqResUtilMap.containsKey(threadId)) {
      reqResUtilMap.put(threadId, new ReqResUtils());
      globalDataMap.put(threadId, new HashMap<>());
    }
  }




}
