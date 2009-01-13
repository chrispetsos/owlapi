package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2007<br><br>
 *
 * Provides an entity URI conversion strategy which converts
 * entity URIs to a common base and alpha-numeric fragment.
 * The fragment is of the form An, where n is an integer (starting
 * at 1), and A is a string which depends on the type of entity:
 * <ul>
 * <li>For classes: A = "C"</li>
 * <li>For object properties: A = "op" </li>
 * <li>For data properties: A = "dp"</li>
 * <li>For individuals: A = "i"</li>
 * </ul>
 */
public class OWLEntityTinyURIConversionStrategy implements OWLEntityURIConverterStrategy {

    public static final String DEFAULT_BASE = "http://tinyname.org#";

    private String base;

    private Map<OWLEntity, URI> entityNameMap;

    private OWLEntityFragmentProvider fragmentProvider;


    /**
     * Constructs an entity URI converter strategy, where the base
     * of the generated URIs corresponds to the value specified
     * by the DEFAULT_BASE constant.
     */
    public OWLEntityTinyURIConversionStrategy() {
        this(DEFAULT_BASE);
    }


    /**
     * Constructs an entity URI converter strategy, where the specified
     * base is used for the base of the URIs generated by the generator.
     * @param base The base to be used.
     */
    public OWLEntityTinyURIConversionStrategy(String base) {
        this.base = base;
        entityNameMap = new HashMap<OWLEntity, URI>();
        fragmentProvider = new OWLEntityFragmentProvider();
    }


    public URI getConvertedURI(OWLEntity entity) {
        URI uri = entityNameMap.get(entity);
        if(uri != null) {
            return uri;
        }
        if(entity instanceof OWLDatatype) {
            return entity.getURI();
        }
        String name = fragmentProvider.getName(entity);
        uri = URI.create(base + name);
        entityNameMap.put(entity, uri);
        return uri;
    }


    private class OWLEntityFragmentProvider implements OWLEntityVisitor {

        private String name;

        private int classCount = 0;

        private int objectPropertyCount = 0;

        private int dataPropertyCount = 0;

        private int individualCount = 0;


        public String getName(OWLEntity entity) {
            entity.accept(this);
            return name;
        }


        public void visit(OWLClass cls) {
            classCount++;
            name = "C" + classCount;
        }


        public void visit(OWLDatatype datatype) {

        }


        public void visit(OWLIndividual individual) {
            individualCount++;
            name = "i" + individualCount;
        }


        public void visit(OWLDataProperty property) {
            dataPropertyCount++;
            name = "dp" + dataPropertyCount;
        }


        public void visit(OWLObjectProperty property) {
            objectPropertyCount++;
            name = "op" + objectPropertyCount;
        }
    }
}
