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
    INVENTORY_VIEW("inventory:view", "View org inventory"),
    INVENTORY_ADD("inventory:add", "Add Item to inventory"),
    INVENTORY_MODIFY("inventory:modify", "Modify Item in inventory"),
    INVENTORY_DELETE("inventory:delete", "Delete Item from inventory");

    @Getter
    private final String permission;
    @Getter
    private final String description;
}