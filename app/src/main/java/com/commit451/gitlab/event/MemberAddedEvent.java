package com.commit451.gitlab.event;

import com.commit451.gitlab.model.api.Member;

/**
 * Indicates that a user was added
 * Created by Jawn on 9/17/2015.
 */
public class MemberAddedEvent {

    public Member member;
    public MemberAddedEvent(Member member) {
        this.member = member;
    }
}
