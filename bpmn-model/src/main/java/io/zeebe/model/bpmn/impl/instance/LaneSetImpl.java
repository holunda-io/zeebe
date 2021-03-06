/*
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
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
 */

package io.zeebe.model.bpmn.impl.instance;

import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN20_NS;
import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_NAME;
import static io.zeebe.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_LANE_SET;

import io.zeebe.model.bpmn.instance.BaseElement;
import io.zeebe.model.bpmn.instance.Lane;
import io.zeebe.model.bpmn.instance.LaneSet;
import java.util.Collection;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.ChildElementCollection;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

/**
 * The BPMN laneSet element
 *
 * @author Sebastian Menski
 */
public class LaneSetImpl extends BaseElementImpl implements LaneSet {

  protected static Attribute<String> nameAttribute;
  protected static ChildElementCollection<Lane> laneCollection;

  public LaneSetImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  public static void registerType(ModelBuilder modelBuilder) {
    final ModelElementTypeBuilder typeBuilder =
        modelBuilder
            .defineType(LaneSet.class, BPMN_ELEMENT_LANE_SET)
            .namespaceUri(BPMN20_NS)
            .extendsType(BaseElement.class)
            .instanceProvider(
                new ModelTypeInstanceProvider<LaneSet>() {
                  @Override
                  public LaneSet newInstance(ModelTypeInstanceContext instanceContext) {
                    return new LaneSetImpl(instanceContext);
                  }
                });

    nameAttribute = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_NAME).build();

    final SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    laneCollection = sequenceBuilder.elementCollection(Lane.class).build();

    typeBuilder.build();
  }

  @Override
  public String getName() {
    return nameAttribute.getValue(this);
  }

  @Override
  public void setName(String name) {
    nameAttribute.setValue(this, name);
  }

  @Override
  public Collection<Lane> getLanes() {
    return laneCollection.get(this);
  }
}
