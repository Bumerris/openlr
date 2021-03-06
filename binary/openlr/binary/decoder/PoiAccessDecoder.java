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
package openlr.binary.decoder;

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.binary.BinaryReturnCode;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.FirstLRP;
import openlr.binary.data.LastLRP;
import openlr.binary.data.Offset;
import openlr.binary.data.RawBinaryData;
import openlr.binary.data.RelativeCoordinates;
import openlr.binary.impl.OffsetsBinaryImpl;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPoiAccessLocRef;

/**
 * The decoder for the poi with access point location type.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class PoiAccessDecoder extends AbstractDecoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final RawLocationReference decodeData(final String id,
			final ByteArrayBitstreamInput ibs, final int totalBytes,
			final int version, final RawBinaryData binData)
			throws OpenLRBinaryException {
		FirstLRP firstLRP = null;
		LastLRP lastLRP = null;
		Offset posOff = null;
		firstLRP = new FirstLRP(ibs);
		Orientation orientation = resolveOrientation(firstLRP.getAttrib1());
		LocationReferencePoint lrp01 = createLRP(1, firstLRP);
		lastLRP = new LastLRP(ibs);
		SideOfRoad sideOfRoad = resolveSideOfRoad(lastLRP.getAttrib1());
		LocationReferencePoint lrp02 = createLRP(2, lastLRP,
				lrp01.getLongitudeDeg(), lrp01.getLatitudeDeg());
		Offsets offsets = new OffsetsBinaryImpl(0, 0);
		if (lastLRP.getAttrib4().getPOffsetf() == OpenLRBinaryConstants.HAS_OFFSET) {
			posOff = new Offset(ibs);
			float pOffValue = calculateRelativeDistance(posOff.getOffset());
			offsets = new OffsetsBinaryImpl(pOffValue, 0);
		}
		RelativeCoordinates relCoord = new RelativeCoordinates(ibs);
		double lon = lrp01.getLongitudeDeg()
				+ (relCoord.getLon() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
		double lat = lrp01.getLatitudeDeg()
				+ (relCoord.getLat() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
		GeoCoordinates geoCoord = null;
		try {
			geoCoord = new GeoCoordinatesImpl(lon, lat);
		} catch (InvalidMapDataException e) {
			throw new OpenLRBinaryException(BinaryReturnCode.INVALID_BINARY_DATA, e);
		}
		RawPoiAccessLocRef rawLocRef = new RawPoiAccessLocRef(id, lrp01, lrp02,
				offsets, geoCoord, sideOfRoad, orientation);
		if (binData != null) {
			binData.setBinaryRelativeCoordinates(relCoord);
			binData.setBinaryFirstLRP(firstLRP);
			binData.setBinaryLastLRP(lastLRP);
			binData.setBinaryPosOffset(posOff);
		}
		return rawLocRef;
	}

}
