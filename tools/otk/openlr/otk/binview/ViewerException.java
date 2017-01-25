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
package openlr.otk.binview;

/**
 * A ViewerException will be thrown if the conversion from binary data to a 
 * human readable string fails.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
@SuppressWarnings("serial")
public class ViewerException extends Exception {

	/**
	 * Instantiates a new viewer exception with a message.
	 * 
	 * @param message
	 *            the message
	 */
	public ViewerException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new viewer exception with a message and cause.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ViewerException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new viewer exception with a cause.
	 * 
	 * @param cause
	 *            the cause
	 */
	public ViewerException(final Throwable cause) {
		super(cause);
	}

}
