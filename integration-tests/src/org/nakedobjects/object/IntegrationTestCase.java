package org.nakedobjects.object;

import org.nakedobjects.LocalReflectionFactory;
import org.nakedobjects.object.repository.NakedObjectsClient;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import test.org.nakedobjects.object.defaults.MockObjectPersistor;
import test.org.nakedobjects.object.repository.object.defaults.AbstractSpecificationLoader;

public abstract class IntegrationTestCase extends TestCase {
    protected MockObjectPersistor manager;

    protected void setUp() throws Exception {
        super.setUp();

        LogManager.getLoggerRepository().setThreshold(Level.OFF);
        manager = MockObjectPersistor.setup();
        new AbstractSpecificationLoader();
        new NakedObjectsClient().setReflectionFactory(new LocalReflectionFactory());
    }
    
    protected void tearDown() throws Exception {
        manager.shutdown();
        super.tearDown();
    }
    

    public static void assertEquals(String name, String expected, NakedObject value) {
        assertEquals(name, expected, value.titleString().toString());
    }

    public static void assertEquals(String expected, NakedObject value) {
        assertEquals(expected, value.titleString().toString());
    }

    public static void assertEquals(NakedObject expected, NakedObject value) {
    	assertEquals(expected.titleString().toString(), value.titleString().toString());
    }

    protected NakedObjectAssociation findAssocation(String attributeName, NakedObject forObject) {
        NakedObjectSpecification c = forObject.getSpecification();

        return (NakedObjectAssociation) c.getField(attributeName);
    }
}


/*
Naked Objects - a framework that exposes behaviourally complete
business objects directly to the user.
Copyright (C) 2000 - 2005  Naked Objects Group Ltd

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

The authors can be contacted via www.nakedobjects.org (the
registered address of Naked Objects Group is Kingsway House, 123 Goldworth
Road, Woking GU21 1NR, UK).
*/