package com.campanar.modulo04_practica02_jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Channel.
 */
@Entity
@Table(name = "channel")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "channel_host",
               joinColumns = @JoinColumn(name="channels_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="hosts_id", referencedColumnName="ID"))
    private Set<Person> hosts = new HashSet<>();

    @OneToMany(mappedBy = "channel")
    @JsonIgnore
    private Set<Podcast> podcasts = new HashSet<>();

    @ManyToMany(mappedBy = "channels")
    @JsonIgnore
    private Set<Subscription> subscriptions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Channel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Channel description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Person> getHosts() {
        return hosts;
    }

    public Channel hosts(Set<Person> people) {
        this.hosts = people;
        return this;
    }

    public Channel addHost(Person person) {
        hosts.add(person);
        person.getChannels().add(this);
        return this;
    }

    public Channel removeHost(Person person) {
        hosts.remove(person);
        person.getChannels().remove(this);
        return this;
    }

    public void setHosts(Set<Person> people) {
        this.hosts = people;
    }

    public Set<Podcast> getPodcasts() {
        return podcasts;
    }

    public Channel podcasts(Set<Podcast> podcasts) {
        this.podcasts = podcasts;
        return this;
    }

    public Channel addPodcast(Podcast podcast) {
        podcasts.add(podcast);
        podcast.setChannel(this);
        return this;
    }

    public Channel removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
        podcast.setChannel(null);
        return this;
    }

    public void setPodcasts(Set<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public Channel subscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
        return this;
    }

    public Channel addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        subscription.getChannels().add(this);
        return this;
    }

    public Channel removeSubscription(Subscription subscription) {
        subscriptions.remove(subscription);
        subscription.getChannels().remove(this);
        return this;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Channel channel = (Channel) o;
        if(channel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, channel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Channel{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
