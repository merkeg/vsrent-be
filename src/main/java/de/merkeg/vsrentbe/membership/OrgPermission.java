package de.merkeg.vsrentbe.membership;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrgPermission {

    USER_ADD("user:add", "Add an user to the organisation"),
    USER_DEL("user:delete", "Delete an user from an organisation"),
    USER_ROLE("user:role", "Set an user role"),
    CALENDAR_VIEW("calendar:view", "View calendar events"),
    CALENDAR_BORROW_ADD("calendar.borrow:add", "Add calendar borrow"),
    CALENDAR_BORROW_MODIFY("calendar.borrow:modify", "Modify calendar borrow"),
    CALENDAR_BORROW_DELETE("calendar.borrow:delete", "Delete calendar borrow"),
    ITEM_VIEW("item:list", "List items to org"),
    ITEM_ADD("item:add", "Add Item to org"),
    ITEM_MODIFY("item:modify", "Modify Item in org"),
    ITEM_DELETE("item:delete", "Delete Item from org");

    @Getter
    private final String permission;
    @Getter
    private final String description;
}
