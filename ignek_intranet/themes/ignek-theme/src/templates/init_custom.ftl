<#assign user = themeDisplay.getUser() />
<#assign sign_in_text = languageUtil.get(locale, "sign-in") />
<#assign sign_in_url = htmlUtil.escape(theme_display.getURLSignIn()) />
<#assign user_role = serviceLocator.findService("com.liferay.portal.kernel.service.RoleLocalService") />
<#assign roles = user_role.getUserRoles(user_id) />
<#assign permission_checker = themeDisplay.getPermissionChecker() />
<#assign is_omniadmin = permission_checker.isOmniadmin() />
                