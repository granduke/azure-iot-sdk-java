// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package tests.unit.com.microsoft.azure.sdk.iot.device.fileupload;

import com.microsoft.azure.sdk.iot.device.IotHubFileUploadCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.fileupload.FileUploadInProgress;
import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Unit tests for file upload in progress class.
 * 100% methods, 100% lines covered
 */
public class FileUploadInProgressTest
{
    @Mocked
    private IotHubFileUploadCallback mockIotHubFileUploadCallback;

    @Mocked
    private Future mockFuture;

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_001: [The constructor shall sore the content of the `statusCallback`, and `statusCallbackContext`.] */
    @Test
    public void constructorSuccess()
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();

        // act
        FileUploadInProgress fileUploadInProgress = Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                mockIotHubFileUploadCallback, context);

        // assert
        assertEquals(mockIotHubFileUploadCallback, Deencapsulation.getField(fileUploadInProgress, "statusCallback"));
        assertEquals(context, Deencapsulation.getField(fileUploadInProgress, "statusCallbackContext"));
    }

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_002: [If the `statusCallback` is null, the constructor shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrows()
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();

        // act
        Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                (IotHubFileUploadCallback)null, context);
    }

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_003: [The setTask shall sore the content of the `task`.] */
    @Test
    public void setTaskSuccess()
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();
        FileUploadInProgress fileUploadInProgress = Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                mockIotHubFileUploadCallback, context);

        // act
        Deencapsulation.invoke(fileUploadInProgress, "setTask", new Class[] {Future.class}, mockFuture);

        // assert
        assertEquals(mockFuture, Deencapsulation.getField(fileUploadInProgress, "task"));
    }

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_004: [If the `task` is null, the setTask shall throws IllegalArgumentException.] */
    @Test (expected = IllegalArgumentException.class)
    public void setTaskThrows()
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();
        FileUploadInProgress fileUploadInProgress = Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                mockIotHubFileUploadCallback, context);

        // act
        Deencapsulation.invoke(fileUploadInProgress, "setTask", new Class[] {Future.class}, (Future)null);
    }

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_005: [The triggerCallback shall call the execute in `statusCallback` with the provided `iotHubStatusCode` and `statusCallbackContext`.] */
    @Test
    public void triggerCallbackSuccess() throws URISyntaxException
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();
        final URI blobURI = new URI("test/blob.uri");
        FileUploadInProgress fileUploadInProgress = Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                mockIotHubFileUploadCallback, context);

        // act
        Deencapsulation.invoke(fileUploadInProgress, "triggerCallback", new Class[] {IotHubStatusCode.class, URI.class}, IotHubStatusCode.OK, blobURI);

        // assert
        new Verifications()
        {
            {
                Deencapsulation.invoke(mockIotHubFileUploadCallback, "execute", new Class[] {IotHubStatusCode.class, URI.class, Object.class}, IotHubStatusCode.OK, blobURI, context);
                times = 1;
            }
        };
    }

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_006: [The isCancelled shall return the value of isCancelled on the `task`.] */
    @Test
    public void isCancelledTrueSuccess()
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();
        FileUploadInProgress fileUploadInProgress = Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                mockIotHubFileUploadCallback, context);
        Deencapsulation.invoke(fileUploadInProgress, "setTask", new Class[] {Future.class}, mockFuture);
        new NonStrictExpectations()
        {
            {
                mockFuture.isCancelled();
                result = true;
                times = 1;
            }
        };

        // act
        boolean result = (boolean)Deencapsulation.invoke(fileUploadInProgress, "isCancelled");

        // assert
        assertTrue(result);
    }

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_006: [The isCancelled shall return the value of isCancelled on the `task`.] */
    @Test
    public void isCancelledFalseSuccess()
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();
        FileUploadInProgress fileUploadInProgress = Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                mockIotHubFileUploadCallback, context);
        Deencapsulation.invoke(fileUploadInProgress, "setTask", new Class[] {Future.class}, mockFuture);
        new NonStrictExpectations()
        {
            {
                mockFuture.isCancelled();
                result = false;
                times = 1;
            }
        };

        // act
        boolean result = (boolean)Deencapsulation.invoke(fileUploadInProgress, "isCancelled");

        // assert
        assertFalse(result);
    }

    /* Codes_SRS_FILEUPLOADINPROGRESS_21_007: [If the `task` is null, the isCancelled shall throws IOException.] */
    @Test (expected = IOException.class)
    public void isCancelledThrows()
    {
        // arrange
        final Map<String, Object> context = new HashMap<>();
        FileUploadInProgress fileUploadInProgress = Deencapsulation.newInstance(FileUploadInProgress.class,
                new Class[] {IotHubFileUploadCallback.class, Object.class},
                mockIotHubFileUploadCallback, context);

        // act
        Deencapsulation.invoke(fileUploadInProgress, "isCancelled");
    }

}
