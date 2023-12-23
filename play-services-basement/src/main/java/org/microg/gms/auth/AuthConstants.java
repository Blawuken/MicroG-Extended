/*
 * Copyright (C) 2013-2017 microG Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.microg.gms.auth;

import com.google.android.gms.common.BuildConfig;

public class AuthConstants {
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final String SCOPE_GET_ACCOUNT_ID = "^^_account_id_^^";
    public static final String PROVIDER_METHOD_GET_ACCOUNTS = "get_accounts";
    public static final String PROVIDER_METHOD_CLEAR_PASSWORD = "clear_password";
    public static final String PROVIDER_EXTRA_CLEAR_PASSWORD = "clear_password";
    public static final String PROVIDER_EXTRA_ACCOUNTS = "accounts";
    public static final String DEFAULT_ACCOUNT_TYPE = BuildConfig.BASE_PACKAGE_NAME;
}
