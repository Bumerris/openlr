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
package openlr.encoder.locationCheck.worker;

import java.util.List;

import openlr.OpenLRProcessingException;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.Line;
import openlr.map.MapDatabase;

import org.apache.log4j.Logger;

/**
 * The Class LineLocationCheck.
 */
public class LineLocationCheck extends LocationCheck {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(LineLocationCheck.class);
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CheckResult check(final OpenLREncoderProperties properties,
			final MapDatabase mapDB, final Location location) throws OpenLRProcessingException {
		if (mapDB == null) {
			LOG.error("map database is empty");
			return new CheckResult(EncoderReturnCode.MAP_DATABASE_IS_EMPTY);
		}

		if (location.getLocationLines() == null
				|| location.getLocationLines().isEmpty()) {
			LOG.error("location is empty");
			return new CheckResult(EncoderReturnCode.LOCATION_IS_EMPTY);
		}

		if (!checkLocationConnection(location.getLocationLines())) {
			LOG.error("location " + location.getID() + " is not connected");
			return new CheckResult(EncoderReturnCode.LOCATION_NOT_CONNECTED);
		}
		
		/* check for turn restrictions */
		if (properties.isCheckTurnRestrictions()
				&& checkTurnRestrictions(mapDB, location)) {
			LOG.error("turn restriction conflict (" + location.getID() + ")");
			return new CheckResult(
					EncoderReturnCode.LOCATION_CONTAINS_TURN_RESTRICTION);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("location check passed");
		}
		return CheckResult.PASSED;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CheckResult checkOffsets(final OpenLREncoderProperties properties,
			final Location location) throws OpenLRProcessingException {
		if (location.getLocationLines() == null
				|| location.getLocationLines().isEmpty()) {
			LOG.error("location is empty");
			return new CheckResult(EncoderReturnCode.LOCATION_IS_EMPTY);
		}
		return checkLocationOffsets(location, properties);
	}
	
	/**
	 * Checks the location offsets. Each offset value needs to be less than the
	 * maximum distance value, otherwise the value would not fit into the binary
	 * data format.
	 *
	 * @param location the location
	 * @param properties the encoder properties
	 * @return true, if both offsets match this criteria
	 * @throws OpenLRProcessingException the open lr processing exception
	 */
	private CheckResult checkLocationOffsets(final Location location,
			final OpenLREncoderProperties properties) throws OpenLRProcessingException {
		int posOff = location.getPositiveOffset();
		int negOff = location.getNegativeOffset();
		List<? extends Line> lines = location.getLocationLines();
		int totalLength = 0;
		for (Line l : lines) {
			totalLength += l.getLineLength();
		}
		if (posOff + negOff > totalLength) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("offsets are longer than the location");
			}
			return new CheckResult(EncoderReturnCode.OFFSET_FAILURE);
		}

		int maxDistance = properties.getMaximumDistanceLRP();
		if (posOff > maxDistance) {
			LOG
					.warn("positive offset exceeds maximum distance, a physical format may not support this");
		}
		if (negOff > maxDistance) {
			LOG
					.warn("negative offset exceeds maximum distance, a physical format may not support this");
		}
		return CheckResult.PASSED;
	}

}
