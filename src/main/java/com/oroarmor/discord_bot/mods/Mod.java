package com.oroarmor.discord_bot.mods;

import java.util.Map;

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
}
