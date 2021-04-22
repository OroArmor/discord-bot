/*
 * MIT License
 *
 * Copyright (c) 2021 OroArmor (Eli Orona)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.oroarmor.discordbot.mods;

import java.util.Map;
import java.util.stream.Collectors;

public class Mod {
    private final String name;
    private final String id;
    private final String alias;
    private final String description;
    private final Map<String, String> links;
    private final String extendedDescription;

    public Mod(String name, String id, String alias, String description, Map<String, String> links, String extendedDescription) {
        this.name = name;
        this.id = id;
        this.alias = alias;
        this.description = description;
        this.links = links;
        this.extendedDescription = extendedDescription;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public String getExtendedDescription() {
        return extendedDescription;
    }

    public String asJsonString() {
        String json = "{";

        json += "\"name\": \"" + name + "\",";
        json += "\"id\": \"" + id + "\",";
        json += "\"description\": \"" + description + "\",";
        json += "\"extendedDescription\": \"" + extendedDescription + "\",";
        json += "\"links\": {" + links.entrySet().stream().map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"").collect(Collectors.joining(",")) + "}";

        json += "}";
        return json;
    }

    public static class Builder {
        private String name;
        private String id;
        private String alias;
        private String description;
        private Map<String, String> links;
        private String extendedDescription;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        public Builder setExtendedDescription(String extendedDescription) {
            this.extendedDescription = extendedDescription;
            return this;
        }

        public Mod build() {
            return new Mod(name, id, alias, description, links, extendedDescription);
        }
    }
}
