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

package org.apache.streampipes.export.resolver;

import org.apache.streampipes.export.utils.EventGroundingProcessor;
import org.apache.streampipes.export.utils.SerializationUtils;
import org.apache.streampipes.model.SpDataStream;
import org.apache.streampipes.model.export.ExportItem;

import com.fasterxml.jackson.core.JsonProcessingException;

public class DataSourceResolver extends AbstractResolver<SpDataStream> {

  @Override
  public SpDataStream findDocument(String resourceId) {
    return getNoSqlStore().getDataStreamStorage().getElementById(resourceId);
  }

  @Override
  public SpDataStream modifyDocumentForExport(SpDataStream doc) {
    doc.setRev(null);
    return doc;
  }

  @Override
  public SpDataStream readDocument(String serializedDoc) throws JsonProcessingException {
    return SerializationUtils.getSpObjectMapper().readValue(serializedDoc, SpDataStream.class);
  }

  @Override
  public ExportItem convert(SpDataStream document) {
    return new ExportItem(document.getElementId(), document.getName(), true);
  }

  @Override
  public void writeDocument(String document) throws JsonProcessingException {
    getNoSqlStore().getDataStreamStorage().createElement(deserializeDocument(document));
  }

  public void writeDocument(String document,
                            boolean overrideDocument) throws JsonProcessingException {
    var dataStream = deserializeDocument(document);
    if (overrideDocument) {
      if (dataStream.getEventGrounding() != null) {
        EventGroundingProcessor.applyOverride(dataStream.getEventGrounding().getTransportProtocol());
      }
    }
    getNoSqlStore().getDataStreamStorage().createElement(dataStream);
  }

  @Override
  protected SpDataStream deserializeDocument(String document) throws JsonProcessingException {
    return this.spMapper.readValue(document, SpDataStream.class);
  }
}
