package de.merkeg.vsrentbe.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@RequiredArgsConstructor
@FieldNameConstants(innerTypeName = "permission")
public enum Permission {

    ADMIN_USER_STATE_MODIFY("admin:user.state:modify", "Set user active state (enable or disable user)"),
    ADMIN_USER_CONTACT_MODIFY("admin:user.contact:modify", "Modify user's personal information"),
    ADMIN_USER_PASSWORD_MODIFY("admin:user.password:modify", "Change user's password"),
    ADMIN_USER_DELETE("admin:user:delete", "Delete a user"),
    ADMIN_USER_LIST("admin:user:list", "List all users"),

    ADMIN_ORG_MANAGE("admin:org:manage", "Manage every aspect of an organisation (including their items)"),
    ORG_CREATE("org:create", "Create a organisation"),
    ORG_DELETE("org:delete", "Delete an organisation"),
    ORG_LIST("org:list", "List Organisations"),
    ORG_INFO("org:info", "Get Organisation info"),
    ITEM_LIST("item:list", "List Items"),
    SELF_AUTH_INFO("self:auth:info", "Get auth info"),
    MEDIA_UPLOAD("media:upload", "Media upload permissions"),
    MEDIA_DELETE("media:delete", "(own) Media deletion permissions"),
    ADMIN_MEDIA_DELETE("admin:media:delete", "Media deletion permissions");

    @Getter
    private final String permission;
    @Getter
    private final String description;
}
