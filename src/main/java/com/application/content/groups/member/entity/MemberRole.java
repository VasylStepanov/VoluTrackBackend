package com.application.content.groups.member.entity;

import com.application.content.items.item.model.ItemType;
import lombok.SneakyThrows;

public enum MemberRole {
    MEMBER_ROLE, MODER_ROLE, ADMIN_ROLE;

    @SneakyThrows
    public static MemberRole valueOfChecked(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            throw new RuntimeException("Member role unknown");
        }
    }

    @SneakyThrows
    public static MemberRole valueOfCheckedFull(String value) {
        if (value == null)
            throw new IllegalArgumentException("Member role is null");
        return valueOfChecked(value);
    }
}
