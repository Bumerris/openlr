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
package openlr.mapviewer.location;

import java.util.ArrayList;
import java.util.List;

import openlr.LocationType;
import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.worker.coverage.PolygonCoverage;
import openlr.geomap.MapLayer;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.location.data.AffectedLines;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.mapviewer.maplayer.PolygonMapLayer;
import openlr.mapviewer.utils.Formatter;

/**
 * The Class LocationTypePolygon.
 */
public class LocationTypePolygon extends LocationTypeArea {

	/** The Constant MIN_NUMBER_POLYGON_POINTS. */
	private static final int MIN_NUMBER_POLYGON_POINTS = 3;

	/** The polygon points. */
	private List<GeoCoordinates> polygonPoints = new ArrayList<GeoCoordinates>();

	/**
	 * Adds the or remove polygon point.
	 * 
	 * @param coord
	 *            the coord
	 * @throws InvalidMapDataException
	 *             the invalid map data exception
	 */
	private void addOrRemovePolygonPoint(final GeoCoordinates coord)
			throws InvalidMapDataException {
		if (polygonPoints.contains(coord)) {
			polygonPoints.remove(coord);
		} else {
			polygonPoints.add(coord);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    public final Location createLocation(final String identifier)
            throws LocationIncompleteException {
        if (polygonPoints.size() >= MIN_NUMBER_POLYGON_POINTS) {
            Location loc = LocationFactory.createPolygonLocation(
                    identifier, polygonPoints);
            return loc;
        } else {
            throw new LocationIncompleteException(
                    "Not enough polygon points set");
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<String> getLocationInfo() {
		List<String> info = new ArrayList<String>();
		if (polygonPoints != null) {
			int count = 1;
			for (GeoCoordinates p : polygonPoints) {
				info.add("[" + count + "]. point: "
						+ Formatter.COORD_FORMATTER.format(p.getLongitudeDeg())
						+ "/"
						+ Formatter.COORD_FORMATTER.format(p.getLatitudeDeg()));
				count++;
			}
		}
		if (areaLocCoveredLines != null && !areaLocCoveredLines.isEmpty()) {
			info.add("+++++ covered Lines ++++++++++ ");
			int count = 1;
			for (Line l : areaLocCoveredLines) {
				info.add(count + ") Line ID: " + l.getID());
				count++;
			}
		}
		if (areaLocIntersectedLines != null
				&& !areaLocIntersectedLines.isEmpty()) {
			info.add("+++++ Intersected Lines ++++++++++ ");
			int count = 1;
			for (Line l : areaLocIntersectedLines) {
				info.add(count + ") Line ID: " + l.getID());
				count++;
			}
		}
		return info;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final MapLayer getLocationMapLayer() {
		return new PolygonMapLayer(polygonPoints);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsRowsColumns() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setRowsColumns(final int rows, final int columns) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getColumns() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getRows() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsUpperRightCoord() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setUpperRightCoord(final GeoCoordinates ur) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final GeoCoordinates getUpperRightCoord() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsLowerLeftCoord() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setLowerLeftCoord(final GeoCoordinates ll) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final GeoCoordinates getLowerLeftCoord() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsRadius() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setRadius(final long radius) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsLine() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void addOrRemoveLine(final Line l) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsOrientation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setOrientation(final Orientation o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsSideOfRoad() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setSideOfRoad(final SideOfRoad s) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setLocation(final Location loc) {
		if (loc.getLocationType() == LocationType.POLYGON) {
			polygonPoints.clear();
			polygonPoints.addAll(loc.getCornerPoints());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsGeoCoordinatePoi() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setGeoCoordinatePoi(final double lon, final double lat)
			throws InvalidMapDataException {
		addOrRemovePolygonPoint(new GeoCoordinatesImpl(lon, lat));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsNegOffset() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setNegOffset(final int no) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setPosOffset(final int po) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supportsPosOffset() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean offsetsSelectable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setNegOffset(final double lon, final double lat)
			throws InvalidMapDataException {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setPosOffset(final double lon, final double lat)
			throws InvalidMapDataException {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Orientation getOrientation() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final SideOfRoad getSideOfRoad() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    final void updateCoverage(final MapDatabase map)
            throws OpenLRDecoderProcessingException {
		areaLocCoveredLines.clear();
		areaLocIntersectedLines.clear();
        if (polygonPoints.size() > 2) {
            PolygonCoverage coverage = new PolygonCoverage(polygonPoints);
            AffectedLines affected = coverage.getAffectedLines(map);
            areaLocCoveredLines.addAll(affected.getCoveredLines());
            areaLocIntersectedLines.addAll(affected.getIntersectedLines());
        }
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getPosOffset() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getNegOffset() {
		throw new UnsupportedOperationException();
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationType getLocationType() {
        return LocationType.POLYGON;
    }
}
