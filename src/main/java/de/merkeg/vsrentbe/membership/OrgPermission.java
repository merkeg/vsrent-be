package de.merkeg.vsrentbe.membership;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrgPermission {

    MEMBER_ADD("member:add", "Add an user to the organisation"),
    MEMBER_DELETE("member:delete", "Delete an user from an organisation"),
    MEMBER_ROLE("member:role", "Set an user role"),
    CALENDAR_VIEW("calendar:view", "View calendar events"),
    CALENDAR_BORROW_ADD("calendar.borrow:add", "Add calendar borrow"),
    CALENDAR_BORROW_MODIFY("calendar.borrow:modify", "Modify calendar borrow"),
    CALENDAR_BORROW_DELETE("calendar.borrow:delete", "Delete calendar borrow"),
    ITEM_VIEW("item:list", "List items to org"),
    ITEM_ADD("item:add", "Add Item to org"),
    ITEM_MODIFY("item:modify", "Modify Item in org"),
    ITEM_DELETE("item:delete", "Delete Item from org"),
    ORG_MODIFY("org:modify", "Modify Organisation"),
    ORG_DELETE("org:delete", "Delete Organisation");


    @Getter
    private final String permission;
    @Getter
    private final String description;
}
