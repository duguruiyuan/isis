/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.core.metamodel.facets.collections.collection.typeof;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.FacetedMethod;
import org.apache.isis.core.metamodel.facets.actcoll.typeof.TypeOfFacetAbstract;
import org.apache.isis.core.metamodel.spec.SpecificationLoader;

public class TypeOfFacetOnCollectionFromCollectionAnnotation extends TypeOfFacetAbstract {

    public static TypeOfFacetOnCollectionFromCollectionAnnotation create(final Collection annotation, final FacetedMethod facetHolder, final SpecificationLoader specificationLoader) {
        if(annotation == null) {
            return null;
        }
        final Class<?> typeOfClass = annotation.typeOf();
        if(typeOfClass == Object.class) {
            return null;
        }
        return new TypeOfFacetOnCollectionFromCollectionAnnotation(typeOfClass, facetHolder, specificationLoader);
    }

    private TypeOfFacetOnCollectionFromCollectionAnnotation(final Class<?> type, final FacetHolder holder, final SpecificationLoader specificationLookup) {
        super(type, holder, specificationLookup);
    }

}
