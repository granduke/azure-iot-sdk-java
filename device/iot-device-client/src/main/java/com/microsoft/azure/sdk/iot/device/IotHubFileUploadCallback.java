package com.microsoft.azure.sdk.iot.device;

import java.net.URI;

/**
 * An interface for an IoT Hub file upload callback.
 *
 * Developers are expected to create an implementation of this interface, and
 * the transport will call
 * {@link IotHubFileUploadCallback#execute(IotHubStatusCode, URI, Object)} upon
 * receiving a response from an IoT Hub.
 */
public interface IotHubFileUploadCallback {
	/**
     * Executes the callback.
     *
     * @param responseStatus
     *            the response status code.
     * @param blobURI
     *            the full URI of the uploaded blob (may be null if blob upload
     *            was unsuccessful)
     * @param callbackContext
     *            a custom context given by the developer.
     */
	void execute(IotHubStatusCode responseStatus, URI blobURI, Object callbackContext);
}
