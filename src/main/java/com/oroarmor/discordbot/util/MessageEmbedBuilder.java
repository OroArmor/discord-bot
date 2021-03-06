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

package com.oroarmor.discordbot.util;

import java.time.OffsetDateTime;
import java.util.List;

import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class MessageEmbedBuilder {
    private String url;
    private String title;
    private String description;
    private EmbedType type;
    private OffsetDateTime timestamp;
    private int color;
    private MessageEmbed.Thumbnail thumbnail;
    private MessageEmbed.Provider siteProvider;
    private MessageEmbed.AuthorInfo author;
    private MessageEmbed.VideoInfo videoInfo;
    private MessageEmbed.Footer footer;
    private MessageEmbed.ImageInfo image;
    private List<MessageEmbed.Field> fields;

    public MessageEmbedBuilder() {
    }

    public MessageEmbedBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public MessageEmbedBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MessageEmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MessageEmbedBuilder setType(EmbedType type) {
        this.type = type;
        return this;
    }

    public MessageEmbedBuilder setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MessageEmbedBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    public MessageEmbedBuilder setThumbnail(MessageEmbed.Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public MessageEmbedBuilder setSiteProvider(MessageEmbed.Provider siteProvider) {
        this.siteProvider = siteProvider;
        return this;
    }

    public MessageEmbedBuilder setAuthor(MessageEmbed.AuthorInfo author) {
        this.author = author;
        return this;
    }

    public MessageEmbedBuilder setVideoInfo(MessageEmbed.VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
        return this;
    }

    public MessageEmbedBuilder setFooter(MessageEmbed.Footer footer) {
        this.footer = footer;
        return this;
    }

    public MessageEmbedBuilder setImage(MessageEmbed.ImageInfo image) {
        this.image = image;
        return this;
    }

    public MessageEmbedBuilder setFields(List<MessageEmbed.Field> fields) {
        this.fields = fields;
        return this;
    }

    public MessageEmbed build() {
        return new MessageEmbed(url, title, description, type, timestamp, color, thumbnail, siteProvider, author, videoInfo, footer, image, fields);
    }
}
