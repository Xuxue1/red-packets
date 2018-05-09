package com.xuxue.dapp.red.packetes.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity(name="record")
public class TakeRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="red_id")
    private String redId;

    @Column(name="red_address")
    private String redAddress;

    @Column(name="open_id")
    private String openId;

    @OneToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "open_id", referencedColumnName = "open_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRedId() {
        return redId;
    }

    public void setRedId(String redId) {
        this.redId = redId;
    }

    public String getRedAddress() {
        return redAddress;
    }

    public void setRedAddress(String redAddress) {
        this.redAddress = redAddress;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
