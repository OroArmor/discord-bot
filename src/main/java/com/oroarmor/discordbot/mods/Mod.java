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
