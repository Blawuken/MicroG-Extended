# MicroG Extended

[GmsCore](https://github.com/microg/GmsCore) fork designed for ReVanced apps with beautiful design

[![Builder APK](https://github.com/Blawuken/MicroG-Extended/actions/workflows/build.yml/badge.svg)](https://github.com/Blawuken/MicroG-Extended/actions/workflows/build.yml) [![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=flat-square)](https://www.android.com) [![API](https://img.shields.io/badge/API-23%2B-orange.svg?logo=android&style=flat-square)](https://developer.android.com/studio/releases/platforms) [![Github All Releases](https://img.shields.io/github/downloads/Blawuken/MicroG-Extended/total.svg)](https://github.com/Blawuken/MicroG-Extended/releases) [![Github All Releases](https://img.shields.io/github/release/Blawuken/MicroG-Extended.svg)](https://github.com/Blawuken/MicroG-Extended/releases)

![alt text](https://raw.githubusercontent.com/Blawuken/MicroG-Extended/main/Images/extended.png)

microG GmsCore is a FLOSS (Free/Libre Open Source Software) framework to allow applications designed for Google Play Services to run on systems, where Play Services is not available.

This fork tweaks MicroG to be usable by applications that require Google authentication such as Vanced.

## Notable changes

- No longer a system app
- Package name changed from `com.google.android.gms` to `com.mgoogle.android.gms` to support installation alongside the official MicroG
- Removed unnecessary features:
  - Ads
  - Analytics
  - Car
  - Droidguard
  - Exposure-Notifications
  - Feedback
  - Firebase
  - Games
  - Maps
  - Recovery
  - Registering app permissions
  - SafetyNet
  - Self-Check
  - Search
  - TapAndPay
  - Wallet
  - Wear-Api
- Removed all permissions, as none are required for Google authentication

## Credits

- Source Code for MicroG 0.2.25.224113 was provided by [@OxrxL](https://github.com/OxrxL)
- [@shadow578](https://github.com/shadow578)'s commit used to apply ReVanced's `SPOOFED_PACKAGE_SIGNATURE`