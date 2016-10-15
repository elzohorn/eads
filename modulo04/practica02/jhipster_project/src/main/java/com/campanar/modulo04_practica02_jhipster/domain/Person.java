package com.campanar.modulo04_practica02_jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private Set<Podcast> producedPodcasts = new HashSet<>();

    @OneToOne(mappedBy = "subscriber")
    @JsonIgnore
    private Subscription subscribedPodcast;

    @ManyToMany(mappedBy = "hosts")
    @JsonIgnore
    private Set<Channel> channels = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Podcast> getProducedPodcasts() {
        return producedPodcasts;
    }

    public Person producedPodcasts(Set<Podcast> podcasts) {
        this.producedPodcasts = podcasts;
        return this;
    }

    public Person addProducedPodcast(Podcast podcast) {
        producedPodcasts.add(podcast);
        podcast.getAuthors().add(this);
        return this;
    }

    public Person removeProducedPodcast(Podcast podcast) {
        producedPodcasts.remove(podcast);
        podcast.getAuthors().remove(this);
        return this;
    }

    public void setProducedPodcasts(Set<Podcast> podcasts) {
        this.producedPodcasts = podcasts;
    }

    public Subscription getSubscribedPodcast() {
        return subscribedPodcast;
    }

    public Person subscribedPodcast(Subscription subscription) {
        this.subscribedPodcast = subscription;
        return this;
    }

    public void setSubscribedPodcast(Subscription subscription) {
        this.subscribedPodcast = subscription;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public Person channels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public Person addChannel(Channel channel) {
        channels.add(channel);
        channel.getHosts().add(this);
        return this;
    }

    public Person removeChannel(Channel channel) {
        channels.remove(channel);
        channel.getHosts().remove(this);
        return this;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if(person.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
