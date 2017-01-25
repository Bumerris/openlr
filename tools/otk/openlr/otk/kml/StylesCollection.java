/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except 
 * in compliance with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License. 
**/

/**
 *  Copyright (C) 2009-12 TomTom International B.V.
 *
 *   TomTom (Legal Department)
 *   Email: legal@tomtom.com
 *
 *   TomTom (Technical contact)
 *   Email: openlr@tomtom.com
 *
 *   Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 *   the Netherlands
 */
package openlr.otk.kml;

import java.util.ArrayList;
import java.util.Collection;

import de.micromata.opengis.kml.v_2_2_0.Style;

/**
 * TODO description
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public abstract class StylesCollection extends ArrayList<Style> {

    /**
     * Default serial id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Is not supported by <code>StylesCollection</code>, throws
     * {@link UnsupportedOperationException}.
     * 
     * @param o
     *            ignored
     * @return nothing
     */
    @Override
    public final boolean remove(final Object o) {
        throw new UnsupportedOperationException(
                "Changing of styles collection not supported");
    }

    /**
     * Is not supported by <code>StylesCollection</code>, throws
     * {@link UnsupportedOperationException}.
     * 
     * @param c
     *            ignored
     * @return nothing
     */
    @Override
    public final boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException(
                "Changing of styles collection not supported");
    }

    /**
     * Is not supported by <code>StylesCollection</code>, throws
     * {@link UnsupportedOperationException}.
     * 
     * @param c
     *            ignored
     * @return nothing
     */
    @Override
    public final boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException(
                "Changing of styles collection not supported");
    }

    /**
     * Is not supported by <code>StylesCollection</code>, throws
     * {@link UnsupportedOperationException}.
     */
    @Override
    public final void clear() {
        throw new UnsupportedOperationException(
                "Changing of styles collection not supported");

    }
}
