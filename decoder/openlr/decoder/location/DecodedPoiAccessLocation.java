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
 */
/**
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr.decoder.location;

import java.util.List;

import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;

/**
 * Implementation of the location interface for poi with access point locations.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class DecodedPoiAccessLocation extends openlr.location.PoiAccessLocation {

	/** The list of sub route being found by the decoder. */
	private final List<List<Line>> subRouteList;

	/**
	 * Instantiates a new decoded location.
	 *
	 * @param idValue the id value
	 * @param l the l
	 * @param subs the subs
	 * @param pOff the off
	 * @param gC the g c
	 * @param sor the sor
	 * @param ori the ori
	 * @throws InvalidMapDataException 
	 */
	public DecodedPoiAccessLocation(final String idValue, final Line l,
			final List<List<Line>> subs, final int pOff,
			final GeoCoordinates gC, final SideOfRoad sor, final Orientation ori) throws InvalidMapDataException {
		super(idValue, l, pOff, gC.getLongitudeDeg(), gC.getLatitudeDeg(), sor, ori);
		subRouteList = subs;
	}

	/**
	 * Instantiates a new decoded location.
	 *
	 * @param idValue the id value
	 * @param loc the loc
	 * @param subs the subs
	 * @param gC the g c
	 * @param sor the sor
	 * @param ori the ori
	 * @throws InvalidMapDataException 
	 */
	public DecodedPoiAccessLocation(final String idValue, final Line loc,
			final List<List<Line>> subs, final GeoCoordinates gC,
			final SideOfRoad sor, final Orientation ori) throws InvalidMapDataException {
		this(idValue, loc, subs, 0, gC, sor, ori);
	}

	/**
	 * Gets the list of sub routes being found by the decoder.
	 * 
	 * @return the sub route list
	 */
	public final List<List<Line>> getSubRouteList() {
		return subRouteList;
	}

}
