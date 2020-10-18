/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.apache.streampipes.wrapper.siddhi.engine;

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.stream.output.StreamCallback;
import org.apache.streampipes.wrapper.context.EventProcessorRuntimeContext;
import org.apache.streampipes.wrapper.params.binding.EventProcessorBindingParams;
import org.apache.streampipes.wrapper.routing.SpOutputCollector;
import org.apache.streampipes.wrapper.siddhi.engine.callback.SiddhiDebugCallback;
import org.apache.streampipes.wrapper.siddhi.engine.callback.SiddhiOutputStreamCallback;
import org.apache.streampipes.wrapper.siddhi.engine.callback.SiddhiOutputStreamDebugCallback;
import org.apache.streampipes.wrapper.siddhi.model.EventType;
import org.apache.streampipes.wrapper.siddhi.manager.SpSiddhiManager;
import org.apache.streampipes.wrapper.siddhi.utils.SiddhiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SiddhiEngine {

  private static final Logger LOG = LoggerFactory.getLogger(SiddhiEngine.class);

  private SiddhiAppRuntime siddhiAppRuntime;
  private final Map<String, InputHandler> siddhiInputHandlers;
  private Map<String, List<EventType>> typeInfo;

  private Boolean debugMode;
  private SiddhiDebugCallback debugCallback;

  public SiddhiEngine() {
    this.siddhiInputHandlers = new HashMap<>();
    this.debugMode = false;
  }

  public SiddhiEngine(SiddhiDebugCallback debugCallback) {
    this();
    this.debugCallback = debugCallback;
    this.debugMode = true;
  }

  public void initializeEngine(SiddhiInvocationConfig<? extends EventProcessorBindingParams> settings,
                               SpOutputCollector spOutputCollector,
                               EventProcessorRuntimeContext runtimeContext) {

    EventProcessorBindingParams params = settings.getParams();
    this.typeInfo = settings.getEventTypeInfo();
    SiddhiManager siddhiManager = SpSiddhiManager.INSTANCE.getSiddhiManager();

    //this.timestampField = removeStreamIdFromTimestamp(setTimestamp(parameters));

    siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(settings.getSiddhiAppString());
    settings.getParams()
            .getInEventTypes()
            .forEach((key, value) -> {
              String preparedKey = SiddhiUtils.prepareName(key);
              siddhiInputHandlers.put(key, siddhiAppRuntime.getInputHandler(preparedKey));
            });

    StreamCallback callback;

    if (!debugMode) {
      callback = new SiddhiOutputStreamCallback(spOutputCollector, settings.getOutputEventKeys(), runtimeContext);
    } else {
      callback = new SiddhiOutputStreamDebugCallback(debugCallback);
    }

    siddhiAppRuntime.addCallback(SiddhiUtils.prepareName(SiddhiUtils.getOutputTopicName(params)), callback);
    siddhiAppRuntime.start();
  }

  public void processEvent(org.apache.streampipes.model.runtime.Event event) {
    try {
      String sourceId = event.getSourceInfo().getSourceId();
      InputHandler inputHandler = siddhiInputHandlers.get(sourceId);
      List<String> eventKeys = this.typeInfo
              .get(sourceId)
              .stream()
              .map(EventType::getEventTypeName)
              .collect(Collectors.toList());

      inputHandler.send(SiddhiUtils.toObjArr(eventKeys, event.getRaw()));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void shutdownEngine() {
    this.siddhiAppRuntime.shutdown();
  }

//  public void setSortedEventKeys(List<String> sortedEventKeys) {
//    String streamId = (String) this.listOfEventKeys.keySet().toArray()[0];    // only reliable if there is only one stream, else use changeEventKeys() to respective streamId
//    changeEventKeys(streamId, sortedEventKeys);
//  }
//
//  public void changeEventKeys(String streamId, List<String> newEventKeys) {
//    this.listOfEventKeys.put(streamId, newEventKeys);
//  }

}
