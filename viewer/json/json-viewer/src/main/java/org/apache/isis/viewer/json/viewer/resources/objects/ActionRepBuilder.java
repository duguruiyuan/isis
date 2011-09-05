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
package org.apache.isis.viewer.json.viewer.resources.objects;

import java.util.List;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.ObjectActionParameter;
import org.apache.isis.viewer.json.applib.JsonRepresentation;
import org.apache.isis.viewer.json.viewer.ResourceContext;

import com.google.common.collect.Lists;

public class ActionRepBuilder extends AbstractMemberRepBuilder<ActionRepBuilder, ObjectAction> {

	public static ActionRepBuilder newBuilder(ResourceContext resourceContext, ObjectAdapter objectAdapter, ObjectAction objectAction) {
        return new ActionRepBuilder(resourceContext, objectAdapter, objectAction);
    }

    protected ActionRepBuilder(ResourceContext resourceContext, ObjectAdapter objectAdapter, ObjectAction objectAction) {
        super(resourceContext, objectAdapter, MemberType.ACTION, objectAction);
        MemberRepType memberRepType = MemberRepType.STANDALONE;
        putSelfIfRequired(memberRepType);
        putContributedByIfRequired();
        
        putIdRep();
        withMemberType();
        representation.put("actionType", objectMember.getType());
        representation.put("numParameters", objectMember.getParameterCount());
        putParameterDetailsIfRequired(memberRepType);
        putValueIfRequired(memberRepType);
        putDisabledReason();
        putMutatorsIfRequired(memberRepType);
        putDetailsIfRequired(memberRepType);
    }

    public JsonRepresentation build() {
        return representation;
    }
    
	private void putContributedByIfRequired() {
    	if(!objectMember.isContributed()) {
    		return;
    	}
    	ObjectAdapter serviceAdapter = contributingServiceAdapter();
        JsonRepresentation contributedByLink = DomainObjectRepBuilder.newLinkToBuilder(resourceContext, serviceAdapter, getOidStringifier()).build();
		representation.put("contributedBy", contributedByLink);
	}

	private ObjectAdapter contributingServiceAdapter() {
    	ObjectSpecification serviceType = objectMember.getOnType();
    	List<ObjectAdapter> serviceAdapters = getPersistenceSession().getServices();
    	for (ObjectAdapter serviceAdapter : serviceAdapters) {
			if(serviceAdapter.getSpecification() == serviceType) {
				return serviceAdapter;
			}
		}
    	// fail fast
    	throw new IllegalStateException("Unable to locate contributing service");
	}

    private void putParameterDetailsIfRequired(MemberRepType memberRepType) {
    	if (!memberRepType.isStandalone()) {
    		return;
    	} 
    	List<Object> parameters = Lists.newArrayList();
		for (int i=0; i< objectMember.getParameterCount(); i++) {
			ObjectActionParameter param = objectMember.getParameters().get(i);
			parameters.add(paramDetails(param));
		}
		representation.put("parameters", parameters);
	}

	private Object paramDetails(ObjectActionParameter param) {
		final JsonRepresentation paramRep = JsonRepresentation.newMap();
		paramRep.put("name", param.getName());
		paramRep.put("num", param.getNumber());
		paramRep.put("description", param.getDescription());
		Object paramChoices = choicesFor(param);
		if(paramChoices != null) {
			paramRep.put("choices", paramChoices);
		}
		Object paramDefault = defaultFor(param);
		if(paramDefault != null) {
			paramRep.put("default", paramDefault);
		}
		return paramRep;
	}

	private Object choicesFor(ObjectActionParameter param) {
		ObjectAdapter[] choiceAdapters = param.getChoices(objectAdapter);
		if(choiceAdapters == null || choiceAdapters.length == 0) {
			return null;
		}
        List<Object> list = Lists.newArrayList();
        for (final ObjectAdapter choiceAdapter : choiceAdapters) {
        	ObjectSpecification objectSpec = param.getSpecification();
        	list.add(DomainObjectRepBuilder.valueOrRef(resourceContext, choiceAdapter, objectSpec, getOidStringifier(), getLocalization()));
        }
        return list;
	}

	private Object defaultFor(ObjectActionParameter param) {
		ObjectAdapter defaultAdapter = param.getDefault(objectAdapter);
		if(defaultAdapter == null) {
			return null;
		}
    	ObjectSpecification objectSpec = param.getSpecification();
    	return DomainObjectRepBuilder.valueOrRef(resourceContext, defaultAdapter, objectSpec, getOidStringifier(), getLocalization());
	}

	@Override
	protected JsonRepresentation appendMutatorArgs(MutatorSpec mutatorSpec) {
	    JsonRepresentation argList = JsonRepresentation.newArray();
        for(int i=0; i<objectMember.getParameterCount(); i++) {
            argList.add(argValueFor(i)); 
        }
        return argList;
    }

	private Object argValueFor(int i) {
    	if(objectMember.isContributed()) {
    		ObjectActionParameter actionParameter = objectMember.getParameters().get(i);
    		if (actionParameter.getSpecification().isOfType(objectAdapter.getSpecification())) {
    			return DomainObjectRepBuilder.newLinkToBuilder(resourceContext, objectAdapter, getOidStringifier()).build();
    		}
    	}
    	return "{value}";
	}

}