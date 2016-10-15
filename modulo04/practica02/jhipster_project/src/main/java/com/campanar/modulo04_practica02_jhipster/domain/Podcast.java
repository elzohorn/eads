package com.campanar.modulo04_practica02_jhipster.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Podcast.
 */
@Entity
@Table(name = "podcast")
public class Podcast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "podcast_author",
               joinColumns = @JoinColumn(name="podcasts_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="authors_id", referencedColumnName="ID"))
    private Set<Person> authors = new HashSet<>();

    @ManyToOne
    private Channel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Podcast title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Podcast description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Person> getAuthors() {
        return authors;
    }

    public Podcast authors(Set<Person> people) {
        this.authors = people;
        return this;
    }

    public Podcast addAuthor(Person person) {
        authors.add(person);
        person.getProducedPodcasts().add(this);
        return this;
    }

    public Podcast removeAuthor(Person person) {
        authors.remove(person);
        person.getProducedPodcasts().remove(this);
        return this;
    }

    public void setAuthors(Set<Person> people) {
        this.authors = people;
    }

    public Channel getChannel() {
        return channel;
    }

    public Podcast channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Podcast podcast = (Podcast) o;
        if(podcast.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, podcast.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Podcast{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
