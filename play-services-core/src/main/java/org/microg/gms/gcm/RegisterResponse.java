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

package org.microg.gms.gcm;

import org.microg.gms.common.HttpFormClient.ResponseHeader;
import org.microg.gms.common.HttpFormClient.ResponseStatusText;

import static org.microg.gms.common.HttpFormClient.ResponseField;

public class RegisterResponse {
    @ResponseField("token")
    public String token;
    @ResponseHeader("Retry-After")
    public String retryAfter;
    @ResponseField("deleted")
    public String deleted;
    @ResponseStatusText
    public String responseText;

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "token='" + token + '\'' +
                ", retryAfter='" + retryAfter + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }
}
