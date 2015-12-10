/*
 * Copyright (c) 2011 James Smith <james@loopj.com>
 * Copyright (c) 2015 Fran Montiel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pam.codenamehippie.http;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpCookie;

/**
 * Based on the code from this stackoverflow answer http://stackoverflow.com/a/25462286/980387 by
 * janoliver
 * Modifications in the structure of the class and addition of serialization of httpOnly attribute
 * <p/>
 * Gracieuseté de https://gist.github.com/franmontiel/ed12a2295566b7076161
 */

public class SerializableHttpCookie implements Serializable {

    private static final String TAG = SerializableHttpCookie.class
            .getSimpleName();

    private static final long serialVersionUID = 6374381323722046732L;

    private transient HttpCookie cookie;

    // Workaround httpOnly: The httpOnly attribute is not accessible so when we
    // serialize and deserialize the cookie it not preserve the same value. We
    // need to access it using reflection
    private Field fieldHttpOnly;

    public String encode(HttpCookie cookie) {
        this.cookie = cookie;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(this);
        } catch (IOException e) {
            Log.d(TAG, "IOException in encodeCookie", e);
            return null;
        }

        return this.byteArrayToHexString(os.toByteArray());
    }

    public HttpCookie decode(String encodedCookie) {
        byte[] bytes = this.hexStringToByteArray(encodedCookie);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                bytes);
        HttpCookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            cookie = ((SerializableHttpCookie) objectInputStream.readObject()).cookie;
        } catch (IOException e) {
            Log.d(TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "ClassNotFoundException in decodeCookie", e);
        }

        return cookie;
    }

    // Workaround httpOnly (getter)
    private boolean getHttpOnly() {
        try {
            this.initFieldHttpOnly();
            return (boolean) this.fieldHttpOnly.get(this.cookie);
        } catch (Exception ignored) {
            // NoSuchFieldException || IllegalAccessException ||
            // IllegalArgumentException
            // Log.w(TAG, e);
        }
        return false;
    }

    // Workaround httpOnly (setter)
    private void setHttpOnly(boolean httpOnly) {
        try {
            this.initFieldHttpOnly();
            this.fieldHttpOnly.set(this.cookie, httpOnly);
        } catch (Exception e) {
            // NoSuchFieldException || IllegalAccessException ||
            // IllegalArgumentException
            Log.w(TAG, e);
        }
    }

    private void initFieldHttpOnly() throws NoSuchFieldException {
        this.fieldHttpOnly = this.cookie.getClass().getDeclaredField("httpOnly");
        this.fieldHttpOnly.setAccessible(true);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this.cookie.getName());
        out.writeObject(this.cookie.getValue());
        out.writeObject(this.cookie.getComment());
        out.writeObject(this.cookie.getCommentURL());
        out.writeObject(this.cookie.getDomain());
        out.writeLong(this.cookie.getMaxAge());
        out.writeObject(this.cookie.getPath());
        out.writeObject(this.cookie.getPortlist());
        out.writeInt(this.cookie.getVersion());
        out.writeBoolean(this.cookie.getSecure());
        out.writeBoolean(this.cookie.getDiscard());
        out.writeBoolean(this.getHttpOnly());
    }

    private void readObject(ObjectInputStream in) throws IOException,
                                                         ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        this.cookie = new HttpCookie(name, value);
        this.cookie.setComment((String) in.readObject());
        this.cookie.setCommentURL((String) in.readObject());
        this.cookie.setDomain((String) in.readObject());
        this.cookie.setMaxAge(in.readLong());
        this.cookie.setPath((String) in.readObject());
        this.cookie.setPortlist((String) in.readObject());
        this.cookie.setVersion(in.readInt());
        this.cookie.setSecure(in.readBoolean());
        this.cookie.setDiscard(in.readBoolean());
        this.setHttpOnly(in.readBoolean());
    }

    /**
     * Using some super basic byte array &lt;-&gt; hex conversions so we don't
     * have to rely on any large Base64 libraries. Can be overridden if you
     * like!
     *
     * @param bytes
     *         byte array to be converted
     *
     * @return string containing hex values
     */
    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    /**
     * Converts hex values from strings to byte array
     *
     * @param hexString
     *         string of hex-encoded values
     *
     * @return decoded byte array
     */
    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}