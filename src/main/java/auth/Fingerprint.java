package auth;
import etc.Json;
import jlibfprint.*;

import java.io.IOException;

public class Fingerprint extends Authentication {
    private User user;
    private Device client;

    public Fingerprint(User user) {
        this.user = user;
        DiscoveredDeviceList discoveredDeviceList = JlibFprint.discoverDevices();
        DiscoveredDevice firstDiscoveredDevice = getFirstDiscoveredDevice(discoveredDeviceList);
        if (firstDiscoveredDevice == null) {
            System.out.println("No device found to enroll fingers");
        } else {
            client = firstDiscoveredDevice.open();
        }
    }

    private static DiscoveredDevice getFirstDiscoveredDevice(DiscoveredDeviceList discoveredDeviceList) {
        return discoveredDeviceList.getDiscoveredDevices().length > 0
                ? discoveredDeviceList.getDiscoveredDevices()[0]
                : null;
    }

    public boolean enroll() {
        EnrollResult enrollResult = null;
        for ( int i = 0 ; i < client.getNumberEnrollStages() ; i++ ) {
            System.out.println(i+1 + ": Please slide your finger on the sensor");
            enrollResult = client.enroll();
            while (!EnrollResultCode.PASS.equals(enrollResult.getCode()) && !EnrollResultCode.COMPLETE.equals(enrollResult.getCode())) {
                System.out.println("Try again!");
                System.out.println(i+1 + ": Please slide your finger on the sensor");
                enrollResult = client.enroll();
            }
        }
        if(enrollResult != null && EnrollResultCode.COMPLETE.equals(enrollResult.getCode())) {
            PrintData printData = enrollResult.getPrintData();
            user.setFingerprint(printData);
            System.out.println("Enrolled Successfully!");
            return true;
        }
        return false;

    }

    public boolean verify() {
        try {
            User user = new Json().fromJson();
            PrintData printData = user.getFingerprint();
            if (printData == null)
                throw new JlibFprint.EnrollException();
            System.out.println("Please slide your finger on the sensor to verify");
            VerifyResultCode result = client.verify(printData);
            return VerifyResultCode.MATCH.equals(result);

        } catch (JlibFprint.EnrollException e) {
            System.err.println("Enrollment hasn't been Completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}