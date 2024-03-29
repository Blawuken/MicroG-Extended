/*
 * SPDX-FileCopyrightText: 2023 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.CertData;
import com.google.android.gms.common.internal.ICertData;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableCreatorAndWriter;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import org.microg.gms.common.Hide;

@Hide
@SafeParcelable.Class
public class GoogleCertificatesQuery extends AbstractSafeParcelable {
    @Field(value = 1, getterName = "getCallingPackage")
    String callingPackage;
    @Field(2)
    IBinder certDataBinder;
    private CertData certData;
    @Field(3)
    boolean allowTestKeys;
    @Field(4)
    boolean ignoreTestKeysOverride;

    public String getCallingPackage() {
        return callingPackage;
    }

    public CertData getCertData() {
        if (certData == null && certDataBinder != null) {
            certData = CertData.unwrap(certDataBinder);
        }
        return certData;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        CREATOR.writeToParcel(this, dest, flags);
    }

    public static final SafeParcelableCreatorAndWriter<GoogleCertificatesQuery> CREATOR = findCreator(GoogleCertificatesQuery.class);
}
