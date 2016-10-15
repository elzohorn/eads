package com.campanar.modulo04_practica02_jhipster.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Subscription.
 */
@Entity
@Table(name = "subscription")
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Person subscriber;

    @ManyToMany
    @JoinTable(name = "subscription_channel",
               joinColumns = @JoinColumn(name="subscriptions_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="channels_id", referencedColumnName="ID"))
    private Set<Channel> channels = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getSubscriber() {
        return subscriber;
    }

    public Subscription subscriber(Person person) {
        this.subscriber = person;
        return this;
    }

    public void setSubscriber(Person person) {
        this.subscriber = person;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public Subscription channels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public Subscription addChannel(Channel channel) {
        channels.add(channel);
        channel.getSubscriptions().add(this);
        return this;
    }

    public Subscription removeChannel(Channel channel) {
        channels.remove(channel);
        channel.getSubscriptions().remove(this);
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
        Subscription subscription = (Subscription) o;
        if(subscription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subscription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subscription{" +
            "id=" + id +
            '}';
    }
}
