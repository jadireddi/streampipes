/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.connect.adapter.specific;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.streampipes.connect.adapter.specific.sensemap.OpenSenseMapAdapter;
import org.streampipes.connect.exception.AdapterException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

public class PullAdapterTest {
    private int port = 4082;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);


    @Test
    public void getDataFromEndpointStringSuccess() throws AdapterException {
        String expected = "EXPECTED_STRING";

        stubFor(get(urlEqualTo("/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(expected)));

        String result = PullAdapter.getDataFromEndpointString("http://localhost:" + port + "/");

        assertEquals(expected, result);

    }

    @Test(expected = AdapterException.class)
    public void getDataFromEndpointStringFail() throws AdapterException {
        stubFor(get(urlEqualTo("/"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("")));

        PullAdapter.getDataFromEndpointString("http://localhost:" + port + "/");

    }
}