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
package openlr.mapviewer.maplayer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import openlr.geomap.JMapDataRepository;
import openlr.geomap.transform.JMapTransformMercator;
import openlr.map.GeoCoordinates;
import openlr.mapviewer.MapViewer;

/**
 * The Class GeoCoordinateSearchMapLayer.
 */
public class GeoCoordinateSearchMapLayer extends SearchMapLayer {

	/** The Constant NAME. */
	private static final String NAME = "GeoCoordinates search layer";

	/** The geo coord. */
	private final GeoCoordinates geoCoord;

	/** The color. */
	private Color colorPoint;

	/** The stroke size. */
	private int strokeSizePoint;

	/**
	 * Instantiates a new geo coordinate search map layer.
	 *
	 * @param gc the gc
	 * @param repo the repo
	 */
	public GeoCoordinateSearchMapLayer(final GeoCoordinates gc) {
		super(NAME);
		geoCoord = gc;
		colorPoint = MapViewer.PROPERTIES.getSearchColor();
		strokeSizePoint = MapViewer.PROPERTIES.getNodeStrokeSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void draw(final Graphics2D g,
			final JMapTransformMercator transform, final JMapDataRepository repo) {
		double lon = geoCoord.getLongitudeDeg();
		double lat = geoCoord.getLatitudeDeg();
		Point p = transform.getPixel(lon, lat);
		int diff = strokeSizePoint / 2;
		g.setColor(colorPoint);
		g.setStroke(new BasicStroke(strokeSizePoint));
		g.drawLine(p.x - diff, p.y - diff, p.x + diff, p.y + diff);
		g.drawLine(p.x - diff, p.y + diff, p.x + diff, p.y - diff);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void updateProperty(final String changedKey) {
		colorPoint = MapViewer.PROPERTIES.getSearchColor();
	}

}
