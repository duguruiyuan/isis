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

package org.apache.isis.applib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Layout hints for domain services.
 *
 * <p>
 * Also indicates the name, and menu ordering UI hints.
 * </p>
 */
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainServiceLayout {

    /**
     * Name of this class (overriding the name derived from its name in code).
     */
    String named() default "";

    public enum MenuBar {
        PRIMARY,
        SECONDARY,
        TERTIARY
    }

    /**
     * The menubar in which the menu that holds this service's actions should reside.
     */
    MenuBar menuBar() default MenuBar.PRIMARY;

    /**
     * Number in Dewey Decimal format representing the order.
     *
     * <p>
     * Same convention as {@link org.apache.isis.applib.annotation.MemberOrder#sequence()}.  If not specified, placed after any named.
     * </p>
     */
    String menuOrder() default "" + Integer.MAX_VALUE;
}
