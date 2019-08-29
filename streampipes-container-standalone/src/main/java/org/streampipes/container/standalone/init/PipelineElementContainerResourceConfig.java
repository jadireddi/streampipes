/*
Copyright 2019 FZI Forschungszentrum Informatik

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.streampipes.container.standalone.init;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import org.streampipes.container.api.Element;
import org.streampipes.container.api.InvocableElement;
import org.streampipes.container.api.PipelineTemplateElement;
import org.streampipes.container.api.SecElement;
import org.streampipes.container.api.SepElement;
import org.streampipes.container.api.SepaElement;
import org.streampipes.container.api.WelcomePage;

@Component
public class PipelineElementContainerResourceConfig extends ResourceConfig {

  public PipelineElementContainerResourceConfig() {
    register(Element.class);
    register(InvocableElement.class);
    register(SecElement.class);
    register(SepaElement.class);
    register(SepElement.class);
    register(WelcomePage.class);
    register(PipelineTemplateElement.class);
  }
}