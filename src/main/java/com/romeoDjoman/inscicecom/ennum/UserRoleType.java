package com.romeoDjoman.inscicecom.ennum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum UserRoleType {

    USER(
            Set.of(
                    PermissionType.USER_SEARCH_PUBLICATIONS,
                    PermissionType.USER_FILTER_PUBLICATIONS,
                    PermissionType.USER_VIEW_LATEST_PUBLICATIONS,
                    PermissionType.USER_VIEW_TOP_SALES,
                    PermissionType.USER_VIEW_TOP_RATED,
                    PermissionType.USER_VIEW_PUBLICATION_DETAIL,
                    PermissionType.USER_AUTHENTICATE,
                    PermissionType.USER_REGISTER
            )
    ),
    CUSTOMER(
            Set.of(
                    PermissionType.CUSTOMER_ADD_TO_CART,
                    PermissionType.CUSTOMER_VIEW_CART,
                    PermissionType.CUSTOMER_REMOVE_FROM_CART,
                    PermissionType.CUSTOMER_CLEAR_CART,
                    PermissionType.CUSTOMER_PROCESS_PAYMENT,
                    PermissionType.CUSTOMER_MANAGE_PROFILE,
                    PermissionType.CUSTOMER_VIEW_ORDER_HISTORY,
                    PermissionType.CUSTOMER_VIEW_SUBSCRIPTION,
                    PermissionType.CUSTOMER_CANCEL_SUBSCRIPTION,
                    PermissionType.CUSTOMER_RENEW_SUBSCRIPTION,
                    PermissionType.CUSTOMER_VIEW_ORDER_DETAIL
            )
    ),
    CONTRIBUTOR(
            Set.of(
                    PermissionType.CONTRIBUTOR_VIEW_PENDING_CONTRIBUTIONS
            )
    ),
    PUBLISHER(
            Set.of(
                    PermissionType.PUBLISHER_CREATE_PUBLICATION,
                    PermissionType.PUBLISHER_VIEW_SUBMITTED_PUBLICATIONS,
                    PermissionType.PUBLISHER_VIEW_VALIDATED_PUBLICATIONS,
                    PermissionType.PUBLISHER_VIEW_CONTRIBUTOR_RESPONSES,
                    PermissionType.PUBLISHER_COMMUNICATE_WITH_CONTRIBUTORS,
                    PermissionType.PUBLISHER_VIEW_SUBMITTED,
                    PermissionType.PUBLISHER_VIEW_NON_SUBMITTED,
                    PermissionType.PUBLISHER_VIEW_VALIDATED,
                    PermissionType.PUBLISHER_REVISE_PUBLICATION,
                    PermissionType.PUBLISHER_SUBMIT_FOR_REVIEW,
                    PermissionType.PUBLISHER_SUBMIT_FINAL
            )
    ),
    ADMINISTRATOR(
            Set.of(
                    PermissionType.ADMINISTRATOR_VIEW_SUBMITTED_PUBLICATIONS,
                    PermissionType.ADMINISTRATOR_VIEW_PUBLICATION_DETAIL,
                    PermissionType.ADMINISTRATOR_MANAGE_CUSTOMERS,
                    PermissionType.ADMINISTRATOR_CREATE_CONTRIBUTOR,
                    PermissionType.ADMINISTRATOR_UPDATE_CONTRIBUTOR,
                    PermissionType.ADMINISTRATOR_DEACTIVATE_CONTRIBUTOR
            )
    );

    @Getter
    Set<PermissionType> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<SimpleGrantedAuthority> grantedAuthorities = this.getPermissions().stream().map(
                permission -> new SimpleGrantedAuthority(permission.name())
        ).collect(Collectors.toList());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
