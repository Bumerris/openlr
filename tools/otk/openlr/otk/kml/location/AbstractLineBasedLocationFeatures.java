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
package openlr.otk.kml.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import openlr.location.Location;
import openlr.map.Line;
import openlr.map.Node;
import openlr.otk.kml.ContentProvider;
import openlr.otk.kml.KmlUtil;
import de.micromata.opengis.kml.v_2_2_0.Feature;

/**
 * Generates KML features of the list of lines provided by the location.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 * @param <T>
 *            The concrete type of location reference to process, must be one
 *            that delivers content for {@link Location#getLocationLines()}
 */
abstract class AbstractLineBasedLocationFeatures<T extends Location> extends
        ContentProvider<T> {

    /**
     * Creates a new content writer
     * 
     * @param location
     *            The object to draw
     */
    public AbstractLineBasedLocationFeatures(final T location) {
        super(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Feature> createContent(final T location) {

        List<Feature> features = createLines(location);
        features.addAll(createAdditionalContent(location));
        return features;
    }

    /**
     * Creates KML features that draw the lines provided by the location
     * including their attached network nodes.
     * 
     * @param location
     *            the location to store.
     * @return The list of KML features
     */
    private List<Feature> createLines(final T location) {

        Set<Node> drawnNodes = new HashSet<Node>();

        List<Feature> features = new ArrayList<Feature>();
        for (int i = 0; i < location.getLocationLines().size(); i++) {
            Line line = location.getLocationLines().get(i);
            if (i == 0) {

                /* first start node. */
                Node startNode = line.getStartNode();
                features.add(KmlUtil.createPoint(startNode.getLongitudeDeg(),
                        startNode.getLatitudeDeg(),
                        String.valueOf(startNode.getID()), "",
                        String.format("node-%d", startNode.getID()),
                        LocationStyleIdentifier.POINT));
                drawnNodes.add(startNode);
            }

            features.add(KmlUtil.createLineString(line,
                    String.valueOf(line.getID()), "Line " + line.getID(),
                    "Line " + line.getID(), LocationStyleIdentifier.LINE));

            /* End node. */
            Node endNode = line.getEndNode();

            if (!drawnNodes.contains(endNode)) {
                features.add(KmlUtil.createPoint(endNode.getLongitudeDeg(),
                        endNode.getLatitudeDeg(),
                        String.valueOf(endNode.getID()), "",
                        String.format("node-%d", endNode.getID()),
                        LocationStyleIdentifier.POINT));

                drawnNodes.add(endNode);
            }
        }

        return features;
    }

    /**
     * Implements additional KML generation for the specific point location
     * implementation
     * 
     * @param location
     *            The location to draw
     * @return The generated KML features
     */
    protected abstract List<Feature> createAdditionalContent(Location location);

}
