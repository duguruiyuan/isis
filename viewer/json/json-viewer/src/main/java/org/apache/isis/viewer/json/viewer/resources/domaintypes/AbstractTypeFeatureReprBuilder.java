/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.isis.viewer.json.viewer.resources.domaintypes;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectFeature;
import org.apache.isis.core.metamodel.spec.feature.ObjectMember;
import org.apache.isis.viewer.json.applib.JsonRepresentation;
import org.apache.isis.viewer.json.applib.RepresentationType;
import org.apache.isis.viewer.json.viewer.ResourceContext;
import org.apache.isis.viewer.json.viewer.representations.LinkFollower;
import org.apache.isis.viewer.json.viewer.representations.LinkBuilder;
import org.apache.isis.viewer.json.viewer.representations.ReprRendererAbstract;
import org.apache.isis.viewer.json.viewer.resources.domainobjects.MemberType;

public abstract class AbstractTypeFeatureReprBuilder<R extends ReprRendererAbstract<R, SpecAndFeature<T>>, T extends ObjectFeature> extends ReprRendererAbstract<R, SpecAndFeature<T>> {

    protected ObjectSpecification objectSpecification;
    protected MemberType memberType;
    protected T objectFeature;

    public AbstractTypeFeatureReprBuilder(ResourceContext resourceContext, LinkFollower linkFollower, RepresentationType representationType, JsonRepresentation representation) {
        super(resourceContext, linkFollower, representationType, representation);
    }

    public ObjectSpecification getObjectSpecification() {
        return objectSpecification;
    }
    
    public T getObjectFeature() {
        return objectFeature;
    }

    /**
     * null if the feature is an object action param.
     * @return
     */
    public MemberType getMemberType() {
        return memberType;
    }
    
    @Override
    public R with(SpecAndFeature<T> specAndFeature) {
        objectSpecification = specAndFeature.getObjectSpecification();
        objectFeature = specAndFeature.getObjectFeature();
        memberType = MemberType.determineFrom(objectFeature);
        return cast(this);
    }
    
    
    protected void includeSelfIfRequired() {
        if(!includesSelf) {
            return;
        } 
        
        representation.mapPut("self", 
            LinkBuilder.newBuilder(getResourceContext(), "self", getRepresentationType(), "domainTypes/%s/%s/%s", 
                    getObjectSpecification().getFullIdentifier(), getMemberType().getUrlPart(), ((ObjectMember)getObjectFeature()).getId()).build());
    }


}