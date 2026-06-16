# Android Device Names (Automated Fork)

[![Auto-Update & Release](https://github.com/antonio2904/AndroidDeviceNames/actions/workflows/update-devices.yml/badge.svg)](https://github.com/antonio2904/AndroidDeviceNames/actions)
![Maintenance - Automated Monthly](https://img.shields.io/badge/Maintenance-Automated%20(Monthly)-brightgreen?style=flat-square&logo=github-actions)
![Latest Release](https://img.shields.io/github/v/release/antonio2904/AndroidDeviceNames?style=flat-square&color=blue)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://opensource.org/licenses/Apache-2.0)

> 🚀 **100% Automated & Always Up-to-Date:** This is an actively maintained fork of [jaredrummler/AndroidDeviceNames](https://github.com/jaredrummler/AndroidDeviceNames). Unlike other forks that rely on human intervention and eventually get abandoned, this repository uses **GitHub Actions** to automatically fetch new Android devices and publish a new release **every single month**. 

---

On many popular devices, the market name of the device is not natively available via the Android SDK. For example, on a Samsung Galaxy S7, the value of `Build.MODEL` could return strings like `"SAMSUNG-SM-G930A"`, `"SM-G930F"`, `"SM-G930K"`, or `"SM-G930L"`. 

This small library acts as a seamless mapper to turn those cryptic model strings into consumer-friendly market names (e.g., `"Galaxy S7 Edge"`).

---

### 🌟 Why use this fork?

* **Zero Human Dependency:** The update and release cycles are entirely code-driven. Even if the maintainer is offline, your app will get access to newest device names every month.
* **Drop-in Replacement:** No code modifications are required! Just swap out the dependency path in your build files.
* **Reliable Data Pipeline:** Powered by a monthly GitHub Action that syncs directly with the latest official Google Play supported devices list.

---

## 📦 Installation

Add the dependency to your module's `build.gradle` file:

### Modern Gradle (`build.gradle.kts` / `build.gradle`)
If you use **JitPack** to pull this fork, ensure you have the jitpack repository in your settings/root gradle file, then add:

```groovy
dependencies {
    implementation 'io.github.antonio2904:android-device-names:2026.06.00'
}
```
---

## 🛠️ Usage

### 1. Initialize the Library
It is highly recommended to initialize the library once in your `Application` class:

```java
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DeviceName.init(this);
    }
}
```

### 2. Get the Name of the Current Device
You can quickly pull the consumer-friendly name on the UI thread:

```java
// Returns consumer-friendly name or falls back to Build.MODEL if unrecognized
String deviceName = DeviceName.getDeviceName(); 
```

### 3. Get the Name of a Device Using a Codename
```java
// Returns "Moto X Style"
DeviceName.getDeviceName("clark", "Unknown device");
```

### 4. Fetch Complete Device Information (Asynchronous)
The above sync methods match the top popular devices instantly. If you want to check against the full database of over 10,000+ devices natively, utilize the asynchronous loader which lazily loads and caches the payload:

```java
DeviceName.with(context).request(new DeviceName.Callback() {
    @Override 
    public void onFinished(DeviceName.DeviceInfo info, Exception error) {
        if (info != null) {
            String manufacturer = info.manufacturer; // e.g. "Samsung"
            String marketName = info.marketName;     // e.g. "Galaxy S8+"
            String model = info.model;               // e.g. "SM-G955W"
            String codename = info.codename;         // e.g. "dream2qltecan"
            String deviceName = info.getName();      // e.g. "Galaxy S8+"
            
            // Note: You are safely back on the UI thread here
        }
    }
});
```
*This will only make a network request once if the local database needs updating, saving the data to `SharedPreferences` for all future app sessions.*

---

## 🤖 How the Automation Works

Every month, a scheduled GitHub Actions cron job triggers a script that:
1. Scrapes/downloads the latest official Android device mapping list provided by Google.
2. Parses, updates, and optimizes the local project database.
3. Automatically increments the project version, commits the modifications, and creates a clean GitHub Release tag.

This guarantees your app remains fully aware of brand-new flagship and budget devices shortly after they hit the market!

---

## 📄 License

```text
Copyright (C) 2015 Jared Rummler
Copyright (C) 2026 Antonio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
