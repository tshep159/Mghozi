/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Tshepo
 */

@Entity
public class Place implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "place_id")
	private Long id;

	@Column(name = "name")
	private String name;
        @Column(name = "province")
	private String province;

	@OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE)
	private Collection<Post> posts;

    public Place() {
    }

    public Place(Long id, String name, String province, Collection<Post> posts) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.posts = posts;
    }

        
    
        
        
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}

}
