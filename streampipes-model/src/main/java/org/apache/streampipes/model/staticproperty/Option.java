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

package org.apache.streampipes.model.staticproperty;

import org.apache.streampipes.model.base.UnnamedStreamPipesEntity;

public class Option extends UnnamedStreamPipesEntity {

  private static final long serialVersionUID = 8536995294188662931L;

  private String name;

  private boolean selected;

  private String internalName;

  public Option() {
    super();
  }

  public Option(String name) {
    super();
    this.name = name;
  }

  public Option(String name, String internalName) {
    super();
    this.name = name;
    this.internalName = internalName;
  }

  public Option(String name, boolean selected) {
    super();
    this.name = name;
    this.selected = selected;
  }

  public Option(Option o) {
    super(o);
    this.name = o.getName();
    this.selected = o.isSelected();
    this.internalName = o.getInternalName();

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public String getInternalName() {
    return internalName;
  }

  public void setInternalName(String internalName) {
    this.internalName = internalName;
  }
}
