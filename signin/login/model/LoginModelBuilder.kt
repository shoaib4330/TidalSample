package com.careem.mt.adma.ui.signin.login.model

class LoginModelBuilder {
    private var driverPhone: String? = null
    private var pinCode: String? = null
    private var admaDeviceId: String? = null
    private var pushyDeviceId: String? = null
    private var admaVersion: String? = null
    private var admaVersionNumber: Int? = null
    private var deviceImei: String? = null
    private var simId: String? = null
    private var googlePlayServicesVersion: Int? = null
    private var deviceSoftwareVersion: String? = null
    private var lineNumber: String? = null
    private var networkCountryIso: String? = null
    private var networkOperator: String? = null
    private var networkOperatorName: String? = null
    private var networkType: String? = null
    private var simCountryIso: String? = null
    private var simOperator: String? = null
    private var simOperatorName: String? = null
    private var simSerialNumber: String? = null
    private var simState: String? = null
    private var deviceBoard: String? = null
    private var bootLoader: String? = null
    private var deviceBrand: String? = null
    private var device: String? = null
    private var display: String? = null
    private var fingerPrint: String? = null
    private var hardware: String? = null
    private var manufacturer: String? = null
    private var model: String? = null
    private var product: String? = null
    private var serial: String? = null
    private var buildRelease: String? = null
    private var sdkInt: Int = 0
    private var lastLoggedInDriverId: Int? = null
    private var pushyEnterpriseDeviceId: String? = null

    fun setDriverPhone(driverPhone: String): LoginModelBuilder {
        this.driverPhone = driverPhone
        return this
    }

    fun setPinCode(pinCode: String): LoginModelBuilder {
        this.pinCode = pinCode
        return this
    }

    fun setAdmaDeviceId(admaDeviceId: String): LoginModelBuilder {
        this.admaDeviceId = admaDeviceId
        return this
    }

    fun setPushyDeviceId(pushyDeviceId: String): LoginModelBuilder {
        this.pushyDeviceId = pushyDeviceId
        return this
    }

    fun setAdmaVersion(admaVersion: String): LoginModelBuilder {
        this.admaVersion = admaVersion
        return this
    }

    fun setAdmaVersionNumber(admaVersionNumber: Int?): LoginModelBuilder {
        this.admaVersionNumber = admaVersionNumber
        return this
    }

    fun setDeviceImei(deviceImei: String): LoginModelBuilder {
        this.deviceImei = deviceImei
        return this
    }

    fun setSimId(simId: String): LoginModelBuilder {
        this.simId = simId
        return this
    }

    fun setGooglePlayServicesVersion(googlePlayServicesVersion: Int?): LoginModelBuilder {
        this.googlePlayServicesVersion = googlePlayServicesVersion
        return this
    }

    fun setDeviceSoftwareVersion(deviceSoftwareVersion: String): LoginModelBuilder {
        this.deviceSoftwareVersion = deviceSoftwareVersion
        return this
    }

    fun setLineNumber(lineNumber: String): LoginModelBuilder {
        this.lineNumber = lineNumber
        return this
    }

    fun setNetworkCountryIso(networkCountryIso: String): LoginModelBuilder {
        this.networkCountryIso = networkCountryIso
        return this
    }

    fun setNetworkOperator(networkOperator: String): LoginModelBuilder {
        this.networkOperator = networkOperator
        return this
    }

    fun setNetworkOperatorName(networkOperatorName: String): LoginModelBuilder {
        this.networkOperatorName = networkOperatorName
        return this
    }

    fun setNetworkType(networkType: String): LoginModelBuilder {
        this.networkType = networkType
        return this
    }

    fun setSimCountryIso(simCountryIso: String): LoginModelBuilder {
        this.simCountryIso = simCountryIso
        return this
    }

    fun setSimOperator(simOperator: String): LoginModelBuilder {
        this.simOperator = simOperator
        return this
    }

    fun setSimOperatorName(simOperatorName: String): LoginModelBuilder {
        this.simOperatorName = simOperatorName
        return this
    }

    fun setSimSerialNumber(simSerialNumber: String): LoginModelBuilder {
        this.simSerialNumber = simSerialNumber
        return this
    }

    fun setSimState(simState: String): LoginModelBuilder {
        this.simState = simState
        return this
    }

    fun setDeviceBoard(deviceBoard: String): LoginModelBuilder {
        this.deviceBoard = deviceBoard
        return this
    }

    fun setBootLoader(bootLoader: String): LoginModelBuilder {
        this.bootLoader = bootLoader
        return this
    }

    fun setDeviceBrand(deviceBrand: String): LoginModelBuilder {
        this.deviceBrand = deviceBrand
        return this
    }

    fun setDevice(device: String): LoginModelBuilder {
        this.device = device
        return this
    }

    fun setDisplay(display: String): LoginModelBuilder {
        this.display = display
        return this
    }

    fun setFingerPrint(fingerPrint: String): LoginModelBuilder {
        this.fingerPrint = fingerPrint
        return this
    }

    fun setHardware(hardware: String): LoginModelBuilder {
        this.hardware = hardware
        return this
    }

    fun setManufacturer(manufacturer: String): LoginModelBuilder {
        this.manufacturer = manufacturer
        return this
    }

    fun setModel(model: String): LoginModelBuilder {
        this.model = model
        return this
    }

    fun setProduct(product: String): LoginModelBuilder {
        this.product = product
        return this
    }

    fun setSerial(serial: String): LoginModelBuilder {
        this.serial = serial
        return this
    }

    fun setBuildRelease(buildRelease: String): LoginModelBuilder {
        this.buildRelease = buildRelease
        return this
    }

    fun setSdkInt(sdkInt: Int): LoginModelBuilder {
        this.sdkInt = sdkInt
        return this
    }

    fun setLastLoggedInDriverId(lastLoggedInDriverId: Int?): LoginModelBuilder {
        this.lastLoggedInDriverId = lastLoggedInDriverId
        return this
    }

    fun setPushyEnterpriseDeviceId(pushyEnterpriseDeviceId: String): LoginModelBuilder {
        this.pushyEnterpriseDeviceId = pushyEnterpriseDeviceId
        return this
    }

    fun create(): LoginModel {
        return LoginModel(admaDeviceId, admaVersion, admaVersionNumber, bootLoader,
            buildRelease, device, deviceBoard, deviceBrand, deviceImei, deviceSoftwareVersion,
            display, driverPhone, fingerPrint, googlePlayServicesVersion, hardware, lastLoggedInDriverId,
            manufacturer, model, networkCountryIso, networkOperator, networkOperatorName, networkType,
            pinCode, product, pushyDeviceId, pushyEnterpriseDeviceId, sdkInt, serial, simCountryIso,
            simId, simOperator, simOperatorName, simState)
    }
}
