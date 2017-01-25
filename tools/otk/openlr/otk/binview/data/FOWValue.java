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
package openlr.otk.binview.data;

import openlr.map.FormOfWay;

/**
 * The FOWValue represents a form of way.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class FOWValue {
	
	/** The internal form of way id. */
	private final int fow;

	/**
	 * Instantiates a new form of way value with id fowValue.
	 * 
	 * @param fowValue the fow id
	 */
	public FOWValue(final int fowValue) {
		fow = fowValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String toString() {
		return Integer.toString(fow);
	}

	/**
	 * Returns the form of way name.
	 * 
	 * @return the fow name
	 */
	public final String name() {
		if (fow >= 0 && fow < FormOfWay.getFOWs().size()) {
			return FormOfWay.getFOWs().get(fow).name();
		} else {
			return null;
		}
	}

}
