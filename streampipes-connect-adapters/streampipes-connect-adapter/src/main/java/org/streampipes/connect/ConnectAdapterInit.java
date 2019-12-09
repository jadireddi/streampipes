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

package org.streampipes.connect;

import org.streampipes.connect.adapters.plc4x.passive.Plc4xPassiveAdapter;
import org.streampipes.connect.adapters.ti.TISensorTag;
import org.streampipes.connect.protocol.set.HttpProtocol;
import org.streampipes.connect.adapters.coindesk.CoindeskBitcoinAdapter;
import org.streampipes.connect.adapters.gdelt.GdeltAdapter;
import org.streampipes.connect.adapters.iex.IexCloudNewsAdapter;
import org.streampipes.connect.adapters.iex.IexCloudStockAdapter;
import org.streampipes.connect.adapters.influxdb.InfluxDbSetAdapter;
import org.streampipes.connect.adapters.influxdb.InfluxDbStreamAdapter;
import org.streampipes.connect.adapters.mysql.MySqlSetAdapter;
import org.streampipes.connect.adapters.mysql.MySqlStreamAdapter;
import org.streampipes.connect.adapters.opcua.OpcUaAdapter;
import org.streampipes.connect.adapters.plc4x.s7.Plc4xS7Adapter;
import org.streampipes.connect.adapters.ros.RosBridgeAdapter;
import org.streampipes.connect.adapters.simulator.RandomDataSetAdapter;
import org.streampipes.connect.adapters.simulator.RandomDataStreamAdapter;
import org.streampipes.connect.adapters.slack.SlackAdapter;
import org.streampipes.connect.adapters.wikipedia.WikipediaEditedArticlesAdapter;
import org.streampipes.connect.adapters.wikipedia.WikipediaNewArticlesAdapter;
import org.streampipes.connect.config.ConnectWorkerConfig;
import org.streampipes.connect.container.worker.init.AdapterWorkerContainer;
import org.streampipes.connect.init.AdapterDeclarerSingleton;
import org.streampipes.connect.protocol.set.FileProtocol;
import org.streampipes.connect.protocol.set.HttpProtocol;
import org.streampipes.connect.protocol.stream.FileStreamProtocol;
import org.streampipes.connect.protocol.stream.HDFSProtocol;
import org.streampipes.connect.protocol.stream.HttpStreamProtocol;
import org.streampipes.connect.protocol.stream.KafkaProtocol;
import org.streampipes.connect.protocol.stream.MqttProtocol;
import org.streampipes.connect.protocol.stream.pulsar.PulsarProtocol;

public class ConnectAdapterInit extends AdapterWorkerContainer {

  public static void main(String[] args) {
    AdapterDeclarerSingleton
            .getInstance()

            // Protocols
            .add(new FileProtocol())
            .add(new HttpProtocol())
            .add(new FileStreamProtocol())
            .add(new HDFSProtocol())
            .add(new KafkaProtocol())
            .add(new MqttProtocol())
            .add(new HttpStreamProtocol())
            .add(new PulsarProtocol())

            // Specific Adapters
            .add(new GdeltAdapter())
            .add(new CoindeskBitcoinAdapter())
            .add(new IexCloudNewsAdapter())
            .add(new IexCloudStockAdapter())
            .add(new MySqlStreamAdapter())
            .add(new MySqlSetAdapter())
            .add(new RandomDataSetAdapter())
            .add(new RandomDataStreamAdapter())
            .add(new SlackAdapter())
            .add(new WikipediaEditedArticlesAdapter())
            .add(new WikipediaNewArticlesAdapter())
            .add(new RosBridgeAdapter())
            .add(new OpcUaAdapter())
            .add(new InfluxDbStreamAdapter())
            .add(new InfluxDbSetAdapter())
            .add(new TISensorTag())
            .add(new Plc4xS7Adapter());

    String workerUrl = ConnectWorkerConfig.INSTANCE.getConnectContainerWorkerUrl();
    String masterUrl = ConnectWorkerConfig.INSTANCE.getConnectContainerMasterUrl();
    Integer workerPort = ConnectWorkerConfig.INSTANCE.getConnectContainerWorkerPort();

    new ConnectAdapterInit().init(workerUrl, masterUrl, workerPort);

  }
}
