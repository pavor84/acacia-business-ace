/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.test;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "hello_world")
@NamedQueries({@NamedQuery(name = "HelloWorld.findByHelloWorldId", query =
                                                                   "SELECT h FROM HelloWorld h WHERE h.helloWorldId = :helloWorldId"),
               @NamedQuery(name = "HelloWorld.findByHelloWorld", query =
                                                                 "SELECT h FROM HelloWorld h WHERE h.helloWorld = :helloWorld")})
public class HelloWorld implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "hello_world_id", nullable = false)
    private Long helloWorldId;
    @Column(name = "hello_world")
    private String helloWorld;

    public HelloWorld() {
    }

    public HelloWorld(Long helloWorldId) {
        this.helloWorldId = helloWorldId;
    }

    public Long getHelloWorldId() {
        return helloWorldId;
    }

    public void setHelloWorldId(Long helloWorldId) {
        this.helloWorldId = helloWorldId;
    }

    public String getHelloWorld() {
        return helloWorld;
    }

    public void setHelloWorld(String helloWorld) {
        this.helloWorld = helloWorld;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (helloWorldId != null ? helloWorldId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HelloWorld)) {
            return false;
        }
        HelloWorld other = (HelloWorld) object;
        if ((this.helloWorldId == null && other.helloWorldId != null) ||
            (this.helloWorldId != null &&
             !this.helloWorldId.equals(other.helloWorldId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.HelloWorld[helloWorldId=" +
               helloWorldId + "]";
    }

}
