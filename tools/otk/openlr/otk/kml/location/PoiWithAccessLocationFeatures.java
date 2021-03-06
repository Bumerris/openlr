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
import java.util.List;

import openlr.location.PoiAccessLocation;
import openlr.location.PointLocation;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.otk.kml.KmlUtil;
import de.micromata.opengis.kml.v_2_2_0.Feature;

/**
 * A content provider that delivers the KML features for drawing a POI with
 * access point location.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
class PoiWithAccessLocationFeatures extends
        AbstractPointLocationFeatures<PoiAccessLocation> {

    /**
     * Creates a new content writer
     * 
     * @param location
     *            The object to draw
     */
    public PoiWithAccessLocationFeatures(final PoiAccessLocation location) {
        super(location);
        addGeneralData(SideOfRoad.class.getSimpleName(), location.getSideOfRoad()
                .toString());
        addGeneralData(Orientation.class.getSimpleName(), location.getOrientation()
                .toString());             
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Feature> createAdditionalContent(final PointLocation location) {
        List<Feature> result = new ArrayList<Feature>();
        GeoCoordinates poi = location.getPointLocation();
        result.add(KmlUtil.createPoint(poi.getLongitudeDeg(),
                poi.getLatitudeDeg(), "poi", "POI", "",
                LocationStyleIdentifier.POINT));
        return result;
    }

}
